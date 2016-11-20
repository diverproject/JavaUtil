package org.diverproject.util.service;

/**
 * <h1>Serviço</h1>
 *
 * <p>Interface utilizada para a criação de diferentes tipos de serviços no engine.
 * Esses serviços serão armazenados no gerenciador de serviços e podem ser usados.
 * Todo serviço deverá possuir algumas funcionalidades básicas para tal, segue:</p>
 *
 * <p>Primeiramente, os serviços tem que ser inicializados para que este entre em vigor.
 * Assim sendo, todo serviço tem que possuir um código de estado de como está.
 * Todos os serviços devem possuir um nome que irá permitir a identificação deste.</p>
 *
 * <p>Serviços serão necessários sempre que houver uma necessidade de criação de um
 * sistema que seja muito grande, como por exemplo o gerenciamento dos arquivos usados
 * pelo cliente, recursos gráficos e sonoros como também a entrada de mouse e teclado.</p>
 *
 * @author Andrew
 */

public interface Service
{
	/**
	 * Código para determinar que o serviço se encontra em estado não definido.
	 */
	public static final int SERVICE_UNDEFINID = 0;

	/**
	 * Código para determinar que o serviço se encontra já iniciado mas não rodando.
	 */
	public static final int SERVICE_STARTED = 1;

	/**
	 * Código para determinar que o serviço se encontrava iniciado e está rodando.
	 */
	public static final int SERVICE_RUNNING = 2;

	/**
	 * Código para determinar que o serviço se encontrava iniciado e agora está parado.
	 */
	public static final int SERVICE_STOPED = 3;

	/**
	 * Código para determinar que o serviço se encontra em fase de finalização.
	 */
	public static final int SERVICE_TERMINATED = 4;


	/**
	 * Chamado a cada frame executado no cliente para garantir que o sistema atualize.
	 * @param delay quantos milissegundos se passaram desde a última atualização.
	 */

	void update(long delay);

	/**
	 * Procedimento chamado sempre que o serviço entrar em vigor no sistema, iniciar.
	 * Deve garantir que todos os recursos utilizados no sistema estejam disponíveis.
	 * Caso o serviço esteja interrompido, apenas deve retornar a rodar normalmente.
	 * @throws ServiceException falha na inicialização do serviço, provavelmente há um
	 * recurso do qual não pode ser inicializado que estará especificado na mensagem.
	 */

	void start() throws ServiceException;

	/**
	 * Terminar o serviço significa que ele será interrompido e em seguida apagado.
	 * Uma vez que ele tenha sido apagado todas as informações no mesmo irão junto.
	 * Além disso um serviço terminado não pode retornar a operar, porém não diz
	 * que um novo serviço possa ser criado, indo de serviço para serviço.
	 * @throws ServiceException falha na finalização do serviço, provavelmente há um
	 * recurso do qual não pode ser finalizado que estará especificado na mensagem.
	 */

	void terminate() throws ServiceException;

	/**
	 * Interromper o serviço significa que ele irá parar de funcionar temporariamente.
	 * Assim sendo o serviço poderá retornar a operação quando for iniciado novamente.
	 * Iniciá-lo novamente e não significa restabelecê-lo completamente, apenas que ele
	 * deverá entrar em vigor novamente assim que solicitado.
	 * @throws ServiceException falha na interrupção do serviço, provavelmente há um
	 * recurso do qual não pode ser interrompido que estará especificado na mensagem.
	 */

	void interrupted() throws ServiceException;

	/**
	 * Estado irá determinar o que o serviço está atualmente fazendo para se mantar.
	 * Cada procedimento chamado no serviço ou funcionalidades poderá acarretar na
	 * mudança do estado do sistema de acordo com as especificações internas do mesmo.
	 * @return aquisição do código do estado atual do serviço no sistema.
	 */

	int getState();

	/**
	 * Identificador é um nome que é dado a todos os serviços com preferência a padrões.
	 * Sendo utilizado para que o sistema possa identificar exatamente qual serviço é.
	 * @return aquisição do nome de identificação do serviço em questão.
	 */

	String getIdentificator();
}
