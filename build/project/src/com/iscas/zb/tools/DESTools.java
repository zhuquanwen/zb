package com.iscas.zb.tools;
/**
*@date: 2017��3��6��
*@author: zhuquanwen
*@desc: ����һ����˵��
*/

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.iscas.zb.data.StaticData;

public class DESTools {
    //�㷨����
    public static final String KEY_ALGORITHM = "DES";
    private static final String DES_ALGORITHM = "DES";
    //�㷨����/����ģʽ/��䷽ʽ
    //DES�������ֹ���ģʽ-->>ECB���������뱾ģʽ��CBC�����ܷ�������ģʽ��CFB�����ܷ���ģʽ��OFB���������ģʽ
    public static final String CIPHER_ALGORITHM = "DES/ECB/NoPadding";

    public static String encrypt(String plainData, String secretKey) throws Exception{

		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(DES_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, generateKey(secretKey));

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}catch(InvalidKeyException e){

		}

		try {
			// Ϊ�˷�ֹ����ʱ��javax.crypto.IllegalBlockSizeException: Input length must be multiple of 8 when decrypting with padded cipher�쳣��
			// ���ܰѼ��ܺ���ֽ�����ֱ��ת�����ַ���
			byte[] buf = cipher.doFinal(plainData.getBytes());

			return Base64Utils.encode(buf);

		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new Exception("IllegalBlockSizeException", e);
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new Exception("BadPaddingException", e);
		}

	}

	/**
	 * DES����
	 * @param secretData
	 * @param secretKey
	 * @return
	 * @throws Exception
	 */
	public static  String decrypt(String secretData, String secretKey) throws Exception{

		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(DES_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, generateKey(secretKey));

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("NoSuchAlgorithmException", e);
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			throw new Exception("NoSuchPaddingException", e);
		}catch(InvalidKeyException e){
			e.printStackTrace();
			throw new Exception("InvalidKeyException", e);

		}

		try {

			byte[] buf = cipher.doFinal(Base64Utils.decode(secretData.toCharArray()));

			return new String(buf);

		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new Exception("IllegalBlockSizeException", e);
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new Exception("BadPaddingException", e);
		}
	}


	/**
	 * ���������Կ
	 *
	 * @param secretKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private static SecretKey generateKey(String secretKey) throws NoSuchAlgorithmException{
		SecureRandom secureRandom = new SecureRandom(secretKey.getBytes());

		// Ϊ����ѡ���DES�㷨����һ��KeyGenerator����
		KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance(DES_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
		}
		kg.init(secureRandom);
		//kg.init(56, secureRandom);

		// ������Կ
		return kg.generateKey();
	}

	public static void main(String[] a) throws Exception{
		String input = "jdbc:oracle:thin:@172.16.10.156:1521:orcl";
		String result = encrypt(input, StaticData.des_key);
		System.out.println(result);

		System.out.println(decrypt(result, StaticData.des_key));

		}
    static class Base64Utils {

		static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
		static private byte[] codes = new byte[256];
		static {
			for (int i = 0; i < 256; i++)
				codes[i] = -1;
			for (int i = 'A'; i <= 'Z'; i++)
				codes[i] = (byte) (i - 'A');
			for (int i = 'a'; i <= 'z'; i++)
				codes[i] = (byte) (26 + i - 'a');
			for (int i = '0'; i <= '9'; i++)
				codes[i] = (byte) (52 + i - '0');
			codes['+'] = 62;
			codes['/'] = 63;
		}

		/**
		 * ��ԭʼ���ݱ���Ϊbase64����
		 */
		static public String encode(byte[] data) {
			char[] out = new char[((data.length + 2) / 3) * 4];
			for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
				boolean quad = false;
				boolean trip = false;
				int val = (0xFF & (int) data[i]);
				val <<= 8;
				if ((i + 1) < data.length) {
					val |= (0xFF & (int) data[i + 1]);
					trip = true;
				}
				val <<= 8;
				if ((i + 2) < data.length) {
					val |= (0xFF & (int) data[i + 2]);
					quad = true;
				}
				out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
				val >>= 6;
				out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
				val >>= 6;
				out[index + 1] = alphabet[val & 0x3F];
				val >>= 6;
				out[index + 0] = alphabet[val & 0x3F];
			}

			return new String(out);
		}

		/**
		 * ��base64��������ݽ����ԭʼ����
		 */
		static public byte[] decode(char[] data) {
			int len = ((data.length + 3) / 4) * 3;
			if (data.length > 0 && data[data.length - 1] == '=')
				--len;
			if (data.length > 1 && data[data.length - 2] == '=')
				--len;
			byte[] out = new byte[len];
			int shift = 0;
			int accum = 0;
			int index = 0;
			for (int ix = 0; ix < data.length; ix++) {
				int value = codes[data[ix] & 0xFF];
				if (value >= 0) {
					accum <<= 6;
					shift += 6;
					accum |= value;
					if (shift >= 8) {
						shift -= 8;
						out[index++] = (byte) ((accum >> shift) & 0xff);
					}
				}
			}
			if (index != out.length)
				throw new Error("miscalculated data length!");
			return out;
		}
	}
}
