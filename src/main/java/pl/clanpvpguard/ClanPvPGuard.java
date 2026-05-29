package pl.clanpvpguard;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ClanPvPGuard extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().severe("PlaceholderAPI nie jest zainstalowane! Plugin nie bedzie dzialac.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("ClanPvPGuard wlaczony!");
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity victim = event.getEntity();

        if (!(damager instanceof Player) || !(victim instanceof Player)) return;

        Player attackerPlayer = (Player) damager;
        Player victimPlayer = (Player) victim;

        String attackerClan = PlaceholderAPI.setPlaceholders(attackerPlayer, "%clansLite_clanPrefix%");
        String victimClan = PlaceholderAPI.setPlaceholders(victimPlayer, "%clansLite_clanPrefix%");

        if (attackerClan != null && !attackerClan.isEmpty()
                && !attackerClan.equals("%clansLite_clanPrefix%")
                && attackerClan.equals(victimClan)) {
            event.setCancelled(true);
            attackerPlayer.sendMessage("§cNie mozesz atakowac czlonka swojego dystryktu!");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("ClanPvPGuard wylaczony.");
    }
}
