package finz.modules.impl.movement;

import finz.events.impl.EventUpdate;
import finz.modules.Category;
import finz.modules.Module;
import finz.util.Timer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

public class AutoWalk extends Module {
    Timer t = new Timer();
    BlockPos destination;

    public AutoWalk() {
        super("AutoWalk", "Automatically presses W", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

    public BlockPos getDestination() {
        return destination;
    }

    public void setDestination(BlockPos destination) {
        this.destination = destination;
    }

    @Override
    public void onUpdate(EventUpdate e) {
        if (t.hasTimeElapsed(300, true)) {
            if (destination == null)
                KeyBinding.setKeyBindState(Keyboard.KEY_W, true);
            else {
                if (mc.thePlayer.posX > destination.getX()) {
                    mc.thePlayer.motionX = -0.39;
                } else {
                    mc.thePlayer.motionX = 0.39;
                }
                if (mc.thePlayer.posZ > destination.getZ()) {
                    mc.thePlayer.motionZ = -0.39;
                } else {
                    mc.thePlayer.motionZ = 0.39;
                }
            }
        }
    }

    public void onEnable() {
        KeyBinding.setKeyBindState(Keyboard.KEY_W, true);
    }

    public void onDisable() {
        KeyBinding.setKeyBindState(Keyboard.KEY_W, false);
    }
}
