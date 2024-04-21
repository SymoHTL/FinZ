package dev.symo.finz.util;

import dev.symo.finz.FinZClient;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPos;

public class ToolUtil {

    public static int getBestToolSlot(BlockPos pos) {
        ClientPlayerEntity player = FinZClient.mc.player;
        PlayerInventory inventory = player.getInventory();
        ItemStack heldItem = FinZClient.mc.player.getMainHandStack();

        if (player.isCreative()) return -1;

        var state = FinZClient.mc.world.getBlockState(pos);
        var bestSpeed = getMiningSpeed(heldItem, state);
        int bestSlot = -1;

        for (int slot = 0; slot < 9; slot++) {
            if (slot == inventory.selectedSlot) continue;

            ItemStack stack = inventory.getStack(slot);

            float speed = getMiningSpeed(stack, state);
            if (speed <= bestSpeed) continue;

            bestSpeed = speed;
            bestSlot = slot;
        }

        return bestSlot;
    }

    private static float getMiningSpeed(ItemStack stack, BlockState state) {
        float speed = stack.getMiningSpeedMultiplier(state);

        if (speed > 1) {
            int efficiency = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, stack);
            if (efficiency > 0 && !stack.isEmpty())
                speed += efficiency * efficiency + 1;
        }

        return speed;
    }

    public static int getBestWeaponSlot(LivingEntity entity) {
        ClientPlayerEntity player = FinZClient.mc.player;
        PlayerInventory inventory = player.getInventory();

        float bestValue = Integer.MIN_VALUE;
        int bestSlot = -1;
        for (int i = 0; i < 9; i++) {
            if (inventory.getStack(i).isEmpty()) continue;

            ItemStack stack = inventory.getStack(i);
            float value = getBestAttackValue(stack, entity, player);

            if (value > bestValue) {
                bestValue = value;
                bestSlot = i;
            }
        }
        return bestSlot;
    }

    private static float getBestAttackValue(ItemStack stack, LivingEntity entity, PlayerEntity player) {
        Item item = stack.getItem();
        if (!(item instanceof ToolItem || item instanceof TridentItem || item instanceof SwordItem))
            return Integer.MIN_VALUE;

        var group = entity.getGroup();
        double dmg =  EnchantmentHelper.getAttackDamage(stack, group);;

        // Fetch attribute modifiers from the item
        var attributeModifiers = item.getAttributeModifiers(EquipmentSlot.MAINHAND);
        double attackSpeed = attributeModifiers.get(EntityAttributes.GENERIC_ATTACK_SPEED)
                .stream().findFirst().orElseThrow().getValue();
        double additionalDamage = 0.0;

        if (item instanceof SwordItem) {
            additionalDamage = ((SwordItem) item).getAttackDamage();
        } else if (item instanceof MiningToolItem) {
            additionalDamage = ((MiningToolItem) item).getAttackDamage();
        } else if (item instanceof TridentItem) {
            additionalDamage = TridentItem.ATTACK_DAMAGE;
        }

        dmg += additionalDamage;

        // Include player base attack attributes if available
        if (player != null) {
            dmg += player.getAttributeBaseValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            attackSpeed += player.getAttributeBaseValue(EntityAttributes.GENERIC_ATTACK_SPEED) - 4; // Normalize around base attack speed
        }

        // Compute the final attack value considering both damage and attack speed
        return (float) (dmg * (1 + attackSpeed / 5)); // Adjusted for a balanced emphasis on attack speed
    }

}
