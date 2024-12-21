package com.github.fleivinho.gitupdater.bukkit;

import com.github.fleivinho.gitupdater.GitUpdater;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.io.File;
import java.util.logging.Logger;

/**
 * @author fleivius
 * @project https://github.com/fleivinho/GitUpdater
 */
public class BukkitUpdater extends GitUpdater {

    private final JavaPlugin plugin;

    public BukkitUpdater(JavaPlugin plugin, String organization, String repository) {
        super(plugin.getName(), plugin.getDescription().getVersion(), organization, repository);
        this.plugin = plugin;
    }

    @Override
    public File getUpdateFolder() {
        return Bukkit.getUpdateFolderFile();
    }

    @Override
    public void checkUpdate() {
        Logger logger = getLogger();

        new BukkitRunnable() {
            @Override
            public void run() {
                logger.info("Checking updates...");

                String lastVersion = getLastVersion();

                if (lastVersion.equalsIgnoreCase(getCurrentVersion())) {
                    logger.info("Plugin already updated!");
                    return;
                }

                logger.info("A new update has been found! (" + getCurrentVersion() + " > " + lastVersion + ")");

                try {
                    boolean update = downloadLastVersion();
                    if (update) {
                        logger.info("Update " + lastVersion + " downloaded successfully! Restart the server to apply");
                    } else {
                        logger.severe("Update " + lastVersion + " downloaded failed!");
                    }
                } catch (Exception e) {
                    logger.severe("Failed to download " + lastVersion + " update! " + e.getMessage());
                    e.printStackTrace();
                }

            }
        }.runTaskAsynchronously(plugin);
    }
}
