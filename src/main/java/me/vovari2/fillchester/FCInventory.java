package me.vovari2.fillchester;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FCInventory {

    private ItemStack[] store;
    private FCInventory(int size){
        store = new ItemStack[size];
    }
    private FCInventory(int size, ItemStack[] itemStacks){
        store = new ItemStack[size];
        store = itemStacks.clone();

    }

    public Inventory getMCInventory(Player player, String title){
        Inventory inventory = Bukkit.createInventory(player, store.length, MiniMessage.miniMessage().deserialize(title));
        inventory.setContents(store.clone());
        return inventory;
    }
    public ItemStack[] getStore() {
        return store;
    }

    public static FCInventory at(FCInventory inventory){
        return new FCInventory(inventory.store.length, inventory.store);
    }
    public static FCInventory at(int size){
        return new FCInventory(size);
    }
    public static FCInventory at(int size, ItemStack[] store){
        return new FCInventory(size, store);
    }
    public static FCInventory adapt(Inventory inventory){
        return new FCInventory(inventory.getSize(), inventory.getContents());
    }
}
