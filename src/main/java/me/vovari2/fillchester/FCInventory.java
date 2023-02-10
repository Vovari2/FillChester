package me.vovari2.fillchester;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FCInventory {

    private ItemStack[] store;
    private final String title;

    private FCInventory(String title, int size){
        store = new ItemStack[size];
        this.title = title;
    }
    private FCInventory(String title, int size, ItemStack[] itemStacks){
        store = new ItemStack[size];
        store = itemStacks.clone();
        this.title = title;
    }

    public Inventory getMCInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player, store.length, Component.text(title));
        inventory.setContents(store.clone());
        return inventory;
    }
    public String getTitle(){
        return title;
    }
    public FCInventory addTitle(String partTitle){
        return new FCInventory(title + partTitle, store.length, store.clone());
    }
    public ItemStack[] getStore(){
        return store;
    }

    public static FCInventory at(FCInventory inventory){
        return new FCInventory(inventory.getTitle(), inventory.store.length, inventory.store);
    }
    public static FCInventory at(String title, int size){
        return new FCInventory(title, size);
    }
    public static FCInventory at(String title, int size, ItemStack[] store){
        return new FCInventory(title, size, store);
    }
    public static FCInventory adapt(String title, Inventory inventory){
        return new FCInventory(title, inventory.getSize(), inventory.getContents());
    }
}
