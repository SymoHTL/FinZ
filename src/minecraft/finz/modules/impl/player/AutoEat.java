package finz.modules.impl.player;

import finz.events.impl.EventUpdate;
import finz.modules.Category;
import finz.modules.Module;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import org.lwjgl.input.Keyboard;

import java.util.TimerTask;

public class AutoEat extends Module {

        public AutoEat() {
            super("AutoEat", "Automatically eats food", Keyboard.KEY_P, Category.PLAYER);
        }

       public void onUpdate(EventUpdate e) {
            if (mc.thePlayer.getFoodStats().foodLevel == 20)
               return;
            if (mc.thePlayer.isUsingItem())
               return;

            int slot = mc.thePlayer.inventory.getFirstExactSuitableFoodSlot(20 - mc.thePlayer.getFoodStats().foodLevel);
            if (slot != -1)
                mc.thePlayer.inventory.currentItem = slot;

           new java.util.Timer().schedule(new TimerTask() {
               @Override
               public void run() {
                   mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
               }
           }, 75);

        }
}
