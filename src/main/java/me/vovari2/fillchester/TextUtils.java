package me.vovari2.fillchester;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TextUtils {

    public static Component getButtonTP(int number){
        return MiniMessage.miniMessage().deserialize("<hover:show_text:'<gray>[ЛКМ] - Телепортироваться к хранилищу'><click:run_command:/fc tp " + number + ">[Tp]</click></hover>");
    }
    public static Component getButtonEdit(int number){
        return MiniMessage.miniMessage().deserialize("<hover:show_text:'<gray>[ЛКМ] - Открыть хранилище по умолчанию'><click:run_command:/fc edit " + number + ">[Edit]</click></hover>");
    }
    public static Component getButtonOpen(int number, String playerName){
        return MiniMessage.miniMessage().deserialize("<hover:show_text:'<gray>[ЛКМ] - Открыть хранилище игрока'><click:run_command:/fc open " + number + " " + playerName + ">[Open]</click></hover>");
    }


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
