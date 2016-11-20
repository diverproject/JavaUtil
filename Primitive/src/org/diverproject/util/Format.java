package org.diverproject.util;

import org.diverproject.util.collection.StringExtend;
import org.diverproject.util.collection.abstraction.DynamicList;
import org.diverproject.util.lang.BooleanUtil;
import org.diverproject.util.lang.ByteUtil;
import org.diverproject.util.lang.DoubleUtil;
import org.diverproject.util.lang.FloatUtil;
import org.diverproject.util.lang.IntUtil;
import org.diverproject.util.lang.LongUtil;
import org.diverproject.util.lang.ShortUtil;
import org.diverproject.util.lang.StringUtil;

public class Format
{
	/**
	 * Como uma coluna deve ser definido para ser byte.
	 */
	public static final char TYPE_BYTE = 't';

	/**
	 * Como uma coluna deve ser definido para ser character.
	 */
	public static final char TYPE_CHAR = 'c';

	/**
	 * Como uma coluna deve ser definido para ser short.
	 */
	public static final char TYPE_SHORT = 'h';

	/**
	 * Como uma coluna deve ser definido para ser int.
	 */
	public static final char TYPE_INT = 'i';

	/**
	 * Como uma coluna deve ser definido para ser long.
	 */
	public static final char TYPE_LONG = 'l';

	/**
	 * Como uma coluna deve ser definido para ser float.
	 */
	public static final char TYPE_FLOAT = 'f';

	/**
	 * Como uma coluna deve ser definido para ser double.
	 */
	public static final char TYPE_DOUBLE = 'd';

	/**
	 * Como uma coluna deve ser definido para ser string.
	 */
	public static final char TYPE_STRING = 's';

	/**
	 * Como uma coluna deve ser definido para ser boolean.
	 */
	public static final char TYPE_BOOLEAN = 'b';

	/**
	 * Como uma coluna deve ser definido para ser vetor de tamanho fixo.
	 */
	public static final char TYPE_ARRAY_STATIC = 'a';

	/**
	 * Como uma coluna deve ser definido para ser matriz.
	 */
	public static final char TYPE_MATRIX = 'm';

	/**
	 * Como uma Coluna deve ser definido para ser vetor de tamanho variável.
	 */
	public static final char TYPE_ARRAY_DINAMIC = 'n';

	/**
	 * Como o restante de uma formatação deve ser considerada alternativa.
	 */
	public static final char TYPE_ALTERNATIVE = '?';

	/**
	 * Vetor contendo todos os tipos de dados que são permitidos (caracteres).
	 */

	public static final char VALID_TYPES[] = new char[]
	{
		TYPE_BYTE, TYPE_CHAR, TYPE_SHORT, TYPE_INT, TYPE_LONG, TYPE_FLOAT, TYPE_DOUBLE,
		TYPE_STRING, TYPE_BOOLEAN, TYPE_ARRAY_STATIC, TYPE_ARRAY_DINAMIC
	};

	/**
	 * Código para identificar o tipo de dado como desconhecido.
	 */
	public static final int CLASS_REPEAT = 0;

	/**
	 * Código para identificar o tipo de dado como primitivo.
	 */
	public static final int CLASS_PRIMITIVE = 1;

	/**
	 * Código para identificar o tipo de dado com vetor.
	 */
	public static final int CLASS_ARRAY = 2;

	/**
	 * Código para identificar o tipo de dado com alternativo.
	 */
	public static final int CLASS_ALTERNATIVE = 3;

	/**
	 * Código para identificar o tipo de dado com conjunto (parentese, colchetes ou chaves).
	 */
	public static final int CLASS_BRACE = 4;

	/**
	 * Código para identificar o tipo de dado como matriz.
	 */
	public static final int CLASS_MATRIX = 5;

	/**
	 * Propriedade que irá ignorar espaçamentos no inicio e fim dos valores obtidos.
	 */
	public static final int PROPERTIE_TRIM = 0x01;

	/**
	 * Propriedade que irá aceitar colunas que não possuem nem mesmo espaços em branco.
	 */
	public static final int PROPERTIE_BLANK_VALUES = 0x02;

	/**
	 * Propriedade que irá aceitar colunas apenas com espaçamentos.
	 */
	public static final int PROPERTIE_BLANK_COLUMNS = 0x04;

	/**
	 * Propriedade que irá considerar espaços largos como espaços simples.
	 */
	public static final int PROPERTIE_TAB_AS_SPACE = 0x08;

	/**
	 * Propriedade que irá considerar espaços como dados nas colunas.
	 */
	public static final int PROPERTIE_SPACE_IN_COLUMN = 0x10;

	/**
	 * String contendo as regras de formatação.
	 */
	private String format;

	/**
	 * Coluna que está sendo atualmente processada.
	 */
	private int col;

	/**
	 * Quais as propriedades que serão aplicadas.
	 */
	private BitWise properties;

