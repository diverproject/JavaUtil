package org.diverproject.util.service;

import static org.diverproject.log.LogSystem.logError;
import static org.diverproject.log.LogSystem.logInfo;

import java.io.File;

import org.diverproject.util.ObjectDescription;
import org.diverproject.util.UtilException;

/**
 * <h1>Sistema para Bibliotecas</h1>
 *
 * <p>Esse sistema tem como finalidade o carregamento de bibliotecas.
 * para que funcione deve ser definido (preferencialmente) um diretório.
 * Nesse diretório deverá conter uma pasta para cada tipo de sistema.</p>
 *
 * <p>No caso são os três seguintes SO: Mac OS (mac), Windows (win),
 * Sunos (solaris), para cada um deles deve haver um diretório que irá
 * conter os arquivos respectivos para as bibliotecas carregáveis.</p>
 *
 * <p>O sistema irá solicitar um pré-fixo que será dado como nome da library.
 * Para então tentar carregar o mesmo no sistema se assim for possível.
 * Caso não haja definições para tal irá verificar a existência do mesmo.</p>
 *
 * <p>Uma vez que não tenha gerado qualquer exceção essa biblioteca estará
 * disponível para ser utilizada no sistema sem problemas de erro por
 * falta de informações carregadas da biblioteca em questão.</p>
 *
 * @see SystemBase
 *
 * @author Andrew
 *
 */

public class LibrarySystem extends SystemBase
{
	/**
	 * Referência do objeto LibrarySystem para adaptar ao padrão de projetos Singleton.
	 */
	private static final LibrarySystem instance = new LibrarySystem();

	/**
	 * Diretório onde deve estar localizado as bibliotecas.
	 */
	private String directory;

	/**
	 * Constrói um novo sistema para gerenciamento de bibliotecas.
	 * Deverá iniciar definido um diretório padrão para o mesmo.
	 */

	private LibrarySystem()
	{
		directory = "3rdparty";
	}

	@Override
	public String getSystemName()
	{
		return "SYS.Library";
	}

	@Override
	public void update(long delay)
	{
		
	}

	@Override
	public void shutdown() throws UtilException
	{
		this.directory = null;
	}

	/**
	 * Diretório de bibliotecas é usado para que as bibliotecas possam ser carregadas.
	 * Por padrão o diretório definido é <b>/3rdparty/</b>, podendo ser modificada.
	 * Nesse diretório deverá conter outras pastas, uma para cada tipo de SO.
	 * Dentro dessas pastas irão conter os arquivos das bibliotecas de acordo.
	 * @param directory caminho completo ou parcial do diretório para bibliotecas.
	 */

	public void setDirectory(String directory)
	{
		if (directory != null)
			this.directory = directory;
	}

	/**
	 * O procedimento de vinculação deverá determinar um diretório para location.
	 * Esse diretório será de acordo com as especificações do sistema operacional.
	 * Assim o software poderá aceitar diversos tipos de SO se houver as bibliotecas.
	 * @param name nome da biblioteca como nome de arquivo para ser vinculado.
	 * @param location definição do nome para localização da configuração.
	 * @return true se conseguir vincular ou false caso contrário.
	 */

	public boolean bind(String name, String location)
	{
		if (directory == null)
		{
			ServiceException exception = new ServiceException("diretório não definido");
			putException(exception);

			return false;
		}

		String libname = System.mapLibraryName(name);
		String osname = System.getProperty("os.name").toLowerCase();
		String osarch = System.getProperty("os.arch");

		if (osname.startsWith("windows"))
		{
			osname = "win";
			osarch = (osarch.endsWith("86")) ? "32" : "64";
		}

		try {

			String path = String.format("%s/%s%s/%s", directory, osname, osarch, libname);
			File file = new File(path);

			if (!file.exists())
			{
				if (isPropertie(PROPERTIE_USE_LOG))
					logError("%s: biblioteca não encontrada (%s)\n", getSystemName(), libname);

				return false;
			}

			System.setProperty(location, file.getParentFile().getAbsolutePath());

			if (isPropertie(PROPERTIE_USE_LOG))
				logInfo("%s: biblioteca vinculada com êxito (%s)\n", getSystemName(), libname);

			return true;

		} catch (Exception e) {

			ServiceException exception = new ServiceException(e.getMessage());
			putException(exception);

		}

		return false;
	}

	/**
	 * Carrega uma determinada DLL no software através de <code>load()</code>.
	 * Deve ser necessário haver uma configuração de diretório nas propriedades do
	 * sistema da Java Virtual Machine (JVM), que pode ser feito por <code>bind()</code>.
	 * @param libname nome do arquivo sem extensão .dll de biblioteca, apenas o nome.
	 * @param location propriedade que guarda a localização da biblioteca.
	 */

	public void load(String libname, String location)
	{
		String propertie = System.getProperty(location);
		String filename = String.format("%s\\%s.dll", propertie, libname);

		System.load(filename);
	}

	/**
	 * LibrarySistem utiliza Singleton que permite a criação de apenas um objeto do tipo.
	 * Usado para que não seja possível criar mais do que dois clientes ao mesmo tempo.
	 * @return aquisição da referência do objeto cliente criado.
	 */

	public static LibrarySystem getInstance()
	{
		return instance;
	}

	@Override
	public String toString()
	{
		ObjectDescription description = new ObjectDescription(getClass());

		description.append("directory", directory);

		return description.toString();
	}
}
