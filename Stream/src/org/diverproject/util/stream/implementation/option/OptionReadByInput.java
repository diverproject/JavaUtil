package org.diverproject.util.stream.implementation.option;

import org.diverproject.util.stream.Input;
import org.diverproject.util.stream.StreamException;
import org.diverproject.util.stream.StreamRuntimeException;

/**
 * <h1>Opções Lidas por Input</h1>
 *
 * <p>Classe com implementação básica para se ler opções armazenadas em bytes de um input.
 * Os dados são lidos e se tenta criar opções a partir destes e os despacha em seguida.
 * O despache é feito através de um método abstrato que será por onde as opções serão vistas.</p>
 *
 * @see OptionInput
 * @see Input
 *
 * @author Andrew
 */

public abstract class OptionReadByInput implements OptionInput
{
	/**
	 * Cria um novo leitor de opções através de um objeto com entrada de dados em bytes.
	 * @param input referência da stream que irá conter os dados das opções a ser lidas.
	 */

	public OptionReadByInput(Input input)
	{
		while (input.space() > 0)
		{
			int offset = input.offset();
			StreamOptionValue<?> value = readNext(input);

			if (value == null)
				throw new StreamRuntimeException("opção inválida (offset: %d)", offset);

			dispatch(value);
		}
	}

	/**
	 * Procedimento chamado internamente pelo construtor sempre que uma nova opção é encontrada.
	 * Todas opções lidas do input passado por parâmetro no construtor será despachado aqui.
	 * @param option referência do objeto contendo as informações da opção que foi lida.
	 */

	protected abstract void dispatch(StreamOptionValue<?> option);

	/**
	 * Deve fazer a leitura da próxima opção na stream de entrada de dados do arquivo especificado.
	 * @param input referência da stream para entrada de dados do arquivo especificado.
	 * @return objeto que irá armazenar o nome da opção e o seu valor ou null se for inválido.
	 * @throws StreamException arquivo inexistente, ser um diretório ou falha na leitura.
	 */

	private StreamOptionValue<?> readNext(Input input)
	{
		switch (input.getByte())
		{
			case OPTION_BYTE: return readOptionByte(input);
			case OPTION_CHAR: return readOptionChar(input);
			case OPTION_SHORT: return readOptionShort(input);
			case OPTION_INT: return readOptionInt(input);
			case OPTION_LONG: return readOptionLong(input);
			case OPTION_FLOAT: return readOptionFloat(input);
			case OPTION_DOUBLE: return readOptionDouble(input);
			case OPTION_STRING: return readOptionString(input);
			case OPTION_BOOLEAN: return readOptionBoolean(input);
		}

		return null;
	}

	/**
	 * Deve fazer a leitura da próxima opção disponível no arquivo (stream para entrada).
	 * @param input referência da stream para entrada de dados do arquivo especificado.
	 * @return objeto opção do tipo byte contendo o seu valor respectivo.
	 * @throws StreamException arquivo inexistente, ser um diretório ou falha na leitura.
	 */

	private StreamOptionValue<Byte> readOptionByte(Input input)
	{
		return new StreamOptionValue<Byte>(input.getString(), input.getByte());
	}

	/**
	 * Deve fazer a leitura da próxima opção disponível no arquivo (stream para entrada).
	 * @param input referência da stream para entrada de dados do arquivo especificado.
	 * @return objeto opção do tipo char contendo o seu valor respectivo.
	 * @throws StreamException arquivo inexistente, ser um diretório ou falha na leitura.
	 */

	private StreamOptionValue<Character> readOptionChar(Input input)
	{
		return new StreamOptionValue<Character>(input.getString(), input.getChar());
	}

	/**
	 * Deve fazer a leitura da próxima opção disponível no arquivo (stream para entrada).
	 * @param input referência da stream para entrada de dados do arquivo especificado.
	 * @return objeto opção do tipo short contendo o seu valor respectivo.
	 * @throws StreamException arquivo inexistente, ser um diretório ou falha na leitura.
	 */

	private StreamOptionValue<Short> readOptionShort(Input input)
	{
		return new StreamOptionValue<Short>(input.getString(), input.getShort());
	}

	/**
	 * Deve fazer a leitura da próxima opção disponível no arquivo (stream para entrada).
	 * @param input referência da stream para entrada de dados do arquivo especificado.
	 * @return objeto opção do tipo int contendo o seu valor respectivo.
	 * @throws StreamException arquivo inexistente, ser um diretório ou falha na leitura.
	 */

	private StreamOptionValue<Integer> readOptionInt(Input input)
	{
		return new StreamOptionValue<Integer>(input.getString(), input.getInt());
	}

	/**
	 * Deve fazer a leitura da próxima opção disponível no arquivo (stream para entrada).
	 * @param input referência da stream para entrada de dados do arquivo especificado.
	 * @return objeto opção do tipo long contendo o seu valor respectivo.
	 * @throws StreamException arquivo inexistente, ser um diretório ou falha na leitura.
	 */

	private StreamOptionValue<Long> readOptionLong(Input input)
	{
		return new StreamOptionValue<Long>(input.getString(), input.getLong());
	}

	/**
	 * Deve fazer a leitura da próxima opção disponível no arquivo (stream para entrada).
	 * @param input referência da stream para entrada de dados do arquivo especificado.
	 * @return objeto opção do tipo float contendo o seu valor respectivo.
	 * @throws StreamException arquivo inexistente, ser um diretório ou falha na leitura.
	 */

	private StreamOptionValue<Float> readOptionFloat(Input input)
	{
		return new StreamOptionValue<Float>(input.getString(), input.getFloat());
	}

	/**
	 * Deve fazer a leitura da próxima opção disponível no arquivo (stream para entrada).
	 * @param input referência da stream para entrada de dados do arquivo especificado.
	 * @return objeto opção do tipo double contendo o seu valor respectivo.
	 * @throws StreamException arquivo inexistente, ser um diretório ou falha na leitura.
	 */

	private StreamOptionValue<Double> readOptionDouble(Input input)
	{
		return new StreamOptionValue<Double>(input.getString(), input.getDouble());
	}

	/**
	 * Deve fazer a leitura da próxima opção disponível no arquivo (stream para entrada).
	 * @param input referência da stream para entrada de dados do arquivo especificado.
	 * @return objeto opção do tipo string contendo o seu valor respectivo.
	 * @throws StreamException arquivo inexistente, ser um diretório ou falha na leitura.
	 */

	private StreamOptionValue<String> readOptionString(Input input)
	{
		return new StreamOptionValue<String>(input.getString(), input.getString());
	}

	/**
	 * Deve fazer a leitura da próxima opção disponível no arquivo (stream para entrada).
	 * @param input referência da stream para entrada de dados do arquivo especificado.
	 * @return objeto opção do tipo boolean contendo o seu valor respectivo.
	 * @throws StreamException arquivo inexistente, ser um diretório ou falha na leitura.
	 */

	private StreamOptionValue<Boolean> readOptionBoolean(Input input)
	{
		return new StreamOptionValue<Boolean>(input.getString(), input.getByte() == 1);
	}
}
