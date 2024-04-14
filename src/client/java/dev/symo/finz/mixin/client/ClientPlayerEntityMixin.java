package dev.symo.finz.mixin.client;

import com.mojang.authlib.GameProfile;
import dev.symo.finz.FinZClient;
import dev.symo.finz.events.impl.EventManager;
import dev.symo.finz.events.listeners.KnockbackListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    @Shadow
    @Final
    protected MinecraftClient client;

    private Screen tempCurrentScreen;
    private boolean hideNextItemUse;

    public ClientPlayerEntityMixin(FinZClient finz, ClientWorld world,
                                   GameProfile profile) {
        super(world, profile);
    }


    @Override
    public void setVelocityClient(double x, double y, double z) {
        KnockbackListener.KnockbackEvent event = new KnockbackListener.KnockbackEvent(x, y, z);
        EventManager.fire(event);
        super.setVelocityClient(event.getX(), event.getY(), event.getZ());
    }
}

