package dev.symo.finz.mixin.client.fastplace;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public interface FastPlaceClientMixin {
    @Accessor
    void setItemUseCooldown(int itemUseCooldown);

}

