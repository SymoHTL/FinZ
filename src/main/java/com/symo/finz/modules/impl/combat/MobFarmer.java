package com.symo.finz.modules.impl.combat;

import com.symo.finz.modules.Module;
import com.symo.finz.utils.Timer;
import com.symo.finz.utils.extension.PlayerExtension;
import com.symo.finz.utils.extension.PlayerInventoryExtension;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import org.lwjgl.input.Keyboard;

import java.util.TimerTask;

public class MobFarmer extends Module {
    Timer attackTimer = new Timer();

    public MobFarmer() {
        super("MobFarmer", Keyboard.KEY_U, "FinZ - Combat");
    }

    public void onUpdate() {
            if (!attackTimer.hasTimeElapsed(100, true))
                return;

            if (mc.thePlayer.isDead)
                return;

            EntityLivingBase entity = PlayerExtension.getClosetMob();

            if (entity == null)
                return;
            if (entity.getDistanceToEntity(mc.thePlayer) >= 4)
                return;
            if (!PlayerExtension.canSeeEyesMidOrFeet(entity))
                return;
            // Attack
            if (mc.thePlayer.isBlocking())
                return;

            //check if the player has a sword in his hand
            if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
                mc.thePlayer.swingItem();
                mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
            } else {
                // switch to sword
                int slot = PlayerInventoryExtension.getFirstSwordInHotBarIndex();
                if (slot != -1)
                    mc.thePlayer.inventory.currentItem = slot;

                mc.thePlayer.swingItem();

                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
                    }
                }, 50);
            }
    }

}
