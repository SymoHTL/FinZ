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

        //double distance = BaseHiv.INSTANCE.mc.thePlayer.getDistanceToBlockMiddle();
        //double[] middle = BaseHiv.INSTANCE.mc.thePlayer.getPositionDouble();

        //double angle = Math.toDegrees(Math.atan2(middle[2], middle[0])) - 90;

        //BaseHiv.INSTANCE.mc.thePlayer.rotationYaw = (float) angle;
        //BaseHiv.INSTANCE.mc.thePlayer.rotationPitch = (float)Math.toDegrees(Math.atan2(middle[1], distance));

        //BaseHiv.INSTANCE.mc.thePlayer.motionX = Math.cos(Math.toRadians(angle + 90)) * 0.2;
        //BaseHiv.INSTANCE.mc.thePlayer.motionZ = Math.sin(Math.toRadians(angle + 90)) * 0.2;


        module.setEnabled(!module.isEnabled());
        BaseHiv.addChatMessage("Top is now " + (module.isEnabled() ? "enabled" : "disabled"));
    }
}
