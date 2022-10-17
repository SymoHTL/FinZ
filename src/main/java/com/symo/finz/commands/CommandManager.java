package com.symo.finz.commands;

import com.symo.finz.commands.impl.FindBlock;
import com.symo.finz.commands.impl.OpenSettings;
import com.symo.finz.commands.impl.select4x4;
import com.symo.finz.commands.impl.waypoint.WayPointCommand;
import net.minecraftforge.client.ClientCommandHandler;

public class CommandManager {

    public void init() {
        ClientCommandHandler.instance.registerCommand(new FindBlock());
        ClientCommandHandler.instance.registerCommand(new select4x4());
        ClientCommandHandler.instance.registerCommand(new OpenSettings());

        //Waypoint
        ClientCommandHandler.instance.registerCommand(new WayPointCommand());
    }
}
