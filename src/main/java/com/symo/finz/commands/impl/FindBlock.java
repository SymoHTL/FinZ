package com.symo.finz.commands.impl;

import com.symo.finz.FinZ;
import com.symo.finz.commands.Command;
import com.symo.finz.modules.impl.visual.esp.FindBlockESP;
import com.symo.finz.utils.ChatUtils;
import net.minecraft.block.Block;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class FindBlock extends Command {

    public static List<BlockPos> foundBlockPos = new ArrayList<>();

    @Override
    public String getCommandName() {
        return "findblock";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        FindBlockESP module = (FindBlockESP) FinZ.moduleManager.getModule("FindBlockESP");
        if (args.length < 2) {
            ChatUtils.sendMessage("Invalid arguments");
            return;
        }
        int amount;
        int xRadius;
        int yRadius;
        int zRadius;
        try {
            amount = Integer.parseInt(args[1]);
            xRadius = Integer.parseInt(args[2]);
            yRadius = Integer.parseInt(args[3]);
            zRadius = Integer.parseInt(args[4]);
        } catch (Exception e) {
            ChatUtils.sendMessage("Parameters needs to be of type Integer");
            return;
        }

        ChatUtils.sendMessage("Searching for " + args[0] + "...");

        // get the block 100 blocks around the player
        int xPlayer = (int) FinZ.mc.thePlayer.posX;
        int yPlayer = (int) FinZ.mc.thePlayer.posY;
        int zPlayer = (int) FinZ.mc.thePlayer.posZ;

        int count = 0;
        int startX = xPlayer - xRadius;
        int startY = yPlayer + yRadius; // from top to bottom
        int startZ = zPlayer - zRadius;

        int endX = xPlayer + xRadius;
        int endY = yPlayer - yRadius; // from top to bottom
        int endZ = zPlayer + zRadius;

        for (int y = startY; y > endY; y--) { // from top to bottom
            for (int x = startX; x < endX; x++) {
                for (int z = startZ; z < endZ; z++) {
                    Block b = FinZ.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (b.getLocalizedName().toLowerCase().replace(" ", "_").equals(args[0])) {
                        foundBlockPos.add(new BlockPos(x, y, z));
                        //System.out.println("Found " + b.getLocalizedName() + " at " + x + " " + y + " " + z);
                        count++;
                        amount--;
                        if (amount == 0)
                            break;
                    }
                    if (amount == 0)
                        break;
                }
                if (amount == 0)
                    break;
            }
            if (amount == 0)
                break;
        }
        if (count > 0) {
            module.enable("You searched for blocks");
            //System.out.println("Now rendering " + count + " ores");
        } else {
            module.disable("Nothing found");
        }
    }


}
