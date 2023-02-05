package me.vovari2.fillchester;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public enum StoreType {
    BARREL (MiniMessage.miniMessage().deserialize("<!italic><#EF6400>Бочка"), new ItemStack(Material.BARREL, 1)),
    CHEST (MiniMessage.miniMessage().deserialize("<!italic><#EF6400>Сундук"), new ItemStack(Material.CHEST, 1)),
    DOUBLE_CHEST (MiniMessage.miniMessage().deserialize("<!italic><#EF6400>Двойной сундук"), new ItemStack(Material.CHEST, 2));

    private final ItemStack item;
    private final Component title;
    public ItemStack getItem(){
        return item;
    }
    public Component getTitle() {
        return title;
    }

    StoreType(Component title, ItemStack item){
        this.item = item;
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
