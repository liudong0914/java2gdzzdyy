package com.util.des;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DesUtil {

	private final static String DES = "DES";
	public final static String DESKEY = "javadev83456666!@#$%^&*()";

	public static void main(String[] args) throws Exception {
		
		String data="http://www.hxnlmooc.com:80/upload/1/vwh/2017/03/21/f306325466011f9a8506cb0fc0885dbb.mp4";
		String valueString = Base64.encodeBase64String(data.getBytes("utf-8"));
		System.out.println(valueString);
		System.out.println("www.hxnlmooc.com:80/1/vwh/2017/03/21/f306325466011f9a8506cb0fc0885dbb.mp4");
		System.err.println(encrypt("http://www.hxnlmooc.com:80/upload/1/vwh/2017/03/21/f306325466011f9a8506cb0fc0885dbb.mp4", "73ad893b090daf4aab4eb02741a943b3"));
		System.err.println(decrypt(encrypt("www.hxnlmooc.com:80/upload/1/vwh/2017/03/21/f306325466011f9a8506cb0fc0885dbb.mp4", "73ad893b090daf4aab4eb02741a943b3"), "73ad893b090daf4aab4eb02741a943b3"));
	}

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String key) throws Exception {
		byte[] bt = encrypt(data.getBytes("utf-8"), key.getBytes());
		String strs = Base64.encodeBase64String(bt);
		return strs;
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String decrypt(String data, String key) throws IOException, Exception {
		if (data == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] buf = decoder.decodeBuffer(data);
		byte[] bt = decrypt(buf, key.getBytes());
		return new String(bt, "utf-8");
	}

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}
}
