package com.symo.finz.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import com.symo.finz.modules.Module;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class FastPlace extends Module {

    public FastPlace() {
        super("FastPlace","player");
    }

    public void onUpdate(TickEvent.ClientTickEvent event) {
        //mc.rightClickDelayTimer = 0;
    }

    @Override
    public void onDisable() {
        //mc.rightClickDelayTimer = 4;
        super.onDisable();
    }
}
