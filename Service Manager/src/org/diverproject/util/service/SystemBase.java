package org.diverproject.util.service;

import static org.diverproject.log.LogSystem.logError;
import static org.diverproject.log.LogSystem.logWarning;

import org.diverproject.util.BitWise;
import org.diverproject.util.ObjectDescription;
import org.diverproject.util.UtilException;
import org.diverproject.util.collection.Queue;
import org.diverproject.util.collection.abstraction.DynamicQueue;

/**
 * <h1>Base para Sistema</h1>
 *
 * <p>Classe que permite criar um novo e pequeno sistema dentro do projeto.
 * Deve possuir algumas funcionalidades básicas que todo sistema possui.
 * Dentre elas, algumas já implementadas e outras que deverão ser.</p>
 *
 * <p>Todo sistema deve ser atualizado, por tanto será de obrigação deste,
 * implementar um procedimento que deverá fazer a atualização do mesmo.
 * O mesmo vale para a funcionalidade que permite desligar o sistema.</p>
 *
 * <p>Em relação as funcionalidades já implementadas de uma base para sistema,
 * se refere a utilização de exception, deverá ser registrado internamente.
 * As vezes o sistema não deve relatar na hora, sendo necessário tal.</p>
 *
 * @see UtilException
 *
 * @author Andrew
 */

public abstract class SystemBase
{
	/**
	 * Propriedade que irá dizer ao sistema para usar <b>Util Log</b>.
	 * Será usado apenas em algumas ocasiões e da própria base de sitema.
	 */
	public static final int PROPERTIE_LOG_EXCEPTIONS = 0x01;

	/**
	 * Propriedade que irá dizer ao sistema para mostrar exceções.
	 * Será mostrado a mensagem da exceção sempre que uma for enfileirada.
	 * Essa propriedade será considerada mesmo com <code>PROPERTIE_EXCEPTION_SIGNAL</code>.
	 */
	public static final int PROPERTIE_EXCEPTION_SHOW = 0x02;

	/**
	 * Propriedade que irá dizer ao sistema para sinalizar exceções.
	 * Será mostrado apenas o nome da exceção sempre que uma for enfileirada.
	 * Essa propriedade não será considerada com <code>PROPERTIE_EXCEPTION_SHOW</code>.
	 */
	public static final int PROPERTIE_EXCEPTION_SIGNAL = 0x04;

	/**
	 * Propriedade que irá dizer ao sistema para utilizar o <b>Util Log</b>.
	 * Permite que a extensão de uma classe como base para sistema possa usar
	 * essa propriedade para determinar se haverá o uso de logs nas operações.
	 */
	public static final int PROPERTIE_USE_LOG = 0x08;

	/**
	 * Vetor que deverá armazenar o nome das propriedades disponíveis.
	 */
	public static final String PROPERTIE_NAMES[] = new String[]
	{
		"LOG_EXCEPTIONS",
		"EXCEPTION_SHOW",
		"EXCEPTION_SIGNAL",
		"USE_LOG",
	};

	/**
	 * Exceção que foi gerada durante a atualização do sistema.
	 */
	private Queue<UtilException> exceptions;

	/**
	 * Armazenamento e controle das propriedades da base do sistema.
	 */
	private BitWise properties;

	/**
	 * Constrói uma nova base para a criação de sistemas no software.
	 * Para esse construtor deve ser inicializado a fila de exceções.
	 */

	public SystemBase()
	{
		properties = new BitWise(PROPERTIE_NAMES);
		exceptions = new DynamicQueue<UtilException>();
	}

	/**
	 * Para melhorar a diversificação dos sistemas existem as propriedades.
	 * Podem ser usadas para adicionar/remover funções do sistema ou avisos.
	 * @param propertie código da propriedade que deseja verificar.
	 * @return true se estiver habilitada ou false caso contrário.
	 */

	public boolean isPropertie(int propertie)
	{
		return properties.is(propertie);
	}

	/**
	 * Permite definir determinadas propriedades para serem usadas no sistema.
	 * Essas propriedades podem ser verificadas através de <code>isPropertie()</code>.
	 * @param propertie código da propriedade do qual deverá ser definida.
	 * @param enable true para habilitar ou false para desabilitar.
	 */

	public void setPropertie(int propertie, boolean enable)
	{
		if (enable)
			properties.set(propertie);
		else
			properties.unset(propertie);
	}

	/**
	 * Nome do sistema pode ser usado externamente e internamente.
	 * Internamente alguns procedimentos usam o nome do sistema para que
	 * seja possível identificar em mensagens de qual sistema veio.
	 * @return aquisição do nome que foi dado a esse sistema.
	 */

	public abstract String getSystemName();

	/**
	 * Deve ser chamado periodicamente, preferencialmente em intervalos pré-definidos.
	 * Seu objeto é garantir que os atributos internos estejam atualizados de acordo.
	 * Podendo ainda chamar procedimentos para garantir funcionalidades no sistema.
	 * @param delay quantos milissegundos se passou desde a última atualização.
	 */

	public abstract void update(long delay);

	/**
	 * Para esse procedimento, será designado as funcionalidades para desligamento.
	 * O desligamento do sistema deverá liberar as informações da memória (objeto null).
	 * Se houver ainda threads para tal, interromper estas e tira-las de circulação.
	 * @throws UtilException apenas se houver alguma falha ao desligar o sistema.
	 */

	public abstract void shutdown() throws UtilException;

	/**
	 * Exceções podem ocorrer durante a execução de um software, internamente no sistema.
	 * De modo a garantir que algumas exceções não obriguem try catch este deve ser usado.
	 * @return aquisição da exceções mais antiga que está armazenada na fila.
	 */

	public final UtilException getException()
	{
		return exceptions.poll();
	}

	/**
	 * Permite definir uma nova exceção causada internamente no sistema.
	 * Novas exceções são colocadas no final da fila de exceções.
	 * @param e exceção que foi gerada internamente.
	 */

	public final void putException(UtilException e)
	{
		if (exceptions.offer(e))
		{
			if (properties.is(PROPERTIE_EXCEPTION_SHOW))
				logWarning("%s: %s", getSystemName(), e.getMessage());

			else if (properties.is(PROPERTIE_EXCEPTION_SIGNAL))
				logError("%s: %s", getSystemName(), e.getMessage());
		}
	}

	/**
	 * Procedimento chamado por toString a fim de preencher adequadamente o mesmo.
	 * O posicionamento do conteúdo é sempre feito apenas ao final da descrição.
	 * @param description referência da descrição desse objeto que será usado.
	 */

	protected void toString(ObjectDescription description)
	{
		
	}

	@Override
	public String toString()
	{
		ObjectDescription description = new ObjectDescription(getClass());

		toString(description);

		description.append("exceptions", exceptions.size());
		description.append(properties.toStringProperties());

		return description.toString();
	}
}
