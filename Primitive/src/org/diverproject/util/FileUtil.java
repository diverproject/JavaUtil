package org.diverproject.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.diverproject.util.lang.StringUtil;

/**
 * <p><h1>Utilitário para Arquivo</h1></p>
 *
 * Utilitários de gerenciamento de arquivos, permite fazer análises com alguns tipos
 * de operações relacionadas a arquivos como contagem de linhas, conversão de bytes
 * para uma string legível dentre outros aspectos úteis para melhorar as aplicações.
 */

public class FileUtil
{
	/**
	 * Construtor privado pois é um utilitário estático (apenas métodos estáticos).
	 */

	private FileUtil()
	{
		
	}

	/**
	 * Conta quantas linhas existem nos arquivos dentro de um determinado diretório.
	 * @param path caminho da pasta do qual será analisada.
	 * @return número total de linhas encontradas nos arquivos.
	 */

	public static int countLineIn(String path)
	{
		int count = 0;

		File file = new File(path);

		if (file.isFile())
			try {

				Scanner scan = new Scanner(file);

				while (scan.hasNext())
				{
					scan.nextLine();
					count++;
				}

				scan.close();

				return count;

			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			}

		for (String s : file.list())
			count += countLineIn(path + "\\" + s);

		return count;
	}

	/**
	 * Corrige caminhos de  de um arquivo ou pasta para o formato padrão do java.
	 * Alguns caminhos de arquivos são definidos com "\" (barra invertida), estes por sua
	 * vez são utilizados para expressões regulares e coisas do gênero.
	 * Assim sendo, este método irá substitui quaisquer barras ou barras duplas
	 * para o tipo de barra padrão utilizado para definir caminhos no java: <b>/</b>.
	 * @param path caminho que deve ser corrigido (em  ou local).
	 * @return caminho corrigido com as normas acima.
	 */

	public static String adaptPath(String path)
	{
		if (path == null)
			return null;

		while (path.startsWith("/") || path.startsWith("\\"))
			path = StringUtil.sub(path, 2, path.length() - 1);

		while (path.contains("//"))
			path = path.replace("//", "/");

		while (path.contains("\\\\"))
			path = path.replace("\\\\", "\\");

		return path.replace("\\", "/");
	}

	/**
	 * Análise o caminho passado a fim de obter apenas a extensão do arquivo.
	 * <b>Exemplo:</b> [C:\Windows\system32\cmd.exe] : [exe]
	 * @param path caminho do qual deseja obter apenas a extensão do arquivo.
	 * @return aquisição da string contendo apenas a extensão do arquivo.
	 */

	public static String getExtension(String path)
	{
		path = adaptPath(path);

		if (path.lastIndexOf('.') == -1)
			return "";

		return path.substring(path.lastIndexOf('.') + 1, path.length());
	}

	/**
	 * Análise o caminho passado a fim de obter apenas o nome do arquivo.
	 * <b>Exemplo:</b> [C:\Windows\system32\cmd.dll.exe] : [cmd.dll]
	 * @param path caminho do qual deseja obter apenas o nome do arquivo.
	 * @return aquisição da string contendo apenas o nome do arquivo.
	 */

	public static String getFileName(String path)
	{
		path = adaptPath(path);
		path = getFileNameExtension(path);

		if (path.contains("."))
			path = path.substring(0, path.lastIndexOf("."));

		return path;
	}

	/**
	 * Análise o caminho passado a fim de obter o nome completo do arquivo.
	 * <b>Exemplo:</b> [C:\Windows\system32\cmd.exe] : [cmd.exe]
	 * @param path caminho do qual deseja obter o nome do arquivo com extensão.
	 * @return aquisição da string contendo o nome do arquivo com extensão.
	 */

	public static String getFileNameExtension(String path)
	{
		path = adaptPath(path);

		if (path.contains("/"))
			return path.substring(path.lastIndexOf('/') + 1, path.length());

		return path;
	}

	/**
	 * Análise o caminho passado a fim de obter o caminho completo da pasta raíz.<br>
	 * <b>Exemplo:</b> [C:\Windows\system32\cmd.exe] : [C:\Windows\system32]
	 * @param path caminho do qual deseja obter o diretório externo.
	 * @return aquisição da string contendo o caminho do diretório externo.
	 */

	public static String getParentPath(String path)
	{
		path = adaptPath(path);

		while (path.endsWith("/"))
			path = StringUtil.sub(path, 0, path.length() - 1);

		if (path.contains("/"))
			return path.substring(0, path.lastIndexOf("/"));

		return path + "/";
	}

	/**
	 * Análise o caminho passado a fim de obter o caminho virtual da raíz.<br>
	 * <b>Exemplo:</b> [C:\Windows\system32\cmd.exe] : [Windows\system32]
	 * @param path caminho do qual deseja obter o diretório interno.
	 * @return aquisição da string contendo o caminho do diretório interno.
	 */

	public static String getVirtualPath(String path)
	{
		path = adaptPath(path);

		if (path.contains("/"))
		{
			path = path.substring(path.indexOf("/") + 1, path.length());

			if (path.contains("/"))
				path = path.substring(0, path.lastIndexOf("/"));

			return path;
		}

		return path;
	}

	/**
	 * Análise o caminho passado a fim de obter o caminho virtual completo.<br>
	 * <b>Exemplo:</b> [C:\Windows\system32\cmd.exe] : [Windows\system32\cmd.exe]
	 * @param path caminho do qual deseja obter o diretório interno.
	 * @return aquisição da string contendo o caminho do diretório interno.
	 */

	public static String getInnerPath(String path)
	{
		path = adaptPath(path);

		if (path.contains("/"))
			return path.substring(path.indexOf("/") + 1, path.length());

		return path;
	}

	/**
	 * Análise o caminho passado a fim de obter apenas o nome da raíz principal.<br>
	 * <b>Exemplo:</b> [C:\Windows\system32\cmd.exe] : [Windows]
	 * @param path caminho do qual deseja obter o diretório interno.
	 * @return aquisição da string contendo o caminho do diretório interno.
	 */

	public static String getRootName(String path)
	{
		if (path.contains(":"))
			path = path.substring(path.indexOf(":") + 2, path.length());

		path = adaptPath(path);

		return path.substring(0, path.indexOf("/"));
	}

	/**
	 * <p>Análise o caminho passado a fim de obter apenas o nome do parente.<p>
	 * <p><b>Exemplo:</b> [C:\Windows\system32\cmd.exe] : [system32]</p>
	 * @param path caminho do qual deseja obter o diretório interno.
	 * @return aquisição da string contendo o caminho do diretório interno.
	 */

	public static String getParentName(String path)
	{
		path = getVirtualPath(path);

		return path.substring(path.indexOf("/") + 1, path.length());
	}

	/**
	 * Cria todos os diretórios de um objeto file especificado caso não existam.
	 * @param file referência do objeto contendo o caminho do arquivo.
	 */

	public static void makeDirs(File file)
	{
		file = new File(file.getAbsolutePath());
		File parent = file.getParentFile();

		if (!parent.exists())
			makeDirs(parent);

		parent.mkdir();
	}
}