	/**
	 * Determina se o formato já está como alternativo (não obrigatório).
	 */
	private boolean alternative;

	/**
	 * Último separador que foi encontrado (usado para garantir valores vazios no final do conteúdo).
	 */
	private char lastSeparator;

	/**
	 * Conteúdo que está sendo considerado para fazer a formatação.
	 */
	private StringExtend nodeFormat;

	/**
	 * Conteúdo que está sendo considerado para ser analisado de acordo com a formatação.
	 */
	private StringExtend nodeContent;

	/**
	 * Lista que irá armazenar todos os objetos já analisados.
	 */
	private DynamicList<Object> objects;

	/**
	 * Constrói uma nova formatação iniciando a lista de objetos analisados e propriedades.
	 * Por padrão nenhuma propriedade é ativada durante a instância sendo necessário definir.
	 */

	public Format()
	{
		properties = new BitWise("TRIM", "BLANK_VALUES", "BLANK_COMUNS", "TAB_AS_SPACE", "PROPERTIE_SPACE_IN_COLUMN");
		objects = new DynamicList<Object>();
	}

	/**
	 * Permite definir as regras a serem utilizadas para a formatação.
	 * @param format string contendo as regras de formatação.
	 * @return vetor contendo o nome de cada campo definido.
	 * @throws UtilException formato inválido ou já definido.
	 */

	public String[] setFormat(String format) throws UtilException
	{
		if (this.format != null)
			throw new UtilException("formato já definido");

		String[] types = isFormat(format);

		this.format = format;

		return types;
	}

	/**
	 * Remover espaços no inicio e no fim do conteúdo de uma coluna.
	 * @param enable true para habilitar ou false para desabilitar.
	 */

	public void setTrim(boolean enable)
	{
		if (enable)
			properties.set(PROPERTIE_TRIM);
		else
			properties.unset(PROPERTIE_TRIM);
	}

	/**
	 * Aceitar colunas que não contenham valores em branco (sem bytes).
	 * Nesse caso os valores serão obtidos como objetos em branco ou zero.
	 * @param enable true para habilitar ou false para desabilitar.
	 */

	public void setAcceptBlankValues(boolean enable)
	{
		if (enable)
			properties.set(PROPERTIE_BLANK_VALUES);
		else
			properties.unset(PROPERTIE_BLANK_VALUES);
	}

	/**
	 * Aceitar colunas que contenham apenas valores em branco (apenas espaços).
	 * Nesse caso os valores serão obtidos como objetos em branco ou zero.
	 * @param enable true para habilitar ou false para desabilitar.
	 */

	public void setAcceptBlankColumns(boolean enable)
	{
		if (enable)
			properties.set(PROPERTIE_BLANK_COLUMNS);
		else
			properties.unset(PROPERTIE_BLANK_COLUMNS);
	}

	/**
	 * Considerar espaçamentos com tabs como espaços em branco '\t' e ' '.
	 * @param enable true para habilitar ou false para desabilitar.
	 */

	public void setSpaceTab(boolean enable)
	{
		if (enable)
			properties.set(PROPERTIE_TAB_AS_SPACE);
		else
			properties.unset(PROPERTIE_TAB_AS_SPACE);
	}

	/**
	 * Permite obter objetos analisados a partir de um formato definido e conteúdo de uma string.
	 * @param content conteúdo do qual deverá ser analisado de acordo com a formatação.
	 * @return vetor contendo os objetos feitos da análise.
	 * @throws UtilException apenas se o formato for inválido.
	 */

	public Object[] parse(String content) throws UtilException
	{
		objects.clear();

		StringUtil.checkParse(format);
		StringUtil.checkParse(content);

		nodeFormat = new StringExtend(format);
		nodeContent = new StringExtend(content);

		try {

			for (col = 0; !nodeFormat.finish();)
			{
				if (nodeFormat.get() == TYPE_ALTERNATIVE || nodeFormat.get(nodeFormat.offset() + 1) == TYPE_ALTERNATIVE)
					alternative = true;

				switch (checkChar(nodeFormat.get()))
				{
					case CLASS_ALTERNATIVE:
						parseAlternative();
						continue;

					case CLASS_BRACE:
					case CLASS_REPEAT:
						parseRepeatClass();
						break;

					case CLASS_PRIMITIVE:
						col++;
						parsePrimitiveClass();
						break;

					case CLASS_ARRAY:
						col++;
						parseArrayClass();
						break;

					case CLASS_MATRIX:
						col++;
						parseMatrixClass();
						break;
				}

				if (parseEndLoop())
					break;
			}

		} catch (UtilException e) {
			throw new UtilException(e, "coluna: %d", col);
		}

		if (!nodeContent.finish())
			throw new UtilException("conteúdo sobrando");

		return objects.toArray();
	}

