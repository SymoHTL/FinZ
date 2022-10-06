package aids.modules.impl.movement;

import aids.events.impl.EventUpdate;
import aids.modules.Category;
import aids.modules.Module;
import com.sun.javafx.geom.Vec3d;
import org.lwjgl.input.Keyboard;

public class JetPack extends Module {
    public JetPack() {
        super("JetPack", "Makes you fly with a jetpack", Category.MOVEMENT);
        setKey(Keyboard.KEY_K);
    }

    @Override
    public void onUpdate(EventUpdate e){
        Vec3d velocity = mc.thePlayer.getVelocityXYZ();
        if(velocity.y >= 0.5)
            return;
        // only do this when the player presses space
        if(mc.gameSettings.keyBindJump.isKeyDown())
            // he do be flying
            mc.thePlayer.setVelocity(velocity.x, 0.5, velocity.z);
    }


}
