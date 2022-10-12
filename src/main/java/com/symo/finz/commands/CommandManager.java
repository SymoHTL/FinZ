package com.symo.finz.commands;

import com.symo.finz.commands.impl.DownCommand;
import com.symo.finz.commands.impl.FindOres;
import com.symo.finz.commands.impl.UpCommand;
import net.minecraftforge.client.ClientCommandHandler;

public class CommandManager {

    public void init() {
        ClientCommandHandler.instance.registerCommand(new UpCommand());
        ClientCommandHandler.instance.registerCommand(new DownCommand());
        ClientCommandHandler.instance.registerCommand(new FindOres());
    }
}
