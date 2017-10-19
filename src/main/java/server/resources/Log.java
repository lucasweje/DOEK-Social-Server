package server.resources;


import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.*;
import java.util.Date;



public class Log {

    public String Timestamp () {

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        return timeStamp;
    }


    public Log() throws FileNotFoundException, UnsupportedEncodingException {

        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("myLog.txt"), "UTF-8"));
            writer.write(Timestamp() + "Fejl");
        } catch (IOException e) {
            //report
        }finally {
            try {writer.close();} catch (Exception e){}

        }

    }


}
