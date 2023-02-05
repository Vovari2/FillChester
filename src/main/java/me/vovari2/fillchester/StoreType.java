package me.vovari2.fillchester;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public enum StoreType {
    BARREL (MiniMessage.miniMessage().deserialize("<hover:show_text:'<#EF6400>Бочка'><#EF6400>Б</hover>")),
    CHEST (MiniMessage.miniMessage().deserialize("<hover:show_text:'<#EF6400>Сундук'><#EF6400>С</hover>")),
    DOUBLE_CHEST (MiniMessage.miniMessage().deserialize("<hover:show_text:'<#EF6400>Двойной сундук'><#EF6400>ДС</hover>"));

    private final Component title;
    public Component getTitle() {
        return title;
    }

    StoreType(Component title){
        this.title = title;
    }

    private static List<Material> containers;
    public static List<Material> getMaterials(){
        return containers;
    }
    public static void setContainers(){
        containers = new ArrayList<>();
        containers.add(Material.CHEST);
        containers.add(Material.BARREL);
    }
}
