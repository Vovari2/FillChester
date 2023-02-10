package me.vovari2.fillchester;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Chest;

public enum StoreType {
    BARREL ("<hover:show_text:'<#EF6400>Бочка'>Б</hover>"),
    CHEST ("<hover:show_text:'<#EF6400>Сундук'>С</hover>"),
    DOUBLE_CHEST ("<hover:show_text:'<#EF6400>Двойной сундук'>ДС</hover>");

    private final String title;
    public String getTitle() {
        return title;
    }

    StoreType( String title){
        this.title = title;
    }

    public static boolean equals(Block block, StoreType type){
        if (block.getType().equals(Material.CHEST))
            if (type.equals(StoreType.DOUBLE_CHEST) && !((Chest)block.getBlockData()).getType().equals(Chest.Type.SINGLE))
                return true;
            else return type.equals(StoreType.CHEST) && ((Chest) block.getBlockData()).getType().equals(Chest.Type.SINGLE);
        else return block.getType().equals(Material.BARREL) && type.equals(StoreType.BARREL);
    }
}
