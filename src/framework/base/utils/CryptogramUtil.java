package framework.base.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * aes加解密
 * 
 * @author hjin
 * @cratedate 2013-9-10 上午8:45:07
 * 
 */
public class CryptogramUtil
{
	private static String key = "";

	private static KeyGenerator kgen;
	private static SecretKey secretKey;
	private static SecretKeySpec secretKeySpec;
	private static Cipher cipher;

	/**
	 * 初始化
	 */
	static
	{
		try
		{
			key = "F9A1D52C6CDF1E0A";

			kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(key.getBytes("utf-8")));
			secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
			cipher = Cipher.getInstance("AES");// 创建密码器
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchPaddingException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 加密为byte[]
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public static byte[] encryptByte(String content)
	{
		try
		{
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		}
		catch (InvalidKeyException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch (IllegalBlockSizeException e)
		{
			e.printStackTrace();
		}
		catch (BadPaddingException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密为byte[]
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public static byte[] decryptByte(byte[] content)
	{
		try
		{
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		}
		catch (InvalidKeyException e)
		{
			e.printStackTrace();
		}
		catch (IllegalBlockSizeException e)
		{
			e.printStackTrace();
		}
		catch (BadPaddingException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[])
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++)
		{
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1)
			{
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr)
	{
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++)
		{
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
			        16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 将明文加密为十六进制字符串
	 * 
	 * @author hjin
	 * @cratedate 2013-9-10 上午9:20:13
	 * @param pass
	 * @return
	 * 
	 */
	public static String encryptHexStr(String pass)
	{
		byte[] passBinByte = encryptByte(pass);// 加密后二进制byte[]
		String passHexstr = parseByte2HexStr(passBinByte);// 二进制byte[]转十六进制str

		return passHexstr;
	}

	/**
	 * 将十六进制字符串解密为明文
	 * 
	 * @author hjin
	 * @cratedate 2013-9-10 上午9:22:10
	 * @param encodedPass
	 * @return
	 * 
	 */
	public static String decryptHexStr(String encodedPass)
	{
		byte[] depassHexbyte = parseHexStr2Byte(encodedPass);// 十六进制str转十六进制byte[]
		byte[] depassBinByte = decryptByte(depassHexbyte);// 十六进制byte[]转二进制byte[]
		String depass = new String(depassBinByte);// byte[]解密
		return depass;
	}

	public static void main(String[] args)
	{
		System.out.println(encryptHexStr("123"));
	}
}
