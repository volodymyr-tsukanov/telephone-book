package com.IO.telephoneBook;


public class Encryption{
	private static final String AES_MODE = "AES/CBC/PKCS7Padding";
	private static final String CHARSET = "UTF-8";
	private static final String HASH_ALGORITHM = "SHA-256";
	private static final byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
	
	private static javax.crypto.spec.SecretKeySpec generateKey(final String password) throws java.security.NoSuchAlgorithmException, java.io.UnsupportedEncodingException {
		final java.security.MessageDigest digest = java.security.MessageDigest.getInstance(HASH_ALGORITHM);
		byte[] bytes = password.getBytes("UTF-8");
		digest.update(bytes, 0, bytes.length);
		byte[] key = digest.digest();
		
		javax.crypto.spec.SecretKeySpec secretKeySpec = new javax.crypto.spec.SecretKeySpec(key, "AES");
		return secretKeySpec;
	}
	
	public static String encrypt(final String password, String message)
	throws java.security.GeneralSecurityException {
		try {
			final javax.crypto.spec.SecretKeySpec key = generateKey(password);
			
			byte[] cipherText = encrypt(key, ivBytes, message.getBytes(CHARSET));
			String encoded = android.util.Base64.encodeToString(cipherText, android.util.Base64.NO_WRAP);
			
			return encoded;
		} catch (java.io.UnsupportedEncodingException e) {
			throw new java.security.GeneralSecurityException(e);
		}
	}
	
	public static byte[] encrypt(final String password, byte[] data)
	throws java.security.GeneralSecurityException {
		try {
			final javax.crypto.spec.SecretKeySpec key = generateKey(password);
			byte[] cipherText = encrypt(key, ivBytes, data);
			return cipherText;
		} catch (java.io.UnsupportedEncodingException e) {
			throw new java.security.GeneralSecurityException(e);
		}
	}
	
	public static byte[] encrypt(final javax.crypto.spec.SecretKeySpec key, final byte[] iv, final byte[] message)
	throws java.security.GeneralSecurityException {
		final javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(AES_MODE);
		javax.crypto.spec.IvParameterSpec ivSpec = new javax.crypto.spec.IvParameterSpec(iv);
		cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key, ivSpec);
		byte[] cipherText = cipher.doFinal(message);
		
		return cipherText;
	}
	
	public static String decrypt(final String password, String base64EncodedCipherText)
	throws java.security.GeneralSecurityException {
		try {
			final javax.crypto.spec.SecretKeySpec key = generateKey(password);
			
			byte[] decodedCipherText = android.util.Base64.decode(base64EncodedCipherText, android.util.Base64.NO_WRAP);
			
			byte[] decryptedBytes = decrypt(key, ivBytes, decodedCipherText);
			
			String message = new String(decryptedBytes, CHARSET);
			
			return message;
		} catch (java.io.UnsupportedEncodingException e) {
			throw new java.security.GeneralSecurityException(e);
		}
	}
	
	public static byte[] decrypt(final String password, byte[] data)
	throws java.security.GeneralSecurityException {
		try {
			final javax.crypto.spec.SecretKeySpec key = generateKey(password);
			byte[] decryptedBytes = decrypt(key, ivBytes, data);
			return decryptedBytes;
		} catch (java.io.UnsupportedEncodingException e) {
			throw new java.security.GeneralSecurityException(e);
		}
	}
	
	public static byte[] decrypt(final javax.crypto.spec.SecretKeySpec key, final byte[] iv, final byte[] decodedCipherText)
	throws java.security.GeneralSecurityException {
		final javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(AES_MODE);
		javax.crypto.spec.IvParameterSpec ivSpec = new javax.crypto.spec.IvParameterSpec(iv);
		cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key, ivSpec);
		byte[] decryptedBytes = cipher.doFinal(decodedCipherText);
		
		return decryptedBytes;
	}
}