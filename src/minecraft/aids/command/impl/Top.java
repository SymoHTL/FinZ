package aids.command.impl;

import aids.BaseHiv;
import aids.command.Command;

public class Top extends Command {

    public Top() {
        super("top", "builds/mines you to skylight", "top <y?>");
    }

    @Override
    public void onCommand(String[] args, String command) {
        aids.modules.impl.movement.Top module = (aids.modules.impl.movement.Top) BaseHiv.INSTANCE.getManager().getModule("Top");
        int y;
        if (args.length >= 1) {
            try {
                y = Integer.parseInt(args[0]);
            } catch (Exception e) {
                BaseHiv.addChatMessage("Parameter needs to be of type Integer");
                return;
            }
            module.y = y;
        }
        double[] current = BaseHiv.INSTANCE.mc.thePlayer.getExactPositionDouble();
        double[] destination = BaseHiv.INSTANCE.mc.thePlayer.getPositionDouble();
        double[] travelPos = new double[]{current[0] - destination[0], 0, current[2] - destination[2]};// y is unimportant
        BaseHiv.INSTANCE.mc.thePlayer.motionX = travelPos[0] / 10;
        BaseHiv.INSTANCE.mc.thePlayer.motionZ = travelPos[2] / 10;
        BaseHiv.addChatMessage("Motion: " + travelPos[0] + ", " + travelPos[2]);


        module.setEnabled(!module.isEnabled());
        BaseHiv.addChatMessage("Top is now " + (module.isEnabled() ? "enabled" : "disabled"));
    }
}
