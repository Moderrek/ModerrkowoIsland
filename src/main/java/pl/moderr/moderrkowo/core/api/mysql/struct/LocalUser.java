package pl.moderr.moderrkowo.core.api.mysql.struct;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import pl.moderr.moderrkowo.core.api.exceptions.UserIsOffline;
import pl.moderr.moderrkowo.core.api.mysql.MySQL;
import pl.moderr.moderrkowo.core.api.mysql.callback.IMySQLUpdateCallback;
import pl.moderr.moderrkowo.core.api.mysql.type.IDType;

import java.util.UUID;

public class LocalUser {

    private final MySQL mySQL;

    //<editor-fold> Variables
    // ID
    private final String GlobalUUID;
    private final UUID MinecraftUUID;
    // Name
    private final String Name;
    // Admin
    private final boolean Admin;
    // Money
    private double Money;
    private float ModerrCoin;
    //</editor-fold> Variables

    @Contract(pure = true)
    public LocalUser(MySQL mySQL, String GlobalUUID, UUID MinecraftUUID, String Name, boolean Admin, double Money, float ModerrCoin){
        this.mySQL = mySQL;
        this.GlobalUUID = GlobalUUID;
        this.MinecraftUUID = MinecraftUUID;
        this.Name = Name;
        this.Admin = Admin;
        this.Money = Money;
        this.ModerrCoin = ModerrCoin;
    }

    public String getGlobalUUID() {
        return this.GlobalUUID;
    }
    public UUID getMinecraftUUID() {
        return this.MinecraftUUID;
    }
    public String getName() {
        return this.Name;
    }
    public boolean isAdmin() {
        return this.Admin;
    }

    public double getMoney() {
        return this.Money;
    }
    public void setMoney(double Money){
        this.Money = Money;
    }
    public float getModerrCoin() {
        return this.ModerrCoin;
    }
    public void setModerrCoin(float ModerrCoin){
        this.ModerrCoin = ModerrCoin;
    }

    public Player getPlayer() throws UserIsOffline {
        Player p = Bukkit.getPlayer(this.MinecraftUUID);
        if(p != null){
            return p;
        }else{
            throw new UserIsOffline();
        }
    }

    public void Save(IMySQLUpdateCallback callback){
        getMySQL().getQueries().updateLocalUserAsync(getGlobalUUID(), IDType.GlobalUUID, this, callback);
    }
    public MySQL getMySQL() {
        return mySQL;
    }
}
