package dev.symo.finz.mixin.client;

import dev.symo.finz.events.impl.EventManager;
import dev.symo.finz.events.listeners.BreakingListener;
import dev.symo.finz.mixininterfaces.BreakProgressTracker;
import dev.symo.finz.modules.Modules;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(net.fabricmc.api.EnvType.CLIENT)
@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin implements BreakProgressTracker {
    @Unique
    private float lastBreakingProgress;

    @Shadow
    private float currentBreakingProgress;

    @Override
    public float getBreakProgress(float tickDelta) {
        if (this.currentBreakingProgress == 0)
            return 0;
        return MathHelper.lerp(tickDelta, this.lastBreakingProgress, this.currentBreakingProgress);
    }

    @Inject(method = "updateBlockBreakingProgress", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/block/BlockState;calcBlockBreakingDelta(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)F"))
    public void updateLastBreakingProgress(CallbackInfoReturnable<Boolean> ci) {
        this.lastBreakingProgress = this.currentBreakingProgress;
    }

    @Inject(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/network/ClientPlayerEntity;getId()I",
            ordinal = 0),
            method = "updateBlockBreakingProgress(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z")
    private void onPlayerDamageBlock(BlockPos pos, Direction direction,
                                     CallbackInfoReturnable<Boolean> cir) {
        EventManager.fire(new BreakingListener.BreakingProgressEvent(pos, direction));
    }


    @Inject(at = @At("HEAD"),
            method = "getReachDistance()F",
            cancellable = true)
    private void onGetReachDistance(CallbackInfoReturnable<Float> ci) {
        if (Modules.reach.isEnabled())
            ci.setReturnValue(Modules.reach.getReach());
    }

    @Inject(at = @At("HEAD"),
            method = "hasExtendedReach()Z",
            cancellable = true)
    private void hasExtendedReach(CallbackInfoReturnable<Boolean> cir) {
        if (Modules.reach.isEnabled())
            cir.setReturnValue(true);
    }
}