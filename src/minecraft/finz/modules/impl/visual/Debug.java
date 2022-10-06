package finz.modules.impl.visual;

import finz.events.impl.Event2D;
import finz.events.impl.EventPickBlock;
import finz.modules.Category;
import finz.modules.Module;
import finz.util.Timer;
import net.minecraft.client.gui.FontRenderer;

public class Debug extends Module {
    Timer timer = new Timer();

    public Debug() {
        super("Debug", "Debug module", 0, Category.VISUAL);
    }


    //public void onRender(EventRender eventRender) {
    //    if (this.isEnabled()) {
    //        for (BlockPos block : blocks) {
    //            BlockESPUtil.drawESP(block);
    //        }
    //        if (timer.hasTimeElapsed(500, true)) {
    //            blocks = AutoMine.blocks;
    //        }
    //    }
    //}


    @Override
    public void onPickBlock(EventPickBlock e) {
        //MovingObjectPosition movingObjectPosition = mc.thePlayer.rayTrace(4.5f, 1.0f);
        //BaseHiv.addChatMessage(movingObjectPosition.toString());
        //super.onPickBlock(e);
    }

    @Override
    public void on2D(Event2D e) {
        FontRenderer fr = mc.fontRendererObj;
        fr.drawStringWithShadow("X: " + mc.thePlayer.motionX, 100, 50, -1);
        fr.drawStringWithShadow("Z: " + mc.thePlayer.motionZ, 100, 50 + fr.FONT_HEIGHT, -1);
        //fr.drawString(mc.displayWidth / 2f + fr.getStringWidth("X: " + mc.thePlayer.motionX + "Z: " + mc.thePlayer.motionZ) + "", 100, 100, -1);

    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
