package cx.mia.samsara.api;

import cx.mia.samsara.Samsara;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Sound.getLoopers().put(event.getPlayer(), new HashMap<>());
        Samsara.getInstance().getLogger().debug(event.getPlayer().getName() + " was added to the Looper HashMap");
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Sound.getLoopers().remove(event.getPlayer());
        Samsara.getInstance().getLogger().debug(event.getPlayer().getName() + " was removed from the Looper HashMap");
    }
}