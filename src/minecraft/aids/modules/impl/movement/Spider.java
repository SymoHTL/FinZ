package aids.modules.impl.movement;

import aids.events.impl.EventUpdate;
import aids.modules.Category;
import aids.modules.Module;
import com.sun.javafx.geom.Vec3d;
import org.lwjgl.input.Keyboard;

public class Spider extends Module {
    public Spider() {
        super("Spider", "Allows you to climb walls", Category.MOVEMENT);
        setKey(Keyboard.KEY_P);
    }


    @Override
    public void onUpdate(EventUpdate e)    {
        if(!mc.thePlayer.isCollidedHorizontally) return;
        Vec3d velocity = mc.thePlayer.getVelocityXYZ();
        if(velocity.y >= 0.2)
            return;

        mc.thePlayer.setVelocity(velocity.x, 0.2, velocity.z);
    }
}
