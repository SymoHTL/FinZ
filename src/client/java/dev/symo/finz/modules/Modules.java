package dev.symo.finz.modules;

import dev.symo.finz.modules.impl.*;
import dev.symo.finz.modules.impl.esp.BedESP;
import dev.symo.finz.modules.impl.esp.ItemESP;
import dev.symo.finz.modules.impl.esp.MobEsp;
import dev.symo.finz.modules.impl.esp.PlayerESP;

import java.util.ArrayList;

public class Modules {

    public static final ArrayList<AModule> all = new ArrayList<>();

    // misc
    public static AntiKnockback knockback = new AntiKnockback();

    public static Zoom zoom = new Zoom();

    // render
    public static WhatTheFAmILookingAt wtfaila = new WhatTheFAmILookingAt();
    public static MaterialScanner materialScanner = new MaterialScanner();
    public static PathTracer pathTracer = new PathTracer();
    public static ChromaOutline chromaOutline = new ChromaOutline();

    // esp
    public static PlayerESP playerESP = new PlayerESP();
    public static MobEsp mobEsp = new MobEsp();
    public static ItemESP itemESP = new ItemESP();
    public static BedESP bedESP = new BedESP();

    // settings
    public static SettingsModule settings = new SettingsModule();

    static  {
        // misc
        all.add(knockback);
        all.add(zoom);

        // render
        all.add(wtfaila);
        all.add(pathTracer);
        all.add(materialScanner);
        all.add(chromaOutline);

        // esp
        all.add(playerESP);
        all.add(mobEsp);
        all.add(itemESP);
        all.add(bedESP);

        // settings
        all.add(settings);
    }
}
