package org.diverproject.util;

/**
 * <p><h1>Bit Wise</h1></p>
 *
 * <p>O sistema de bit mask é usado para determinar opções preferenciais de modo mais prático.
 * Para usar opções na programação onde algo é ativado ou desativado normalmente se usa o 'booleano'.
 * Valores booleanos podem assumir tanto true quando false (ativado/desativado, sim/não, verdadeiro/falso).
 * Na memória isso irá ocupar exatamente 1 byte para cada valor booleano o que normalmente é usado no fim.</p>
 *
 * <p>Mas algumas vezes queremos usar mais do que uma única opção para determinar preferências a algo.
 * Quando encontramos uma possibilidade de usar mais de quatro opções é recomendável usar o 'bit mask'.
 * Bit Mask nada mais é do que considerar um valor numérico inteiro ou longo (int/long) para true/false.
 * Para cada bit no número considerado, será determinado uma opção true ou false para o índice do bit.</p>
 *
 * <p>Por exemplo, consideramos os seguintes números abaixo e em seguida o seus bits separados.</p>
 *
 * <p>Hexa | Dec: [Bit 1] [Bit 2] [Bit 3] [Bit 4]<br>
 *<br>
 * <b>0x00 | 00:</b> [ 0 ] [ 0 ] [ 0 ] [ 0 ]<br>
 * <b>0x01 | 01:</b> [ 0 ] [ 0 ] [ 0 ] [ 1 ]<br>
 * <b>0x02 | 02:</b> [ 0 ] [ 0 ] [ 1 ] [ 0 ]<br>
 * <b>0x03 | 03:</b> [ 0 ] [ 0 ] [ 1 ] [ 1 ]<br>
 * <b>0x04 | 04:</b> [ 0 ] [ 1 ] [ 0 ] [ 0 ]<br>
 * <b>0x05 | 05:</b> [ 0 ] [ 1 ] [ 0 ] [ 1 ]<br>
 * <b>0x06 | 06:</b> [ 0 ] [ 1 ] [ 1 ] [ 0 ]<br>
 * <b>0x07 | 07:</b> [ 0 ] [ 1 ] [ 1 ] [ 1 ]<br>
 * <b>0x08 | 08:</b> [ 1 ] [ 0 ] [ 0 ] [ 0 ]<br>
 * <b>0x09 | 09:</b> [ 1 ] [ 0 ] [ 0 ] [ 1 ]<br>
 * <b>0x0A | 10:</b> [ 1 ] [ 0 ] [ 1 ] [ 0 ]<br>
 * <b>0x0B | 11:</b> [ 1 ] [ 0 ] [ 1 ] [ 1 ]<br>
 * <b>0x0C | 12:</b> [ 1 ] [ 1 ] [ 0 ] [ 0 ]<br>
 * <b>0x0D | 13:</b> [ 1 ] [ 1 ] [ 0 ] [ 1 ]<br>
 * <b>0x0E | 14:</b> [ 1 ] [ 1 ] [ 1 ] [ 0 ]<br>
 * <b>0x0F | 15:</b> [ 1 ] [ 1 ] [ 1 ] [ 1 ]</p>
 *
 * <p>Se considerarmos cada Bit (1~4) como uma opção podemos aqui em 4bits determinar 4 opções.
 * Onde todas as possibilidades de escolhas entre as ativar/desativar as 4 opções podem ser consideradas.
 * Essas considerações estão em um intervalo entre os valores 0x00 (0) até 0x0F (15) sem faltar combinações.</p>
 *
 * @author Andrew
 */

public class BitWise
{
	/**
	 * Vetor que irá guardar o nome das propriedades.
	 */
	private String propertieNames[];

	/**
	 * Valor do qual 
	 */
	private int value;

	/**
	 * Constrói um novo BitWise permitindo definir o nome das propriedades.
	 * @param propertieNames nome das propriedades separados por vírgula.
	 */

	public BitWise(String... propertieNames)
	{
		this.propertieNames = propertieNames;
	}

	/**
	 * As propriedades definidas a esse bitwise são guardados nesse valor.
	 * @return aquisição do valor numérico inteiro das propriedades.
	 */

	public int getValue()
	{
		return value;
	}

	/**
	 * Permite definir um valor as propriedades através dos bits de um número inteiro.
	 * @param value valor inteiro que irá guardar as propriedades desse bitwise.
	 */

