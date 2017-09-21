package util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class StreamTools {

	/**
	 * 把流中的数据转换成字符串
	 * 
	 * @param is
	 * @return
	 */
	public static String steamToStr(InputStream is) {
		String result = "";
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			int len = -1;
			byte[] buffer = new byte[1024];

			while ((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}

			result = baos.toString();
			baos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 关闭流
	 * @param stream
	 */
	public static void endStream(Closeable stream) {
		if(stream!=null){
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
