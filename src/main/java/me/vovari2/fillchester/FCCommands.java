package me.vovari2.fillchester;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Barrel;
import org.bukkit.block.data.type.Chest;
import org.bukkit.entity.Player;

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
        if (!FC.materialContainers.contains(block.getType())){
            TextUtils.sendPlayerErrorMessage(player, "Чтобы создать новое хранилище, нужно навестить на сундук или бочку!");
            return;
        }

        // Проверка, является ли блок сундуком плагина
        if (FC.getChest(FCPoint.adapt(block.getLocation())) != null){
            TextUtils.sendPlayerErrorMessage(player, "Это хранилище уже является хранилищем, созданным при помощи плагина FillChester!");
            return;
        }

        if (!FC.amountSlots.contains(size)){
            TextUtils.sendPlayerErrorMessage(player, "Нельзя задавать такое количество слотов!\n Можно указывать только: 9, 18, 27, 36, 45 и 54");
            return;
        }

        FCPoint point = FCPoint.adapt(block.getLocation());
        if (block.getBlockData() instanceof Chest blockChest){
            if (block.getType().equals(Material.CHEST) && !blockChest.getType().equals(Chest.Type.SINGLE)){
                List<FCPoint> points = new ArrayList<>();
                points.add(point);
                points.add(FC.pointSecondChest(point, blockChest));

                FC.stores.add(new FCChest(points, title, size, StoreType.DOUBLE_CHEST));
                TextUtils.sendPlayerMessage(player, "<green>Новый настраиваемый двойной сундук создан!");
            }
            else{
                FC.stores.add(new FCChest(point, title, size, StoreType.CHEST));
                TextUtils.sendPlayerMessage(player, "<green>Новый настраиваемый сундук создан!");
            }
        }
        else if (block.getBlockData() instanceof Barrel){
            FC.stores.add(new FCChest(point, title, size, StoreType.BARREL));
            TextUtils.sendPlayerMessage(player, "<green>Новая настраиваемая бочка создана!");
        }

        FCChest chest = FC.getChest(point);
        player.openInventory(chest.getDefaultInventory().addTitle(" (По умолчанию)").getMCInventory(player));
        FC.openNewChests.put(player.getName().toLowerCase(), FCPoint.adapt(block.getLocation()));
    }


    @Subcommand("edit")
    @CommandCompletion("@nothing")
    public void edit(Player player){
        Block block = player.getTargetBlock(null, 10);
        // Проверка, является ли блок контейнером
        if (!FC.materialContainers.contains(block.getType())){
            TextUtils.sendPlayerErrorMessage(player, "Чтобы изменить заполнение хранилища, нужно навестить на сундук или бочку!");
            return;
        }

        FCChest chest = FC.getChest(FCPoint.adapt(block.getLocation()));
        // Проверка, является ли блок сундуком плагина
        if (chest == null){
            TextUtils.sendPlayerErrorMessage(player, "Блок не является сундуком, созданным плагином FillChester!");
            return;
        }

        player.openInventory(chest.getDefaultInventory().addTitle(" (По умолчанию)").getMCInventory(player));
        FC.openNewChests.put(player.getName().toLowerCase(), FCPoint.adapt(block.getLocation()));
    }
    @Subcommand("edit")
    @CommandCompletion("@nothing")
    public void edit(Player player, int number){
        FCChest chest = FC.getChest(number - 1);
        // Проверка, является ли блок контейнером
        if (chest == null){
            TextUtils.sendPlayerErrorMessage(player, "Хранилища с таким номером не существует!");
            return;
        }

        if (!chest.isWork) {
            TextUtils.sendPlayerErrorMessage(player, "Хранилище с таким номером выключено!");
            return;
        }

        player.openInventory(chest.getDefaultInventory().addTitle(" (По умолчанию)").getMCInventory(player));
        FC.openNewChests.put(player.getName().toLowerCase(), chest.getPoints().get(0));
    }


    @Subcommand("tp")
    @CommandCompletion("@nothing")
    public void teleport(Player player, int number){
        FCChest chest = FC.getChest(number - 1);
        // Проверка, является ли блок контейнером
        if (chest == null){
            TextUtils.sendPlayerErrorMessage(player, "Хранилища с таким номером не существует!");
            return;
        }
        player.teleport(chest.getPoints().get(0).getLocation().add(0.5F, 0, 0.5F));
    }


    @Subcommand("open")
    public void open(Player player, int number){
        FCChest chest = FC.getChest(number - 1);
        // Проверка, является ли блок контейнером
        if (chest == null){
            TextUtils.sendPlayerErrorMessage(player, "Хранилища с таким номером не существует!");
            return;
        }

        if (!chest.isWork) {
            TextUtils.sendPlayerErrorMessage(player, "Хранилище с таким номером выключено!");
            return;
        }

        String playerName = player.getName().toLowerCase();
        if (!chest.contains(playerName)){
            TextUtils.sendPlayerErrorMessage(player, "Указанный игрок ещё не открывал это хранилище!");
            return;
        }

        player.openInventory(chest.getPlayerInventory(playerName).getMCInventory(player));
        FC.openChests.put(playerName, chest.getPoints().get(0));
    }
    @Subcommand("open")
    @CommandCompletion("@players")
    public void open(Player player, int number, Player targetPlayer){
        FCChest chest = FC.getChest(number - 1);
        // Проверка, является ли блок контейнером
        if (chest == null){
            TextUtils.sendPlayerErrorMessage(player, "Хранилища с таким номером не существует!");
            return;
        }

        if (!chest.isWork) {
            TextUtils.sendPlayerErrorMessage(player, "Хранилище с таким номером выключено!");
            return;
        }

        String playerName = targetPlayer.getName().toLowerCase();
        if (!chest.contains(playerName)){
            TextUtils.sendPlayerErrorMessage(player, "Указанный игрок ещё не открывал это хранилище!");
            return;
        }

        player.openInventory(chest.getPlayerInventory(playerName).getMCInventory(player));
        FC.openChests.put(player.getName().toLowerCase(), chest.getPoints().get(0));
    }


    @Subcommand("list")
    public void list(Player player, int page){
        if (FC.isListPage(page)){
            TextUtils.sendPlayerErrorMessage(player, "Страницы хранилищ с таким номером не существует!");
            return;
        }
        FC.getListInventory(player, page);
    }
    @Subcommand("list")
    public void list(Player player){
        if (FC.isListPage(1)){
            TextUtils.sendPlayerErrorMessage(player, "Не создано ни одного хранилища!");
            return;
        }
        FC.getListInventory(player, 1);
    }


    @Subcommand("info")
    public void info(Player player){
        Block block = player.getTargetBlock(null, 10);
        // Проверка, является ли блок контейнером
        if (!FC.materialContainers.contains(block.getType())){
            TextUtils.sendPlayerErrorMessage(player, "Чтобы изменить заполнение хранилища, нужно навестить на сундук или бочку!");
            return;
        }

        FCChest chest = FC.getChest(FCPoint.adapt(block.getLocation()));
        // Проверка, является ли блок сундуком плагина
        if (chest == null){
            TextUtils.sendPlayerErrorMessage(player, "Блок не является сундуком, созданным плагином FillChester!");
            return;
        }
        int number = FC.getNumberChest(chest) + 1;
        TextUtils.sendInfoStore(player, chest, number);
    }
    @Subcommand("info")
    public void info(Player player, int number){
        FCChest chest = FC.getChest(number - 1);
        // Проверка, является ли блок контейнером
        if (chest == null){
            TextUtils.sendPlayerErrorMessage(player, "Хранилища с таким номером не существует!");
            return;
        }
        TextUtils.sendInfoStore(player, chest, number);
    }


    @Subcommand("delete")
    public void delete(Player player){
        Block block = player.getTargetBlock(null, 10);
        // Проверка, является ли блок контейнером
        if (!FC.materialContainers.contains(block.getType())){
            TextUtils.sendPlayerErrorMessage(player, "Чтобы изменить заполнение хранилища, нужно навестить на сундук или бочку!");
            return;
        }

        FCChest chest = FC.getChest(FCPoint.adapt(block.getLocation()));
        // Проверка, является ли блок сундуком плагина
        if (chest == null){
            TextUtils.sendPlayerErrorMessage(player, "Блок не является сундуком, созданным плагином FillChester!");
            return;
        }

        FC.stores.remove(FC.getNumberChest(chest));
        TextUtils.sendPlayerMessage(player, "<green>Хранилище удалено");
    }
    @Subcommand("delete")
    public void delete(Player player, int number){
        FCChest chest = FC.getChest(number - 1);
        // Проверка, является ли блок контейнером
        if (chest == null){
            TextUtils.sendPlayerErrorMessage(player, "Хранилища с таким номером не существует!");
            return;
        }

        FC.stores.remove(FC.getNumberChest(chest));
        TextUtils.sendPlayerMessage(player, "<green>Хранилище удалено");
    }


    @Subcommand("clear")
    public void clear(Player player){
        Block block = player.getTargetBlock(null, 10);
        // Проверка, является ли блок контейнером
        if (!FC.materialContainers.contains(block.getType())){
            TextUtils.sendPlayerErrorMessage(player, "Чтобы изменить заполнение хранилища, нужно навестить на сундук или бочку!");
            return;
        }

        FCChest chest = FC.getChest(FCPoint.adapt(block.getLocation()));
        // Проверка, является ли блок сундуком плагина
        if (chest == null){
            TextUtils.sendPlayerErrorMessage(player, "Блок не является сундуком, созданным плагином FillChester!");
            return;
        }

        chest.clearPlayerInventory();
        TextUtils.sendPlayerMessage(player, "<green>Содержимое всех хранилищ игроков пересоздано по умолчанию");
    }
    @Subcommand("clear")
    public void clear(Player player, int number){
        FCChest chest = FC.getChest(number - 1);
        // Проверка, является ли блок контейнером
        if (chest == null){
            TextUtils.sendPlayerErrorMessage(player, "Хранилища с таким номером не существует!");
            return;
        }

        chest.clearPlayerInventory();
        TextUtils.sendPlayerMessage(player, "<green>Содержимое всех хранилищ игроков пересоздано по умолчанию");
    }

    @Subcommand("load")
    public void load(Player player){
        ConfigUtils.loadStores();
        TextUtils.sendPlayerMessage(player, "<green>Все хранилища загружены");
    }
    @Subcommand("save")
    public void save(Player player){
        ConfigUtils.saveStores();
        TextUtils.sendPlayerMessage(player, "<green>Все хранилища сохранены");
    }
    @Subcommand("reload")
    public void reload(Player player){
        FC.reload(player);
        TextUtils.sendPlayerMessage(player, "<green>Плагин перезагружен");
    }


    @Subcommand("help")
    @Default
    public void help(Player player){

    }

    @CatchUnknown
    public void unknown(){
        FC.getInstance().getLogger().info("Work4!");
    }
}