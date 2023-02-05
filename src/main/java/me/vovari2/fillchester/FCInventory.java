package me.vovari2.fillchester;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class FCInventory {

    private ItemStack[] store;
    private final InventoryHolder holder;
    private final String title;

    private FCInventory(InventoryHolder holder, String title, int size){
        store = new ItemStack[size];
        this.holder = holder;
        this.title = title;
    }
    private FCInventory(InventoryHolder holder, String title, int size, ItemStack[] itemStacks){
        store = new ItemStack[size];
        store = itemStacks.clone();
        this.holder = holder;
        this.title = title;
    }

    public Inventory getMCInventory(){
        Inventory inventory = Bukkit.createInventory(holder, store.length, title);
        inventory.setContents(store.clone());
        return inventory;
    }
    public String getTitle(){
        return title;
    }
    public FCInventory addTitle(String partTitle){
        return new FCInventory(holder, title + partTitle, store.length, store.clone());
    }
    public ItemStack[] getStore(){
        return store;
    }
    public void setStore(ItemStack[] store){
        this.store = store;
    }

    public static FCInventory at(FCInventory inventory){
        return new FCInventory(inventory.holder, inventory.getTitle(), inventory.store.length);
    }
    public static FCInventory at(InventoryHolder holder, String title, int size){
        return new FCInventory(holder, title, size);
    }
    public static FCInventory at(Player player, String title, int size){
        return new FCInventory(player, title, size);
    }
    public static FCInventory adapt(String title, Inventory inventory){
        return new FCInventory(inventory.getHolder(), title, inventory.getSize(), inventory.getContents());
    }
}
