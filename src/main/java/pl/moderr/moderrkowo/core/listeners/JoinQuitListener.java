package pl.moderr.moderrkowo.core.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.moderr.moderrkowo.core.api.utils.text.ColorUtils;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.setJoinMessage(ColorUtils.color("&8» &a" + e.getPlayer().getName() + " przypłynął na wyspę"));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        e.setQuitMessage(ColorUtils.color("&8» &c" + e.getPlayer().getName() + " odpłynął z wyspy"));
    }

}
