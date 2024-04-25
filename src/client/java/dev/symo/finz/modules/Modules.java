package dev.symo.finz.modules;

import dev.symo.finz.modules.impl.*;
import dev.symo.finz.modules.impl.esp.BedESP;
import dev.symo.finz.modules.impl.esp.ItemESP;
import dev.symo.finz.modules.impl.esp.MobEsp;
import dev.symo.finz.modules.impl.esp.PlayerESP;

import java.util.ArrayList;

public class Modules {

    public static final ArrayList<AModule> all = new ArrayList<>();

    // combat
    public static final AutoTool autoTool = new AutoTool();
    public static final ProjectileWarner projectileWarner = new ProjectileWarner();
    public static final AntiKnockback knockback = new AntiKnockback();
    public static final Reach reach = new Reach();

    // blocks
    public static final AutoGetBlock autoGetBlock = new AutoGetBlock();

    // misc

    public static final Zoom zoom = new Zoom();

    // movement
    public static final AutoSneak autoSneak = new AutoSneak();

    // render
    public static final WhatTheFAmILookingAt wtfaila = new WhatTheFAmILookingAt();
    public static final MaterialScanner materialScanner = new MaterialScanner();
    public static final PathTracer pathTracer = new PathTracer();
    public static final ChromaOutline chromaOutline = new ChromaOutline();

    public static final NoBlindness noBlindness = new NoBlindness();

    // esp
    public static final PlayerESP playerESP = new PlayerESP();
    public static final MobEsp mobEsp = new MobEsp();
    public static final ItemESP itemESP = new ItemESP();
    public static final BedESP bedESP = new BedESP();

    // settings
    public static final SettingsModule settings = new SettingsModule();


    static  {

        // combat
        all.add(autoTool);
        all.add(projectileWarner);
        all.add(knockback);
        all.add(reach);

        // blocks
        all.add(autoGetBlock);

        // misc
        all.add(zoom);

        // movement
        all.add(autoSneak);

        // render
        all.add(wtfaila);
        all.add(pathTracer);
        all.add(materialScanner);
        all.add(chromaOutline);

        all.add(noBlindness);

        // esp
        all.add(playerESP);
        all.add(mobEsp);
        all.add(itemESP);
        all.add(bedESP);

        // settings
        all.add(settings);
    }
}
