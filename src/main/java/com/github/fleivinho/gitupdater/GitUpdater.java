package com.github.fleivinho.gitupdater;

import com.github.fleivinho.gitupdater.shared.WebAccessAPI;
import com.github.fleivinho.gitupdater.shared.PluginPlatform;
import com.google.gson.JsonObject;
import java.io.File;
import java.util.logging.Logger;

/**
 * @author fleivius
 * @project https://github.com/fleivinho/GitUpdater
 */
public abstract class GitUpdater {

    /*
     * URL of the JSON file used for version checking.
     * This file should contain metadata about the latest plugin version, such as version number and release notes.
     */
    private static final String VERSION_URL = "https://api.github.com/repos/{repos}/{plugin}/releases/latest";

    /*
     * URL of the updated .jar file for downloading the latest plugin version.
     * Ensure this URL points to a valid and accessible location, such as a GitHub release or private server.
     */
    private static final String DOWNLOAD_URL = "https://github.com/{repos}/{plugin}/releases/latest/download/{plugin}.jar";

    /* Detected platform for the plugin (SPIGOT, BUNGEECORD). */
    private static final PluginPlatform platform = PluginPlatform.getPlatform();

    private final String pluginName;

    private final String currentVersion;

    private final String organization;

    private final String repository;

    private String lastVersionDetected;

    private static Logger logger;

    public GitUpdater(String pluginName, String currentVersion, String organization, String repository) {
        this.pluginName = pluginName;
        this.currentVersion = currentVersion;
        this.organization = organization;
        this.repository = repository;
        logger = Logger.getLogger(pluginName + " Updater");
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public String getOrganization() {
        return organization;
    }

    public String getRepository() {
        return repository;
    }

    public static Logger getLogger() {
        return logger;
    }

    public String getVersionUrl() {
        return VERSION_URL.replace("{repos}", repository).replace("{plugin}", pluginName);
    }

    public String getDownloadUrl() {
        return DOWNLOAD_URL.replace("{repos}", repository).replace("{plugin}", pluginName);
    }

    public String getLastVersion() {
        JsonObject json = WebAccessAPI.getResultAsJson(getVersionUrl(), 10, JsonObject.class);

        if (json != null) {
            lastVersionDetected = String.valueOf(json.get("tag_name")).replace("\"", "");
        }

        return lastVersionDetected != null ? lastVersionDetected : getCurrentVersion();
    }

    public boolean downloadLastVersion() {
        return WebAccessAPI.downloadFile(getDownloadUrl(), new File(getUpdateFolder(), pluginName + ".jar"));
    }

    public abstract File getUpdateFolder();

    public abstract void checkUpdate();

}
