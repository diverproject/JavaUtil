package org.diverproject.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JOptionPane;

import org.diverproject.util.collection.List;
import org.diverproject.util.collection.abstraction.DynamicList;

/**
 * <h1>Sistema dos Registros</h1>
 *
 * <p>Classe principal para o controle de registros de mensagens da aplicação que está sendo executada.
 * Esse log permite que um arquivo seja criado em um local especificado e salve determinadas mensagens.
 * Mensagens que podem ser enviadas através dos métodos disponíveis em LogDisplay conforme necessário.</p>
 *
 * <p>Uma das possibilidades que essa biblioteca permite é que os registros sejam feitos no meio do código.
 * Além disso há algumas opções que podem ser definidas através da classe LogPreferences com setters.</p>
 *
 * <p>Outro objeto dessa classe é conter métodos que permitam que o sistema possa ser usado adequadamente.
 * Possuindo diversos tipos de impressão de mensagens como registro no sistema de acordo com as necessidades.
 * Há a possibilidade de especificar se é registro de debug, info, notice, warning, error ou exception.</p>
 *
 * @see LogPreferences
 * @see LogConsole
 * @see LogFile
 * @see Log
 *
 * @author Andrew
 */

public class LogSystem
{
	/**
	 * Tipo de mensagens geradas por log.
	 */
	private static final String LOG_TYPE = "Log";

	/**
	 * Tipo de mensagens geradas por logDebug.
	 */
	private static final String DEBUG_TYPE = "Debug";

	/**
	 * Tipo de mensagens geradas por logInfo.
	 */
	private static final String INFO_TYPE = "Info";

	/**
	 * Tipo de mensagens geradas por logNotice.
	 */
	private static final String NOTICE_TYPE = "Notice";

	/**
	 * Tipo de mensagens geradas por logWarning.
	 */
	private static final String WARNING_TYPE = "Warning";

	/**
	 * Tipo de mensagens geradas por logError.
	 */
	private static final String ERROR_TYPE = "Error";

	/**
	 * Tipo de mensagens geradas por logException.
	 */
	private static final String EXCEPTION_TYPE = "Exception";

	/**
	 * Referência do writer que será usado para salvar as mensagens de registro.
	 */
	private static BufferedWriter bufferedWriter;

	/**
	 * Lista com os objetos que irão receber as mensagens do log.
	 */
	private static final List<LogListener> listeners;

	/**
	 * Altera a fonte de quem será mostrado no registro;
	 */
	private static int upSource;

	/**
	 * Objeto para sincronização afim de evitar erros com upSource;
	 */
	private static Object lock;

	static
	{
		listeners = new DynamicList<>();
		listeners.add(new LogListener()
		{
			@Override
			public void onMessage(Log log)
			{
				LogFile.print(log);
			}
		});

		lock = new Object();
	}

	/**
	 * Procedimento que permite obter a referência do writer que irá guardar os registros.
	 * Caso não tenha sido criado um writer, será exibido uma mensagem em uma janela.
	 * @return referência do writer que irá guardar os registros.
	 */

	static BufferedWriter getBufferedWrite()
	{
		if (bufferedWriter == null)
			JOptionPane.showMessageDialog(null, "Log não foi inicializado.", "Falha de Log", JOptionPane.ERROR_MESSAGE);

		return bufferedWriter;
	}

	/**
	 * Verifica se o writer já foi inicializado no sistema de registros.
	 * @return true se tiver sido iniciado ou false caso contrário.
	 */

	public static boolean hasInitialize()
	{
		return bufferedWriter != null;
	}

	/**
	 * Deve fazer a inicialização do writer para gravar todos os registros em um determinado arquivo.
	 * @return true se conseguir inicializar ou false caso já tenha sido inicializado.
	 * @throws LogException ocorre apenas se houver falha durante o uso do arquivo para log.
	 */

