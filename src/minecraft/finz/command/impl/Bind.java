package finz.command.impl;

import finz.FinZ;
import finz.command.Command;
import finz.modules.Module;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {

    public Bind() {
        super("Bind", "Bind a module", "bind <name> <key> | clear", "b");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length == 2) {
            String moduleName = args[0];
            String keyName = args[1];
            boolean foundModule = false;
            for (Module module : FinZ.INSTANCE.getManager().getModules()) {
                if (module.name.equalsIgnoreCase(moduleName)) {
                    foundModule = true;
                    if (keyName.equalsIgnoreCase("clear")) {
                        module.setKey(0);
                        FinZ.addChatMessage("Cleared keybind for " + module.getName());
                        return;
                    }
                    int key = Keyboard.getKeyIndex(keyName.toUpperCase());
                    if (key == 0) {
                        FinZ.addChatMessage("Invalid key.");
                        return;
                    }
                    module.setKey(key);
                    FinZ.addChatMessage("Set keybind for " + module.getName() + " to " + keyName.toUpperCase());
                    break;
                }
            }
            if (!foundModule) {
                FinZ.addChatMessage("Module not found.");
            }
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("clear")) {
                for (Module module : FinZ.INSTANCE.getManager().getModules()) {
                    module.setKey(Keyboard.KEY_NONE);
                }
                FinZ.addChatMessage("Cleared all binds.");
            } else {
                FinZ.addChatMessage("Invalid arguments.");
            }
        } else {
            FinZ.addChatMessage("Invalid arguments.");
        }
    }
}
