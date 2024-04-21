package dev.symo.finz.config;

import dev.symo.finz.modules.AModule;

public class KeyBind {
    private final String keyName;
    private final AModule module;

    public KeyBind(String keyName, AModule module) {
        this.keyName = keyName;
        this.module = module;
    }

    public void onKeyPress(String keyName) {
        if (this.keyName.equalsIgnoreCase(keyName))
            this.module.toggle();
    }

    public String getKeyName() {
        return keyName;
    }

    public AModule getModule() {
        return module;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        else if (!(o instanceof KeyBind keyBind)) return false;
        else return this.keyName.equalsIgnoreCase(keyBind.keyName) && this.module.equals(keyBind.module);
    }
}
