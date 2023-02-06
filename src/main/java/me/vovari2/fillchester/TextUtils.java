package me.vovari2.fillchester;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TextUtils {

    public static String getButtonTP(int number){
        return "<hover:show_text:'<gray>[ЛКМ] - Телепортироваться к хранилищу'><click:suggest_command:/fc tp " + number + ">[Tp]</click></hover>";
    }
    public static String getButtonEdit(int number){
        return "<hover:show_text:'<gray>[ЛКМ] - Открыть хранилище по умолчанию'><click:suggest_command:/fc edit " + number + ">[Edit]</click></hover>";
    }
    public static String getButtonOpen(int number, String playerName){
        return "<hover:show_text:'<gray>[ЛКМ] - Открыть хранилище игрока'><click:suggest_command:/fc open " + number + " " + playerName + ">[Open]</click></hover>";
    }


    public static void sendInfoStore(Player player, FCChest chest, int number){
        TextUtils.sendPlayerMessage(player, "<#0075ff>И<#007aff>н<#0080ff>ф<#0085ff>о<#008aff>р<#0090ff>м<#0095fe>а<#009afe>ц<#009ffe>и<#00a5fe>я <#00aafe>о <#00affe>х<#00b5fe>р<#00bafe>а<#00bffe>н<#00c5fe>и<#00cafd>л<#00cffd>и<#00d4fd>щ<#00dafd>е<#00dffd>:\n <#EF6400>Состояние: " + chest.getTitleWork() + "\n <#EF6400>Тип: <#f48a00>[" + chest.getStoreType().getTitle() + "] \n <#EF6400>Номер: <#f48a00>" +  number + "\n <#EF6400>Название: <#f48a00>" + chest.getDefaultInventory().getTitle() + "\n <#EF6400>Координаты: <#f48a00>" + chest.getTitlePoints() + "\n <#EF6400>Кол. слотов: <#f48a00>" + chest.getDefaultInventory().getStore().length + "\n <#fdd500>" + TextUtils.getButtonTP(number) + " " + TextUtils.getButtonEdit(number) + " " + TextUtils.getButtonOpen(number, player.getName()) + "\n");
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
