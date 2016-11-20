package org.diverproject.util.jzip;

import java.io.ByteArrayOutputStream;

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
		Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
		deflater.setStrategy(Deflater.HUFFMAN_ONLY);
		deflater.setInput(input);
		deflater.finish();

		ByteArrayOutputStream output = new ByteArrayOutputStream();

		while (!deflater.finished())
		{
			byte buffer[] = new byte[input.length];
			int compressed = deflater.deflate(buffer);
			output.write(buffer, 0, compressed);
		}

		if (LOG)
			System.out.printf("Compactado %s em %s.\n", SizeUtil.toString(deflater.getTotalIn()), SizeUtil.toString(deflater.getTotalOut()));

		return output.toByteArray();
	}

	/**
	 * Descompacta um determinado vetor de bytes supostamente em formato ZIP.
	 * @param input dados que serão descompactados.
	 * @return vetor dom os bytes descompactados.
	 */

	public static byte[] unzip(byte input[])
	{
		Inflater inflater = new Inflater();
		inflater.setInput(input);

		ByteArrayOutputStream output = new ByteArrayOutputStream();

		while (!inflater.finished())
		{
			try {

				byte buffer[] = new byte[input.length];
				int decompress = inflater.inflate(buffer);
				output.write(buffer, 0, decompress);

			} catch (DataFormatException e) {
				System.out.printf("Falha ao tentear descompactar dados: %s", e.getMessage());
				return null;
			}
		}

		if (LOG)
			System.out.printf("Descompactado %s em %s.\n", SizeUtil.toString(inflater.getTotalIn()), SizeUtil.toString(inflater.getTotalOut()));

		return output.toByteArray();
	}
}
