package me.vovari2.fillchester;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public enum StoreType {
    BARREL ("<hover:show_text:'<#EF6400>Бочка'>Б</hover>"),
    CHEST ("<hover:show_text:'<#EF6400>Сундук'>С</hover>"),
    DOUBLE_CHEST ("<hover:show_text:'<#EF6400>Двойной сундук'>ДС</hover>");

    private final String title;
    public String getTitle() {
        return title;
    }

    StoreType(String title){
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
