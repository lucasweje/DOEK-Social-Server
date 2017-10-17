package server.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

public final class Config {
    private static String dbUrl;
    private static Integer dbPort;
    private static String dbName;
    private static String dbUser;
    private static String dbPassword;

    public void initConfig() throws IOException {

        JsonObject json = new JsonObject();

        //Filen hentes i Inputstream
        //config.java åbnes for at kunne læses
        //Bufferedreader læser streamen igennem
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("/config.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        //Stringbuffer bruges til at samle hele filen i en streng
        StringBuffer stringBuffer = new StringBuffer();
        String str = "";

        //Filen læses en linje ad gangen og indlæses i stringbuffer
        while((str = reader.readLine()) != null){
            stringBuffer.append(str);
        }
        JsonParser parser = new JsonParser();

        json = (JsonObject) parser.parse(stringBuffer.toString());

        setDbUrl(json.get("dbUrl").toString().replace("\"", ""));
        setDbPort(Integer.parseInt(json.get("dbPort").toString().replace("\"", "")));
        setDbName(json.get("dbName").toString().replace("\"", ""));
        setDbUser(json.get("dbUser").toString().replace("\"", ""));
        setDbPassword(json.get("dbPassword").toString().replace("\"", ""));
    }

    public static String getDbUrl() {
        return dbUrl;
    }

    public static void setDbUrl(String dbUrl) {
        Config.dbUrl = dbUrl;
    }

    public static Integer getDbPort() {
        return dbPort;
    }

    public static void setDbPort(Integer dbPort) {
        Config.dbPort = dbPort;
    }

    public static String getDbName() {
        return dbName;
    }

    public static void setDbName(String dbName) {
        Config.dbName = dbName;
    }

    public static String getDbUser() {
        return dbUser;
    }

    public static void setDbUser(String dbUser) {
        Config.dbUser = dbUser;
    }

    public static String getDbPassword() {
        return dbPassword;
    }

    public static void setDbPassword(String dbPassword) {
        Config.dbPassword = dbPassword;
    }

}
