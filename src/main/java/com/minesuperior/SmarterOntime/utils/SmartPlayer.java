package com.minesuperior.SmarterOntime.utils;

import com.minesuperior.SmarterOntime.Main;
import lilypad.client.connect.api.event.MessageEvent;
import lilypad.client.connect.api.request.impl.MessageRequest;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.UUID;

public class SmartPlayer {
    String name;
    UUID uuid;

    /**
     * Create smart player instance for player
     * @param name Player name
     * @param uuid Player UUID
     */
    public SmartPlayer(String name, UUID uuid){
        this.name=name;
        this.uuid=uuid;
    }

    /**
     * Fetch summary of Smart Player from all participating players (Async)
     * @param waiter Callback function
     */
    public void getSummary(LilyAPI.dataWait waiter) {
        // (SERVERS)
        // TOTAL

        int id = Main.lilyAPI.getRequests();

        try {
            Main.lilyAPI.getConnect().request(new MessageRequest(Collections.<String> emptyList(),"MSOntimeRequest",(id+"|"+uuid).getBytes()));
        }catch (Exception e){
            e.printStackTrace();
        }

        new BukkitRunnable(){
            @Override
            public void run() {
                String message = "";
                int total = 0;
                try {
                    for (MessageEvent e : Main.lilyAPI.getResponses(id)) {
                        message += "&7" + (e.getSender().equals(Main.lilyAPI.getServerName())?"&n":"")+(Main.config.getString("translations."+e.getSender())!=null?Main.config.getString("translations."+e.getSender()):e.getSender()) + "&7: &6" + (e.getMessageAsString().split("[|]")[3]) + " &7mins\n";
                        total+=Integer.parseInt(e.getMessageAsString().split("[|]")[3]);
                    }
                    message+="\n&7Total: &6"+total+" &7minutes";
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                    message = "&cAn error occurred while getting ontime";
                }
                waiter.dataWait(message,total);
            }
        }.runTaskLater(Main.getPlugin(Main.class),5L);
    }

    /**
     * Get player name from object cache
     * @return Player name
     */
    public String getName() {
        return name;
    }

    /**
     * Get player UUID from object cache
     * @return Player UUID
     */
    public UUID getUuid() {
        return uuid;
    }
}
