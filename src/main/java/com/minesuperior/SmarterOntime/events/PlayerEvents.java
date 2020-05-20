package com.minesuperior.SmarterOntime.events;

import com.minesuperior.SmarterOntime.Main;
import com.minesuperior.SmarterOntime.utils.SmartPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEvents implements Listener {

    @EventHandler
    public void ServerConnect(PlayerJoinEvent e){
        SmartPlayer player = new SmartPlayer(e.getPlayer().getName(),e.getPlayer().getUniqueId());
        Main.players.put(e.getPlayer(),player);

    }

    @EventHandler
    public void PlayerLeave(PlayerQuitEvent e){
        SmartPlayer player = Main.players.get(e.getPlayer());

        if(player==null)return;

        Main.players.remove(e.getPlayer());
    }

}
