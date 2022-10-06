package aids.modules;

import aids.modules.impl.combat.*;
import aids.modules.impl.misc.*;
import aids.modules.impl.movement.*;
import aids.modules.impl.player.*;
import aids.modules.impl.ui.*;
import aids.modules.impl.visual.*;

import java.util.ArrayList;

public class ModuleManager {
    protected ArrayList<Module> modules = new ArrayList<>();

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
        add(new Top());
        add(new JetPack());
        add(new Spider());
        // PLAYER
        add(new FastPlace());
        add(new NoFall());
        add(new AutoMine());
        add(new NoHunger());
        // MISC
        add(new Timer());
        add(new PickBlock());
        // VISUAL
        add(new FullBright());
        add(new BlockESP());
        add(new Debug());
        add(new FindOreESP());
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

    public ArrayList<Module> getEnabledModules() {
        ArrayList<Module> enabledModules = new ArrayList<>();
        for (Module module : modules) {
            if (module.isEnabled())
                enabledModules.add(module);
        }
        return enabledModules;
    }
}
