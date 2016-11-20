package org.diverproject.util.stream.implementation;

/**
 * <h1>Fábrica de Stream</h1>
 *
 * <p>Stream é um meio de comunicação que no caso será de dados em bytes com a aplicação desenvolvida.
 * Essa comunicação irá permitir interpretar dados <b>crus</b> em puro bytes para dados primitivos.
 * Tais como valores numéricos inteiros (short, int e long) como flutuantes (float e double).</p>
 *
 * <p>A fábrica vai funcionar como uma espécie de fronteira entre o desenvolvimento e esses serviços.
 * Através dele será possível obter as montadoras que irão de fato criar as streams de acordo com o desejado.
 * No caso pode ser um arquivo pelo seu caminho especificado ou ainda então um objeto do tipo File.
 * Como ainda permite trabalhar com InputStream e OutputStream e também usando um vetor de bytes como dados.</p>
 *
 * <p>A utilização do Factory Method foi uma das formas de aumentar o encapsulamento das classes envolvidas.
 * De modo que, não seja possível acessar estas diretamente (instanciar) como se quer visualizar estas.
 * Por tanto, a visualização ficará como responsabilidade das interfaces que irão redirecionar aos métodos.</p>
 *
 * @see BufferBuilder
 * @see InputBuilder
 * @see OutputBuilder
 *
 * @author Andrew Mello
 */

public class StreamFactory
{
	/**
	 * Cria uma nova instância para o montador de streams para trabalhar diretamente com buffers.
	 * Buffers aqui será considerado como um vetor de bytes com conteúdo ou totalmente em branco.
	 * Os buffers também não irão copiar o vetor, irão utilizá-lo diretamente com fonte de dados.
	 * @return referência do montador de buffers para interpretação de dados em bytes.
	 */

	public BufferBuilder newBufferBuilder()
	{
		return new BufferBuilder();
	}

	/**
	 * Cria uma nova instância para o montador de streams para trabalhar com streams do Java.
	 * Essas streams são referentes apenas ao lado de entrada de dados, ou seja, a leitura destes.
	 * Streams desse tipo podem ser originadas ou por um InputStream ou então por um arquivo.
	 * @return referência do montador de entradas para interpretação de dados em bytes.
	 */

	public InputBuilder newInputBuilder()
	{
		return new InputBuilder();
	}

	/**
	 * Cria uma nova instância para o montador de streams para trabalhar com streams do Java.
	 * Essas streams são referentes apenas ao lado de saída de dados, ou seja, escrevê-los.
	 * Streams desse tipo podem ser originadas ou por um OutputStream ou para escrever arquivos.
	 * @return referência do montador de saídas da interpretação de dados em bytes.
	 */

	public OutputBuilder newOutputBuilder()
	{
		return new OutputBuilder();
	}

	/**
	 * Cria uma nova instância para o montador de streams para trabalhar com opções.
	 * Essas streams são referentes tanto ao lado de leitura de opções como gravação delas.
	 * @return referência do montador de stream para interpretação de opções como bytes.
	 */

	public OptionBuilder newOptionBuilder()
	{
		return new OptionBuilder();
	}
}
