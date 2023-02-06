package me.vovari2.fillchester;

import org.bukkit.block.Block;
import org.bukkit.block.Lidded;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class FCListener implements Listener {

    @EventHandler
    public void onOpenChest(PlayerInteractEvent event){
        if (!event.getPlayer().hasPermission("fillchester.blacklist")) {
            // Проверка, нажали ли правой кнопкой мыши по блоку
            if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
                return;

            Block block = event.getClickedBlock();
            // Проверка, является ли нажатый блок хранилищем
            if (block == null || !StoreType.getMaterials().contains(block.getType()))
                return;

            FCChest chest = FC.getChest(FCPoint.adapt(block.getLocation()));
            // Проверка, является ли блок сундуками плагина
            if (chest == null)
                return;

            Player player = event.getPlayer();
            String playerName = player.getName().toLowerCase();
            // Проверка, является ли сундук плагина включеным
            if (!chest.isWork){
                TextUtils.sendPlayerErrorMessage(player, "Это хранилище, созданное плагином FillChester, выключено!");
                event.setCancelled(true);
                return;
            }

            if (chest.contains(playerName))
                player.openInventory(chest.getPlayerInventory(playerName));
            else {
                chest.addPlayerInventory(playerName);
                player.openInventory(chest.getPlayerInventory(playerName));
            }
            ((Lidded) event.getClickedBlock().getState()).open();
            FC.openChests.put(player.getName().toLowerCase(), FCPoint.adapt(event.getClickedBlock().getLocation()));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCloseChest(InventoryCloseEvent event){
        if (event.getPlayer() instanceof Player player){
            String playerName = player.getName().toLowerCase();
            if (FC.openNewChests.containsKey(playerName)){
                FCChest chest = FC.getChest(FC.openNewChests.get(playerName));
                if (chest != null){
                    chest.setDefaultInventory(event.getInventory());
                    chest.isWork = true;
                    FC.openNewChests.remove(playerName);
                }
                else TextUtils.sendPlayerErrorMessage((Player) event.getPlayer(), "Не удалось сохранить содержимое хранилища (хранилище удалено)!");
            }
            else if (FC.openChests.containsKey(playerName)){
                FCChest chest = FC.getChest(FC.openChests.get(playerName));
                if (chest != null){
                    chest.setPlayerInventory(playerName, event.getInventory());
                    for (FCPoint point : chest.getPoints())
                        ((Lidded) point.getWorld().getBlockAt(point.getX(), point.getY(), point.getZ()).getState()).close();
                    FC.openChests.remove(playerName);
                }
            }
        }
    }
}
