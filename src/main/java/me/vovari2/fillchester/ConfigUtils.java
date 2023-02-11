package me.vovari2.fillchester;

import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigUtils {
    // Load list stores from stores.yml
    public static void loadStores(){
        File file = new File(FC.getInstance().getDataFolder(), "stores.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection section = config.getConfigurationSection("stores");
        if (section == null || section.getKeys(false).size() == 0){
            TextUtils.sendWarningConsoleMessage("Не получилось загрузить хранилища, так как нет сохраненных хранилищ!");
            return;
        }

        FC.stores.clear();
        for (String nameStore : section.getKeys(false)){
            // Load location store
            List<FCPoint> points = new ArrayList<>();
            if (nameStore.contains("/")){
                String[] strings = nameStore.split("/");
                points.add(FCPoint.adapt(strings[0]));
                points.add(FCPoint.adapt(strings[1]));
            }
            else points.add(FCPoint.adapt(nameStore));

            // Init path on store in the config
            String path = "stores." + nameStore + ".";

            // Init values store
            StoreType type = StoreType.valueOf(config.getString(path + "type"));
            String title = config.getString(path + "title");
            int size = config.getInt(path + "size");

            FCInventory defaultInventory = FCInventory.at(title, size, loadInventory(config, path + "default", size));

            HashMap<String, FCInventory> playerInventories = new HashMap<>();
            section = config.getConfigurationSection(path + "players");
            if (section != null)
                for(String playerName : section.getKeys(false))
                    playerInventories.put(playerName, FCInventory.at(title, size, loadInventory(config, path + "players." + playerName, size)));


            Block block = points.get(0).getWorld().getBlockAt(points.get(0).getLocation());
            if (StoreType.equals(block, type))
                FC.stores.add(new FCChest(points, type, defaultInventory, playerInventories));
            else TextUtils.sendWarningConsoleMessage("Не обнаружен блок хранилища " + type + " на координатах " + points.get(0).getString());
        }
    }

    // Save list stores in stores.yml
    public static void saveStores(){
        File file = new File(FC.getInstance().getDataFolder(), "stores.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set("stores", null);
        for (FCChest chest : FC.stores){
            String path;
            if (chest.getPoints().size() > 1)
                path = "stores." + chest.getPoints().get(0).getString() + "/" + chest.getPoints().get(1).getString() + ".";
            else path = "stores." + chest.getPoints().get(0).getString() + ".";
            config.set(path + "type", chest.getStoreType().toString());
            config.set(path + "title", chest.getDefaultInventory().getTitle());
            config.set(path + "size", chest.getDefaultInventory().getStore().length);
            saveInventory(config, path + "default", chest.getDefaultInventory().getStore());
            for (String player : chest.getPlayersInventory().keySet())
                saveInventory(config, path + "players." + player, chest.getPlayerInventory(player).getStore());
        }
        try{
            config.save(file);
        }
        catch (IOException error){
            TextUtils.sendWarningConsoleMessage("Can't save \"stores.yml\": \n" + error.getMessage());
        }
    }

    // Load/save inventory from/in stores.yml
    private static ItemStack[] loadInventory(FileConfiguration config, String path, int size){
        ItemStack[] itemStacks = new ItemStack[size];
        ConfigurationSection section = config.getConfigurationSection(path);
        if (section == null)
            return itemStacks;

        for (String slot : section.getKeys(false)){
            int index = Integer.parseInt(slot.substring(slot.indexOf("_")+1));
            itemStacks[index] = config.getItemStack(path + "." + slot);
        }
        return itemStacks;
    }
    private static void saveInventory(FileConfiguration config, String path, ItemStack[] itemStacks){
        int size = itemStacks.length;
        for (int i = 0; i < size; i++)
            if (itemStacks[i] != null)
                config.set(path + ".slot_" + i, itemStacks[i]);
    }
}
