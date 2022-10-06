package finz.command.impl;

import finz.FinZ;
import finz.command.Command;
import finz.modules.Module;

public class Help extends Command {

    public Help() {
        super("Help", "Lists all modules and commands", "help", "h");
    }

    @Override
    public void onCommand(String[] args, String command) {
        FinZ.addChatMessage("Modules:");
        for (Module module : FinZ.INSTANCE.manager.getModules()) {
            FinZ.addChatMessage(" " + module.getName() + " - " + module.getDescription());
        }
        FinZ.addChatMessage("Commands:");
        for (Command commandFor : FinZ.INSTANCE.commandManager.commands) {
            FinZ.addChatMessage(" " + commandFor.getName() + " - " + commandFor.getDescription() + " - " + commandFor.getSyntax());
        }
    }
}
