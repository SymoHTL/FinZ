package dev.symo.finz.modules.impl;

import dev.symo.finz.modules.AModule;
import dev.symo.finz.modules.impl.esp.ItemESP;
import dev.symo.finz.modules.impl.esp.MobEsp;
import dev.symo.finz.modules.impl.esp.PlayerESP;

import java.util.ArrayList;

public class Modules {

    public static final ArrayList<AModule> all = new ArrayList<>();

    public static Zoom zoom = new Zoom();
    public static WhatTheFAmILookingAt wtfaila = new WhatTheFAmILookingAt();
    public static PathTracer pathTracer = new PathTracer();
    public static MaterialScanner materialScanner = new MaterialScanner();
    public static PlayerESP playerESP = new PlayerESP();
    public static MobEsp mobEsp = new MobEsp();
    public static ItemESP itemESP = new ItemESP();

    static  {
        all.add(zoom);
        all.add(wtfaila);
        all.add(pathTracer);
        all.add(materialScanner);
        all.add(playerESP);
        all.add(mobEsp);
        all.add(itemESP);
    }
}
