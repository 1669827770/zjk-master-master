package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	/**
	 * md5加密 不可逆
	 * 
	 * @param text
	 * @return
	 */
	public static String enCode(String text) {
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			// 得到一个加密后的byte[]
			byte[] digest2 = digest.digest(text.getBytes());

			StringBuffer sb = new StringBuffer();
			for (byte b : digest2) {
				int a = b & 0xff;// 取低8位 取正数
				String hexString = Integer.toHexString(a);// 9b ac a
				// 如果长度是1 前面补0
				if (hexString.length() == 1) {
					hexString = "0" + hexString;
				}
				sb.append(hexString);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return text;
	}
}
