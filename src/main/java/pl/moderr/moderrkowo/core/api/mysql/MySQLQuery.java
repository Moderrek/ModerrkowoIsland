package pl.moderr.moderrkowo.core.api.mysql;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.junit.Test;
import pl.moderr.moderrkowo.core.Main;
import pl.moderr.moderrkowo.core.api.exceptions.IDTypeUnknown;
import pl.moderr.moderrkowo.core.api.exceptions.UserNotFound;
import pl.moderr.moderrkowo.core.api.mysql.struct.LocalUser;
import pl.moderr.moderrkowo.core.api.mysql.type.IDType;
import pl.moderr.moderrkowo.core.api.utils.logging.LogType;
import pl.moderr.moderrkowo.core.api.utils.logging.LoggingClass;
import pl.moderr.moderrkowo.core.api.utils.logging.ModerrkowoLogger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@LoggingClass(type = LogType.Queries)
public class MySQLQuery {

    private final MySQL mySQL;
    @Contract(pure = true)
    public MySQLQuery(MySQL mySQL){
        this.mySQL = mySQL;
    }

    public String Convert_GlobalUUID_to_MinecraftUUID(String GlobalUUID) throws Exception {
        long start = System.currentTimeMillis();
        String result;
        ModerrkowoLogger.log("Pobieranie MinecraftUUID z GlobalUUID (NORMAL)", this);
        try{
            String SQL = "SELECT `MCID` FROM `minecraft_data` WHERE `UUID`=? LIMIT 1";
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement(SQL);
            preparedStatement.setString(1, GlobalUUID);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                result = rs.getString("MCID");
            }else{
                throw new Exception("User don't exists");
            }
            ModerrkowoLogger.log("Wykonano zapytanie w " + (System.currentTimeMillis()-start) +  " ms (NORMAL)", this);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            ModerrkowoLogger.log("Wystąpił błąd podczas zapytania (NORMAL)", this);
            throw throwables;
        }
        return result;
    }
    public String Convert_MinecraftUUID_to_GlobalUUID(String MinecraftUUID) throws Exception {
        long start = System.currentTimeMillis();
        String result;
        ModerrkowoLogger.log("Pobieranie GlobalUUID z MinecraftUUID (NORMAL)", this);
        try{
            String SQL = "SELECT `UUID` FROM `minecraft_data` WHERE `MCID`=? LIMIT 1";
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement(SQL);
            preparedStatement.setString(1, MinecraftUUID);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                result = rs.getString("UUID");
            }else{
                throw new Exception("User don't exists");
            }
            ModerrkowoLogger.log("Wykonano zapytanie w " + (System.currentTimeMillis()-start) +  " ms (NORMAL)", this);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            ModerrkowoLogger.log("Wystąpił błąd podczas zapytania (NORMAL)", this);
            throw throwables;
        }
        return result;
    }

    // EXISTS `minecraft_data` USER
    public void isMinecraftUserExistsAsync(String mcId, IMySQLCallback<Boolean> callback){
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                boolean result = false;
                ModerrkowoLogger.log("Próba sprawdzenia użytkownika (ASYNC)", this);
                try{
                    String SQL = "SELECT `MCID` FROM `minecraft_data` WHERE `MCID`=? LIMIT 1";
                    PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement(SQL);
                    preparedStatement.setString(1, mcId);
                    ResultSet rs = preparedStatement.executeQuery();
                    result = rs.next();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    callback.onFailure(throwables);
                    ModerrkowoLogger.log("Wystąpił błąd podczas zapytania (ASYNC)", this);
                    return;
                }
                boolean finalResult = result;
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    callback.onSuccess(finalResult);
                    ModerrkowoLogger.log("Wykonano zapytanie w " + (System.currentTimeMillis()-start) +  " ms (ASYNC)", this);
                });
            }
        });
    }
    public boolean isMinecraftUserExists(String mcId) throws SQLException {
        long start = System.currentTimeMillis();
        boolean result = false;
        ModerrkowoLogger.log("Próba sprawdzenia użytkownika (NORMAL)", this);
        try{
            String SQL = "SELECT `MCID` FROM `minecraft_data` WHERE `MCID`=? LIMIT 1";
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement(SQL);
            preparedStatement.setString(1, mcId);
            ResultSet rs = preparedStatement.executeQuery();
            result = rs.next();
            ModerrkowoLogger.log("Wykonano zapytanie w " + (System.currentTimeMillis()-start) +  " ms (NORMAL)", this);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            ModerrkowoLogger.log("Wystąpił błąd podczas zapytania (NORMAL)", this);
            throw throwables;
        }
        return result;
    }
    // MONEY
    public void getMinecraftUserMoneyAsync(String globalId, IMySQLCallback<Integer> callback){
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                int result = 0;
                ModerrkowoLogger.log("Próba pobrania pieniędzy użytkownika (ASYNC)", this);
                try{
                    String SQL = "SELECT `MONEY` FROM `bank_data` WHERE `UUID`=? LIMIT 1";
                    PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement(SQL);
                    preparedStatement.setString(1, globalId);
                    ResultSet rs = preparedStatement.executeQuery();
                    if(rs.next()){
                        // exists
                        result = rs.getInt("MONEY");
                    }else{
                        // not exists
                        callback.onFailure(new Exception("User don't exists! " + globalId));
                        return;
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    callback.onFailure(throwables);
                    ModerrkowoLogger.log("Wystąpił błąd podczas zapytania (ASYNC)", this);
                    return;
                }
                int finalResult = result;
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    callback.onSuccess(finalResult);
                    ModerrkowoLogger.log("Wykonano zapytanie w " + (System.currentTimeMillis()-start) +  " ms (ASYNC)", this);
                });
            }
        });
    }
    public int getMinecraftUserMoney(String id, IDType idType) throws Exception {
        int result;
        ModerrkowoLogger.log("Próba sprawdzenia użytkownika (NORMAL)", this);
        try{
            String SQL;
            switch (idType){
                case GlobalUUID:
                    SQL = "SELECT `MONEY` FROM `panel_data` WHERE `UUID`=? LIMIT 1";
                    break;
                case MinecraftUUID:
                    SQL = "SELECT `MONEY` FROM `panel_data` WHERE `SERVER_UUID`=? LIMIT 1";
                    break;
                case Username:
                    SQL = "SELECT `MONEY` FROM `panel_data` WHERE `NAME`=? LIMIT 1";
                    break;
                default:
                    throw new Exception("IDType unknown");
            }
            long start = System.currentTimeMillis();
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement(SQL);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                result = rs.getInt("MONEY");
            }else{
                throw new Exception("User don't exists");
            }
            ModerrkowoLogger.log("Wykonano zapytanie w " + (System.currentTimeMillis()-start) +  " ms (NORMAL)", this);
        } catch (SQLException sqlError) {
            sqlError.printStackTrace();
            ModerrkowoLogger.log("Wystąpił błąd podczas zapytania (NORMAL)", this);
            throw sqlError;
        }
        return result;
    }
    // USER
    public void getLocalUserAsync(String id, IDType idType, IMySQLCallback<LocalUser> callback){
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                LocalUser result;
                ModerrkowoLogger.log("Próba pobrania lokalnego użytkownika (ASYNC)", this);
                try{
                    String SQL;
                    switch (idType){
                        case GlobalUUID:
                            SQL = "SELECT `UUID`,`SERVER_UUID`,`NAME`,`IS_ADMIN`,`MONEY`,`MODERRCOIN` FROM `panel_data` WHERE `UUID`=? LIMIT 1";
                            break;
                        case MinecraftUUID:
                            SQL = "SELECT `UUID`,`SERVER_UUID`,`NAME`,`IS_ADMIN`,`MONEY`,`MODERRCOIN` FROM `panel_data` WHERE `SERVER_UUID`=? LIMIT 1";
                            break;
                        case Username:
                            SQL = "SELECT `UUID`,`SERVER_UUID`,`NAME`,`IS_ADMIN`,`MONEY`,`MODERRCOIN` FROM `panel_data` WHERE `NAME`=? LIMIT 1";
                            break;
                        default:
                            callback.onFailure(new IDTypeUnknown());
                            return;
                    }
                    PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement(SQL);
                    preparedStatement.setString(1, id);
                    ResultSet rs = preparedStatement.executeQuery();
                    if(rs.next()){
                        // exists
                        result = new LocalUser(
                                mySQL,
                                rs.getString("UUID"),
                                UUID.fromString(rs.getString("SERVER_UUID")),
                                rs.getString("NAME"),
                                rs.getBoolean("IS_ADMIN"),
                                rs.getDouble("MONEY"),
                                rs.getFloat("MODERRCOIN")
                        );
                    }else{
                        // not exists
                        callback.onFailure(new UserNotFound());
                        ModerrkowoLogger.log("Nie znaleziono użytkownika (ASYNC)", this);
                        return;
                    }
                } catch (SQLException sqlBug) {
                    sqlBug.printStackTrace();
                    callback.onFailure(sqlBug);
                    ModerrkowoLogger.log("Wystąpił błąd podczas zapytania (ASYNC)", this);
                    return;
                }
                LocalUser finalResult = result;
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    callback.onSuccess(finalResult);
                    ModerrkowoLogger.log("Wykonano zapytanie w " + (System.currentTimeMillis()-start) +  " ms (ASYNC)", this);
                });
            }
        });
    }
    public void updateLocalUserAsync(String id, IDType idType, IMySQLCallback<Boolean> callback){
        
    }
}
