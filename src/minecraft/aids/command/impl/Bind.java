package aids.command.impl;

import aids.BaseHiv;
import aids.command.Command;
import aids.modules.Module;
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
            for (Module module : BaseHiv.INSTANCE.getManager().getModules()) {
                if (module.name.equalsIgnoreCase(moduleName)) {
                    foundModule = true;
                    if (keyName.equalsIgnoreCase("clear")) {
                        module.setKey(0);
                        BaseHiv.addChatMessage("Cleared keybind for " + module.getName());
                        return;
                    }
                    int key = Keyboard.getKeyIndex(keyName.toUpperCase());
                    if (key == 0) {
                        BaseHiv.addChatMessage("Invalid key.");
                        return;
                    }
                    module.setKey(key);
                    BaseHiv.addChatMessage("Set keybind for " + module.getName() + " to " + keyName.toUpperCase());
                    break;
                }
            }
            if (!foundModule) {
                BaseHiv.addChatMessage("Module not found.");
            }
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("clear")) {
                for (Module module : BaseHiv.INSTANCE.getManager().getModules()) {
                    module.setKey(Keyboard.KEY_NONE);
                }
                BaseHiv.addChatMessage("Cleared all binds.");
            } else {
                BaseHiv.addChatMessage("Invalid arguments.");
            }
        } else {
            BaseHiv.addChatMessage("Invalid arguments.");
        }
    }
}
