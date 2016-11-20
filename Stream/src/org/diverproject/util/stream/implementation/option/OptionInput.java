package org.diverproject.util.stream.implementation.option;

/**
 * <p><h1>Stream para Opções</h1></p>
 *
 * <p>Deve implementar métodos que permite obter valores através de chaves.
 * Essas chaves são salvos para referenciar um determinado dado na stream.
 * Através dessas chaves é possível obter-se um determinado dano.</p>
 *
 * @author Andrew
 */

public interface OptionInput extends StreamOption
{
	/**
	 * Pega a próxima opção sendo esperado que seja do tipo byte.
	 * @param name nome da opção seguinte escrita na stream.
	 * @param notfound valor que será retornado se não encontrar a opção.
	 * @return valor que foi definido a essa opção quando salvo.
	 */

	byte getByte(String name, byte notfound);

	/**
	 * Pega a próxima opção sendo esperado que seja do tipo char.
	 * @param name nome da opção seguinte escrita na stream.
	 * @param notfound valor que será retornado se não encontrar a opção.
	 * @return valor que foi definido a essa opção quando salvo.
	 */

	char getChar(String name, char notfound);

	/**
	 * Pega a próxima opção sendo esperado que seja do tipo short.
	 * @param name nome da opção seguinte escrita na stream.
	 * @param notfound valor que será retornado se não encontrar a opção.
	 * @return valor que foi definido a essa opção quando salvo.
	 */

	short getShort(String name, short notfound);

	/**
	 * Pega a próxima opção sendo esperado que seja do tipo int.
	 * @param name nome da opção seguinte escrita na stream.
	 * @param notfound valor que será retornado se não encontrar a opção.
	 * @return valor que foi definido a essa opção quando salvo.
	 */

	int getInt(String name, int notfound);

	/**
	 * Pega a próxima opção sendo esperado que seja do tipo long.
	 * @param name nome da opção seguinte escrita na stream.
	 * @return valor que foi definido a essa opção quando salvo.
	 */

	long getLong(String name, long notfound);

	/**
	 * Pega a próxima opção sendo esperado que seja do tipo float.
	 * @param name nome da opção seguinte escrita na stream.
	 * @param notfound valor que será retornado se não encontrar a opção.
	 * @return valor que foi definido a essa opção quando salvo.
	 */

	float getFloat(String name, float notfound);

	/**
	 * Pega a próxima opção sendo esperado que seja do tipo double.
	 * @param name nome da opção seguinte escrita na stream.
	 * @param notfound valor que será retornado se não encontrar a opção.
	 * @return valor que foi definido a essa opção quando salvo.
	 */

	double getDouble(String name, double notfound);

	/**
	 * Pega a próxima opção sendo esperado que seja do tipo String.
	 * @param name nome da opção seguinte escrita na stream.
	 * @param notfound valor que será retornado se não encontrar a opção.
	 * @return valor que foi definido a essa opção quando salvo.
	 */

	String getString(String name, String notfound);

	/**
	 * Pega a próxima opção sendo esperado que seja do tipo boolean.
	 * @param name nome da opção seguinte escrita na stream.
	 * @return valor que foi definido a essa opção quando salvo.
	 */

	boolean getBoolean(String name, boolean notfound);
}
