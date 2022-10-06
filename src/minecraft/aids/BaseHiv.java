package aids;


import aids.command.CommandManager;
import aids.events.impl.*;
import aids.modules.Module;
import aids.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public enum BaseHiv {
    INSTANCE;
    public static final String name = "BaseHiv", version = "1.0", author = "ShingShang";
    //public EventBus bus;
    public ModuleManager manager;
    public Minecraft mc = Minecraft.getMinecraft();
    public CommandManager commandManager;

    public static void addChatMessage(String message) {
        message = "\2479[\2477" + INSTANCE.getName() + "\2479]\2477 " + message;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }

    public void init() {
        //bus = new EventBus();
        manager = new ModuleManager();
        commandManager = new CommandManager();
        //bus.register(this);
    }

    /* public EventBus getBus() {
        return bus;
    }*/

    public void stop() {
        //bus.unregister(this);
    }

    public ModuleManager getManager() {
        return manager;
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void onKey(EventKey e) {
        // Key should not be changed
        manager.getModules().stream().filter(module -> module.getKey() == e.getKey()).forEach(module -> module.setEnabled(!module.isEnabled()));
        // Key can be changed
        manager.getModules().stream().filter(Module::isEnabled).forEach(module -> module.onKey(e));
    }

    public void onMessage(EventChat e) {
        commandManager.handleChat(e);
    }

    public void onGetPackets(EventGetPackets e) {
        manager.getModules().stream().filter(Module::isEnabled).forEach(module -> module.onGetPackets(e));
    }

    public void onRender(EventRender e) {
        manager.getModules().stream().filter(Module::isEnabled).forEach(module -> module.onRender(e));
    }

    public void on2D(Event2D e) {
        manager.getModules().stream().filter(Module::isEnabled).forEach(module -> module.on2D(e));
    }

    public void onPickBlock(EventPickBlock e) {
        manager.getModules().stream().filter(Module::isEnabled).forEach(module -> module.onPickBlock(e));
    }

    public void onPacketSent(EventSentPacket e) {
        manager.getModules().stream().filter(Module::isEnabled).forEach(module -> module.onPacketSent(e));
    }

    public void onUpdate(EventUpdate e) {
        manager.getModules().stream().filter(Module::isEnabled).forEach(module -> module.onUpdate(e));
    }

    public void onMotion(EventMotion e) {
        manager.getModules().stream().filter(Module::isEnabled).forEach(module -> module.onMotion(e));
    }
}
