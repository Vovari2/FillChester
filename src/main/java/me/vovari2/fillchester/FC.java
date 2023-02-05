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
    public static HashMap<String, Integer> openLists;

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
        openLists = new HashMap<>();
        StoreType.setContainers();
        ItemUtils.setItems();

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
        return chests.size() >= (page - 1) * 45 + 1;
    }

    public static FCInventory getListInventory(Player player, int page){
        int chestOnPage = page * 45;
        FCInventory inventory = FCInventory.at(player, "Хранилища - <Страница " + page + ">", 54);

        ItemStack[] itemStacks = inventory.getStore();
        for (int i = 0; i < 45 && i < chests.size(); i++){
            FCChest chest = chests.get((page - 1) * 45 + i);
            itemStacks[i] = chest.getStoreType().getItem();
            ItemMeta itemMeta = itemStacks[i].getItemMeta();
            itemMeta.displayName(chest.getStoreType().getTitle());
            FCPoint point = chest.getPoints().get(0);
            List<Component> lore = new ArrayList<>();
            lore.add(MiniMessage.miniMessage().deserialize("<!italic><#FD9800>Название: <#FDD500>" + chest.defaultInventory.getTitle()));
            lore.add(MiniMessage.miniMessage().deserialize("<!italic><#FD9800>Координаты: <#FDD500>" + point.getX() + " " + point.getY() + " " + point.getZ()));
            lore.add(Component.text(""));
            lore.add(MiniMessage.miniMessage().deserialize("<!italic><#AAAAAA>[ЛКМ] - Открыть сундук по умолчанию"));
            lore.add(MiniMessage.miniMessage().deserialize("<!italic><#AAAAAA>[ПКМ] - Телепортироваться к сундуку"));
            itemMeta.lore(lore);
            itemStacks[i].setItemMeta(itemMeta);
        }
        for (int i = 46; i < 53; i++)
            itemStacks[i] = ItemUtils.getBackground();
        itemStacks[45] = ItemUtils.getLastPage();
        itemStacks[53] = ItemUtils.getNextPage();
        inventory.setStore(itemStacks);
        return inventory;
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
