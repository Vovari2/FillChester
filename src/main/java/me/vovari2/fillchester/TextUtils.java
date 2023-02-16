package me.vovari2.fillchester;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public class TextUtils {

    public static String getButtonTP(int number){
        return "<hover:show_text:'<gray>[ЛКМ] - Телепортироваться к хранилищу'><click:run_command:/fc tp " + number + ">[Tp]</click></hover>";
    }
    public static String getButtonEdit(int number){
        return "<hover:show_text:'<gray>[ЛКМ] - Изменить хранилище по умолчанию'><click:run_command:/fc edit " + number + ">[Edit]</click></hover>";
    }
    public static String getButtonOpen(int number, String playerName){
        return "<hover:show_text:'<gray>[ЛКМ] - Открыть хранилище игрока'><click:suggest_command:/fc open " + number + " " + playerName + ">[Open]</click></hover>";
    }
    public static String getButtonDelete(int number){
        return "<hover:show_text:'<gray>[ЛКМ] - Удалить хранилище'><click:suggest_command:/fc delete " + number + ">[Delete]</click></hover>";
    }
    public static String getButtonClear(int number){
        return "<hover:show_text:'<gray>[ЛКМ] - Очистить инвентарь хранилища у игроков'><click:suggest_command:/fc clear " + number + ">[Clear]</click></hover>";
    }
    public static String getButtonInfo(int number){
        return "<hover:show_text:'<gray>[ЛКМ] - Показать информацию о хранилище'><click:run_command:/fc info " + number + ">[Info]</click></hover>";
    }
    public static String getButtonNextPage(int page){
        return "<hover:show_text:'<gray>[ЛКМ] - Следующая страница'><click:run_command:/fc list " + (page+1) + ">>></click></hover>";
    }
    public static String getButtonLastPage(int page){
        return "<hover:show_text:'<gray>[ЛКМ] - Предыдущая страница'><click:run_command:/fc list " + (page-1) + "><<</click></hover>";
    }

    public static void sendInfoStore(Player player, FCChest chest, int number){
        TextUtils.sendPlayerMessage(player, "<#0075ff>И<#007aff>н<#0080ff>ф<#0085ff>о<#008aff>р<#0090ff>м<#0095fe>а<#009afe>ц<#009ffe>и<#00a5fe>я <#00aafe>о <#00affe>х<#00b5fe>р<#00bafe>а<#00bffe>н<#00c5fe>и<#00cafd>л<#00cffd>и<#00d4fd>щ<#00dafd>е<#00dffd>:\n <#EF6400>Состояние: " + chest.getTitleWork() + "\n <#EF6400>Тип: <#f48a00>[" + chest.getStoreType().getTitle() + "] \n <#EF6400>Номер: <#f48a00>" +  number + "\n <#EF6400>Координаты: <#f48a00>" + chest.getTitlePoints() + "\n <#EF6400>Кол. слотов: <#f48a00>" + chest.getDefaultInventory().getStore().length + "\n <#fdd500>" + TextUtils.getButtonTP(number) + " " + TextUtils.getButtonEdit(number) + " " + TextUtils.getButtonDelete(number) + " " + TextUtils.getButtonClear(number) + "\n");
    }

    public static String removeLegacyColorString(String title){
        if (title == null)
            return "";
        if (title.contains("&")){
            TextUtils.sendWarningConsoleMessage("Строка не должна содержать цвета &f, &a и другие. Нужно использовать <white>, <green>, <bold>");
            while (title.contains("&"))
                title = title.substring(0, title.lastIndexOf("&")) + title.substring(title.lastIndexOf("&") + 2);
        }
        return title;
    }

    public static void sendPlayerErrorMessage(Player player, String message){
        player.sendMessage(MiniMessage.miniMessage().deserialize("<red><bold>[!] <gray><!bold>" + message));
    }
    public static void sendPlayerMessage(Player player, String message){
        player.sendMessage(MiniMessage.miniMessage().deserialize(message));
    }
    public static void sendWarningConsoleMessage(String message){
        FC.getInstance().getLogger().warning(message);
    }
}
