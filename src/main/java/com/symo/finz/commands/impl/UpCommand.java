package com.symo.finz.commands.impl;

import com.symo.finz.FinZ;
import com.symo.finz.commands.Command;
import com.symo.finz.modules.impl.movement.GoUp;
import com.symo.finz.utils.ChatUtils;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class UpCommand extends Command {
    @Override
    public String getCommandName() {
        return "up";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        try {
            GoUp module = (GoUp) FinZ.moduleManager.getModule("GoUp");
            int y = 0;
            if (args.length >= 1) {
                try {
                    y = Integer.parseInt(args[0]);
                } catch (Exception e) {
                    ChatUtils.sendMessage("Parameter needs to be of type Integer");
                    return;
                }
            }
            module.y = y;
            module.toggle();
        }catch (Exception e){
            e.printStackTrace();
            ChatUtils.sendMessage("Error");
        }

    }
}
