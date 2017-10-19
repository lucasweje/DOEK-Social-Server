package server.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Authenticator {

    private static String salt;
    private static MessageDigest authenticator;

    public Authenticator() {
    }

    static {
        try {
            authenticator = MessageDigest.getInstance("MD5");


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String hashWithSalt(String password, String salt) {
        salt = performHashing(salt);
        return Authenticator.performHashing(password + salt);
// muligvis slet "+ email" det er bare for test.

    }

    //taget fra jespers project secure-dis.
    private static String performHashing(String str) {
        authenticator.update(str.getBytes());
        byte[] hash = authenticator.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte aHash : hash) {
            if ((0xff & aHash) < 0x10) {
                hexString.append("0" + Integer.toHexString((0xFF & aHash)));
            } else {
                hexString.append(Integer.toHexString(0xFF & aHash));
            }
        }
        return hexString.toString();
    }
}
