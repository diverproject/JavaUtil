package org.diverproject.log;

/**
 * <h1>Listener para Registro</h1>
 *
 * <p>Esse listener é usado por LogSystem para que os registros detectados sejam repassados.
 * Assim qualquer projeto poderá usar os registros detectadas da forma que preferir.
 * Podendo adicionar ao console de desenvolvimento ou um console criado no projeto.
 * Sendo ainda de opção apenas salvar as informações registradas em um arquivo.</p>
 *
 * @see Log
 *
 * @author Andrew
 */

public interface LogListener
{
	/**
	 * Se registrado no sistema de log será chamado sempre que um novo log for detectado.
	 * Log se refere a uma informação ou ocorrido do qual o sistema deseja registrar.
	 * @param log referência do objeto contendo todos os detalhes a serem registrados.
	 */

	void onMessage(Log log);
}
