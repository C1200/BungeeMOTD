package org.js.c1200.bungeemotd;

import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.js.c1200.bungeemotd.listeners.PingListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public final class Main extends Plugin {
    String configFileName = "config.yml";

    File configFile;
    public static Configuration config;

    public static int totalMax = 0;

    @Override
    public void onEnable() {
        // Plugin startup logic

        if (!getDataFolder().exists()) getDataFolder().mkdir();

        configFile = new File(getDataFolder(), configFileName);

        if (!configFile.exists()) {
            try (InputStream in = getResourceAsStream(configFileName)) {
                Files.copy(in, configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), configFileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (String i : getProxy().getServers().keySet()) {
            getProxy().getServers().get(i).ping(new Callback<ServerPing>() {
                @Override
                public void done(ServerPing result, Throwable error) {
                    totalMax += result.getPlayers().getMax();
                }
            });
        }

        getProxy().getPluginManager().registerListener(this, new PingListener());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