	public static boolean initialize() throws LogException
	{
		if (hasInitialize())
			return false;

		try {

			File file = LogPreferences.getFile();

			if (file == null)
				throw new LogException("arquivo para log não definido");

			FileWriter fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter);

			DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();

			LogSystem.log("Log: Inicialização efetuado com êxito (horário: %s, arquivo: %s).\n", format.format(cal.getTime()), LogPreferences.getFile());

			return true;

		} catch (IOException e) {
			throw new LogException("Falha ao iniciar arquivo para log (%s).", e.getMessage());
		}
	}

	/**
	 * Deve fazer o término do writer que grava todos os registros em um determinado arquivo.
	 * @return true se conseguir finalizar com êxito ou false caso não tenha sido iniciado.
	 * @throws LogException ocorre apenas se houver falha durante o uso do arquivo para log.
	 */

	public static boolean terminate() throws LogException
	{
		if (!hasInitialize())
			return false;

		try {

			DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();

			LogSystem.log("Log: Finalização dos registros (horário: %s, arquivo: %s).\n", format.format(cal.getTime()), LogPreferences.getFile());

			bufferedWriter.close();
			bufferedWriter.flush();

			return true;

		} catch (IOException e) {
			throw new LogException("Falha ao encerrar arquivo para log (%s).", e.getMessage());
		}
	}

	/**
	 * Sempre que uma mensagem for registrada no sistema será repassado aos listeners.
	 * Esse método irá adicionar um novo objeto a lista para receber esses registros.
	 * @param listener referência do objeto listener para receber as mensagens.
	 */

	public static void addListener(LogListener listener)
	{
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	/**
	 * Sempre que uma mensagem for registrada no sistema será repassado aos listeners.
	 * Esse método irá remover um novo objeto a lista para receber esses registros.
	 * @param listener referência do objeto listener para receber as mensagens.
	 */

	public static void removeListener(LogListener listener)
	{
		listeners.remove(listener);
	}

	/**
	 * Permite alterar quem será mostrado no registro como fonte da chamada.
	 * POde ser usado por métodos gerais que tendem apenas a facilitar codificações.
	 * @param upSource quantos traces deverão ser voltados a partir da chamada.
	 */

	public static void setUpSource(int upSource)
	{
		if (upSource > 0)
			LogSystem.upSource = upSource;
	}

	/**
	 * Procedimento interno que irá criar o objeto para definir informações do registro.
	 * Este inclui a inicialização de Throwable para descobrir a origem da chamada log.
	 * Assim é possível saber o nome do arquivo, classe, método e linha que foi chamado.
	 * @param type nome do tipo de mensagem que está sendo registrado no serviço.
	 * @param message mensagem contendo as informações ou ocorridos a serem registrados.
	 */

	private static void internalLog(String type, String message)
	{
		Throwable throwable = new Throwable();

		internalLog(type, message, throwable);
	}

	/**
	 * Procedimento interno que irá criar o objeto para definir informações do registro.
	 * Este inclui a inicialização de Throwable para descobrir a origem da chamada log.
	 * Assim é possível saber o nome do arquivo, classe, método e linha que foi chamado.
	 * @param type nome do tipo de mensagem que está sendo registrado no serviço.
	 * @param message mensagem contendo as informações ou ocorridos a serem registrados.
	 * @param throwable fonte da onde a mensagem foi originada.
	 */

	private static void internalLog(String type, String message, Throwable throwable)
	{
		if (!hasInitialize())
			return;

		synchronized (lock)
		{
			Log log = new Log();
			log.setType(type);
			log.setMessage(message);
			log.setStackTrace(throwable.getStackTrace()[2 + upSource]);

			upSource = 0;

			for (LogListener listener : listeners)
				listener.onMessage(log);	
		}
	}

	/**
	 * Imprimi uma determina mensagem formatada no sistema de registros.
	 * Verifica se o uso de console como também o de arquivo estão habilitados.
	 * @param str conteúdo do qual será usado como registro.
	 */

	public static void log(String str)
	{
		internalLog(LOG_TYPE, str);
	}

	/**
	 * Imprimi uma determina mensagem formatada no sistema de registros.
	 * Verifica se o uso de console como também o de arquivo estão habilitados.
	 * @param format formado da mensagem que será exibida pelo registro.
	 * @param args referência dos objetos de acordo com a formatação.
	 */

	public static void log(String format, Object... args)
	{
		internalLog(LOG_TYPE, String.format(format, args));
	}

	/**
	 * Imprimi uma determina mensagem formatada no sistema de registros.
	 * Verifica se o uso de console como também o de arquivo estão habilitados.
	 * Nesse caso apenas se estiver habilitado o registro de debug.
	 * @param str conteúdo do qual será usado como registro.
	 */

	public static void logDebug(String str)
	{
		internalLog(DEBUG_TYPE, str);
	}

	/**
	 * Imprimi uma determina mensagem formatada no sistema de registros.
	 * Verifica se o uso de console como também o de arquivo estão habilitados.
	 * Nesse caso apenas se estiver habilitado o registro de debug.
	 * @param format formado da mensagem que será exibida pelo registro.
	 * @param args referência dos objetos de acordo com a formatação.
	 */

	public static void logDebug(String format, Object... args)
	{
		if (!LogPreferences.isUseDebug())
			return;

		internalLog(DEBUG_TYPE, String.format(format, args));
	}

	/**
	 * Imprimi uma determina mensagem formatada no sistema de registros.
	 * Verifica se o uso de console como também o de arquivo estão habilitados.
	 * Nesse caso apenas se estiver habilitado o registro de info.
	 * @param str conteúdo do qual será usado como registro.
	 */

	public static void logInfo(String str)
	{
		internalLog(INFO_TYPE, str);
	}

	/**
	 * Imprimi uma determina mensagem formatada no sistema de registros.
	 * Verifica se o uso de console como também o de arquivo estão habilitados.
	 * Nesse caso apenas se estiver habilitado o registro de info.
	 * @param format formado da mensagem que será exibida pelo registro.
	 * @param args referência dos objetos de acordo com a formatação.
	 */

	public static void logInfo(String format, Object... args)
	{
		if (!LogPreferences.isUseDebug())
			return;

		internalLog(INFO_TYPE, String.format(format, args));
	}

	/**
	 * Imprimi uma determina mensagem formatada no sistema de registros.
	 * Verifica se o uso de console como também o de arquivo estão habilitados.
	 * Nesse caso apenas se estiver habilitado o registro de notice.
	 * @param str conteúdo do qual será usado como registro.
	 */

	public static void logNotice(String str)
	{
		internalLog(NOTICE_TYPE, str);
	}

	/**
	 * Imprimi uma determina mensagem formatada no sistema de registros.
	 * Verifica se o uso de console como também o de arquivo estão habilitados.
	 * Nesse caso apenas se estiver habilitado o registro de notice.
	 * @param format formado da mensagem que será exibida pelo registro.
	 * @param args referência dos objetos de acordo com a formatação.
	 */

	public static void logNotice(String format, Object... args)
	{
		if (!LogPreferences.isUseDebug())
			return;

		internalLog(NOTICE_TYPE, String.format(format, args));
	}

	/**
	 * Imprimi uma determina mensagem formatada no sistema de registros.
	 * Verifica se o uso de console como também o de arquivo estão habilitados.
	 * Nesse caso apenas se estiver habilitado o registro de warning.
	 * @param str conteúdo do qual será usado como registro.
	 */

	public static void logWarning(String str)
	{
		internalLog(WARNING_TYPE, str);
	}

	/**
	 * Imprimi uma determina mensagem formatada no sistema de registros.
	 * Verifica se o uso de console como também o de arquivo estão habilitados.
	 * Nesse caso apenas se estiver habilitado o registro de warning.
	 * @param format formado da mensagem que será exibida pelo registro.
	 * @param args referência dos objetos de acordo com a formatação.
	 */

	public static void logWarning(String format, Object... args)
	{
		if (!LogPreferences.isUseWarning())
			return;

		internalLog(WARNING_TYPE, String.format(format, args));
	}

	/**
	 * Imprimi uma determina mensagem formatada no sistema de registros.
	 * Verifica se o uso de console como também o de arquivo estão habilitados.
	 * Nesse caso apenas se estiver habilitado o registro de error.
	 * @param str conteúdo do qual será usado como registro.
	 */

	public static void logError(String str)
	{
		internalLog(ERROR_TYPE, str);
	}

	/**
	 * Imprimi uma determina mensagem formatada no sistema de registros.
	 * Verifica se o uso de console como também o de arquivo estão habilitados.
	 * Nesse caso apenas se estiver habilitado o registro de error.
	 * @param format formado da mensagem que será exibida pelo registro.
	 * @param args referência dos objetos de acordo com a formatação.
	 */

	public static void logError(String format, Object... args)
	{
		if (!LogPreferences.isUseError())
			return;

		internalLog(ERROR_TYPE, String.format(format, args));
	}

	/**
	 * Imprimi uma determina mensagem formatada no sistema de registros.
	 * Verifica se o uso de console como também o de arquivo estão habilitados.
	 * Nesse caso apenas se estiver habilitado o registro de exception.
	 * @param e exceção gerada do qual será exibida junto com seu nome.
	 */

	public static void logExeception(Exception e)
	{
		String message = String.format("%s [%s]\n", e.getMessage(), e.getClass().getSimpleName());

		internalLog(EXCEPTION_TYPE, message);
	}

	/**
	 * Imprimi uma determina mensagem formatada no sistema de registros.
	 * Verifica se o uso de console como também o de arquivo estão habilitados.
	 * Nesse caso apenas se estiver habilitado o registro de exception.
	 * @param e exceção gerada do qual será usada a mensagem como registro.
	 */

	public static void logExeceptionMessage(Exception e)
	{
		internalLog(EXCEPTION_TYPE, e.getMessage());
	}

	/**
	 * Imprimi uma determina mensagem formatada no sistema de registros.
	 * Verifica se o uso de console como também o de arquivo estão habilitados.
	 * Nesse caso apenas se estiver habilitado o registro de exception.
	 * @param e exceção gerada do qual será exibida junto com seu nome.
	 */

	public static void logExeceptionSource(Exception e)
	{
		String message = String.format("%s [%s]\n", e.getMessage(), e.getClass().getSimpleName());

		upSource = -2;

		internalLog(EXCEPTION_TYPE, message, e);
	}
}
