package me.vovari2.fillchester;

import co.aikar.commands.BaseCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {
    private static ItemStack background;
    public static ItemStack getBackground(){
        return background;
    }

    private static ItemStack lastPage;
    public static ItemStack getLastPage(){
        return lastPage;
    }

    private static ItemStack nextPage;
    public static ItemStack getNextPage(){
        return nextPage;
    }

    public static void setItems(){
        background = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta itemMeta = background.getItemMeta();
        itemMeta.displayName(MiniMessage.miniMessage().deserialize("<!italic><gray>Ты думал здесь что-то будет?"));
        background.setItemMeta(itemMeta);

        lastPage = new ItemStack(Material.TIPPED_ARROW,1);
        PotionMeta colorMeta = (PotionMeta) lastPage.getItemMeta();
        colorMeta.setColor(Color.fromRGB(251, 208, 144));
        colorMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        colorMeta.displayName(MiniMessage.miniMessage().deserialize("<!italic><#EF6400><<<#FBD090> Предыдущая страница"));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(""));
        lore.add(MiniMessage.miniMessage().deserialize("<!italic><gray>[ЛКМ/ПКМ] - Перейти на предыдующую страницу"));
        colorMeta.lore(lore);
        lastPage.setItemMeta(colorMeta);

        nextPage = new ItemStack(Material.TIPPED_ARROW,1);
        colorMeta = (PotionMeta) nextPage.getItemMeta();
        colorMeta.setColor(Color.fromRGB(251, 208, 144));
        colorMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        colorMeta.displayName(MiniMessage.miniMessage().deserialize("<!italic><#FBD090>Следующая страница <#EF6400>>>"));
        lore = new ArrayList<>();
        lore.add(Component.text(""));
        lore.add(MiniMessage.miniMessage().deserialize("<!italic><gray>[ЛКМ/ПКМ] - Перейти на следующую страницу"));
        colorMeta.lore(lore);
        nextPage.setItemMeta(colorMeta);

    }
}
