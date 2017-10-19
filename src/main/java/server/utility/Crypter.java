package server.utility;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.Random;


public class Crypter {


    // XOR-kypteringsmetoden: (CR)
//husk link, set på dalbys laptop.
    public static String encryptDecrypt(String input) {
        char[] key = {'J', 'M', 'F'};
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            output.append((char) (input.charAt(i) ^ key[i % key.length]));
        }

        return output.toString();
    }
}


     /*

    public String encrypt(String input, String key) {
        try {
            if (input == null || key == null)
                return null;
            char[] keys = key.toCharArray();
            char[] inp = input.toCharArray();
            BASE64Encoder encoder = new BASE64Encoder();

            int il = inp.length;
            int kl = keys.length;

            char[] newInput = new char[il];

            for (int i = 0; i < il; i++) {
                newInput[i] = (char) (inp[i] ^ keys[i % kl]);
            }
            inp = null;
            keys = null;
            String temp = new String(newInput);
            return new String(new BASE64Encoder().encodeBuffer(temp.getBytes()));
        }
        catch (Exception e) {
            return null;
        }
    }
    public String decrypt (String input, String key) {
        try {
            if (input==null || key==null)
                return null,
            BASE64Decoder decoder = new BASE64Decoder();
            char [] keys =key.toCharArray();
            input = new String(decoder.decodeBuffer(input));
            char [] inp = input.toCharArray();

            int il = inp.length;
            int kl = keys.length;
            char[] newInput = new char[il];

            for (int i=0; i<il; i++) {
                newInput[i] = (char) (inp[i] ^ keys[i % kl]);
            }
            inp=null;
            keys=null;
            return new String(newInput);

            }
    } catch(IOException e) {
            e.printStackTrace();
        }
    }

*/





