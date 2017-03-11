package org.diverproject.util.jzip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.diverproject.jzip.DataFormatException;
import org.diverproject.jzip.Deflater;
import org.diverproject.jzip.Inflater;
import org.diverproject.util.SizeUtil;

/**
 * A biblioteca de utilitários também permite gerenciar objetos conhecidos pela biblioteca JZIP (diverproject).
 * As duas principais funcionalidades dele utilitário é compactar bytes em zip e descompactar bytes "zipados".
 */

public class ZipUtil
{
	/**
	 * Tamanho dos buffers internos que serão criados.
	 */
	public static final int BUFFER_SIZE = 1024 * 8;

	/**
	 * Determina se deverá ser exibido uma mensagem dos dados quando compactados ou descompactados.
	 */
	public static boolean LOG = false;

	/**
	 * Compacta um determinado vetor de bytes em formato ZIP.
	 * Definido como padrão com BEST_COMPRESSION e HUFFMAN_ONLY.
	 * @param input dados que serão compactados.
	 * @return vetor com os bytes compactados.
	 */

	public static byte[] zip(byte input[])
	{
		return zip(input, false);
	}

	/**
	 * Compacta um determinado vetor de bytes em formato ZIP.
	 * Definido como padrão com BEST_COMPRESSION e HUFFMAN_ONLY.
	 * @param input vetor dos dados em bytes que serão compactados.
	 * @param enableExpcetion habilitar o uso de UtilRuntimeException.
	 * @return vetor com os bytes compactados.
	 */

	public static byte[] zip(byte input[], boolean enableException)
	{
		Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
		deflater.setStrategy(Deflater.FILTERED);
		deflater.setInput(input);
		deflater.finish();

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[BUFFER_SIZE];

		while (!deflater.finished())
		{
			int compressed = deflater.deflate(buffer);
			output.write(buffer, 0, compressed);
		}

		try {
			output.close();
		} catch (IOException e) {
			if (enableException)
				throw new RuntimeException(e.getMessage());
		}

		if (LOG)
			System.out.printf("Compactado %s em %s.\n", SizeUtil.toString(deflater.getTotalIn()), SizeUtil.toString(deflater.getTotalOut()));

		return output.toByteArray();
	}

	/**
	 * Descompacta um determinado vetor de bytes supostamente em formato ZIP.
	 * @param input vetor dos dados em bytes que serão descompactados.
	 * @return vetor dom os bytes descompactados ou null se falhar.
	 */

	public static byte[] unzip(byte input[])
	{
		return unzip(input, false);
	}

	/**
	 * Descompacta um determinado vetor de bytes supostamente em formato ZIP.
	 * @param input vetor dos dados em bytes que serão descompactados.
	 * @param enableExpcetion habilitar o uso de UtilRuntimeException.
	 * @return vetor dom os bytes descompactados.
	 */

	public static byte[] unzip(byte input[], boolean enableException)
	{
		Inflater inflater = new Inflater();
		inflater.setInput(input);

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte buffer[] = new byte[BUFFER_SIZE];

		try {

			while (!inflater.finished())
			{
				int decompress = inflater.inflate(buffer);
				output.write(buffer, 0, decompress);
			}

			output.close();

		} catch (DataFormatException | IOException e) {
			if (enableException)
				throw new RuntimeException(e.getMessage());
			return null;
		}

		if (LOG)
			System.out.printf("Descompactado %s em %s.\n", SizeUtil.toString(inflater.getTotalIn()), SizeUtil.toString(inflater.getTotalOut()));

		return output.toByteArray();
	}
}
