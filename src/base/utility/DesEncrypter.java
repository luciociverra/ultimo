/*
 * Created on 26-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package base.utility;

import java.io.UnsupportedEncodingException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import java.util.Base64;

public class DesEncrypter extends BaseBean {
	Cipher ecipher;
	Cipher dcipher;

	// 8-byte Salt
	byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x35, (byte) 0xE3,
			(byte) 0x03 };

	// Iteration count
	int iterationCount = 19;

	public DesEncrypter() {
		return;
	}

	public DesEncrypter(String passPhrase) {

		try {

			// Create the key
			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec); // PBEWithMD5AndDES
			ecipher = Cipher.getInstance(key.getAlgorithm());
			dcipher = Cipher.getInstance(key.getAlgorithm());

			// Prepare the parameter to the ciphers
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

			// Create the ciphers
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		} catch (java.security.InvalidAlgorithmParameterException e) {
		} catch (java.security.spec.InvalidKeySpecException e) {
		} catch (javax.crypto.NoSuchPaddingException e) {
		} catch (java.security.NoSuchAlgorithmException e) {
		} catch (java.security.InvalidKeyException e) {
		}
	}

	public String encrypt(String str) {

		if (str.equals(""))
			return "";

		try {
			// Encode the string into bytes using utf-8
			byte[] utf8 = str.getBytes("UTF8");

			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);

			// Encode bytes to base64 to get a string
			String ris = "";

			ris = Base64.getEncoder().encodeToString(enc);
			ris = (numeriC(ris));

			return ris;
		} catch (javax.crypto.BadPaddingException e) {
		} catch (IllegalBlockSizeException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (java.io.IOException e) {
		}
		return "";
	}

	public String decrypt(String str) {

		if (str.equals(""))
			return "";

		str = numeriD(str);

		try {
			// Decode base64 to get bytes

			byte[] dec = Base64.getDecoder().decode(str);

			// Decrypt
			byte[] utf8 = dcipher.doFinal(dec);

			// Decode using utf-8
			return new String(utf8, "UTF8");
		} catch (javax.crypto.BadPaddingException e) {
			System.out.println(e.getMessage());
		} catch (IllegalBlockSizeException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (java.io.IOException e) {
		}
		return "";
	}

	private String numeriC(String inS) {
		String outS = "";
		for (int i = 0; i < inS.length(); i++) {
			outS += Utils.getNumero((inS.substring(i, i + 1)).hashCode(), "000");
		}

		return outS;
	}

	private String numeriD(String inS) {
		String outS = "";

		for (int i = 0; i < inS.length(); i += 3) {
			char s = (char) Integer.parseInt(inS.substring(i, i + 3));
			outS += String.valueOf(s);
		}
		return outS;
	}
}
