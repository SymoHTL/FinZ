package dev.symo.finz.modules.impl;

import dev.symo.finz.modules.AModule;
import dev.symo.finz.modules.settings.BoolSetting;
import dev.symo.finz.util.Category;

public class AutoGetBlock extends AModule {
    public final BoolSetting safeMode = new BoolSetting("Safe Mode", "If enabled only items from the hotbar are considered, (because this sends a clickSlotPacket to the server the authority can check if the player is in his inventory)", true);

    public AutoGetBlock() {
        super("AutoGetBlock", Category.BLOCKS);
        addSetting(safeMode);
    }
}
