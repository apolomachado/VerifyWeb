package nescaaallz.verifyweb.listeners;

import nescaaallz.verifyweb.VerifyWeb;
import nescaaallz.verifyweb.database.DatabaseProvider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.List;

public class VerifyListener implements Listener {

    protected final DatabaseProvider databaseProvider;

    public VerifyListener(DatabaseProvider databaseProvider) {
        this.databaseProvider = databaseProvider;
    }

    public DatabaseProvider getDatabaseProvider() {
        return databaseProvider;
    }

    public VerifyWeb getPlugin() {
        return getDatabaseProvider().getPlugin();
    }

    public String getKickMessage() {
        StringBuilder finalMessage = null;
        int x = 0;
        int size = getDatabaseProvider().getConfig().getStringList("KickMessage").size();
        List<String> stringList = getDatabaseProvider().getConfig().getStringList("KickMessage");
        while (x < size) {
            if(finalMessage != null) {
                finalMessage.append("\n").append(stringList.get(x).replace('&', '§'));
            } else {
                finalMessage = new StringBuilder(stringList.get(x).replace('&', '§'));
            }
            x++;
        }
        if(finalMessage == null) {
            System.out.printf("[VerifyWeb] '%s' not found in the configuration file.%n", "KickMessage");
        }
        assert finalMessage != null;
        return finalMessage.toString();
    }

    public void register() {
        getPlugin().getServer().getPluginManager().registerEvents(this, getPlugin());
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if(!getDatabaseProvider().executeExistsSearch(e.getPlayer())) {
            e.disallow(PlayerLoginEvent.Result.KICK_BANNED, getKickMessage());
            e.setResult(PlayerLoginEvent.Result.KICK_BANNED);
        }
    }
}