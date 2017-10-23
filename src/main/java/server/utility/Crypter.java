package server.utility;

import server.config.Config;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.Random;


public class Crypter {


    //TO DO: Skal implementeres under hvert end point.

    // XOR-kypteringsmetoden: (CR)
//husk link, set på dalbys laptop.
    public static String encryptDecrypt(String input) {

        if (Config.getEncryption()) {
            char[] key = {'J', 'M', 'F'};
            StringBuilder output = new StringBuilder();

            for (int i = 0; i < input.length(); i++) {
                output.append((char) (input.charAt(i) ^ key[i % key.length]));
            }

            return output.toString();
        } else return input;
    }
}





