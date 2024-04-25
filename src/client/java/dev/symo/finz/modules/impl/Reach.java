package dev.symo.finz.modules.impl;

import dev.symo.finz.modules.AModule;
import dev.symo.finz.modules.settings.DoubleSetting;
import dev.symo.finz.util.Category;
import dev.symo.finz.util.InputType;

public class Reach extends AModule{

    private final DoubleSetting distance = new DoubleSetting("Distance",
            "The distance you can hit players from, (Default: 3)",
            3.5, 1, 100, InputType.DECIMAL_SLIDER);

    public Reach() {
        super("Reach", Category.COMBAT);
        addSetting(distance);
    }

    public Float getReach() {
        return (float) distance.getValue();
    }
}
