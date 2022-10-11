package com.symo.finz.utils;

import com.symo.finz.FinZ;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class ChatUtils
{
    public static void sendMessage(final String message) {
        if (FinZ.mc.ingameGUI != null) {
            FinZ.mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("\2479[\2477" + FinZ.MOD_ID + "\2479]\2477 " + message));
        }
    }

    public static ChatStyle createClickStyle(final ClickEvent.Action action, final String value) {
        final ChatStyle style = new ChatStyle();
        style.setChatClickEvent(new ClickEvent(action, value));
        style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(EnumChatFormatting.YELLOW + value)));
        return style;
    }
}
