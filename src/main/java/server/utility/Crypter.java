package server.utility;

import java.util.Random;

public class Crypter {

    // XOR-kypteringsmetoden: (CR)
//husk link, set på dalbys laptop.
    private static String encryptDecrypt(String input) {
        char[] key = {'J', 'M', 'F'};
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            output.append((char) (input.charAt(i) ^ key[i % key.length]));
        }

        return.output.toString();
    }


    public static String buildToken(String chars, int length) {
        Random random = new Random();
        StringBuilder SB = new StringBuilder();
        for (int i = 0; i < length; i++) {
            SB.append(chars.charAt(random.nextInt(chars.length()))):
        }

        return SB.toString();
    }
}