	public void setValue(int value)
	{
		this.value = value;
	}

	/**
	 * Verifica se esse bitwise atende uma ou mais propriedades com o seu valor atual.
	 * Irá verificar cada bit do número passado, considerando apenas os binários 1.
	 * Pode passar mais de um valor utilizando separador | como é usado em java.
	 * @param propertie valor da propriedade que será verificada se contém.
	 * @return true se contiver todas as propriedades passadas ou false caso contrário.
	 */

	public boolean is(int propertie)
	{
		return (value & propertie) == propertie;
	}

	/**
	 * Define uma ou mais propriedades de acordo com a necessidade e objetivo de uso.
	 * Irá verificar cada bit do número passado, considerando apenas os binários 1.
	 * Pode passar mais de um valor utilizando separador | como é usado em java.
	 * Caso a propriedade já tenha sido definida irá continuar como definida.
	 * @param propertie valor da propriedade que será definido ao bitwise.
	 */

	public void set(int propertie)
	{
		value |= propertie;
	}

	/**
	 * Desconsidera uma ou mais propriedades de acordo com a necessidade e objetivo de uso.
	 * Irá verificar cada bit do número passado, considerando apenas os binários 1.
	 * Pode passar mais de um valor utilizando separador | como é usado em java.
	 * Caso a propriedade não tenha sido definida essa irá continuar sem definir.
	 * @param propertie valor da propriedade que será removido do bitwise.
	 */

	public void unset(int propertie)
	{
		value -= value & propertie;
	}

	/**
	 * Constrói uma string que irá guardar todas as propriedades descritivas desse bitwise.
	 * Irá considerar o nome das propriedades passado no construtor ou então caso não
	 * tenha sido definido ou não exista irá usar o padrão BIT{número do bit}.
	 * @return string contendo todas as propriedades definidas separadas por vírgula.
	 */

	public String toStringProperties()
	{
		StringBuilder str = new StringBuilder();

		if (propertieNames.length == 0 || propertieNames == null)
			str.append(Integer.toBinaryString(value));

		else
		{
			String binary = Integer.toBinaryString(value);

			for (int i = 0; i < binary.length(); i++)
			{
				if (binary.charAt(binary.length() - 1 - i) == '0')
					continue;

				if (i >= propertieNames.length)
					str.append("BIT" +i);

				else
					str.append(propertieNames[i]);

				if (i < binary.length() - 1)
					str.append(", ");
			}
		}

		return str.toString();
	}

	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder("BitWise");

		str.append("[");
		str.append(toStringProperties());
		str.append("]");

		return str.toString();
	}

	/**
	 * Verifica se esse bitwise atende uma ou mais propriedades com o seu valor atual.
	 * Irá verificar cada bit do número passado, considerando apenas os binários 1.
	 * Pode passar mais de um valor utilizando separador | como é usado em java.
	 * @param value valor od qual será considerado na verificação de existência.
	 * @param propertie valor da propriedade que será verificada se contém.
	 * @return true se contiver todas as propriedades passadas ou false caso contrário.
	 */

	public static boolean is(int value, int propertie)
	{
		return (value & propertie) == propertie;
	}

	/**
	 * Define uma ou mais propriedades de acordo com a necessidade e objetivo de uso.
	 * Irá verificar cada bit do número passado, considerando apenas os binários 1.
	 * Pode passar mais de um valor utilizando separador | como é usado em java.
	 * Caso a propriedade já tenha sido definida irá continuar como definida.
	 * @param value valor od qual será alterado conforme a propriedade passada.
	 * @param propertie valor da propriedade que será definido ao bitwise.
	 */

	public static int set(int value, int propertie)
	{
		return (value |= propertie);
	}

	/**
	 * Desconsidera uma ou mais propriedades de acordo com a necessidade e objetivo de uso.
	 * Irá verificar cada bit do número passado, considerando apenas os binários 1.
	 * Pode passar mais de um valor utilizando separador | como é usado em java.
	 * Caso a propriedade não tenha sido definida essa irá continuar sem definir.
	 * @param value valor od qual será alterado conforme a propriedade passada.
	 * @param propertie valor da propriedade que será removido do bitwise.
	 */

	public static int unset(int value, int propertie)
	{
		return (value -= value & propertie);
	}
}