	/**
	 * Procedimento que irá fazer a análise do conteúdo com o formado para verificar se é alternativo.
	 * Quanto alternativo, significa que uma parte da formatação não é obrigatória no conteúdo.
	 * Caso o conteúdo já tenha sido terminado então irá encerrar a iteração da formatação.
	 */

	private void parseAlternative()
	{
		if (!nodeContent.finish())
		{
			nodeFormat.next();
			return;
		}

		nodeFormat.terminate();
	}

	/**
	 * Procedimento que irá fazer a análise do conteúdo com o formato para verificar repetição.
	 * Repetição é usada quando caracteres no conteúdo devem ser iguais ao da formatação.
	 * Aqui faz a verificação para identificação de espaçamentos como espaços simples.
	 * @throws UtilException 
	 */

	private void parseRepeatClass() throws UtilException
	{
		if (nodeFormat.get() != nodeContent.get())
			throw new UtilException("caracter inesperado (esperado: %s, encontrado: %s)", nodeFormat.get(), nodeContent.get());

		nodeFormat.next();
		nodeContent.next();
	}

	/**
	 * Chamado sempre que detectar uma formatação que deve ser analisado um dado primitivo.
	 * @throws UtilException ocorre apenas por falha na formatação.
	 */

	private void parsePrimitiveClass() throws UtilException
	{
		char primitive = nodeFormat.nextChar();
		char separator = lastSeparator = nodeFormat.nextChar();

		if (separator == TYPE_ALTERNATIVE)
			separator = lastSeparator = nodeFormat.nextChar();

		Object object = parsePrimitiveContent(primitive, separator, separator);
		objects.add(object);

		nodeContent.next();
	}

	/**
	 * Chamado sempre que detectar uma formatação que deve ser construído um vetor de de dados primitivos.
	 * @throws UtilException ocorre apenas por falha na formatação.
	 */

	private void parseArrayClass() throws UtilException
	{
		char arrayType = nodeFormat.nextChar();
		char separator = lastSeparator = nodeFormat.get();

		String number = new String();

		while (nodeFormat.next() && Character.isDigit(nodeFormat.get()))
			number += nodeFormat.get();

		if (!IntUtil.isInteger(number))
			throw new UtilException("tamanho '%s' do vetor inválido", number);

		char primitive = nodeFormat.get();	nodeFormat.next();

		if (checkChar(primitive) != CLASS_PRIMITIVE)
			throw new UtilException("tipo de vetor '%s' desconhecido", primitive);

		char force = nodeFormat.get();

		int size = IntUtil.parse(number);

		if (arrayType == TYPE_ARRAY_DINAMIC)
			parseArrayDinamic(primitive, size, separator, force);

		else
			parseArrayStatic(primitive, size, separator, force);
	}

	/**
	 * Procedimento que irá analisar o conteúdo atualmente usado na formatação para encontrar um vetor dinâmico.
	 * Vetores dinâmicos permitem que os elementos encontrados não sejam de quantidade igual ao seu tamanho.
	 * Assim sendo, é possível existir um número de elementos igual ou menor que o tamanho definido.
	 * @param primitive caracter representado tipo primitivo de dados que será analisado.
	 * @param size número limite de elementos que poderão ser encontrados no conteúdo.
	 * @param separator separador que irá fazer a divisão entre os elementos.
	 * @param force caracter que será usado para indicar o final do vetor.
	 * @throws UtilException apenas se houver muitos elementos.
	 */

	@SuppressWarnings("unchecked")
	private void parseArrayDinamic(char primitive, int size, char separator, char force) throws UtilException
	{
		DynamicList<Object> list = (DynamicList<Object>) gerDynamicList(primitive);

		Object object = parsePrimitiveContent(primitive, separator, force);
		list.add(object);

		for (int i = 0; nodeContent.get() == separator; i++)
		{
			if (!nodeContent.next())
				throw new UtilException("falta conteúdo para vetor dinâmico (i: %d, size: %d)", i, size);

			if (nodeContent.finish())
			{
				if (nodeFormat.offset() == nodeFormat.length() - 1)
				{
					object = getPrimitiveNullContent(nodeFormat.get());
					objects.add(object);
					nodeFormat.next();
				}

				break;
			}

			object = parsePrimitiveContent(primitive, separator, force);
			list.add(object);
		}

		if (list.size() > size)
			throw new UtilException("muitos elementos %d/%d", list.size(), size);

		Object array = list.toArray();
		objects.add(array);
	}

	@SuppressWarnings("unchecked")
	private void parseArrayStatic(char primitive, int size, char separator, char force) throws UtilException
	{
		DynamicList<Object> list = (DynamicList<Object>) gerDynamicList(primitive);

		for (int i = 0; i < size; i++)
		{
			Object object = parsePrimitiveContent(primitive, separator, force);
			list.add(object);

			if (i < size -1)
				nodeContent.next();
		}

		if (nodeContent.finish())
		{
			if (nodeFormat.offset() == nodeFormat.length() - 1 && list.size() != size)
			{
				Object object = getPrimitiveNullContent(nodeFormat.get());
				objects.add(object);
				nodeFormat.next();
			}
		}

		if (list.size() < size)
			throw new UtilException("falta de dados (size: %d, required: %d)", list.size(), size);

		Object array = list.toArray();
		objects.add(array);
	}

