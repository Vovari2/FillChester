package me.vovari2.fillchester;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FCChest {
    public boolean isWork;
    private final StoreType typeStore;
    private final List<FCPoint> points;
    public FCInventory defaultInventory;
    public HashMap<String, FCInventory> playerInventories;

    public List<FCPoint> getPoints(){
        return this.points;
    }
    public StoreType getStoreType(){
        return this.typeStore;
    }

    public void setDefaultInventory(Inventory inventory){
        defaultInventory = FCInventory.adapt(defaultInventory.getTitle(), inventory);
    }

    public void addPlayerInventory(String playerName){
        playerInventories.put(playerName, FCInventory.at(defaultInventory));
    }
    public Inventory getPlayerInventory(String playerName){
        return playerInventories.get(playerName).getMCInventory();
    }
    public void setPlayerInventory(String playerName, Inventory inventory){
        playerInventories.put(playerName, FCInventory.adapt(defaultInventory.getTitle(), inventory));
    }
    public boolean contains(String playerName){
        return playerInventories.containsKey(playerName);
    }

    public FCChest(FCPoint point, String title, Player player, int size, StoreType typeStore) {
        isWork = false;
        points = new ArrayList<>();
        points.add(point);
        defaultInventory = FCInventory.at(player, title, size);
        playerInventories = new HashMap<>();
        this.typeStore = typeStore;
    }
    public FCChest(List<FCPoint> points, String title, Player player, int size, StoreType typeStore){
        isWork = false;
        this.points = points;
        defaultInventory = FCInventory.at(player, title, size);
        playerInventories = new HashMap<>();
        this.typeStore = typeStore;
    }
}
