package com.github.fleivinho.gitupdater.shared;

/**
 * @author fleivius
 * @project https://github.com/fleivinho/GitUpdater
 */
public enum PluginPlatform {

    BUKKIT("org.bukkit.Bukkit"),
    BUNGEECORD("net.md_5.bungee.api.ProxyServer");

    private final String classCheck;

    PluginPlatform(String classCheck) {
        this.classCheck = classCheck;
    }

    public String getClassCheck() {
        return classCheck;
    }

    public static PluginPlatform getPlatform() {
        if (currentPlatform != null) return currentPlatform;

        for (PluginPlatform platform : values()) {
            try {
                Class.forName(platform.classCheck);
                currentPlatform = platform;
                break;
            } catch (Exception ignore) {}
        }

        if (currentPlatform != null) return currentPlatform;
        else throw new NullPointerException(PluginPlatform.class.getName()  + " - invalid platform!");
    }

    private static PluginPlatform currentPlatform;
}