	/**
	 * Chamado sempre que detectar uma formatação que deve ser construído uma matriz de de dados primitivos.
	 * @throws UtilException ocorre apenas por falha na formatação.
	 */

	@SuppressWarnings("unchecked")
	private void parseMatrixClass() throws UtilException
	{
		nodeFormat.next();

		char firstSeparator = nodeFormat.get();

		String numberRows = new String();

		while (nodeFormat.next() && Character.isDigit(nodeFormat.get()))
			numberRows += nodeFormat.get();

		if (!IntUtil.isInteger(numberRows))
			throw new UtilException("tamanho '%s' do vetor inválido", numberRows);

		char secondSeparator = nodeFormat.get();
		String numberColumns = new String();

		while (nodeFormat.next() && Character.isDigit(nodeFormat.get()))
			numberColumns += nodeFormat.get();

		if (!IntUtil.isInteger(numberColumns))
			throw new UtilException("tamanho '%s' do vetor inválido", numberColumns);

		char primitive = nodeFormat.get();	nodeFormat.next();
		char force = nodeFormat.get();

		int rows = IntUtil.parse(numberRows);
		int columns = IntUtil.parse(numberColumns);

		DynamicList<DynamicList<?>> matrix = getMatrixList(primitive);
		nodeContent.back();

		for (int i = 0; i < rows && nodeContent.next(); i++)
		{
			DynamicList<Object> list = (DynamicList<Object>) gerDynamicList(primitive);

			for (int j = 0; j < columns && !nodeContent.finish(); j++)
			{
				Object object = parsePrimitiveContent(primitive, secondSeparator, firstSeparator);
				list.add(object);

				if (nodeContent.get() == secondSeparator && nodeContent.next())
					continue;
				else
					break;
			}

			matrix.add(list);

			if (nodeContent.get() == firstSeparator)
				continue;

			else if (nodeContent.get() == force)
				break;
		}

		objects.add(matrix.size());

		for (DynamicList<?> list : matrix)
			objects.add((Object) list.toArray());
	}

	/**
	 * Chamado após a finalização da análise de um caracter analisado como tipo de dado ou repetição.
	 * Sua finalidade é verificar se ainda há ou não conteúdo para ser processado na formatação.
	 * @return true se ainda houver conteúdo para ser processado, seja qual for ele.
	 * @throws UtilException ocorre apenas se a formatação tiver um fim inesperado.
	 */

	private boolean parseEndLoop() throws UtilException
	{
		if (isContentEnd(nodeFormat, nodeContent))
		{
			if (nodeFormat.offset() == nodeFormat.length() - 1)
			{
				Object object = getPrimitiveNullContent(nodeFormat.get());
				objects.add(object);
				nodeFormat.next();

				return true;
			}

			if (!alternative)
				throw new UtilException("conteúdo acabado inesperadamente");

			return true;
		}

		if (properties.is(PROPERTIE_SPACE_IN_COLUMN) || properties.is(PROPERTIE_TRIM))
		{
			while (nodeContent.get() == ' ' || (nodeContent.get() == '\t') && properties.is(PROPERTIE_TAB_AS_SPACE))
				if (!nodeContent.next())
					return true;
		}

		return false;
	}

	/**
	 * Constrói uma nova lista de acordo com um tipo primitivo.
	 * @param primitive caracter respectivo ao tipo primitivo.
	 * @return aquisição da lista do tipo primitivo passado.
	 */

	public static DynamicList<?> gerDynamicList(char primitive)
	{
		DynamicList<?> list;

		switch (primitive)
		{
			case TYPE_BOOLEAN:	list = new DynamicList<Boolean>();		list.setGeneric(Boolean.class);		return list;
			case TYPE_BYTE:		list = new DynamicList<Byte>();			list.setGeneric(Byte.class);		return list;
			case TYPE_CHAR:		list = new DynamicList<Character>();	list.setGeneric(Character.class);	return list;
			case TYPE_SHORT:	list = new DynamicList<Short>();		list.setGeneric(Short.class);		return list;
			case TYPE_INT:		list = new DynamicList<Integer>();		list.setGeneric(Integer.class);		return list;
			case TYPE_LONG:		list = new DynamicList<Long>();			list.setGeneric(Long.class);		return list;
			case TYPE_FLOAT:	list = new DynamicList<Float>();		list.setGeneric(Float.class);		return list;
			case TYPE_DOUBLE:	list = new DynamicList<Double>();		list.setGeneric(Double.class);		return list;
			case TYPE_STRING:	list = new DynamicList<String>();		list.setGeneric(String.class);		return list;
		}

		return new DynamicList<Object>();
	}

