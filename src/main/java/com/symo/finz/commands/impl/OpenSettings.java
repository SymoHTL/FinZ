package com.symo.finz.commands.impl;

import com.symo.finz.FinZ;
import com.symo.finz.commands.Command;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.Arrays;
import java.util.List;

public class OpenSettings extends Command {

    public String getCommandName() {
        return "finz";
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("finz", "f");
    }

    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        FinZ.display = FinZ.configFile.gui();
    }

}