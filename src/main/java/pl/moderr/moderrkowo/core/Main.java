package pl.moderr.moderrkowo.core;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import pl.moderr.moderrkowo.core.api.logging.LogType;
import pl.moderr.moderrkowo.core.api.logging.LoggingClass;
import pl.moderr.moderrkowo.core.api.logging.ModerrkowoLogger;
import pl.moderr.moderrkowo.core.api.management.ModerrkowoManager;
import pl.moderr.moderrkowo.core.api.mysql.MySQL;

@LoggingClass(type = LogType.Plugin)
public final class Main extends JavaPlugin {

    private static Main instance;

    private static MySQL mySQL;
    private static ModerrkowoManager manager;

    @Contract(pure = true)
    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        StartMySQL();
    }

    @Override
    public void onDisable() {

    }

    private void StartMySQL() {
        ModerrkowoLogger.log("Włączanie MySQL", this);
        mySQL = new MySQL("serwer2031888.home.pl", 3306, "32857308_moderrblock", "32857308_moderrblock", "moderrblock");
        ModerrkowoLogger.log("Włączono MySQL", this);
        manager = new ModerrkowoManager(this);
        ModerrkowoLogger.log("Włączono menedżera ModerrkowoIsland", this);
    }

    @Contract(pure = true)
    public static MySQL getMySQL() {
        return mySQL;
    }
    @Contract(pure = true)
    public static ModerrkowoManager getManager() {
        return manager;
    }
}