	/**
	 * Constrói uma nova matriz de acordo com um tipo primitivo.
	 * @param primitive caracter respectivo ao tipo primitivo.
	 * @return aquisição da matriz do tipo primitivo passado.
	 */

	public static DynamicList<DynamicList<?>> getMatrixList(char primitive)
	{
		DynamicList<DynamicList<?>> list = new DynamicList<DynamicList<?>>();

		switch (primitive)
		{
			case TYPE_BOOLEAN:	list.setGeneric(Boolean.class);		break;
			case TYPE_BYTE:		list.setGeneric(Byte.class);		break;
			case TYPE_CHAR:		list.setGeneric(Character.class);	break;
			case TYPE_SHORT:	list.setGeneric(Short.class);		break;
			case TYPE_INT:		list.setGeneric(Integer.class);		break;
			case TYPE_LONG:		list.setGeneric(Long.class);		break;
			case TYPE_FLOAT:	list.setGeneric(Float.class);		break;
			case TYPE_DOUBLE:	list.setGeneric(Double.class);		break;
			case TYPE_STRING:	list.setGeneric(String.class);		break;
		}

		return list;
	}

	/**
	 * Verifica se uma determinada formatação é válida ou não.
	 * @param format string contendo a formatação que será verificada.
	 * @return vetor contendo o nome dos tipos de dados do formato.
	 * @throws UtilException exceção com mensagem do erro da formatação.
	 */

	public static String[] isFormat(String format) throws UtilException
	{
		StringUtil.checkParse(format);

		StringExtend root = new StringExtend(format);
		DynamicList<String> types = new DynamicList<String>();

		while (!root.finish())
		{
			switch (checkChar(root.get()))
			{
				case CLASS_ALTERNATIVE:
					root.next();
					continue;

				case CLASS_BRACE:
				case CLASS_REPEAT:
					root.next(); // Tipo primitivo
					continue;

				case CLASS_PRIMITIVE:
					isFormatPrimitive(root, types);
					continue;

				case CLASS_ARRAY:
					isFormatArray(root, types);
					continue;

				case CLASS_MATRIX:
					isFormatMatrix(root, types);
					continue;
			}
		}

		types.setGeneric(String.class);

		return types.toArray();
	}

	/**
	 * Procedimento interno usado para fazer a análise de um formato de dados especificado.
	 * Essa análise deve ser chamada somente quando o próximo dado for de um tipo primitivo.
	 * @param root referência da string estendida contendo a formatação dos dados.
	 * @param types referência da lista contendo os tipos de dados já analisados.
	 * @throws UtilException ocorre apenas se houver alguma irregularidade na formatação.
	 */

	private static void isFormatPrimitive(StringExtend root, DynamicList<String> types)
	{
		root.next(); // Tipo primitivo
		root.next(); // Separador

		String type = getTypeName(root.get(root.offset() - 2));
		types.add(type);
	}

	/**
	 * Procedimento interno usado para fazer a análise de um formato de dados especificado.
	 * Essa análise deve ser chamada somente quando o próximo dado for do tipo vetor.
	 * @param root referência da string estendida contendo a formatação dos dados.
	 * @param types referência da lista contendo os tipos de dados já analisados.
	 * @throws UtilException ocorre apenas se houver alguma irregularidade na formatação.
	 */

	private static void isFormatArray(StringExtend root, DynamicList<String> types) throws UtilException
	{
		String type = getTypeName(root.get());		root.next(); // Separador
		String number = new String();

		while (root.next() && Character.isDigit(root.get()))
			number += root.get();

		if (!IntUtil.isInteger(number))
			throw new UtilException("tamanho de vetor incorreto próximo a %s", root.fear(6));

		char primitive = root.get();	root.next(); // Separador Forçado

		type = String.format("%s<%s>[%s]", type, getTypeName(primitive), number);
		types.add(type);
	}

	/**
	 * Procedimento interno usado para fazer a análise de um formato de dados especificado.
	 * Essa análise deve ser chamada somente quando o próximo dado for do tipo matriz.
	 * @param root referência da string estendida contendo a formatação dos dados.
	 * @param types referência da lista contendo os tipos de dados já analisados.
	 * @throws UtilException ocorre apenas se houver alguma irregularidade na formatação.
	 */

	private static void isFormatMatrix(StringExtend root, DynamicList<String> types) throws UtilException
	{
		root.next(); // Primeiro Separador

		String rowNumber = new String();

		while (root.next() && Character.isDigit(root.get()))
			rowNumber += root.get();

		if (!IntUtil.isInteger(rowNumber))
			throw new UtilException("matriz com linha inválida próximo a %s", root.fear(6));

		root.next(); // Segundo Separador

		String columnNumber = new String();

		while (root.next() && Character.isDigit(root.get()))
			columnNumber += root.get();

		if (!IntUtil.isInteger(columnNumber))
			throw new UtilException("matriz com coluna inválida próximo a %s", root.fear(6));

		char primitive = root.get();	root.next(); // Separador Forçado

		if (checkChar(primitive) != CLASS_PRIMITIVE)
			throw new UtilException("tipo de matriz '%s' desconhecido", primitive);

		types.add(String.format("matrix.%s.[%s][%s]", getTypeName(primitive), rowNumber, columnNumber));
	}

