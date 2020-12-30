package nescaaallz.verifyweb;

import nescaaallz.verifyweb.database.DatabaseProvider;
import nescaaallz.verifyweb.listeners.VerifyListener;
import org.bukkit.plugin.java.JavaPlugin;

public class VerifyWeb extends JavaPlugin {

    protected DatabaseProvider databaseProvider;
    protected VerifyListener verifyListener;

    public DatabaseProvider getDatabaseProvider() {
        return databaseProvider;
    }

    public VerifyListener getVerifyListener() {
        return verifyListener;
    }

    public void onEnable() {
        saveDefaultConfig();

        this.databaseProvider = new DatabaseProvider(this);
        getDatabaseProvider().startConnection();

        this.verifyListener = new VerifyListener(getDatabaseProvider());
        getVerifyListener().register();
    }

    public void onDisable() {
        getDatabaseProvider().stopConnection();
    }
}