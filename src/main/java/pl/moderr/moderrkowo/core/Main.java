package pl.moderr.moderrkowo.core;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;

public final class Main extends JavaPlugin {

    private static Main instance;
    @Contract(pure = true)
    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {

    }
}
