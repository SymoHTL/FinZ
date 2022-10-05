package aids.modules;

import aids.modules.impl.combat.*;
import aids.modules.impl.misc.*;
import aids.modules.impl.motion.*;
import aids.modules.impl.player.*;
import aids.modules.impl.ui.*;
import aids.modules.impl.visual.*;

import java.util.ArrayList;

public class ModuleManager {
    public ArrayList<Module> modules = new ArrayList<Module>();

    public ModuleManager() {
        init();
    }

    public void init() {
        // COMBAT
        add(new Velocity());
        add(new KillAura());
        add(new Critical());
        // MOTION
        add(new Sprint());
        add(new Fly());
        add(new AutoWalk());
        // PLAYER
        add(new FastPlace());
        add(new NoFall());
        add(new AutoMine());
        // MISC
        add(new Timer());
        add(new PickBlock());
        // VISUAL
        add(new FullBright());
        add(new BlockESP());
        add(new Debug());
        // HUD
        add(new HUD());
    }

    public void add(Module module) {
        modules.add(module);
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public Module getModule(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Module getEnabledModule(String name) {
        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(name) && module.isEnabled())
                return module;
        }
        return null;
    }
}
