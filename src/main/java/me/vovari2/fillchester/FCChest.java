package me.vovari2.fillchester;

import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FCChest {
    public boolean isWork;
    private final StoreType typeStore;
    private String title;
    private final List<FCPoint> points;
    private FCInventory defaultInventory;
    private final HashMap<String, FCInventory> playerInventories;

    public List<FCPoint> getPoints(){
        return this.points;
    }
    public String getTitlePoints(){
        FCPoint p1 = points.get(0);
        String text = p1.getX() + " " + p1.getY() + " " + p1.getZ();
        if (points.size() > 1){
            FCPoint p2 = points.get(1);
            text += " и " + p2.getX() + " " + p2.getY() + " " + p2.getZ();
        }
        return text;
    }
    public String getTitleWork(){
        if (isWork)
            return "<green>Включён";
        else return "<red>Выключен";
    }
    public StoreType getStoreType(){
        return this.typeStore;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public FCInventory getDefaultInventory(){
        return defaultInventory;
    }
    public void setDefaultInventory(Inventory inventory){
        defaultInventory = FCInventory.adapt(inventory);
    }

    public void addPlayerInventory(String playerName){
        playerInventories.put(playerName, FCInventory.at(defaultInventory));
    }
    public FCInventory getPlayerInventory(String playerName){
        return playerInventories.get(playerName);
    }
    public void setPlayerInventory(String playerName, Inventory inventory){
        playerInventories.put(playerName, FCInventory.adapt(inventory));
    }
    public HashMap<String, FCInventory> getPlayersInventory(){
        return playerInventories;
    }
    public void clearPlayerInventory(){
        playerInventories.clear();
    }
    public boolean contains(String playerName){
        return playerInventories.containsKey(playerName);
    }

    public FCChest(FCPoint point, String title, int size, StoreType typeStore) {
        isWork = false;
        points = new ArrayList<>();
        points.add(point);
        this.title = title;
        defaultInventory = FCInventory.at(size);
        playerInventories = new HashMap<>();
        this.typeStore = typeStore;
    }
    public FCChest(List<FCPoint> points, String title, int size, StoreType typeStore){
        isWork = false;
        this.points = points;
        this.title = title;
        defaultInventory = FCInventory.at(size);
        playerInventories = new HashMap<>();
        this.typeStore = typeStore;
    }

    public FCChest(List<FCPoint> points, String title, StoreType typeStore, FCInventory defaultInventory, HashMap<String, FCInventory> playerInventories){
        this.points = points;
        this.title = title;
        this.defaultInventory = defaultInventory;
        this.playerInventories = playerInventories;
        this.typeStore = typeStore;
        isWork = true;
    }
}
