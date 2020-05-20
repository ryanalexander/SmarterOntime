package com.minesuperior.SmarterOntime;

import com.minesuperior.SmarterOntime.commands.OntimeCommand;
import com.minesuperior.SmarterOntime.events.PlayerEvents;
import com.minesuperior.SmarterOntime.events.PluginMessageListener;
import com.minesuperior.SmarterOntime.utils.LilyAPI;
import com.minesuperior.SmarterOntime.utils.SmartPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Main extends JavaPlugin {

    public static FileConfiguration config;
    public static HashMap<Player, SmartPlayer> players = new HashMap<>();
    public static LilyAPI lilyAPI;

    public void onEnable() {

        // Load configuration
        saveDefaultConfig();
        config=getConfig();

        // Custom LilyPad api layer
        lilyAPI = new LilyAPI();

        PluginManager manager = Bukkit.getServer().getPluginManager();

        // Register Events
        manager.registerEvents(new PlayerEvents(),this);
        manager.registerEvents(new PluginMessageListener(lilyAPI,this),this);

        lilyAPI.getConnect().registerEvents(new PluginMessageListener(lilyAPI,this));

        // Register Commands
        getCommand("ontime").setExecutor(new OntimeCommand());
    }

}
