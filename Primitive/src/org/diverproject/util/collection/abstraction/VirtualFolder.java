package org.diverproject.util.collection.abstraction;

import java.util.Iterator;

import org.diverproject.util.FileUtil;
import org.diverproject.util.ObjectDescription;
import org.diverproject.util.collection.Folder;
import org.diverproject.util.collection.FolderElement;
import org.diverproject.util.collection.List;
import org.diverproject.util.collection.Map;

/**
 * <p><h1>Pasta Virtual</h1></p>
 *
 * <p>Essa pasta virtual vai funcionar da seguinte forma, é composta por um nome, arquivos e outras pastas virtuais.
 * Como uma pasta física em disco, ela pode possuir tanto arquivos como pastas dentro dela, e o seu nome.
 * Utilizar isso, apesar de uma adição e remoção mais demorada que outras estruturas a busca é altamente boa.</p>
 *
 * <p>Utiliza uma tabela espalhada para armazenamento dos arquivos de modo que permita uma fácil identificação.
 * Quando maior o número de arquivos com o mesmo inicio no nome maior o tempo de procura usando essa estrutura.
 * No caso das pastas é utilizado uma lista dinâmica, quanto a performance depende do número de pastas.
 * Caso o nome da pasta seja de ordem alfabética menor será mais rápido caso contrário mais demorado.</p>
 *
 * <p>Quanto algumas características exclusivas da estrutura, possui sub-métodos com pré-fixo <code>sub</code>.
 * São chamados pelos métodos principais quando uma ação é executada com êxito, afim de permitir um <i>listener</i>.
 * Como por exemplo, ao adicionar um arquivo a pasta uma ação deve ser executada, ou quando ele é removido.</p>
 *
 * <p>Como toda pasta, esta também possui uma pasta raíz, a pasta no qual ela está alocada.
 * Se não for definido nenhuma pasta raíz para ela, será considerada que ela é a pasta raíz principal.
 * Ou seja, todo arquivo ou pasta estará alocado dentro dela, em quanto ela, não está alocado em ninguém.
 * Internamente é usado apenas para garantir a limpeza de pastas vazias ou lista de pastas vazias.</p>
 *
 * @see Folder
 * @see FolderElement
 * @see AbstractCollection
 *
 * @author Andrew Mello
 *
 * @param <E> tipo de elemento do qual será armazenado.
 */

public class VirtualFolder<E> extends AbstractCollection<E> implements Folder<E>
{
	/**
	 * Nome da pasta virtual.
	 */
	private String name;

	/**
	 * Pasta raíz, onde ela está alocada.
	 */
	private VirtualFolder<E> parent;

	/**
	 * Mapeamento dos arquivos alocados.
	 */
	protected Map<String, E> files;

	/**
	 * Lista de pastas armazenadas.
	 */
	protected List<VirtualFolder<E>> folders;

	/**
	 * Constrói uma nova pasta virtual, para essa pasta virtual será considerada como raíz.
	 * Para esse caso, ela não estará alocado a nenhuma outra pasta.
	 * @param name nome do qual será dado a pasta, para validar diretórios.
	 */

	public VirtualFolder(String name)
	{
		this(name, null);
	}

	/**
	 * Constrói uma nova pasta virtual, considera que ela está sendo criada a partir de outra.
	 * @param name nome do qual será dado a pasta, para validar diretórios.
	 * @param parent qual é a pasta do qual originou essa.
	 */

	public VirtualFolder(String name, VirtualFolder<E> parent)
	{
		this.name = name;
		this.parent = parent;
	}

	@Override
	public void clear()
	{
		if (folders != null)
		{
			for (VirtualFolder<E> folder : folders)
				folder.clear();

			folders = null;
		}

		if (files != null)
		{
			for (E file : files)
				subClear(file);

			files = null;
		}
	}

	@Override
	public int size()
	{
		int size = 0;

		if (files != null)
			size += files.size();

		if (folders != null)
			for (VirtualFolder<E> folder : folders)
				size += folder.size();

		return size;
	}

