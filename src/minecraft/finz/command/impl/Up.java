package finz.command.impl;

import finz.FinZ;
import finz.command.Command;
import finz.modules.impl.movement.GoUp;

public class Up extends Command {

    public Up() {
        super("Up", "builds/mines you to skylight", "up <y?>");
    }

    @Override
    public void onCommand(String[] args, String command) {
        GoUp module = (GoUp) FinZ.INSTANCE.getManager().getModule("GoUp");
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
        FinZ.addChatMessage("Top is now " + (module.isEnabled() ? "enabled" : "disabled"));
    }
}
