package org.diverproject.util.service;

import static org.diverproject.log.LogSystem.logError;
import static org.diverproject.log.LogSystem.logException;
import static org.diverproject.log.LogSystem.logNotice;
import static org.diverproject.log.LogSystem.logWarning;

import org.diverproject.util.collection.Map;
import org.diverproject.util.collection.abstraction.StringMap;

/***
 * <h1>Sistema de Serviços</h1>
 *
 * <p>Tem como finalidade fazer o gerenciamento dos serviços existentes no engine.
 * Além disso é ele quem irá garantir que todos os serviços sejam atualizados.
 * Nele é possível iniciar, interromper ou terminar um serviço pelo identificador.</p>
 *
 * @author Andrew
 */

public final class ServiceSystem extends SystemBase
{
	/**
	 * Referência do objeto ServiceSystem para adaptar ao padrão de projetos Singleton.
	 */
	private static final ServiceSystem INSTANCE = new ServiceSystem();


	/**
	 * Lista que irá armazenar os serviços.
	 */
	private Map<String, Service> services;

	/**
	 * Construtor privado para respeitar o padrão de projetos Singleton.
	 * Deve fazer a inicialização da lista para armazenamento dos serviços.
	 */

	public ServiceSystem()
	{
		services = new StringMap<Service>();

		logNotice("%s: sistema para serviços instanciado.\n", getSystemName());
	}

	@Override
	public String getSystemName()
	{
		return "SYS.Services";
	}

	@Override
	public void update(long delay)
	{
		for (Service service : services)
			if (service.getState() == Service.SERVICE_RUNNING)
				service.update(delay);
	}

	@Override
	public void shutdown()
	{
		for (Service service : services)
		{
			try {
				service.terminate();
			} catch (ServiceException e) {
				logException(e);
			}
		}
	}

	/**
	 * Deve adicionar um novo serviço a lista de serviços, uma vez que tenha sido
	 * adicionado será possível controlar o mesmo através do sistema de serviços.
	 * @param service referência do serviço a ser adicionado.
	 */

	public void add(Service service)
	{
		if (service == null)
			logWarning("Service System: serviço nulo.\n");

		else if (service.getState() != Service.SERVICE_UNDEFINID)
			logWarning("Service System: serviço inválido (%s).\n", service.getIdentificator());

		else if (services.add(service.getIdentificator(), service))
			logNotice("Service System: registro feito com êxito (%s).\n", service.getIdentificator());

		else
			logError("Service System: falha no regitros(%s).\n", service.getIdentificator());
	}

	/**
	 * Deve excluir um serviço existente da lista de serviços, uma vez que tenha sido
	 * excluído não será possível fazer a utilização do mesmo pelo sistema de serviço.
	 * @param service referência do serviço a ser excluído.
	 */

	public void delete(Service service)
	{
		if (service == null);

		switch (service.getState())
		{
			case Service.SERVICE_STARTED:
			case Service.SERVICE_RUNNING:
				logNotice("Service System: serviço rodando (%s).\n", service.getIdentificator());
				return;

			case Service.SERVICE_STOPED:
				logNotice("Service System: serviço parado (%s).\n", service.getIdentificator());
				return;

			case Service.SERVICE_TERMINATED:
				logNotice("Service System: serviço em término (%s).\n", service.getIdentificator());
				return;
		}

		if (services.removeKey(service.getIdentificator()))
			logNotice("Service System: serviço excluído (%s).\n", service.getIdentificator());
		else
			logWarning("Service System: seviço não encontrado (%s).\n", service.getIdentificator());
	}

	/**
	 * Permite iniciar um determinado serviço especificado pelo identificador.
	 * Se houver falha na interrupção é possível obter o erro por getException().
	 * @param identification identificador que foi definido ao serviço.
	 * @return true se conseguiu interromper ou false caso contrário.
	 */

	public boolean start(String identification)
	{
		Service service = services.get(identification);

		if (service == null)
			return false;

		try {
			service.start();
			return true;
		} catch (ServiceException e) {
			putException(e);
			return false;
		}
	}

	/**
	 * Permite terminar um determinado serviço especificado pelo identificador.
	 * Se houver falha na interrupção é possível obter o erro por getException().
	 * @param identification identificador que foi definido ao serviço.
	 * @return true se conseguiu interromper ou false caso contrário.
	 */

	public boolean terminate(String identification)
	{
		Service service = services.get(identification);

		if (service == null)
			return false;

		try {
			service.terminate();
			return true;
		} catch (ServiceException e) {
			putException(e);
			return false;
		}
	}

	/**
	 * Permite interromper um determinado serviço especificado pelo identificador.
	 * Se houver falha na interrupção é possível obter o erro por getException().
	 * @param identification identificador que foi definido ao serviço.
	 * @return true se conseguiu interromper ou false caso contrário.
	 */

	public boolean interrupted(String identification)
	{
		Service service = services.get(identification);

		if (service == null)
			return false;

		try {
			service.interrupted();
			return true;
		} catch (ServiceException e) {
			putException(e);
			return false;
		}
	}

	/**
	 * ServiceSystem utiliza o padrão de projetos Singleton tendo apenas uma instância.
	 * Utilizado para que não seja possível criar um outro sistema para entrada.
	 * @return aquisição do gerenciador de entradas do engine.
	 */

	public static ServiceSystem getInstance()
	{
		return INSTANCE;
	}
}
