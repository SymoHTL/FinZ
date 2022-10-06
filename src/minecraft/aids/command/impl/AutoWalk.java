package aids.command.impl;

import aids.BaseHiv;
import aids.command.Command;
import net.minecraft.util.BlockPos;

public class AutoWalk extends Command {

    public AutoWalk() {
        super("Walk", "Set a destination to walk to", "walk <x?> <y?> <z?>", "w");
    }

    @Override
    public void onCommand(String[] args, String command) {
        aids.modules.impl.movement.AutoWalk module = (aids.modules.impl.movement.AutoWalk) BaseHiv.INSTANCE.getManager().getModule("AutoWalk");
        if (args.length == 0) {
            BaseHiv.addChatMessage("Disabled AutoWalk");
            module.setEnabled(!module.isEnabled());
            return;
        } else if (args.length != 3) {
            BaseHiv.addChatMessage("Invalid arguments");
            return;
        }

        BlockPos b;
        try {
            b = new BlockPos(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        } catch (Exception e) {
            BaseHiv.addChatMessage("Coordinates need to be of type Integer");
            return;
        }
        module.setDestination(b);
        module.setEnabled(true);
    }
}
