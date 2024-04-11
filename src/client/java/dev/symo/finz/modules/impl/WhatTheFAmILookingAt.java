package dev.symo.finz.modules.impl;

import dev.symo.finz.FinZClient;
import dev.symo.finz.modules.AModule;
import dev.symo.finz.tracker.BreakProgressTracker;
import dev.symo.finz.util.*;
import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WhatTheFAmILookingAt extends AModule {

    private final int yTextOffset = 25;
    private int width;
    private int height;
    private TextRenderer fr;

    public WhatTheFAmILookingAt() {
        super("WhatTheFAmILookingAt", "HUD");
    }

    @Override
    public boolean IsEnabled() {
        return FinZClient.config.whatTheFAmILookingAtEnabled;
    }

    @Override
    public void SetEnabled(boolean enabled) {
        FinZClient.config.whatTheFAmILookingAtEnabled = enabled;
        AfterEnableChange();
    }

    @Override
    public void onConfigChange() {
        AfterEnableChange();
    }

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta){
        if (!IsEnabled()) return;
        if (mc.player == null || mc.world == null) return;
        if (mc.crosshairTarget == null) return;

        width = mc.getWindow().getScaledWidth();
        height = mc.getWindow().getScaledHeight();
        fr = mc.textRenderer;

        if (mc.crosshairTarget.getType() == HitResult.Type.BLOCK && mc.crosshairTarget.getPos() != null) {
            renderBlockOverlay(drawContext);
        } else if (mc.crosshairTarget.getType() == HitResult.Type.ENTITY) {
            renderEntityOverlay(drawContext);
        }
    }

    private void renderEntityOverlay(DrawContext drawContext) {
        assert mc.crosshairTarget != null;
        var Entity = ((EntityHitResult) mc.crosshairTarget).getEntity();
        if (Entity == null) return;
        if (!Entity.isLiving()) return;
        var livingEntity = (LivingEntity) Entity;

        float entityHealth = (float) MathUtil.round(livingEntity.getHealth() / 2, 1);
        float entityMaxHealth = (float) MathUtil.round(livingEntity.getMaxHealth() / 2, 1);
        var entityHealthPercentage = (int) ((entityHealth / entityMaxHealth) * 100);
        // set the background color according to the health percentage
        var color = ColorUtil.PercentageToColor(entityHealthPercentage);

        var infos = buildBasicEntityInfo(livingEntity);
        if (livingEntity instanceof AnimalEntity)
            infos.addAll(buildAnimalInfo((AnimalEntity) livingEntity));

        drawEntityBackground(drawContext, infos.size());
        var ySize = Math.max(6, infos.size() + 1);
        var bottomOffset = fr.fontHeight * ySize;


        var maxRight = (double) width / 2 + 99;
        var maxLeft = (double) width / 2 - 79;
        var leftHealth = (maxRight - maxLeft) * (entityHealthPercentage / 100.0);
        // make a progress bar
        UiRenderer.drawRectDouble(drawContext, (double) width / 2 - 79,
                3,
                (double) width / 2 - 79 + leftHealth,
                bottomOffset - 7, color.getRGB());


        var yTextAddOffset = 1;
        for (var info : infos) { //  drawTextWithShadow(TextRenderer textRenderer, Text text, int x, int y, int color)
            drawContext.drawTextWithShadow(fr, info.text,
                    (int) (((double) width / 2 - fr.getWidth(info.text) / 2) + yTextOffset),
                    (fr.fontHeight * yTextAddOffset) - 6,
                    info.color);
            yTextAddOffset++;
        }


        // get the height and width of the entity
        var entityHeight = livingEntity.getHeight();
        var entityWidth = livingEntity.getWidth();
        // get the scale according to the entity size
        var scale = (int) MathUtil.round((1000 / (Math.max(entityHeight, entityWidth) * 10)) / 2.5, 0);

        if (livingEntity.isBaby())
            scale = (int) (scale / 1.5);

        // draw entity
        UiRenderer.drawEntity(drawContext, (int) (width / 2 - width * 0.048), 2 + fr.fontHeight * 2, scale,(float) width / 7,
                height  * 0.75f, livingEntity);
    }

    private @NotNull List<HudText> buildBasicEntityInfo(@NotNull LivingEntity livingEntity) {
        var infos = new ArrayList<HudText>();

        var entityName = livingEntity.getName().getString();
        if (livingEntity.isBaby())
            entityName = "Baby " + entityName;

        // draw entity name
        infos.add(new HudText(Text.of(entityName), 0xFFFFFFFF));

        // draw health
        infos.add(new HudText(Text.of("Health: " + (int) livingEntity.getHealth() + "/" + (int) livingEntity.getMaxHealth()), 0xFFFFFFFF));

        return infos;
    }

    private @NotNull List<HudText> buildAnimalInfo(AnimalEntity animalEntity) {
/*
        if (!animalEntity.isBaby())
            if (animalEntity.getBreedingAge() == 0 && animalEntity.canEat())
            fr.drawWithShadow(matrices,
                    "Ready to Breed",
                    (float) (((double) width / 2 - fr.getWidth("Ready to Breed") / 2) + yTextOffset),
                    4 + fr.fontHeight * 3,
                    0xFFFFFFFF);
            else {
                var loveTicks = animalEntity.getLoveTicks();
                ChatUtils.sendMessage(loveTicks + "");
                var loveTicksInSeconds = loveTicks / 20;
                var loveTicksInMinutes = loveTicksInSeconds / 60;
                var loveTicksInSecondsLeft = loveTicksInSeconds % 60;

                if (loveTicksInMinutes > 0)
                    fr.drawWithShadow(matrices,
                            String.format("Breed in %s:%s", loveTicksInMinutes, loveTicksInSecondsLeft),
                            (float) (((double) width / 2 - fr.getWidth(String.format("Breed in %s:%s", loveTicksInMinutes, loveTicksInSecondsLeft)) / 2) + yTextOffset),
                            4 + fr.fontHeight * 2,
                            0xFFFFFFFF);
                else
                    fr.drawWithShadow(matrices,
                            String.format("Breed in %s", loveTicksInSecondsLeft),
                            (float) (((double) width / 2 - fr.getWidth(String.format("Breed in %s", loveTicksInSecondsLeft)) / 2) + yTextOffset),
                            4 + fr.fontHeight * 2,
                            0xFFFFFFFF);
            }
*/
        var infos = new ArrayList<HudText>();
        if (animalEntity instanceof TameableEntity tameableEntity) {
            if (tameableEntity.isTamed()) {
                var owner = tameableEntity.getOwner();
                if (owner != null) {
                    var ownerName = owner.getName().getString();
                    infos.add(new HudText(Text.of("Owner: " + ownerName), 0xFFFFFFFF));
                }
            }
        }
        return infos;
    }

    private void renderBlockOverlay(DrawContext drawContext) {
        assert mc.crosshairTarget != null;
        BlockPos blockpos = ((BlockHitResult) mc.crosshairTarget).getBlockPos();
        if (blockpos == null)
            return;
        assert mc.world != null;
        BlockState blockState = mc.world.getBlockState(blockpos);
        if (blockState == null)
            return;
        Block block = blockState.getBlock();
        if (block == Blocks.AIR)
            return;

        var infos = buildBasicBlockInfo(blockState);
        infos.addAll(infos.size() - 1, additionalBlockInfo(blockState));
        infos.addAll(infos.size() - 1, redStoneInfo(blockState));

        var bottomOffset = fr.fontHeight * (infos.size() + 1);
        drawBlockBackground(drawContext, infos.size());

        if (mc.interactionManager != null) {
            int percentBroken = ((BreakProgressTracker) mc.interactionManager).getBreakProgressPercent(mc.getTickDelta());
            if (percentBroken > 0) {
                var maxRight = (double) width / 2 + 89;
                var maxLeft = (double) width / 2 - 79;
                var widthBroken = (maxRight - maxLeft) * (percentBroken / 100.0);
                // make a progress bar
                UiRenderer.drawRectDouble(drawContext, (double) width / 2 - 79,
                        3,
                        (double) width / 2 - 79 + widthBroken,
                        bottomOffset - 7, 0x80FFFFFF);
            }
        }

        var yTextAddOffset = 1;

        for (var info : infos) {
            drawContext.drawTextWithShadow(fr, info.text,
                    (int) (((double) width / 2 - fr.getWidth(info.text) / 2) + yTextOffset),
                    (fr.fontHeight * yTextAddOffset) - 6,
                    info.color);
            yTextAddOffset++;
        }

        var x = width / 2 - 70;
        var y = 16;
        UiRenderer.drawItem(drawContext, new ItemStack(block.asItem()), x, y, 32);
    }

    private @NotNull List<HudText> buildBasicBlockInfo(@NotNull BlockState blockState) {
        var infos = new ArrayList<HudText>();
        String blockName = blockState.getBlock().getName().getString();

        infos.add(new HudText(Text.of(blockName), 0xFFFFFFFF));

        var block = blockState.getBlock();
        if (block == Blocks.BEDROCK ||
                block == Blocks.BARRIER ||
                block == Blocks.NETHER_PORTAL ||
                block == Blocks.END_PORTAL ||
                block == Blocks.CHAIN_COMMAND_BLOCK ||
                block == Blocks.REPEATING_COMMAND_BLOCK ||
                block == Blocks.STRUCTURE_BLOCK ||
                block == Blocks.STRUCTURE_VOID) {
            infos.add(new HudText(Text.of("✗ Unbreakable"), 0xFFFF0000));
            return infos;
        }

        assert mc.player != null;
        boolean canHarvest = mc.player.canHarvest(blockState);
        int harvestLevel = MiningLevelManager.getRequiredMiningLevel(blockState);
        String harvestLevelName = HarvestLevelExtension.LevelToString(harvestLevel);


        // effective tool variables
        boolean hasEffectiveTool = false;
        if (mc.player.getMainHandStack() != null && blockState.isToolRequired())
            hasEffectiveTool = mc.player.getMainHandStack().getItem().isSuitableFor(blockState);

        String harvestTool = HarvestLevelExtension.BlockStateTagToString(blockState);

        // check if the block is harvestable with the current tool
        // if not, draw a red X, if yes, draw a green checkmark
        if (canHarvest)
            infos.add(new HudText(Text.of("✓ Currently Harvestable"), 0xFF00FF00));
        else
            infos.add(new HudText(Text.of("✗ Currently Harvestable"), 0xFFFF0000));

        // draw effective tool
        infos.add(new HudText(Text.of("Effective Tool: " + harvestTool), hasEffectiveTool ? 0xFF00FF00 : 0xFFFF0000));

        // draw harvest level
        infos.add(new HudText(Text.of("Harvest Level: " + harvestLevelName), canHarvest ? 0xFF00FF00 : 0xFFFF0000));

        // draw mod name in blue
        var modName = Text.literal(ModNameProvider.getModName(block)).formatted(Formatting.ITALIC);
        if (modName != null)
            infos.add(new HudText(modName, 0xFF2222FF));


        return infos;
    }

    public List<HudText> redStoneInfo(@NotNull BlockState blockState) {
        var infos = new ArrayList<HudText>();
        var block = blockState.getBlock();
        // if the block is a lever, draw the state
        if (block == Blocks.LEVER) {
            var leverState = blockState.get(Properties.POWERED);
            infos.add(new HudText(Text.of("Lever State: " + (leverState ? "On" : "Off")), 0xFFFFFFFF));
        }

        // if the block is redstone, add redstone info
        if (block == Blocks.REDSTONE_WIRE) {
            var redstonePower = blockState.get(Properties.POWER);
            infos.add(new HudText(Text.of("Redstone Power: " + redstonePower), 0xFFFFFFFF));
            return infos;
        }
        // if the block is a repeater, add repeater info
        if (block == Blocks.REPEATER) {
            var repeaterDelay = blockState.get(Properties.DELAY);
            infos.add(new HudText(Text.of("Repeater Delay: " + repeaterDelay), 0xFFFFFFFF));
            return infos;
        }
        // if the block is a comparator, add comparator info
        if (block == Blocks.COMPARATOR) {
            var comparatorMode = blockState.get(Properties.COMPARATOR_MODE);
            infos.add(new HudText(Text.of("Comparator Mode: " + comparatorMode), 0xFFFFFFFF));
            return infos;
        }
        // if the block is a piston, add piston info
        if (block == Blocks.PISTON || block == Blocks.STICKY_PISTON) {
            var pistonState = blockState.get(Properties.EXTENDED);
            infos.add(new HudText(Text.of("Piston State: " + (pistonState ? "Extended" : "Retracted")), 0xFFFFFFFF));
            return infos;
        }
        // if the block is a redstone lamp, add redstone lamp info
        if (block == Blocks.REDSTONE_LAMP || block == Blocks.REDSTONE_BLOCK) {
            var redstoneLampState = blockState.get(Properties.LIT);
            infos.add(new HudText(Text.of("Redstone Lamp State: " + (redstoneLampState ? "On" : "Off")), 0xFFFFFFFF));
            return infos;
        }
        // if the block is a redstone ore, add redstone ore info
        if (block == Blocks.REDSTONE_ORE) {
            var redstoneOrePower = blockState.get(Properties.LIT);
            infos.add(new HudText(Text.of("Redstone Ore State: " + (redstoneOrePower ? "On" : "Off")), 0xFFFFFFFF));
            return infos;
        }
        // if the block is a daylight sensor, add daylight sensor info
        if (block == Blocks.DAYLIGHT_DETECTOR) {
            var daylightSensorPower = blockState.get(Properties.POWER);
            infos.add(new HudText(Text.of("Daylight Sensor Power: " + daylightSensorPower), 0xFFFFFFFF));
            return infos;
        }
        return infos;
    }

    private @NotNull List<HudText> additionalBlockInfo(@NotNull BlockState blockState) {
        var infos = new ArrayList<HudText>();
        var block = blockState.getBlock();
        // if the block is a chest, add chest info
        if (block == Blocks.CHEST || block == Blocks.TRAPPED_CHEST) {
            var chestType = blockState.get(Properties.CHEST_TYPE);
            infos.add(new HudText(Text.of("Chest Type: " + chestType), 0xFFFFFFFF));
            return infos;
        }


        return infos;
    }

    public void drawEntityBackground(DrawContext drawContext, int textOverflow) {
        var ySize = Math.max(6, textOverflow + 1);
        var bottomOffset = fr.fontHeight * ySize;

        // draw background
        UiRenderer.drawRectDouble(drawContext, (double) width / 2 - 79,
                3,
                (double) width / 2 + 99,
                bottomOffset - 7, 0x80000000);

        // --------------------------------------------
        // top lines
        // top bottom line
        UiRenderer.drawRectDouble(drawContext, (double) width / 2 - 80,
                2,
                (double) width / 2 + 100,
                3,
                0x80FFFFFF);
        // top top line
        UiRenderer.drawRectDouble(drawContext, (double) width / 2 - 79,
                1,
                (double) width / 2 + 99,
                2,
                0x80FFFFFF);

        // bottom lines
        // bottom top line
        UiRenderer.drawRectDouble(drawContext, (double) width / 2 - 80,
                bottomOffset - 7,
                (double) width / 2 + 100,
                bottomOffset - 6,
                0x80FFFFFF);
        // bottom bottom line
        UiRenderer.drawRectDouble(drawContext, (double) width / 2 - 79,
                bottomOffset - 6,
                (double) width / 2 + 99,
                bottomOffset - 5,
                0x80FFFFFF);
        // left line
        UiRenderer.drawRectDouble(drawContext, (double) width / 2 - 81,
                3,
                (double) width / 2 - 79,
                bottomOffset - 7,
                0x80FFFFFF);
        // right line
        UiRenderer.drawRectDouble(drawContext, (double) width / 2 + 99,
                3,
                (double) width / 2 + 101,
                bottomOffset - 7,
                0x80FFFFFF);
    }

    public void drawBlockBackground(DrawContext drawContext, int textAmount) {
        var bottomOffset = fr.fontHeight * (textAmount + 1);

        // draw background
        UiRenderer.drawRectDouble(drawContext, (double) width / 2 - 79,
                3,
                (double) width / 2 + 99,
                bottomOffset - 7, 0x80000000);

        // --------------------------------------------
        // top lines
        // top bottom line
        UiRenderer.drawRectDouble(drawContext, (double) width / 2 - 80,
                2,
                (double) width / 2 + 100,
                3,
                0x80FFFFFF);
        // top top line
        UiRenderer.drawRectDouble(drawContext, (double) width / 2 - 79,
                1,
                (double) width / 2 + 99,
                2,
                0x80FFFFFF);

        // bottom lines
        // bottom top line
        UiRenderer.drawRectDouble(drawContext, (double) width / 2 - 80,
                bottomOffset - 7,
                (double) width / 2 + 100,
                bottomOffset - 6,
                0x80FFFFFF);
        // bottom bottom line
        UiRenderer.drawRectDouble(drawContext, (double) width / 2 - 79,
                bottomOffset - 6,
                (double) width / 2 + 99,
                bottomOffset - 5,
                0x80FFFFFF);
        // left line
        UiRenderer.drawRectDouble(drawContext, (double) width / 2 - 81,
                3,
                (double) width / 2 - 79,
                bottomOffset - 7,
                0x80FFFFFF);
        // right line
        UiRenderer.drawRectDouble(drawContext, (double) width / 2 + 99,
                3,
                (double) width / 2 + 101,
                bottomOffset - 7,
                0x80FFFFFF);
    }


    public record HudText(Text text, int color) {
    }

}
