package com.symo.finz.modules.impl.gui;

import com.symo.finz.modules.Module;
import com.symo.finz.utils.UiDrawer;
import com.symo.finz.utils.extension.HarvestLevelExtension;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.WorldType;

public class BlockInfo extends Module {

    public BlockInfo() {
        super("BlockInfo", "FinZ - GUI");
    }

    public void onServerJoin() {
        this.enable();
    }

    public void onServerLeave() {
        this.disable();
    }

    public void onRender() {
        try {
            if (mc.objectMouseOver == null ||
                    mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK ||
                    mc.objectMouseOver.getBlockPos() == null)
                return;

            // get screen resolution
            ScaledResolution sr = new ScaledResolution(mc);
            // get font renderer
            FontRenderer fr = mc.fontRendererObj;
            int yOffset = 25;


            // get block info
            BlockPos blockpos = mc.objectMouseOver.getBlockPos();
            IBlockState iblockstate = mc.theWorld.getBlockState(blockpos);
            Block block = iblockstate.getBlock();

            // harvest variables
            boolean canHarvest = block.canHarvestBlock(mc.theWorld, blockpos, mc.thePlayer);
            int harvestLevel = block.getHarvestLevel(iblockstate);
            String harvestLevelName = HarvestLevelExtension.toString(harvestLevel);

            // effective tool variables
            boolean hasEffectiveTool = false;
            if (mc.thePlayer.getHeldItem() != null && block.getHarvestTool(iblockstate) != null)
                hasEffectiveTool = mc.thePlayer.getHeldItem().getItem().getToolClasses(mc.thePlayer.getHeldItem()).contains(block.getHarvestTool(iblockstate));
            else if (block.getHarvestTool(iblockstate) == null)
                hasEffectiveTool = true;

            String harvestTool = block.getHarvestTool(iblockstate);
            if (harvestTool == null) harvestTool = "None";


            if (mc.theWorld.getWorldType() != WorldType.DEBUG_WORLD)
                iblockstate = iblockstate.getBlock().getActualState(iblockstate, mc.theWorld, blockpos);

            //String oreDictName = String.valueOf(Block.blockRegistry.getNameForObject(iblockstate.getBlock()));
            String blockName = iblockstate.getBlock().getLocalizedName();


            // draw background
            UiDrawer.drawRectDouble(sr.getScaledWidth_double() / 2 - 80,
                    2,
                    sr.getScaledWidth_double() / 2 + 90,
                    (fr.FONT_HEIGHT * 5) - 6, 0x80000000);

            // --------------------------------------------
            // top lines
            // top bottom line
            UiDrawer.drawRectDouble(sr.getScaledWidth_double() / 2 - 80,
                    2,
                    sr.getScaledWidth_double() / 2 + 90,
                    3,
                    0x80FFFFFF);
            // top top line
            UiDrawer.drawRectDouble(sr.getScaledWidth_double() / 2 - 79,
                    1,
                    sr.getScaledWidth_double() / 2 + 89,
                    2,
                    0x80FFFFFF);

            // bottom lines
            // bottom top line
            UiDrawer.drawRectDouble(sr.getScaledWidth_double() / 2 - 80,
                    (fr.FONT_HEIGHT * 5) - 7,
                    sr.getScaledWidth_double() / 2 + 90,
                    (fr.FONT_HEIGHT * 5) - 6,
                    0x80FFFFFF);
            // bottom bottom line
            UiDrawer.drawRectDouble(sr.getScaledWidth_double() / 2 - 79,
                    (fr.FONT_HEIGHT * 5) - 6,
                    sr.getScaledWidth_double() / 2 + 89,
                    (fr.FONT_HEIGHT * 5) - 5,
                    0x80FFFFFF);
            // left line
            UiDrawer.drawRectDouble(sr.getScaledWidth_double() / 2 - 81,
                    3,
                    sr.getScaledWidth_double() / 2 - 79,
                    (fr.FONT_HEIGHT * 5) - 7,
                    0x80FFFFFF);
            // right line
            UiDrawer.drawRectDouble(sr.getScaledWidth_double() / 2 + 89,
                    3,
                    sr.getScaledWidth_double() / 2 + 91,
                    (fr.FONT_HEIGHT * 5) - 7,
                    0x80FFFFFF);


            // draw block name
            fr.drawStringWithShadow(blockName,
                    (float) ((sr.getScaledWidth_double() / 2 - fr.getStringWidth(blockName) / 2) + yOffset),
                    4,
                    0xFFFFFFFF);

            // if unbreakable block
            if (block == Blocks.bedrock ||
                    block == Blocks.end_portal ||
                    block == Blocks.end_portal_frame ||
                    block == Blocks.command_block ||
                    block == Blocks.barrier) {
                fr.drawStringWithShadow("\u2717 Unbreakable",
                        (float) ((sr.getScaledWidth_double() / 2 - fr.getStringWidth("\u2717 Unbreakable") / 2) + yOffset),
                        (fr.FONT_HEIGHT * 2) - 6,
                        0xFFFF0000);
                return;
            }


            // check if the block is harvestable with the current tool
            // if not, draw a red X, if yes, draw a green checkmark
            if (canHarvest) fr.drawStringWithShadow("\u2713 Currently Harvestable",
                    (float) ((sr.getScaledWidth_double() / 2 - fr.getStringWidth("\u2713  Currently Harvestable") / 2) + yOffset),
                    (fr.FONT_HEIGHT * 2) - 6,
                    0xFF00FF00);
            else fr.drawStringWithShadow("\u2717 Currently Harvestable",
                    (float) ((sr.getScaledWidth_double() / 2 - fr.getStringWidth("\u2717  Currently Harvestable") / 2) + yOffset),
                    (fr.FONT_HEIGHT * 2) - 6,
                    0xFFFF0000);

            // draw effective tool
            fr.drawStringWithShadow("Effective Tool: " + harvestTool,
                    (float) ((sr.getScaledWidth_double() / 2 - fr.getStringWidth("Effective Tool: " + harvestTool) / 2) + yOffset),
                    (fr.FONT_HEIGHT * 3) - 6,
                    hasEffectiveTool ? 0xFF00FF00 : 0xFFFF0000);

            // draw harvest level
            fr.drawStringWithShadow("Harvest Level: " + harvestLevelName,
                    (float) ((sr.getScaledWidth_double() / 2 - fr.getStringWidth("Harvest Level: " + harvestLevelName) / 2) + yOffset),
                    (fr.FONT_HEIGHT * 4) - 6,
                    canHarvest ? 0xFF00FF00 : 0xFFFF0000);


            try {
                // render block item texture
                // make the item texture bigger
                GlStateManager.pushMatrix();
                RenderHelper.enableGUIStandardItemLighting();
                GlStateManager.scale(2, 2, 2);
                // render the item texture
                mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Item.getItemFromBlock(iblockstate.getBlock())),
                        (int) (((sr.getScaledWidth_double() / 2) - (100 - yOffset)) / 2),
                        2);
                RenderHelper.disableStandardItemLighting();
                GlStateManager.popMatrix();

            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.disable("Error");
        }

    }

}
