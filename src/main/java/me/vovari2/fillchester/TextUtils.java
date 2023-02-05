package me.vovari2.fillchester;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TextUtils {

    public static void sendPlayerErrorMessage(Player player, String message){
        player.sendMessage(MiniMessage.miniMessage().deserialize("<red><bold>[!] <gray><!bold>" + message));
    }
    public static void sendPlayerMessage(Player player, String message){
        player.sendMessage(MiniMessage.miniMessage().deserialize(message));
    }
    public static void sendConsoleMessage(CommandSender sender, String message){
        sender.sendMessage(MiniMessage.miniMessage().deserialize(message));
    }
}
