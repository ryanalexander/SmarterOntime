package com.minesuperior.SmarterOntime.utils;

import lilypad.client.connect.api.Connect;
import lilypad.client.connect.api.event.MessageEvent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class LilyAPI {

    Connect connect;
    int requests = 0;
    HashMap<String, LilyServer> servers = new HashMap<>();
    HashMap<Integer, List<MessageEvent>> cached_replies = new HashMap<>();

    /**
     * Register API and access sub methods
     */
    public LilyAPI() {
        connect = getServer().getServicesManager().getRegistration(Connect.class).getProvider();
    }

    /**
     * Fetch server unique name
     * @return Lilypad unique server name
     */
    public String getServerName() {
        return connect.getSettings().getUsername();
    }

    /**
     * Generate Request ID
     * @return Unique request id
     */
    public int getRequests() {
        if(requests>9999)requests=1;
        requests++;
        return requests;
    }

    /**
     * Send a player to a specific server
     * @param server Lilypad Server
     * @param player Bukkit Player
     */
    public void sendPlayer(LilyServer server, Player player) throws ServerWhitelistedExeption, ServerFullExeption {
        // TODO Create method for sending players to specific servers
    }



    public void cacheResponse(Integer identifier, MessageEvent e){
        if(!this.cached_replies.containsKey(identifier))this.cached_replies.put(identifier,new ArrayList<MessageEvent>());
        this.cached_replies.get(identifier).add(e);
    }
    public List<MessageEvent> getResponses(Integer identifier){
        List<MessageEvent> reply = this.cached_replies.get(identifier);
        this.cached_replies.remove(identifier);
        return reply;
    }

    class LilyServer {
        private String name;

        public LilyServer(String name) {
            this.name=name;
        }

        public int getCount() {
            return 0;
        }
    }

    public Connect getConnect() {
        return this.connect;
    }

    class ServerWhitelistedExeption extends Exception {
        public ServerWhitelistedExeption() {
            super();
        }
    }
    class ServerFullExeption extends Exception {
        public ServerFullExeption() {
            super();
        }
    }

    public static interface dataWait {
        public void dataWait(String data, Integer total);
    }

}
