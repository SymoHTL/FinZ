package dev.symo.finz.mixin.client.fastplace;

import dev.symo.finz.FinZClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(BlockItem.class)
public class FastPlaceInvoker {

    @Inject(method = "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;emitGameEvent(Lnet/minecraft/world/event/GameEvent;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/event/GameEvent$Emitter;)V",
                    shift = At.Shift.AFTER))
    public void onBlockPlace(ItemPlacementContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (FinZClient.config.fastPlaceEnabled)
            ((FastPlaceClientMixin) FinZClient.mc).setItemUseCooldown(FinZClient.config.fastPlaceValue);

    }
}
