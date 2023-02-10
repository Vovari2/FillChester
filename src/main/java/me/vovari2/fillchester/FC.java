package me.vovari2.fillchester;

import co.aikar.commands.*;
import com.google.common.collect.ImmutableList;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class FC extends JavaPlugin {

    public static FC plugin;
    public static PaperCommandManager commandManager;
    public static FC getInstance(){
        return plugin;
    }

    public static List<FCChest> stores; // List stores
    public static HashMap<String, FCPoint> openNewChests; // List of open inventories
    public static HashMap<String, FCPoint> openChests; // List of open inventories

    public static ImmutableList<Integer> amountSlots = ImmutableList.of(9, 18, 27, 36, 45, 54);
    public static ImmutableList<Material> materialContainers = ImmutableList.of(Material.CHEST, Material.BARREL);

    @Override
    public void onEnable() {
        plugin = this;
        commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new FCCommands());

        if (!new File(getDataFolder(), "stores.yml").exists())
            saveResource("stores.yml", false);

        getServer().getPluginManager().registerEvents(new FCListener(), this);

        stores = new ArrayList<>();
        openNewChests = new HashMap<>();
        openChests = new HashMap<>();

        ConfigUtils.loadStores();
    }

    @Override
    public void onDisable() {
        ConfigUtils.saveStores();
    }

    public static void reload(Player player){
        ConfigUtils.saveStores();
        if (!new File(plugin.getDataFolder(), "stores.yml").exists())
            plugin.saveResource("stores.yml", false);

        stores = new ArrayList<>();
        openNewChests = new HashMap<>();
        openChests = new HashMap<>();

        ConfigUtils.loadStores();
    }

    public static boolean isListPage(int page) {
        return page <= 0 || stores.size() < (page - 1) * 10 + 1;
    }
    public static void getListInventory(Player player, int page){
        String text = "<#0075ff>С<#007dff>п<#0084ff>и<#008cff>с<#0093fe>о<#009bfe>к <#00a2fe>х<#00aafe>р<#00b2fe>а<#00b9fe>н<#00c1fe>и<#00c8fd>л<#00d0fd>и<#00d7fd>щ <#00dffd>- <#0075ff>С<#007cff>т<#0082ff>р<#0089fe>а<#0090fe>н<#0096fe>и<#009dfe>ц<#00a4fd>а <#00aafd>" + page + "<#00b1fd>:\n";

        int chestOnPage = page * 10;
        for (int i = (page-1) * 10; i < chestOnPage && i < stores.size(); i++){
            FCChest chest = stores.get(i);
            FCPoint point = chest.getPoints().get(0);
            text += "<#00B1FD> " + (i+1) + ". <#EF6400>[" + chest.getStoreType().getTitle() + "] <#f48a00>\"" + chest.getDefaultInventory().getTitle() + "\" <#f8af00>" + point.getX() + " " + point.getY() + " " + point.getZ() + " <#fdd500>" + TextUtils.getButtonTP(i+1) + " " + TextUtils.getButtonEdit(i+1) + " " + TextUtils.getButtonOpen(i+1, player.getName()) + "\n";
        }
        TextUtils.sendPlayerMessage(player, text);
    }

    // Нахождение основной части сундука
    public static FCChest getChest(FCPoint point){
        for (FCChest chest : stores)
            for (FCPoint pos : chest.getPoints())
                if (pos.equals(point))
                    return chest;
        return null;
    }
    public static FCChest getChest(int number){
        try{
            return stores.get(number);
        }
        catch(Exception error){
            return null;
        }
    }
    public static int getNumberChest(FCChest chest){
        return stores.indexOf(chest);
    }

    // Получение второй части сундука
    public static FCPoint pointSecondChest(FCPoint point, Chest chest){
        if (FC.isSecondChest(point.add(1,0,0), chest.getType(), chest.getFacing()))
            return point.add(1,0,0);
        else if (FC.isSecondChest(point.subtract(1,0,0), chest.getType(), chest.getFacing()))
            return point.subtract(1,0,0);
        else if (FC.isSecondChest(point.add(0,0,1), chest.getType(), chest.getFacing()))
            return point.add(0,0,1);
        else if (FC.isSecondChest(point.subtract(0,0,1), chest.getType(), chest.getFacing()))
            return point.subtract(0,0,1);
        return FCPoint.at(point);
    }
    public static boolean isSecondChest(FCPoint point, Chest.Type type, BlockFace facing){
        BlockData blockData = point.getWorld().getBlockAt(point.getX(), point.getY(), point.getZ()).getBlockData();
        if (!(blockData instanceof Chest))
            return false;
        Chest chest = (Chest) point.getWorld().getBlockAt(point.getX(), point.getY(), point.getZ()).getBlockData();
        return chest.getType() == reverseType(type) && chest.getFacing() == facing;
    }
    public static Chest.Type reverseType(Chest.Type type){
        if (type.equals(Chest.Type.RIGHT))
            return Chest.Type.LEFT;
        else return Chest.Type.RIGHT;
    }
}
