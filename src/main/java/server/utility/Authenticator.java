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
        return Authenticator.performHashing(password+salt);


    }
    public static String randomSalt (String password){

        String hashWithSalt = performHashing(password);
// vi bruger 10, da md5 bruger 16 bytes.
        int startIndex = (int) (10*Math.random());

        return hashWithSalt.substring(startIndex,startIndex+8);

    }

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
