package org.diverproject.log;

import java.io.File;

/**
 * <h1>Preferências para Registros</h1>
 *
 * <p>Classe que possui diversos procedimentos que permitem definir determinadas opções para registros.
 * Em sua maioria determinar quais os tipos de registros que de fato devem ser registrados (arquivo/console).
 * Além disso, é aqui que se define o caminho do diretório no qual será salvo os registros em arquivo.</p>
 *
 * @author Andrew
 */

public class LogPreferences
{
	/**
	 * Determina se deve ser feito o uso de registros do tipo debug.
	 */
	private static boolean useDebug;

	/**
	 * Determina se deve ser feito o uso de registros do tipo informação.
	 */
	private static boolean useInfo;

	/**
	 * Determina se deve ser feito o uso de registros do tipo notícia.
	 */
	private static boolean useNotice;

	/**
	 * Determina se deve ser feito o uso de registros do tipo alerta.
	 */
	private static boolean useWarning;

	/**
	 * Determina se deve ser feito o uso de registros do tipo erro.
	 */
	private static boolean useError;

	/**
	 * Determina se deve ser feito o uso de registros do tipo exception.
	 */
	private static boolean useException;

	/**
	 * Determina qual será o caminho (diretório) que será usado para salvar o arquivo de registros.
	 */
	private static String filePath;

	/**
	 * A utilização de registros de debug permite que logDebug() seja funcional.
	 * @return true se estiver habilitado ou false caso contrário.
	 */

	public static boolean isUseDebug()
	{
		return useDebug;
	}

	/**
	 * Permite definir se os registros do tipo debug devem ou não ser usados.
	 * @param useDebug true para habilitar ou false caso contrário.
	 */

	public static void setUseDebug(boolean useDebug)
	{
		LogPreferences.useDebug = useDebug;
	}

	/**
	 * A utilização de registros de debug permite que logInfo() seja funcional.
	 * @return true se estiver habilitado ou false caso contrário.
	 */

	public static boolean isUseInfo()
	{
		return useInfo;
	}

	/**
	 * Permite definir se os registros do tipo info devem ou não ser usados.
	 * @param useInfo true para habilitar ou false caso contrário.
	 */

	public static void setUseInfo(boolean useInfo)
	{
		LogPreferences.useInfo = useInfo;
	}

	/**
	 * A utilização de registros de debug permite que logNotice() seja funcional.
	 * @return true se estiver habilitado ou false caso contrário.
	 */

	public static boolean isUseNotice()
	{
		return useNotice;
	}

	/**
	 * Permite definir se os registros do tipo notice devem ou não ser usados.
	 * @param useNotice true para habilitar ou false caso contrário.
	 */

	public static void setUseNotice(boolean useNotice)
	{
		LogPreferences.useNotice = useNotice;
	}

	/**
	 * A utilização de registros de debug permite que logWarning() seja funcional.
	 * @return true se estiver habilitado ou false caso contrário.
	 */

	public static boolean isUseWarning()
	{
		return useWarning;
	}

	/**
	 * Permite definir se os registros do tipo warning devem ou não ser usados.
	 * @param useWarning true para habilitar ou false caso contrário.
	 */

	public static void setUseWarning(boolean useWarning)
	{
		LogPreferences.useWarning = useWarning;
	}

	/**
	 * A utilização de registros de debug permite que logError() seja funcional.
	 * @return true se estiver habilitado ou false caso contrário.
	 */

	public static boolean isUseError()
	{
		return useError;
	}

	/**
	 * Permite definir se os registros do tipo error devem ou não ser usados.
	 * @param useError true para habilitar ou false caso contrário.
	 */

	public static void setUseError(boolean useError)
	{
		LogPreferences.useError = useError;
	}

	/**
	 * A utilização de registros de debug permite que logException() seja funcional.
	 * @return true se estiver habilitado ou false caso contrário.
	 */

	public static boolean isUseException()
	{
		return useException;
	}

	/**
	 * Permite definir se os registros do tipo exception devem ou não ser usados.
	 * @param useException true para habilitar ou false caso contrário.
	 */

	public static void setUseException(boolean useException)
	{
		LogPreferences.useException = useException;
	}

	/**
	 * A utilização de um arquivo permite que os registros sejam salvo no mesmo.
	 * @return caminho do arquivo que será usado para salvar registros.
	 */

	public static File getFile()
	{
		return new File(filePath);
	}

	/**
	 * Permite definir o caminho para salvar os arquivos criados se assim for possível.
	 * Caso o caminho especificado seja inválido ou o sistema já tenha sido iniciado,
	 * esse método não terá nenhuma utilidade retornando antes de definir o diretório.
	 * @param path caminho do diretório do qual os arquivos de registros serão criados.
	 */

	public static void setFile(String path)
	{
		filePath = path;
	}

	/**
	 * Quando chamado irá definir que todos os tipos de mensagens devem ser registrados.
	 * Além das mensagens irá utilizar todo e qualquer recurso como uso do console ou arquivo.
	 */

	public static void setUseAll()
	{
		setUseDebug(true);
		setUseError(true);
		setUseException(true);
		setUseInfo(true);
		setUseNotice(true);
		setUseWarning(true);
	}
}
