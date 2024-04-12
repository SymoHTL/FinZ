package dev.symo.finz.modules.impl.esp;

import dev.symo.finz.events.listeners.KnockbackListener;
import dev.symo.finz.modules.AModule;
import dev.symo.finz.modules.settings.DoubleSetting;
import dev.symo.finz.util.Category;
import dev.symo.finz.util.InputType;
import dev.symo.finz.util.ValueDisplay;

public class AntiKnockback extends AModule implements KnockbackListener {

    private final DoubleSetting _horizontal = new DoubleSetting("Horizontal", "100% = no knockback -- >100% = reverse knockback", 1, 0.0, 1.0, ValueDisplay.DECIMAL, InputType.PERCENT_SLIDER);
    private final DoubleSetting _vertical = new DoubleSetting("Vertical", "100% = no knockback -- >100% = reverse knockback",1, 0.0, 1.0, ValueDisplay.DECIMAL, InputType.PERCENT_SLIDER);

    public AntiKnockback() {
        super("AntiKnockback", Category.MOVEMENT);
        addSetting(_horizontal);
    }

    @Override
    public void onKnockback(KnockbackEvent event) {
        double verticalMultiplier = 1 - _vertical.getValue();
        double horizontalMultiplier = 1 - _horizontal.getValue();

        event.setX(event.getDefaultX() * horizontalMultiplier);
        event.setY(event.getDefaultY() * verticalMultiplier);
        event.setZ(event.getDefaultZ() * horizontalMultiplier);
    }

    @Override
    public void onEnable() {
        EVENTS.add(KnockbackListener.class, this);
    }

    @Override
    public void onDisable() {
        EVENTS.remove(KnockbackListener.class, this);
    }
}
