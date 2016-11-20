package org.diverproject.util.stream.implementation;

import org.diverproject.util.stream.Buffer;
import org.diverproject.util.stream.implementation.buffer.BufferArrayData;

/**
 * <h1>Construtor de Buffer</h1>
 *
 * <p>Criado para funcionar como meio intermediário entre a criação da comunicação e a fábrica.
 * Possuindo apenas os métodos que por nome a parâmetros é possível entender sua funcionalidade.
 * Assim, será possível "esconder" a real origem das comunicações que foram usadas por ele.</p>
 *
 * <p>Interface que possui todos os métodos possíveis para a criação de comunicação de dados.
 * A única forma de se criar um buffer é através de um vetor de bytes permitindo escrita e leitura.
 * A forma como é feito a escrita dos dados será respectiva de acordo com a fábrica que gerou este.</p>
 *
 * @see Buffer
 *
 * @author Andrew Mello
 */

public class BufferBuilder
{
	/**
	 * Cria uma nova comunicação buffer a partir de um vetor de bytes para entrada e saída de dados.
	 * @param data referência do vetor que será usado tanto para a leitura quando escrita dos dados.
	 * @return aquisição de uma nova comunicação buffer usando o vetor especificado.
	 */

	public Buffer newBuffer(byte[] data)
	{
		return new BufferArrayData(data);
	}
}
