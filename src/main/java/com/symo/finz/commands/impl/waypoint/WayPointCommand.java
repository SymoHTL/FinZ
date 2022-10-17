package com.symo.finz.commands.impl.waypoint;

import com.symo.finz.commands.Command;
import com.symo.finz.entities.WayPoint;
import com.symo.finz.modules.impl.misc.WayPointManager;
import com.symo.finz.utils.ChatUtils;
import com.symo.finz.utils.extension.PlayerExtension;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.Collections;
import java.util.List;

public class WayPointCommand extends Command {
    @Override
    public String getCommandName() {
        return "waypoint";
    }

    public List<String> getCommandAliases() {
        return Collections.singletonList("wp");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0)
            ChatUtils.sendMessage("Usage: /waypoint <add/remove/list/help>");
        if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("a"))
            processAddCommand(args);
        if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("r"))
            processRemoveCommand(args);
        if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("l"))
            processListCommand();
        if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h"))
            processHelpCommand();
    }

    public void processAddCommand(String[] args) {
        if (args.length == 1)
            WayPointManager.addWayPoint(new WayPoint(PlayerExtension.getAccurateBlockPos()));
        if (args.length == 2)
            WayPointManager.addWayPoint(new WayPoint(PlayerExtension.getAccurateBlockPos(), args[1]));
    }

    public void processRemoveCommand(String[] args) {
        if (args.length == 1)
            WayPointManager.removeWayPoint(new WayPoint(PlayerExtension.getAccurateBlockPos()));
        if (args.length == 2)
            WayPointManager.removeWayPoint(new WayPoint(PlayerExtension.getAccurateBlockPos(), args[1]));
    }

    public void processListCommand() {
        ChatUtils.sendMessage("Waypoints:");
        WayPointManager.getWayPoints().forEach(wayPoint -> ChatUtils.sendMessage(wayPoint.name + " " + wayPoint.pos));
    }

    public void processHelpCommand() {
        ChatUtils.sendMessage("Usage: /waypoint <add/remove/list/help>");
    }

}
