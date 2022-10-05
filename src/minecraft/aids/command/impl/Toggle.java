package aids.command.impl;

import aids.BaseHiv;
import aids.command.Command;
import aids.modules.Module;

public class Toggle extends Command {

    public Toggle() {
        super("Toggle", "Toggles a module", "toggle <module>", "t");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length > 0) {
            String moduleName = args[0];

            boolean foundModule = false;

            for (Module module : BaseHiv.INSTANCE.getManager().modules) {
                if (module.name.equalsIgnoreCase(moduleName)) {
                    module.toggle();
                    BaseHiv.addChatMessage(module.isEnabled() ? "Enabled " + module.getName() : "Disabled " + module.getName());
                    foundModule = true;
                    break;
                }
            }
            if (!foundModule) {
                BaseHiv.addChatMessage("Module not found.");
            }
        }
    }
}
