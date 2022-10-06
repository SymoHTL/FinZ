package finz.command.impl;

import finz.FinZ;
import finz.command.Command;
import net.minecraft.util.BlockPos;

public class AutoWalk extends Command {

    public AutoWalk() {
        super("Walk", "Set a destination to walk to", "walk <x?> <y?> <z?>", "w");
    }

    @Override
    public void onCommand(String[] args, String command) {
        finz.modules.impl.movement.AutoWalk module = (finz.modules.impl.movement.AutoWalk) FinZ.INSTANCE.getManager().getModule("AutoWalk");
        if (args.length == 0) {
            FinZ.addChatMessage("Disabled AutoWalk");
            module.setEnabled(!module.isEnabled());
            return;
        } else if (args.length != 3) {
            FinZ.addChatMessage("Invalid arguments");
            return;
        }

        BlockPos b;
        try {
            b = new BlockPos(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        } catch (Exception e) {
            FinZ.addChatMessage("Coordinates need to be of type Integer");
            return;
        }
        module.setDestination(b);
        module.setEnabled(true);
    }
}
