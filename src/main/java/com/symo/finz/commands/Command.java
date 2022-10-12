package com.symo.finz.commands;

import com.symo.finz.FinZ;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

public abstract class Command implements ICommand {

    public abstract String getCommandName();

    public String getCommandUsage(final ICommandSender sender) {
        return FinZ.commandPrefix + this.getCommandName();
    }

    public List<String> getCommandAliases() {
        return new ArrayList<>();
    }

    public abstract void processCommand(final ICommandSender sender, final String[] args) throws CommandException;

    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }

    public List<String> addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return null;
    }

    public boolean isUsernameIndex(final String[] args, final int index) {
        return false;
    }

    public int compareTo(final ICommand o) {
        return 0;
    }

}
