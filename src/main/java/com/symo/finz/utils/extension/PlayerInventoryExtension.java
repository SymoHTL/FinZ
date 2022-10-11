package com.symo.finz.utils.extension;

import com.symo.finz.FinZ;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.*;


public class PlayerInventoryExtension {

    static InventoryPlayer inventory = FinZ.mc.thePlayer.inventory;

    public static boolean isFull(){
        return inventory.getFirstEmptyStack() == -1;
    }


    public static int getFirstFoodSlot() {
        for (int i = 0; i < 9; i++) {
            if (inventory.mainInventory[i] != null && inventory.mainInventory[i].getItem() instanceof ItemFood) {
                return i;
            }
        }
        return -1;
    }
    public static int getFirstLowerFoodSlot(int foodLevel) {
        for (int i = 0; i < 9; i++) {
            if (inventory.mainInventory[i] != null && inventory.mainInventory[i].getItem() instanceof ItemFood) {
                ItemFood food = (ItemFood) inventory.mainInventory[i].getItem();
                if (food.getHealAmount(null) <= foodLevel)
                    return i;
            }
        }
        return -1;
    }

    public static int getFirstExactSuitableFoodSlot(int foodLevel) {
        for (int i = 0; i < 9; i++) {
            if (inventory.mainInventory[i] != null && inventory.mainInventory[i].getItem() instanceof ItemFood) {
                ItemFood food = (ItemFood) inventory.mainInventory[i].getItem();
                if (food.getHealAmount(null) == foodLevel)
                    return i;
            }
        }
        return -1;
    }


    public static int getFirstEffectiveToolForBlockInHotBar(Block block) {
        for (int i = 0; i < 9; i++) {
            if (inventory.mainInventory[i] != null && inventory.mainInventory[i].getItem() instanceof ItemTool) {
                ItemTool item = (ItemTool) inventory.mainInventory[i].getItem();
                if (item.getStrVsBlock(null,block) > 1.0F)
                    return i;
            }
        }
        return -1;
    }

    public static int getFirstNotNullInHotBarIndex() {
        for (int i = 0; i < 9; i++)
            if (inventory.mainInventory[i] != null && inventory.mainInventory[i].getItem() != null)
                return i;
        return -1;
    }

    public static int getFirstBlockInHotBarIndex() {
        for (int i = 0; i < 9; i++)
            if (inventory.mainInventory[i] != null && inventory.mainInventory[i].getItem() instanceof ItemBlock) {
                Block block = Block.getBlockFromItem(inventory.mainInventory[i].getItem());
                if (!block.isFullBlock() || !block.isFullCube())
                    continue;
                return i;
            }
        return -1;
    }

    public static int getFirstNonFallableBlockInHotBarIndex() {
        for (int i = 0; i < 9; i++)
            if (inventory.mainInventory[i] != null && inventory.mainInventory[i].getItem() instanceof ItemBlock) {
                Block block = Block.getBlockFromItem(inventory.mainInventory[i].getItem());
                if (block instanceof BlockFalling)
                    continue;
                return i;
            }
        return -1;
    }

    public static int getFirstNonFallableSolidBlockInHotBarIndex() {
        for (int i = 0; i < 9; i++)
            if (inventory.mainInventory[i] != null && inventory.mainInventory[i].getItem() instanceof ItemBlock) {
                Block block = Block.getBlockFromItem(inventory.mainInventory[i].getItem());
                if (!block.isFullBlock() || !block.isFullCube())
                    continue;
                if (block instanceof BlockFalling)
                    continue;
                return i;
            }
        return -1;
    }

    public static int getFirstPickaxeInHotBarIndex() {
        for (int i = 0; i < 9; i++)
            if (inventory.mainInventory[i] != null && inventory.mainInventory[i].getItem() instanceof ItemPickaxe)
                return i;
        return -1;
    }

    public static int getFirstSwordInHotBarIndex() {
        for (int i = 0; i < 9; i++)
            if (inventory.mainInventory[i] != null && inventory.mainInventory[i].getItem() instanceof ItemSword)
                return i;
        return -1;
    }

    public static int getFirstShovelInHotBarIndex() {
        for (int i = 0; i < 9; i++)
            if (inventory.mainInventory[i] != null && inventory.mainInventory[i].getItem() instanceof ItemSpade)
                return i;
        return -1;
    }

    public static int getFirstAxeInHotBarIndex() {
        for (int i = 0; i < 9; i++)
            if (inventory.mainInventory[i] != null && inventory.mainInventory[i].getItem() instanceof ItemAxe)
                return i;
        return -1;
    }

}
