package aids.modules.impl.ui;

import aids.BaseHiv;
import aids.events.impl.Event2D;
import aids.modules.Category;
import aids.modules.Module;
import aids.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HUD extends Module {
    public HUD() {
        super("HUD", "Displays the HUD", Category.HUD);
        setKey(Keyboard.KEY_RSHIFT);
    }

    public void on2D(Event2D event) {
        if (Minecraft.getMinecraft().gameSettings.showDebugInfo) return;
        ScaledResolution sr = new ScaledResolution(mc);
        FontRenderer fr = mc.fontRendererObj;

        double paddingBottom = 2;
        double paddingRight = 4;
        double clientName = fr.FONT_HEIGHT + 2 + paddingBottom;
        double stripeWidth = 1.5d;
        // Client name
        fr.drawStringWithShadow(BaseHiv.INSTANCE.getName() + " v" + BaseHiv.INSTANCE.getVersion(), (float) (2 + stripeWidth), 2, 0xFFFFFFFF);
        Gui.drawRectDouble(
                0,
                0,
                stripeWidth,
                clientName + (paddingBottom + fr.FONT_HEIGHT),
                0xFF1FBED6);
        // FPS
        String fps = "FPS: " + Minecraft.getDebugFPS();
        fr.drawString(fps, sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(fps) - 2, 2, 0xFFFFFFFF);

        // Modules
        ModuleManager manager = BaseHiv.INSTANCE.getManager();
        List<Module> modules = new ArrayList<>(manager.getModules());
        modules.sort(Comparator.comparingDouble(m -> fr.getStringWidth(((Module) m).getName() + Keyboard.getKeyName(((Module) m).getKey()))).reversed());

        int counter = 0;
        for (Module module : modules) {
            double y = clientName + (paddingBottom + fr.FONT_HEIGHT) * counter++;
            //BaseHiv.INSTANCE.manager.getModules().indexOf(module));
            // TEXT SHADOW
            //Gui.drawRectDouble(0, y - paddingBottom / 2f, (double) fr.getStringWidth(module.getName() + ": " + Keyboard.getKeyName(module.getKey())) + paddingRight, y + paddingBottom / 2f + fr.FONT_HEIGHT, 0x33000000);
            // LINE ON RIGHT SIDE
            //Gui.drawRectDouble(fr.getStringWidth(module.getName() + ": " + Keyboard.getKeyName(module.getKey())) + paddingRight, y - paddingBottom / 2f, fr.getStringWidth(module.getName() + ": " + Keyboard.getKeyName(module.getKey())) + stripeWidth + paddingRight, y + paddingBottom / 2f + fr.FONT_HEIGHT, 0xFF1FBED6);
            // BLUE LINE ON LEFT SIDE
            Gui.drawRectDouble(0, y - paddingBottom / 2f, stripeWidth, y + paddingBottom / 2f + fr.FONT_HEIGHT, 0xFF1FBED6);
            // TEXT
            fr.drawStringWithShadow(module.getName() + ": " + Keyboard.getKeyName(module.getKey()), (float) (2 + stripeWidth), (float) y, module.isEnabled() ? 0xFFFFFFFF : 0xFFD3D3D3);
        }

        // mc.displayHeight... full size e.g. 1080
    }
}
