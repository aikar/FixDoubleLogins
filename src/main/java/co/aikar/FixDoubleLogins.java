package co.aikar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class FixDoubleLogins extends JavaPlugin {
    @Override
    public void onEnable() {
        super.onEnable();
        Bukkit.getServer().getPluginManager().registerEvents(new Listener() {
            public void onPlayerPrelogin(AsyncPlayerPreLoginEvent event) throws ExecutionException, InterruptedException {
                final Player player = Bukkit.getPlayer(event.getUniqueId());
                if (player != null) {
                    Bukkit.getScheduler().callSyncMethod(FixDoubleLogins.this, new Callable<Object>() {
                        public Object call() throws Exception {
                            player.kickPlayer("You logged in from another location");
                            return null;
                        }
                    }).get();
                }
            }
        }, this);
    }
}
