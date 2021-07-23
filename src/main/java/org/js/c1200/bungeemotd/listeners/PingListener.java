package org.js.c1200.bungeemotd.listeners;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing.Players;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;
import org.js.c1200.bungeemotd.Main;

import java.util.Random;

public class PingListener implements Listener {
    @EventHandler
    public void onProxyPing(ProxyPingEvent ev) {
        Configuration config = Main.config;

        Random ran = new Random();

        ev.getResponse().setDescription(
                ChatColor.translateAlternateColorCodes(
                        '&',
                        config.getString("motd").replaceAll(
                                "@dynamicmsg@",
                                config.getStringList("dynamicmsgs").get(ran.nextInt(config.getStringList("dynamicmsgs").size()))
                        )
                )
        );

        if (config.getBoolean("useservercap")) {
            Players players = ev.getResponse().getPlayers();
            players.setMax(Main.totalMax);
            ev.getResponse().setPlayers(players);
        }
    }
}
