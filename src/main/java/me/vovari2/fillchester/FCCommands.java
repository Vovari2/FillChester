package me.vovari2.fillchester;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Barrel;
import org.bukkit.block.data.type.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@CommandAlias("fc|fillchester")
@CommandPermission("fillchester.admin")
public class FCCommands extends BaseCommand {

    @Subcommand("create")
    @CommandCompletion("@nothing")
    public void create(Player player, int size, String title) {
        Block block = player.getTargetBlock(null, 10);
        // Проверка, является ли блок контейнером
        if (!StoreType.getMaterials().contains(block.getType())){
            TextUtils.sendPlayerErrorMessage(player, "Чтобы создать новое хранилище, нужно навестить на сундук или бочку!");
            return;
        }

        // Проверка, является ли блок сундуком плагина
        if (FC.getChest(FCPoint.adapt(block.getLocation())) != null){
            TextUtils.sendPlayerErrorMessage(player, "Это хранилище уже является хранилищем, созданным при помощи плагина FillChester!");
            return;
        }

        if (!FC.listAmountSlots.contains(String.valueOf(size))){
            TextUtils.sendPlayerErrorMessage(player, "Нельзя задавать такое количество слотов!\n Можно указывать только: 9, 18, 27, 36, 45 и 54");
            return;
        }

        FCPoint point = FCPoint.adapt(block.getLocation());
        if (block.getBlockData() instanceof Chest blockChest){
            if (block.getType().equals(Material.CHEST) && !blockChest.getType().equals(Chest.Type.SINGLE)){
                List<FCPoint> points = new ArrayList<>();
                points.add(point);
                points.add(FC.pointSecondChest(point, blockChest));

                FC.chests.add(new FCChest(points, title, player, size, StoreType.DOUBLE_CHEST));
                TextUtils.sendPlayerMessage(player, "<green>Новый настраиваемый двойной сундук создан!");
            }
            else{
                FC.chests.add(new FCChest(point, title, player, size, StoreType.CHEST));
                TextUtils.sendPlayerMessage(player, "<green>Новый настраиваемый сундук создан!");
            }
        }
        else if (block.getBlockData() instanceof Barrel blockBarrel){
            FC.chests.add(new FCChest(point, title, player, size, StoreType.BARREL));
            TextUtils.sendPlayerMessage(player, "<green>Новая настраиваемая бочка создана!");
        }

        FCChest chest = FC.getChest(point);
        player.openInventory(chest.defaultInventory.addTitle(" (По умолчанию)").getMCInventory());
        FC.openNewChests.put(player.getName().toLowerCase(), FCPoint.adapt(block.getLocation()));
    }

    @Subcommand("edit")
    public void edit(Player player){
        Block block = player.getTargetBlock(null, 10);
        // Проверка, является ли блок контейнером
        if (!StoreType.getMaterials().contains(block.getType())){
            TextUtils.sendPlayerErrorMessage(player, "Чтобы изменить заполнение хранилища, нужно навестить на сундук или бочку!");
            return;
        }

        FCChest chest = FC.getChest(FCPoint.adapt(block.getLocation()));
        // Проверка, является ли блок сундуком плагина
        if (chest == null){
            TextUtils.sendPlayerErrorMessage(player, "Блок не является сундуком, созданным плагином FillChester!");
            return;
        }

        player.openInventory(chest.defaultInventory.addTitle(" (По умолчанию)").getMCInventory());
        FC.openNewChests.put(player.getName().toLowerCase(), FCPoint.adapt(block.getLocation()));
    }

    @Subcommand("open")
    @CommandCompletion("@players")
    public void open(Player player, Player targetPlayer){
        Block block = player.getTargetBlock(null, 10);
        // Проверка, является ли блок контейнером
        if (!StoreType.getMaterials().contains(block.getType())){
            TextUtils.sendPlayerErrorMessage(player, "Чтобы изменить содежимое хранилища игрока, нужно навестить на сундук или бочку!");
            return;
        }

        FCChest chest = FC.getChest(FCPoint.adapt(block.getLocation()));
        // Проверка, является ли блок сундуком плагина
        if (chest == null){
            TextUtils.sendPlayerErrorMessage(player, "Блок не является сундуком, созданным плагином FillChester!");
            return;
        }

        String playerName = targetPlayer.getName().toLowerCase();

        if (chest.contains(playerName)){
            player.openInventory(chest.getPlayerInventory(playerName));
            FC.openChests.put(player.getName().toLowerCase(), FCPoint.adapt(block.getLocation()));
        }
    }

    @Subcommand("list")
    public void list(Player player, int page){
        if (!FC.isListPage(page)){
            TextUtils.sendPlayerErrorMessage(player, "Страницы хранилищ с таким номером не существует!");
            return;
        }
        FCInventory inventory = FC.getListInventory(player, page);
        player.openInventory(inventory.getMCInventory());
        FC.openLists.put(player.getName().toLowerCase(), page);
    }

    @Subcommand("list")
    public void list2(Player player){
        if (!FC.isListPage(1)){
            TextUtils.sendPlayerErrorMessage(player, "Не создано ни одного хранилища!");
            return;
        }
        FCInventory inventory = FC.getListInventory(player, 1);
        player.openInventory(inventory.getMCInventory());
        FC.openLists.put(player.getName().toLowerCase(), 1);
    }


    @Subcommand("help")
    @Default
    public void help(Player player){
        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(1);
        itemMeta.displayName(MiniMessage.miniMessage().deserialize("<!italic><#c16800>Л<#d07800>е<#df8800>й<#ee9700>к<#ee9700>а"));
        List<Component> lore = new ArrayList<>();
        lore.add(MiniMessage.miniMessage().deserialize("<!italic>ꈁ<#f98600> 1/20"));
        itemMeta.lore(lore);
        itemStack.setItemMeta(itemMeta);
        player.getInventory().addItem(itemStack);
    }

    @CatchUnknown
    public void unknown(){
        FC.getInstance().getLogger().info("Work4!");
    }
}