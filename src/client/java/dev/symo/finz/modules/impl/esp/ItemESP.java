package dev.symo.finz.modules.impl.esp;

import dev.symo.finz.modules.AModule;
import dev.symo.finz.util.Category;
import dev.symo.finz.util.WorldSpaceRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;

import java.util.ArrayList;
import java.util.Objects;

public class ItemESP extends AModule {
    private final ArrayList<Entity> items = new ArrayList<>();


    public ItemESP() {
        super("PlayerESP", Category.RENDER);
    }

    @Override
    public boolean isEnabled() {
        return config.itemEsp;
    }

    @Override
    public void setEnabled(boolean enabled) {
        config.itemEsp = enabled;
    }


    @Override
    public void onTick() {
        if (!isEnabled()) return;
        if (mc.player == null) return;
        if (mc.world == null) return;

        items.clear();
        items.addAll(mc.world.getEntitiesByClass(ItemEntity.class, mc.player.getBoundingBox().expand(config.itemEspRange), Objects::nonNull));
    }

    @Override
    public void onWorldRender(MatrixStack matrices, float partialTicks) {
        if (!isEnabled()) return;

        WorldSpaceRenderer.renderEntitiesEsp(matrices, partialTicks, items);
    }
}
