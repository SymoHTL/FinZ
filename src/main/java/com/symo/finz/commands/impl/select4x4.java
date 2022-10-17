package com.symo.finz.commands.impl;

import com.symo.finz.FinZ;
import com.symo.finz.commands.Command;
import com.symo.finz.modules.impl.misc.AimAtBlock;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

public class select4x4 extends Command {
    @Override
    public String getCommandName() {
        return "select4x4";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        // just get every block in a 4x4x4 area around the player
        // and add it to a list
        BlockPos playerPos = FinZ.mc.thePlayer.getPosition();
        for (int x = playerPos.getX() - 2; x < playerPos.getX() + 2; x++) {
            for (int y = playerPos.getY() - 2; y < playerPos.getY() + 2; y++) {
                for (int z = playerPos.getZ() - 2; z < playerPos.getZ() + 2; z++) {
                    AimAtBlock.blockPosList.add(new BlockPos(x, y, z));
                }
            }
        }


    }
}
