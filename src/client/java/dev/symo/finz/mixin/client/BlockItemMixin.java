package dev.symo.finz.mixin.client;


import dev.symo.finz.FinZClient;
import dev.symo.finz.modules.Modules;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(BlockItem.class)
public abstract class BlockItemMixin extends Item {

    @Unique
    public BlockItem prevBlockItem = null;

    public BlockItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V", shift = At.Shift.BEFORE))
    public void backupBlockItem(ItemPlacementContext context, CallbackInfoReturnable<ActionResult> cir) {
        prevBlockItem = (BlockItem) (Object) this;
    }

    @Inject(method = "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V", shift = At.Shift.AFTER))
    public void onBlockPlaced(ItemPlacementContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (!Modules.autoGetBlock.isEnabled()) return;
        if (prevBlockItem == null) return;
        if (!context.getStack().isEmpty()) return;
        if (context.getPlayer() == null) return;

        var inventory = context.getPlayer().getInventory();
        var slot = -1;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.selectedSlot == i) continue;
            var itemStack = inventory.getStack(i);
            if (itemStack.isEmpty()) continue;
            if (itemStack.getItem() == prevBlockItem) {
                slot = i;
                break;
            }
        }
        if (slot == -1) {
            return;
        }

        if (slot < 9) {
            inventory.selectedSlot = slot;
            return;
        }

        if (FinZClient.mc.interactionManager != null && !Modules.autoGetBlock.safeMode.getValue())
            FinZClient.mc.interactionManager.clickSlot(0, slot, inventory.selectedSlot, SlotActionType.SWAP,
                    context.getPlayer());


    }
}
