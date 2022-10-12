package com.symo.finz.modules.impl.misc;


import com.symo.finz.modules.Module;

public class Timer extends Module {
    public Timer() {
        super("Timer", "FinZ - Misc");
    }

    @Override
    public void onEnable() {
        //mc.timer.timerSpeed = 5f;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        //mc.timer.timerSpeed = 1.0f;
        super.onDisable();
    }
}
