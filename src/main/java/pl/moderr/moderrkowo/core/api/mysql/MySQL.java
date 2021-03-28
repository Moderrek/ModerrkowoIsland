package pl.moderr.moderrkowo.core.api.mysql;

import org.jetbrains.annotations.Contract;
import pl.moderr.moderrkowo.core.api.utils.logging.LogType;
import pl.moderr.moderrkowo.core.api.utils.logging.LoggingClass;
import pl.moderr.moderrkowo.core.api.utils.logging.ModerrkowoLogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@LoggingClass(type = LogType.Mysql)
public class MySQL {

    private Connection connection;
    private final String host, database, username, password;
    private final int port;

    private final MySQLQuery queries = new MySQLQuery(this);

    @Contract(pure = true)
    public MySQL(String host, int port, String database, String username, String password){
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        this.port = port;
        MysqlSetup();
    }

    public void MysqlSetup(){
        long start = System.currentTimeMillis();
        ModerrkowoLogger.log("Próba połączenia...", this);
        try{
            synchronized (this){
                if(getConnection() != null && !getConnection().isClosed()){
                    ModerrkowoLogger.log("Połączenie jest już ustawione", this.getClass());
                    return;
                }
                ModerrkowoLogger.log("Łączenie... " + (System.currentTimeMillis() - start) + " ms", this.getClass());
                Class.forName("com.mysql.jdbc.Driver");
                Properties info = new Properties();
                info.put("user", this.username);
                info.put("password", this.password);
                info.put("autoReconnect", "true");
                info.put("useSSL", "false");
                info.put("verifyServerCertificate", "false");
                String connectionString = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database;
                ModerrkowoLogger.log(connectionString, this.getClass());
                setConnection(DriverManager.getConnection(connectionString, info));
                ModerrkowoLogger.log("Pomyślnie połączono w " + (System.currentTimeMillis() - start) + " ms", this.getClass());
            }
        }catch(SQLException e){
            ModerrkowoLogger.log("Wystąpił problem ze skryptem SQL", this);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            ModerrkowoLogger.log("Nie znaleziono sterownika MySQL", this);
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }
    public String getHost() {
        return host;
    }
    public String getDatabase() {
        return database;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public Connection getConnection() {
        return connection;
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
        ModerrkowoLogger.log("Ustawiono nowe połączenie", this.getClass());
    }

    public MySQLQuery getQueries() {
        return queries;
    }

}