	/**
	 * Verifica se o conteúdo teve um fim inesperado de acordo com a formatação.
	 * @param format string estendida respectivo ao formato que será considerado.
	 * @param content string estendida respectivo ao conteúdo que será verificado.
	 * @throws UtilException conteúdo finalizado mas ainda há formatação a ser feita.
	 */

	private boolean isContentEnd(StringExtend format, StringExtend content)
	{
		return content.finish() && !format.finish();
	}

	/**
	 * Procedimento que deve fazer a análise do conteúdo em formatação para obter um valor primitivo.
	 * @param primitive caracter respectivo ao tipo primitivo do dado do qual deverá ser analisado.
	 * @param separator caracter que será indicado como separador para encontrar o fim da coluna.
	 * @param force caracter que irá forçar o término do item no conteúdo passado.
	 * @return objeto analisado e convertido a partir do tipo primitivo desejado.
	 * @throws UtilException falha durante a obtenção ou conversão do conteúdo.
	 */

	private Object parsePrimitiveContent(char primitive, char separator, char force) throws UtilException
	{
		int last = nodeContent.offset() - 1;

		if ((nodeContent.offset() == 0 && nodeContent.get() == separator) || // inicio do conteúdo com separador (coluna vazia)
			(nodeContent.get(last) == separator && nodeContent.get() == separator) || // separador antes e agora (coluna vazia)
			(nodeContent.offset() == nodeContent.length() - 1 && nodeContent.get() == lastSeparator && separator == 0) || // fim do conteúdo com separador (coluna vazia)
			((nodeContent.get(last) == ' ' || (nodeContent.get() == '\t' && properties.is(PROPERTIE_TAB_AS_SPACE))) && (nodeContent.get() == separator)) // espaço (coluna em branco)
			)
			return getPrimitiveNullContent(primitive);

		String value = getNextItem(nodeContent, separator, force);

		if (properties.is(PROPERTIE_TRIM))
			value = value.trim();

		if (properties.is(PROPERTIE_BLANK_VALUES) && value.isEmpty())
			return getPrimitiveNullContent(primitive);

		return parsePrimititiveObject(primitive, value);
	}

	/**
	 * Faz o processamento de um determinado conteúdo em string para um valor em objeto.
	 * Esse valor em objeto será primitivo e de acordo com o tipo desejado passado.
	 * @param primitive qual deve ser o valor primitivo retornado da conversão.
	 * @param value conteúdo em string do qual será convertido para o tipo primitivo.
	 * @return objeto já convertido a partir do valor e tipo primitivo passado.
	 * @throws UtilException tipo primitivo inválido ou conteúdo incorreto para o tipo definido.
	 */

	private Object parsePrimititiveObject(char primitive, String value) throws UtilException
	{
		switch (primitive)
		{
			case TYPE_BOOLEAN:	return parsePrimitiveBoolean(value);
			case TYPE_BYTE:		return parsePrimitiveByte(value);
			case TYPE_CHAR:		return parsePrimitiveChar(value);
			case TYPE_SHORT:	return parsePrimitiveShort(value);
			case TYPE_INT:		return parsePrimitiveInt(value);
			case TYPE_LONG:		return parsePrimitiveLong(value);
			case TYPE_FLOAT:	return parsePrimitiveFloat(value);
			case TYPE_DOUBLE:	return parsePrimitiveDouble(value);
			case TYPE_STRING:	return value;
		}

		throw new UtilException("tipo primitivo inválido");
	}

	/**
	 * Analisa uma determinada string e converte essa para um valor do tipo boolean.
	 * @param value string que irá carregar o esperado valor numérico respectivo.
	 * @return valor primitivo obtido a partir da conversão feita da string.
	 * @throws UtilException apenas quando o valor não for válido.
	 */

	private Object parsePrimitiveBoolean(String value) throws UtilException
	{
		switch (BooleanUtil.parseString(value))
		{
			case BooleanUtil.BOOLEAN_FALSE:
				return false;

			case BooleanUtil.BOOLEAN_TRUE:
				return true;
		}

		throw new UtilException("'%s' não é boolean", value);
	}

	/**
	 * Analisa uma determinada string e converte essa para um valor do tipo byte.
	 * @param value string que irá carregar o esperado valor numérico respectivo.
	 * @return valor primitivo obtido a partir da conversão feita da string.
	 * @throws UtilException apenas quando o valor não for válido.
	 */

