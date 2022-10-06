package finz.command.impl;

import finz.FinZ;
import finz.command.Command;

public class Info extends Command {

    public Info() {
        super("Info", "Display client information", "info", "i");
    }

    @Override
    public void onCommand(String[] args, String command) {
        FinZ.addChatMessage("v" + FinZ.version + " by " + FinZ.author);
    }
}
