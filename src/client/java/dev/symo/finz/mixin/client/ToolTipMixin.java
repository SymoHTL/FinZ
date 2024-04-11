package dev.symo.finz.mixin.client;

import dev.symo.finz.util.ModNameProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(ItemStack.class)
public class ToolTipMixin {


    @Inject(at = {@At("TAIL")}, method = {"getTooltip"})
    public void onGetTooltip(@Nullable PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir) {
        var stack = (ItemStack) (Object) this;

        if (stack.isFood()) {
            var food = stack.getItem().getFoodComponent();
            if (food != null) {
                var text = Text.literal("Hunger: " + (float) food.getHunger() / 2 + " Saturation: " + food.getSaturationModifier() / 2).formatted(Formatting.DARK_GRAY);
                cir.getReturnValue().add(text);
            }
        }


        var fuel = FuelRegistry.INSTANCE.get(stack.getItem());
        if (fuel != null) {
            var text = Text.literal("Burn Time: " + fuel).formatted(Formatting.DARK_GRAY);
            cir.getReturnValue().add(text);
        }
        if (stack.isDamageable()) {
            var damage = stack.getMaxDamage() - stack.getDamage();
            var maxDamage = stack.getMaxDamage();
            var text = Text.of("Durability: " + damage + "/" + maxDamage);
            cir.getReturnValue().add(text);
        }
        var modName = ModNameProvider.getModName(stack.getItem());
        if (modName != null) {
            // format text to be cursively italic
            var text = Text.literal(modName).formatted(Formatting.BLUE, Formatting.ITALIC);
            cir.getReturnValue().add(text);
        }

    }
}