	private Object parsePrimitiveByte(String value) throws UtilException
	{
		try {
			return ByteUtil.parseString(value);
		} catch (UtilException e) {
			throw new UtilException("'%s' não é byte, %s", value, e.getMessage());
		}
	}

	/**
	 * Analisa uma determinada string e converte essa para um valor do tipo char.
	 * @param value string que irá carregar o esperado valor numérico respectivo.
	 * @return valor primitivo obtido a partir da conversão feita da string.
	 * @throws UtilException apenas quando o valor não for válido.
	 */

	private Object parsePrimitiveChar(String value) throws UtilException
	{
		if (value.length() > 1)
			throw new UtilException("'%s' não é um caracter", value);

		return value.charAt(0);
	}

	/**
	 * Analisa uma determinada string e converte essa para um valor do tipo short.
	 * @param value string que irá carregar o esperado valor numérico respectivo.
	 * @return valor primitivo obtido a partir da conversão feita da string.
	 * @throws UtilException apenas quando o valor não for válido.
	 */

	private Object parsePrimitiveShort(String value) throws UtilException
	{
		try {
			return ShortUtil.parseString(value);
		} catch (UtilException e) {
			throw new UtilException("'%s' não é short, %s", value, e.getMessage());
		}
	}

	/**
	 * Analisa uma determinada string e converte essa para um valor do tipo int.
	 * @param value string que irá carregar o esperado valor numérico respectivo.
	 * @return valor primitivo obtido a partir da conversão feita da string.
	 * @throws UtilException apenas quando o valor não for válido.
	 */

	private Object parsePrimitiveInt(String value) throws UtilException
	{
		try {
			return IntUtil.parseString(value);
		} catch (UtilException e) {
			throw new UtilException("'%s' não é int, %s", value, e.getMessage());
		}
	}

	/**
	 * Analisa uma determinada string e converte essa para um valor do tipo long.
	 * @param value string que irá carregar o esperado valor numérico respectivo.
	 * @return valor primitivo obtido a partir da conversão feita da string.
	 * @throws UtilException apenas quando o valor não for válido.
	 */

	private Object parsePrimitiveLong(String value) throws UtilException
	{
		try {
			return LongUtil.parseString(value);
		} catch (UtilException e) {
			throw new UtilException("'%s' não é long, %s", value, e.getMessage());
		}
	}

	/**
	 * Analisa uma determinada string e converte essa para um valor do tipo float.
	 * @param value string que irá carregar o esperado valor numérico respectivo.
	 * @return valor primitivo obtido a partir da conversão feita da string.
	 * @throws UtilException apenas quando o valor não for válido.
	 */

	private Object parsePrimitiveFloat(String value) throws UtilException
	{
		try {
			return FloatUtil.parseString(value);
		} catch (UtilException e) {
			throw new UtilException("'%s' não é float, %s", value, e.getMessage());
		}
	}

	/**
	 * Analisa uma determinada string e converte essa para um valor do tipo double.
	 * @param value string que irá carregar o esperado valor numérico respectivo.
	 * @return valor primitivo obtido a partir da conversão feita da string.
	 * @throws UtilException apenas quando o valor não for válido.
	 */

	private Object parsePrimitiveDouble(String value) throws UtilException
	{
		try {
			return DoubleUtil.parseString(value);
		} catch (UtilException e) {
			throw new UtilException("'%s' não é double, %s", value, e.getMessage());
		}
	}

	/**
	 * De acordo com o tipo primitivo do objeto irá retornar um conteúdo "nulo".
	 * @param primitive tipo de dado primitivo que será obtido o valor nulo.
	 * @return zero se for do tipo numérico, false para booleano,
	 * em branco para strings ou então null se não for primitivo.
	 */

	private Object getPrimitiveNullContent(char primitive)
	{
		switch (primitive)
		{
			case TYPE_BOOLEAN:	return false;
			case TYPE_BYTE:		return (byte) 0;
			case TYPE_CHAR:		return (char) 0;
			case TYPE_SHORT:	return (short) 0;
			case TYPE_INT:		return 0;
			case TYPE_LONG:		return 0L;
			case TYPE_FLOAT:	return 0.0F;
			case TYPE_DOUBLE:	return 0.0D;
			case TYPE_STRING:	return "";
		}

		return null;
	}

	/**
	 * Constrói uma string contento o conteúdo completo até o próximo ponto de separação.
	 * Os pontos de separação podem ser visualizados através de VALID_SEPARATORS.
	 * Esse método considera o uso de {} [] () como uma espécie de conjunto de dados únicos.
	 * Portanto em quanto não for fechado (se aberto) não irá retornar mesmo com separador.
	 * Assim é possível haver um separador dentro desses conjuntos sem que retorne.
	 * Porém se dentro deles houver um separador forçado irá retornar exception em conjuntos.
	 * @param content string estendida que será usado para obter o próximo item.
	 * @param separator caracter que será considerado o separador desse.
	 * @param force caracter que irá forçar o encerramento obrigatório do item,
	 * caso não seja necessário forçar, deve possuir o mesmo valor de primitive.
	 * @return string contendo o valor respectivo do próximo item.
	 * @throws UtilException conjunto de dados mal inválido.
	 */

