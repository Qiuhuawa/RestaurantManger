package org.zkpk.cs.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.security.sasl.SaslException;

public class ScramUtil {

	public static final int DEFAULT_ITERATION_COUNT = 4096;

	private ScramUtil() {}

    public static byte[] createSaltedPassword(byte[] salt, String password, int iters) throws SaslException {
        Mac mac = createSha1Hmac(password.getBytes(StandardCharsets.UTF_8));
        mac.update(salt);
        mac.update(new byte[]{0, 0, 0, 1});
        byte[] result = mac.doFinal();

        byte[] previous = null;
        for (int i = 1; i < iters; i++) {
            mac.update(previous != null ? previous : result);
            previous = mac.doFinal();
            for (int x = 0; x < result.length; x++) {
                result[x] ^= previous[x];
            }
        }

        return result;
    }
    
    public static byte[] computeHmac(final byte[] key, final String string)
            throws SaslException {
        Mac mac = createSha1Hmac(key);
        mac.update(string.getBytes(StandardCharsets.UTF_8));
        return mac.doFinal();
    }

    public static Mac createSha1Hmac(final byte[] keyBytes)
            throws SaslException {
        try {
            SecretKeySpec key = new SecretKeySpec(keyBytes, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(key);
            return mac;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new SaslException(e.getMessage(), e);
        }
    }
	
}
