package me.vovari2.fillchester;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class ConfigUtils {
    public static void loadStores(){
        File file = new File(FC.getInstance().getDataFolder(), "stores.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        FC.stores.clear();
        for (String nameStore : config.getConfigurationSection("stores").getKeys(false)){
            FCPoint point = FCPoint.adapt(nameStore);
            String path = "stores." + nameStore + ".";
        }
    }
    public static void saveStores(){
        File file = new File(FC.getInstance().getDataFolder(), "stores.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set("stores", null);
        for (FCChest chest : FC.stores){
            String path = "stores." + chest.getPoints().get(0).getString() + ".";
            config.set(path + "title", chest.getDefaultInventory().getTitle());
            config.set(path + "player", ((Player)chest.getDefaultInventory().getHolder()).getName());
            config.set(path + "default", chest.getDefaultInventory().getMCInventory().getContents());
            for (String player : chest.getPlayersInventory().keySet()){
                config.set(path + "players." + player, chest.getPlayerInventory(player).getMCInventory().getContents());
            }
        }
        try{
            config.save(file);
        }
        catch (IOException error){
            TextUtils.sendWarningConsoleMessage("Can't save \"stores.yml\": \n" + error.getMessage());
        }
    }
}
