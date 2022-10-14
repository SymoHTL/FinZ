package com.symo.finz.commands;

import com.symo.finz.commands.impl.*;
import net.minecraftforge.client.ClientCommandHandler;

public class CommandManager {

    public void init() {
        ClientCommandHandler.instance.registerCommand(new MineUpCommand());
        ClientCommandHandler.instance.registerCommand(new MineDownCommand());
        ClientCommandHandler.instance.registerCommand(new FindBlock());
        ClientCommandHandler.instance.registerCommand(new select4x4());
        ClientCommandHandler.instance.registerCommand(new OpenSettings());
    }
}
