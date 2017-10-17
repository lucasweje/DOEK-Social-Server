public class config {


    private static string dbUrl;
    private static string dbPort;
    private static string dbName;
    private static string dbUser;
    private static string dbPassword;

    public static string getDbUrl() {
        return dbUrl;
    }

    public static void setDbUrl(string dbUrl) {
        config.dbUrl = dbUrl;
    }

    public static string getDbPort() {
        return dbPort;
    }

    public static void setDbPort(string dbPort) {
        config.dbPort = dbPort;
    }

    public static string getDbName() {
        return dbName;
    }

    public static void setDbName(string dbName) {
        config.dbName = dbName;
    }

    public static string getDbUser() {
        return dbUser;
    }

    public static void setDbUser(string dbUser) {
        config.dbUser = dbUser;
    }

    public static string getDbPassword() {
        return dbPassword;
    }

    public static void setDbPassword(string dbPassword) {
        config.dbPassword = dbPassword;
    }
}