	@Override
	public int length()
	{
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean contains(E file)
	{
		if (file instanceof FolderElement)
		{
			FolderElement element = (FolderElement) file;
			String filepath = element.getFilePath();
			Path path = new Path(filepath);

			return contains(path);
		}

		return false;
	}

	@Override
	public boolean contains(String path)
	{
		return contains(new Path(path));
	}

	/**
	 * Chamado internamente para verificar se essa pasta contém um determinado caminho.
	 * @param path caminho do arquivo do qual deseja verificar se existe na pasta.
	 * @return true se encontrar o caminho ou false caso contrário.
	 */

	private boolean contains(Path path)
	{
		if (!name.equals(path.enter()))
			return false;

		if (path.isFile())
		{
			if (files == null)
				return false;

			return files.containsKey(path.name());
		}

		if (folders != null)
			for (VirtualFolder<E> folder : folders)
				if (folder.name.equals(path.name()))
					return folder.contains(path);

		return false;
	}

	@Override
	public boolean add(E element)
	{
		if (element instanceof FolderElement)
		{
			FolderElement file = (FolderElement) element;
			String filepath = file.getFilePath();
			Path path = new Path(filepath);

			return add(file, path);
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	private boolean add(FolderElement file, Path path)
	{
		if (!name.equals(path.enter()))
			return false;

		if (path.isDirectory())
		{
			if (folders == null)
			{
				VirtualFolder<E> folder = new VirtualFolder<E>(path.name(), this);

				folders = new DynamicList<VirtualFolder<E>>();
				folders.add(folder);

				return folder.add(file, path);
			}

			for (VirtualFolder<E> folder : folders)
				if (folder.name.equals(path.name()))
					return folder.add(file, path);

			VirtualFolder<E> folder = new VirtualFolder<E>(path.name(), this);
			folders.add(folder);

			return folder.add(file, path);
		}

		if (files == null)
			files = new StringMap<E>();

		if (files.containsKey(path.name()))
			return false;

		files.add(path.name(), (E) file);
		subAdd((E) file);

		return true;
	}

	@Override
	public boolean remove(String path)
	{
		return remove(new Path(path));
	}

	/**
	 * Chamado internamente para remover um arquivo a partir de um caminho especificado.
	 * @param path caminho do arquivo do qual deseja remover dessa pasta.
	 * @return true se encontrar e remover ou false caso contrário.
	 */

	private boolean remove(Path path)
	{
		if (!name.equals(path.enter()))
			return false;

		if (path.isFile())
		{
			if (files == null)
				return false;

			E file = files.get(path.name());
			files.removeKey(path.name());

			subRemove(file);
			subRemoveClear(this);

			return true;
		}

		if (folders != null)
			for (VirtualFolder<E> folder : folders)
				if (folder.name.equals(path.name()))
					return folder.remove(path);

		return false;
	}

	@Override
	public E get(String path)
	{
		return get(new Path(path));
	}

	/**
	 * Chamado internamente para obter um arquivo a partir de um caminho especificado.
	 * @param path caminho do arquivo do qual deseja obter dessa pasta.
	 * @return referência do arquivo se encontrar ou null caso contrário.
	 */

	private E get(Path path)
	{
		if (!name.equals(path.enter()))
			return null;

		if (path.isFile())
		{
			if (files == null)
				return null;

			return files.get(path.name());
		}

		if (folders != null)
			for (VirtualFolder<E> folder : folders)
				if (folder.name.equals(path.name()))
				{
					E e = folder.get(path.name());

					if (e != null)
						return e;
				}

		return null;
	}

	@Override
	public String getName()
	{
		return name;
	}

	/**
	 * Quando uma pasta for limpa, ao remover cada arquivo será chamado esse método.
	 * @param file referência do arquivo do qual está sendo removido.
	 */

	protected void subClear(E file)
	{
		
	}

	/**
	 * Quando um novo arquivo for adicionado a essa pasta será chamado esse método.
	 * @param file referência do arquivo do qual está sendo adicionado.
	 */

	protected void subAdd(E file)
	{
		
	}

	/**
	 * Quando um arquivo é removido deve chamar esse método para limpar a pasta do mesmo.
	 * A limpeza se refere a excluir a lista de arquivos se não hover mais nenhum.
	 * Em seguida passa para a pasta raíz verificar se esta pasta possui arquivos.
	 * Caso não possua ela será removida e assim por diante até chegar a raíz máxima.
	 * @param folder referência da pasta onde se encontra o arquivo removido.
	 */

	protected void subRemoveClear(VirtualFolder<E> folder)
	{
		if (folder == null)
			return;

		if (folder.files != null && folder.files.size() == 0)
			folder.files = null;

		if (folder.folders != null)
		{
			for (int i = 0; i < folder.folders.size(); i++)
				if (folder.folders.get(i).size() == 0)
					folder.folders.remove(i);

			if (folder.folders.size() == 0)
				folder.folders = null;
		}

		subRemoveClear(folder.parent);
	}

	/**
	 * Quando um arquivo for removido dessa pasta será chamado esse método.
	 * @param file referência do arquivo do qual foi removido da pasta.
	 */

	protected void subRemove(E file)
	{
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterator<E> iterator()
	{
		E array[] = (E[]) new Object[size()];
		subIterator(array, this, 0);

		return new Iterator<E>()
		{
			private int i = 0;

			@Override
			public boolean hasNext()
			{
				return i < array.length;
			}

			@Override
			public E next()
			{
				return array[i++];
			}
		};
	}

	/**
	 * Chamado sempre que uma nova iteração for criada, irá preencher a lista de arquivos.
	 * @param array vetor do qual irá armazenar os arquivos para serem iterados.
	 * @param root pasta virtual do qual terá os arquivos listados.
	 * @param offset índice atual para posicionar os novos arquivos.
	 */

	private int subIterator(E[] array, VirtualFolder<E> root, int offset)
	{
		int alocated = 0;

		if (root.files != null)
			for (E file : root.files)
				if (file != null)
					array[offset + alocated++] = file;

		if (root.folders != null)
			for (VirtualFolder<E> folder : root.folders)
				if (folder != null)
				{
					int count = subIterator(array, folder, offset + alocated);
					alocated += count;
				}

		return alocated;
	}

	@Override
	public String toString()
	{
		ObjectDescription description = new ObjectDescription(getClass());

		description.append("name", name);
		description.append("size", size());
		description.append("files", files == null ? 0 : files.size());
		description.append("folders", folders == null ? 0 : folders.size());

		return description.toString();
	}

	protected class Path
	{
		private int offset;
		private String splitPath[];

		public Path(String path)
		{
			path = FileUtil.adaptPath(path);

			offset = 0;
			splitPath = path.split("/");
		}

		public String name()
		{
			return splitPath[offset];
		}

		public String enter()
		{
			if (offset < splitPath.length - 1)
				return splitPath[offset++];

			return null;
		}

		public boolean isFile()
		{
			return offset == splitPath.length - 1;
		}

		public boolean isDirectory()
		{
			return offset != splitPath.length - 1;
		}

		@Override
		public String toString()
		{
			StringBuilder str = new StringBuilder();

			for (int i = 0; i < splitPath.length; i++)
			{
				if (i == offset)
					str.append("[" +splitPath[i]+ "]");
				else
					str.append(splitPath[i]);

				if (i != splitPath.length - 1)
					str.append("/");
			}

			return str.toString();
		}
	}
}
