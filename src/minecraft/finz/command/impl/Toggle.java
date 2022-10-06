package finz.command.impl;

import finz.FinZ;
import finz.command.Command;
import finz.modules.Module;

public class Toggle extends Command {

    public Toggle() {
        super("Toggle", "Toggles a module", "toggle <module>", "t");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length > 0) {
            String moduleName = args[0];

            boolean foundModule = false;

            for (Module module : FinZ.INSTANCE.getManager().getModules()) {
                if (module.name.equalsIgnoreCase(moduleName)) {
                    module.toggle();
                    FinZ.addChatMessage(module.isEnabled() ? "Enabled " + module.getName() : "Disabled " + module.getName());
                    foundModule = true;
                    break;
                }
            }
            if (!foundModule) {
                FinZ.addChatMessage("Module not found.");
            }
        }
    }
}
