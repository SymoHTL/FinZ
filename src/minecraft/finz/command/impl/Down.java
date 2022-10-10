package finz.command.impl;

import finz.FinZ;
import finz.command.Command;
import finz.modules.impl.movement.GoDown;

public class Down extends Command {
    public Down() {
        super("Down", "builds/mines you to bedrock", "down <y?>");
    }

    @Override
    public void onCommand(String[] args, String command) {
        GoDown module = (GoDown) FinZ.INSTANCE.getManager().getModule("GoDown");

        int y = 0;
        if (args.length >= 1) {
            try {
                y = Integer.parseInt(args[0]);
            } catch (Exception e) {
                FinZ.addChatMessage("Parameter needs to be of type Integer");
                return;
            }
        }
        module.y = y;
        module.toggle();
        FinZ.addChatMessage("Down is now " + (module.isEnabled() ? "enabled" : "disabled"));
    }
}