	public static String getNextItem(StringExtend content, char separator, char force) throws UtilException
	{
		int offset = content.offset();
		int length = -1;

		Bracket bracket = new Bracket();

		if ((checkChar(separator) == CLASS_BRACE || checkChar(force) == CLASS_BRACE) && checkChar(content.get()) == CLASS_BRACE)
			return "";

		bracket.parseOpen(content.get());
		bracket.parseClose(content.get());

		while (content.next() || bracket.has())
		{
			if (length == -1)
				length = 1;

			if( (content.get() == force && force != separator) || (content.get() == separator && !bracket.has()) )
				break;

			bracket.parseOpen(content.get());
			bracket.parseClose(content.get());
			bracket.isMuch();

			length++;
		}

		bracket.isLittle();

		return content.cut(offset, length);
	}

	/**
	 * Verifica qual a classe do qual um determinado tipo de dado determinado por um caracter é.
	 * @param c caracter do qual deve ser obtido a classe do qual representa.
	 * @return código da classe do caracter analisado, pode ser encontrado por CLASS_{?}.
	 */

	public static int checkChar(char c)
	{
		if (c == TYPE_ALTERNATIVE)
			return CLASS_ALTERNATIVE;

		if (isPrimitiveChar(c))
			return CLASS_PRIMITIVE;

		if (isArrayChar(c))
			return CLASS_ARRAY;

		if (c == TYPE_MATRIX)
			return CLASS_MATRIX;

		if (isBraceChar(c))
			return CLASS_BRACE;

		return CLASS_REPEAT;
	}

	/**
	 * Chamado durante a verificação de um caracter de uma determinada formatação.
	 * Essa verificação consiste em distinguir se é um tipo de dado ou repetição de caracter.
	 * @param c caracter do qual será verificado se é de um tipo de dado primitivo.
	 * @return true se for de um tipo de dado primitivo ou false caso contrário.
	 */

	private static boolean isPrimitiveChar(char c)
	{
		switch (c)
		{
			case TYPE_BOOLEAN:
			case TYPE_BYTE:
			case TYPE_CHAR:
			case TYPE_SHORT:
			case TYPE_INT:
			case TYPE_LONG:
			case TYPE_FLOAT:
			case TYPE_DOUBLE:
			case TYPE_STRING:
				return true;
		}

		return false;
	}

	/**
	 * Chamado durante a verificação de um caracter de uma determinada formatação.
	 * Essa verificação consiste em distinguir se é um tipo de dado ou repetição de caracter.
	 * @param c caracter do qual será verificado se é de um tipo de dado em vetor.
	 * @return true se for de um tipo de dado em vetor ou false caso contrário.
	 */

	private static boolean isArrayChar(char c)
	{
		switch (c)
		{
			case TYPE_ARRAY_STATIC:
			case TYPE_ARRAY_DINAMIC:
				return true;
		}

		return false;
	}

	/**
	 * Chamado durante a verificação de um caracter de uma determinada formatação.
	 * Essa verificação consiste em distinguir se é um tipo de dado ou repetição de caracter.
	 * @param c caracter do qual será verificado se é de um tipo de formatação com brace.
	 * @return true se for de um tipo formatação com brace ou false caso contrário.
	 */

	private static boolean isBraceChar(char c)
	{
		switch (c)
		{
			case '(':
			case ')':
			case '[':
			case ']':
			case '{':
			case '}':
				return true;
		}

		return false;
	}

	/**
	 * Permite obter qual o nome do tipo de dado de acordo com o seu caracter de identificação.
	 * @param type caracter do qual deseja saber o nome dentro de uma formatação.
	 * @return nome do tipo de dado do qual esse caracter representa na formatação.
	 */

	public static String getTypeName(char type)
	{
		switch (type)
		{
			case TYPE_BOOLEAN: return "boolean";
			case TYPE_BYTE: return "byte";
			case TYPE_CHAR: return "char";
			case TYPE_SHORT: return "short";
			case TYPE_INT: return "int";
			case TYPE_LONG: return "long";
			case TYPE_FLOAT: return "fload";
			case TYPE_DOUBLE: return "double";
			case TYPE_STRING: return "string";
			case TYPE_ARRAY_STATIC: return "array.static";
			case TYPE_ARRAY_DINAMIC: return "array.dinamic";
		}

		return "null";
	}

	@Override
	public String toString()
	{
		StringBuffer str = new StringBuffer("Format");

		str.append("[");
		str.append("col: " +col);
		str.append(", alternative: " +alternative);
		str.append(", properties: (" +properties.toStringProperties()+ ")");
		str.append("]");

		return str.toString();
	}
}
