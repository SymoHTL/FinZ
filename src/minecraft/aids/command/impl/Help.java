package aids.command.impl;

import aids.BaseHiv;
import aids.command.Command;
import aids.modules.Module;

public class Help extends Command {

    public Help() {
        super("Help", "Lists all modules and commands", "help", "h");
    }

    @Override
    public void onCommand(String[] args, String command) {
        BaseHiv.addChatMessage("Modules:");
        for (Module module : BaseHiv.INSTANCE.manager.getModules()) {
            BaseHiv.addChatMessage(" " + module.getName() + " - " + module.getDescription());
        }
        BaseHiv.addChatMessage("Commands:");
        for (Command commandFor : BaseHiv.INSTANCE.commandManager.commands) {
            BaseHiv.addChatMessage(" " + commandFor.getName() + " - " + commandFor.getDescription() + " - " + commandFor.getSyntax());
        }
    }
}
