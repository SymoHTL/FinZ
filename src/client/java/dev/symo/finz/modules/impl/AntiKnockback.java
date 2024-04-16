package dev.symo.finz.modules.impl;

import dev.symo.finz.events.listeners.KnockbackListener;
import dev.symo.finz.modules.AModule;
import dev.symo.finz.modules.settings.DoubleSetting;
import dev.symo.finz.modules.settings.IntSetting;
import dev.symo.finz.util.Category;
import dev.symo.finz.util.InputType;

public class AntiKnockback extends AModule implements KnockbackListener {

    private final DoubleSetting _horizontal = new DoubleSetting("Horizontal", "100% = no knockback -- >100% = reverse knockback", 0.2, 0.0, 1.0, InputType.PERCENT_SLIDER);
    private final DoubleSetting _vertical = new DoubleSetting("Vertical", "100% = no knockback -- >100% = reverse knockback", 0.2, 0.0, 1.0, InputType.PERCENT_SLIDER);
    private final IntSetting _everyNth = new IntSetting("Every Nth", "Only apply anti-knockback every Nth hit/velocity change, 1 -> every hit, 2 -> every 2nd hit, etc.", 1, 1, 100);

    private int _hitCount = 0;

    public AntiKnockback() {
        super("AntiKnockback", Category.MOVEMENT);
        addSetting(_horizontal);
        addSetting(_vertical);
        addSetting(_everyNth);
    }

    @Override
    public void onKnockback(KnockbackEvent event) {
          if (++_hitCount % _everyNth.getValue() != 0) return;

        double verticalMultiplier = 1 - _vertical.getValue();
        double horizontalMultiplier = 1 - _horizontal.getValue();

        event.setX(event.getDefaultX() * horizontalMultiplier);
        event.setY(event.getDefaultY() * verticalMultiplier);
        event.setZ(event.getDefaultZ() * horizontalMultiplier);
    }

    @Override
    public void onEnable() {
        EVENTS.add(KnockbackListener.class, this);
        _hitCount = 0;
    }

    @Override
    public void onDisable() {
        EVENTS.remove(KnockbackListener.class, this);
    }
}
