package com.minesuperior.SmarterOntime.commands;

import com.minesuperior.SmarterOntime.Main;
import com.minesuperior.SmarterOntime.utils.Text;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OntimeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if(args.length>0&&sender.hasPermission("minesuperior.commands.ontime.other")){
            OfflinePlayer player = Bukkit.getPlayer(args[0]);
            Main.players.get(player).getSummary((reply,total)-> {
                sender.sendMessage(Text.format("&a&lM&2&lS&7 -- Ontime report for &6"+player.getName()+"&7 --"));
                sender.sendMessage(Text.format(reply));
            });

            return true;
        }else if (!(sender instanceof Player)) {
            sender.sendMessage(Text.format("&7Your on-time is &e∞&7 minutes! (Hint. Do &e/ontime {player}&7)"));
            return false;
        }

        Player player = (Player) sender;
        Main.players.get(player).getSummary((reply,total)-> {
            ComponentBuilder message = new ComponentBuilder(Text.format("&a&lM&2&lS&r "+String.format("&7Your ontime is &6%s&7 minutes! (Hint. &fHover for details&7)",total)));
            message.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder(Text.format(reply)).create()));
            player.spigot().sendMessage(message.create());
        });
        return true;
    }
}
