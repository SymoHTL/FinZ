package dev.symo.finz.modules.impl.esp;

import dev.symo.finz.events.listeners.HudRenderListener;
import dev.symo.finz.events.listeners.TickListener;
import dev.symo.finz.events.listeners.WorldRenderListener;
import dev.symo.finz.modules.AModule;
import dev.symo.finz.modules.settings.BoolSetting;
import dev.symo.finz.modules.settings.IntSetting;
import dev.symo.finz.util.Category;
import dev.symo.finz.util.UiRenderer;
import dev.symo.finz.util.WorldSpaceRenderer;
import me.x150.renderer.util.RendererUtils;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Objects;

public class ItemESP extends AModule implements TickListener, WorldRenderListener, HudRenderListener {
    private final ArrayList<Entity> items = new ArrayList<>();

    private final BoolSetting renderItemSprite = new BoolSetting("Render Sprite", "Render the item sprite",
            false);

    private final IntSetting itemEspRange = new IntSetting("Range", "Range to scan for items",
            50, 1, 100);

    private final IntSetting scale = new IntSetting("Scale", "Scale of the item sprite",
            14, 1, 100);


    public ItemESP() {
        super("ItemESP", Category.RENDER);
        addSetting(itemEspRange);
        addSetting(renderItemSprite);
        addSetting(scale);
    }

    @Override
    public void onEnable() {
        EVENTS.add(TickListener.class, this);
        EVENTS.add(HudRenderListener.class, this);
        EVENTS.add(WorldRenderListener.class, this);
    }

    @Override
    public void onDisable() {
        EVENTS.remove(TickListener.class, this);
        EVENTS.remove(HudRenderListener.class, this);
        EVENTS.remove(WorldRenderListener.class, this);
    }

    @Override
    protected void onSettingsChanged() {
        items.clear();
    }

    @Override
    public void onTick() {
        if (mc.player == null) return;
        if (mc.world == null) return;

        items.clear();
        items.addAll(mc.world.getEntitiesByClass(ItemEntity.class, mc.player.getBoundingBox().expand(itemEspRange.getValue()), Objects::nonNull));
    }

    @Override
    public void onWorldRender(MatrixStack matrices, float partialTicks, WorldRenderContext c) {
        if (renderItemSprite.getValue()) return;

        WorldSpaceRenderer.renderEntitiesEsp(matrices, partialTicks, items);
    }

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        if (mc.player == null) return;
        if (!renderItemSprite.getValue()) return;

        for (Entity item : items) {
            var itemPos = item.getPos();
            itemPos = itemPos.add(0, item.getHeight() / 2, 0);
            Vec3d screenPos = RendererUtils.worldSpaceToScreenSpace(itemPos);
            if (!RendererUtils.screenSpaceCoordinateIsVisible(screenPos)) continue;

            var itemStack = ((ItemEntity) item).getStack();
            UiRenderer.drawItem(drawContext, itemStack, (int) screenPos.x, (int) screenPos.y, scale.getValue());
        }
    }
}
