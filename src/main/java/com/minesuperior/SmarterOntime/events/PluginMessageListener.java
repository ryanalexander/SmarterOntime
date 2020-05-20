package com.minesuperior.SmarterOntime.events;

import com.minesuperior.SmarterOntime.Main;
import com.minesuperior.SmarterOntime.utils.JavaUtils;
import com.minesuperior.SmarterOntime.utils.LilyAPI;
import lilypad.client.connect.api.Connect;
import lilypad.client.connect.api.event.EventListener;
import lilypad.client.connect.api.event.MessageEvent;
import lilypad.client.connect.api.request.RequestException;
import lilypad.client.connect.api.request.impl.MessageRequest;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class PluginMessageListener implements Listener {

    Main main;
    LilyAPI lilyConnection;

    public PluginMessageListener(LilyAPI lilyConnection, Main main){
        this.main=main;
        this.lilyConnection=lilyConnection;
    }

    @EventListener
    public void onMessage(MessageEvent e) {
        switch(e.getChannel()){

            // Incoming request for ontime with player UUID
            case "MSOntimeRequest":
                // Should this server be counted towards ontime
                if(Main.config.getBoolean("counter.to_count"))
                try {
                    String[] args = e.getMessageAsString().split("[|]");
                    String identifier = args[0];
                    String uuid = args[1];
                    OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
                    try {
                        List<String> players = new ArrayList<>();
                        players.add(player.getName());
                        List<Integer> actions = new ArrayList<>();
                        actions.add(8);
                        int total = 0;
                        long last = 0;
                        boolean login;
                        for(String[] s : CoreProtect.getInstance().getAPI().performLookup(((JavaUtils.getDayNumberOld(new Date())+1) * 86400),players,null,null,null,actions,0,null)){
                            long time = Long.parseLong(s[0]);
                            login = s[6].equals("1");
                            if(login){
                                last = time;
                            }else if(last!=0) {
                                total+=(int)time-last;
                                last = 0;
                            }
                        }
                        if(player.isOnline())
                            total +=(int)(Long.parseLong((System.currentTimeMillis()+"").substring(0,10))-last);
                        total=total/60;

                        Main.lilyAPI.getConnect().request(new MessageRequest(e.getSender(),"MSOntimeReply",Main.lilyAPI.getServerName()+"|"+identifier+"|"+player.getName()+"|"+(total)));
                    } catch (RequestException requestException) {
                        requestException.printStackTrace();
                    }
                }catch (UnsupportedEncodingException ex){
                    ex.printStackTrace();
                }
                break;

            // Parse reply from each server after a request
            case "MSOntimeReply":
                try {
                    String[] args = e.getMessageAsString().split("[|]");
                    String identifier = args[1];
                    Main.lilyAPI.cacheResponse(Integer.valueOf(identifier),e);
                }catch (UnsupportedEncodingException ex){
                    ex.printStackTrace();
                }
                break;
        }
    }

}
