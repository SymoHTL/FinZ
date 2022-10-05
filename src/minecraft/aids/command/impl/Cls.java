package aids.command.impl;

import aids.BaseHiv;
import aids.command.Command;
import net.minecraft.client.gui.GuiNewChat;

public class Cls extends Command {

    public Cls() {
        super("Cls", "Clears the chat", "cls", "c");
    }

    @Override
    public void onCommand(String[] args, String command) {
        GuiNewChat chat = BaseHiv.INSTANCE.mc.ingameGUI.getChatGUI();
        //chat.clearChatMessages();
        chat.field_146253_i.clear(); // only clear messages not cache
    }
}
