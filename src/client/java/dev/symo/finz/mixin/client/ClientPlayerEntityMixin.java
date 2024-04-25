package dev.symo.finz.mixin.client;

import com.mojang.authlib.GameProfile;
import dev.symo.finz.events.impl.EventManager;
import dev.symo.finz.events.listeners.KnockbackListener;
import dev.symo.finz.modules.Modules;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    @Shadow
    @Final
    protected MinecraftClient client;

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }


    @Override
    public void setVelocityClient(double x, double y, double z) {
        KnockbackListener.KnockbackEvent event = new KnockbackListener.KnockbackEvent(x, y, z);
        EventManager.fire(event);
        super.setVelocityClient(event.getX(), event.getY(), event.getZ());
    }


    @Override
    protected boolean clipAtLedge() {
        return super.clipAtLedge() || Modules.autoSneak.isEnabled();
    }

    @Override
    protected Vec3d adjustMovementForSneaking(Vec3d movement, MovementType type) {
        Vec3d result = super.adjustMovementForSneaking(movement, type);

        if (movement != null)
            Modules.autoSneak.sneak(!movement.equals(result));

        return result;
    }


    @Override
    public boolean hasStatusEffect(StatusEffect effect) {
        if (effect == StatusEffects.DARKNESS && Modules.noBlindness.isEnabled()) return false;

        return super.hasStatusEffect(effect);
    }


}

