package finz.modules.impl.player;

import finz.modules.Category;
import finz.modules.Module;

public class NoHunger extends Module {
    public NoHunger() {
        super("NoHunger", "prevents hunger", Category.PLAYER);
        //setKey(Keyboard.KEY_H);
    }

    @Override
    public void onEnable() {
        //silentSendPacket(new S06PacketUpdateHealth(10,8, 12));
        //mc.thePlayer.foodStats.foodLevel = 20;
        //mc.thePlayer.setXPStats(mc.thePlayer.experience, 8, 12);
        //mc.thePlayer.addPotionEffect(new PotionEffect(Potion.saturation.id, 100000, 1));
    }


}
