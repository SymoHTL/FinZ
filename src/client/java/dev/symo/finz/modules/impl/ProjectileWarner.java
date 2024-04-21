package dev.symo.finz.modules.impl;

import dev.symo.finz.events.listeners.HudRenderListener;
import dev.symo.finz.events.listeners.TickListener;
import dev.symo.finz.modules.AModule;
import dev.symo.finz.modules.settings.IntSetting;
import dev.symo.finz.util.Category;
import dev.symo.finz.util.EntityUtil;
import dev.symo.finz.util.UiRenderer;
import me.x150.renderer.util.RendererUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;

public class ProjectileWarner extends AModule implements TickListener, HudRenderListener {

    private final IntSetting scale = new IntSetting("Scale", "Scale of the item sprite",
            36, 1, 100);

    private final ArrayList<ProjectileEntity> projectiles = new ArrayList<>();

    public ProjectileWarner() {
        super("Projectile Warner", Category.COMBAT);
        addSetting(scale);
    }

    @Override
    public void onEnable() {
        EVENTS.add(TickListener.class, this);
        EVENTS.add(HudRenderListener.class, this);
    }

    @Override
    public void onDisable() {
        EVENTS.remove(TickListener.class, this);
        EVENTS.remove(HudRenderListener.class, this);
    }

    @Override
    public void onTick() {
        if (mc.world == null || mc.player == null) return;

        projectiles.clear();
        var stream = mc.world.getEntitiesByClass(ProjectileEntity.class, mc.player.getBoundingBox().expand(50.0, 20.0, 50.0), entity -> true)
                .parallelStream()
                .filter(entity -> entity.getOwner() != mc.player)
                .filter(entity -> entity.getVelocity().length() > 0.1)
                .filter(entity -> entity.getVelocity().dotProduct(mc.player.getPos().subtract(entity.getPos())) > 0);

        projectiles.addAll(stream.toList());


    }

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {

        var scale = this.scale.getValue();
        // draw a waring icon on the screen where the projectile is coming from
        for (ProjectileEntity projectile : projectiles) {
            var pos = EntityUtil.getLerpedPos(projectile, tickDelta);
            var screenPos = RendererUtils.worldSpaceToScreenSpace(pos);
            // if it is not visible snap it to the edge of the screen
            screenPos = UiRenderer.sanitizeScreenPos(screenPos, scale);
            var item = projectileToItem(projectile);

            UiRenderer.drawItem(drawContext, item, (int) screenPos.x, (int) screenPos.y, scale);
        }
    }

    private ItemStack projectileToItem(ProjectileEntity projectile) {
        if (projectile instanceof FireballEntity) return new ItemStack(net.minecraft.item.Items.FIRE_CHARGE);
        if (projectile instanceof ArrowEntity) return new ItemStack(net.minecraft.item.Items.ARROW);
        if (projectile instanceof SnowballEntity) return new ItemStack(net.minecraft.item.Items.SNOWBALL);
        if (projectile instanceof EnderPearlEntity) return new ItemStack(net.minecraft.item.Items.ENDER_PEARL);
        if (projectile instanceof EggEntity) return new ItemStack(net.minecraft.item.Items.EGG);
        if (projectile instanceof TridentEntity) return new ItemStack(net.minecraft.item.Items.TRIDENT);
        if (projectile instanceof FireworkRocketEntity) return new ItemStack(net.minecraft.item.Items.FIREWORK_ROCKET);
        if (projectile instanceof SmallFireballEntity) return new ItemStack(net.minecraft.item.Items.FIRE_CHARGE);
        if (projectile instanceof WitherSkullEntity) return new ItemStack(net.minecraft.item.Items.WITHER_SKELETON_SKULL);
        if (projectile instanceof DragonFireballEntity) return new ItemStack(net.minecraft.item.Items.DRAGON_BREATH);
        return new ItemStack(Items.BARRIER);
    }
}
