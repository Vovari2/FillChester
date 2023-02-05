package me.vovari2.fillchester;

import co.aikar.commands.*;
import com.google.common.collect.ImmutableList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class FC extends JavaPlugin {

    public static FC plugin;
    public static PaperCommandManager commandManager;
    public static FC getInstance(){
        return plugin;
    }

    public static List<FCChest> chests;
    public static HashMap<String, FCPoint> openNewChests;
    public static HashMap<String, FCPoint> openChests;

    public static ImmutableList<String> listAmountSlots;

    @Override
    public void onEnable() {
        plugin = this;
        commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new FCCommands());

        getServer().getPluginManager().registerEvents(new FCListener(), this);

        chests = new ArrayList<>();
        openNewChests = new HashMap<>();
        openChests = new HashMap<>();
        StoreType.setContainers();

        listAmountSlots = ImmutableList.of("9", "18", "27", "36", "45", "54");
        commandManager.getCommandCompletions().registerCompletion("amount_slots", c -> listAmountSlots);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static boolean isListPage(int page) {
        if (page <= 0)
            return false;
        return chests.size() >= (page - 1) * 10 + 1;
    }

    public static void getListInventory(Player player, int page){
        Component text = MiniMessage.miniMessage().deserialize("<yellow> Список хранилищ - <aqua>" + page + "<yellow>:\n");

        int chestOnPage = page * 10;
        for (int i = (page-1) * 10; i < chestOnPage && i < chests.size(); i++){
            FCChest chest = chests.get(i);
            FCPoint point = chest.getPoints().get(0);
            text.append(MiniMessage.miniMessage().deserialize("<yellow> " + (i+1) + ". " + chest.getStoreType().getTitle() + " <#f48a00>" + chest.defaultInventory.getTitle() + " <#ef6400>| <#f8af00>" + point.getX() + " " + point.getY() + " " + point.getZ() + " <#fdd500>" + TextUtils.getButtonTP(i) + " " + TextUtils.getButtonEdit(i) + " " + TextUtils.getButtonOpen(i, player.getName()) + "\n"));
        }
        player.sendMessage(text);
    }

    // Нахождение основной части сундука
    public static FCChest getChest(FCPoint point){
        for (FCChest chest : chests)
            for (FCPoint pos : chest.getPoints())
                if (pos.equals(point))
                    return chest;
        return null;
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
