package aids.command.impl;

import aids.BaseHiv;
import aids.command.Command;

public class Info extends Command {

    public Info() {
        super("Info", "Display client information", "info", "i");
    }

    @Override
    public void onCommand(String[] args, String command) {
        BaseHiv.addChatMessage("v" + BaseHiv.version + " by " + BaseHiv.author);
    }
}
