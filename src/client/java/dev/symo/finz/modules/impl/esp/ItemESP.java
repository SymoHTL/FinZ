package dev.symo.finz.modules.impl.esp;

import dev.symo.finz.FinZClient;
import dev.symo.finz.modules.AModule;
import dev.symo.finz.util.WorldSpaceRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;

import java.util.ArrayList;
import java.util.Objects;

public class ItemESP extends AModule {
    private final ArrayList<Entity> items = new ArrayList<>();


    public ItemESP() {
        super("PlayerESP", "ESP");
    }

    @Override
    public boolean IsEnabled() {
        return FinZClient.config.itemEsp;
    }

    @Override
    public void SetEnabled(boolean enabled) {
        FinZClient.config.itemEsp = enabled;
    }

    @Override
    public void onConfigChange() {

    }

    @Override
    public void onTick() {
        if (!IsEnabled()) return;
        if (mc.player == null) return;
        if (mc.world == null) return;

        items.clear();
        items.addAll(mc.world.getEntitiesByClass(ItemEntity.class, mc.player.getBoundingBox().expand(FinZClient.config.itemEspRange), Objects::nonNull));
    }

    @Override
    public void onWorldRender(MatrixStack matrices, float partialTicks) {
        if (!IsEnabled()) return;

        WorldSpaceRenderer.renderEntitiesEsp(matrices, partialTicks, items);
    }
}
