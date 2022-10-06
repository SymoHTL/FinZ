package aids.command.impl;

import aids.BaseHiv;
import aids.command.Command;
import aids.modules.impl.visual.ESP.FindOreESP;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class FindOres extends Command {

    public static List<BlockPos> foundBlockPos = new ArrayList<>();

    public FindOres() {
        super("FindOres", "Find ores in the world", "findores <orename> <amount> <x?> <y?> <z?>", "fo");
    }

    @Override
    public void onCommand(String[] args, String command) {
        FindOreESP module = (FindOreESP) BaseHiv.INSTANCE.getManager().getModule("FineOreESP");
        if (args.length < 2) {
            BaseHiv.addChatMessage("Invalid arguments");
            return;
        }
        int amount;
        int xRadius;
        int yRadius;
        int zRadius;
        try {
            amount = Integer.parseInt(args[1]);
            xRadius = Integer.parseInt(args[2]);
            yRadius = Integer.parseInt(args[3]);
            zRadius = Integer.parseInt(args[4]);
        }catch (Exception e) {
            BaseHiv.addChatMessage("Parameters needs to be of type Integer");
            return;
        }
        World world = BaseHiv.INSTANCE.mc.thePlayer.getEntityWorld();

        BaseHiv.addChatMessage("Searching for " + args[0] + "...");

        // get the block 100 blocks around the player
        int xPlayer = (int) BaseHiv.INSTANCE.mc.thePlayer.posX;
        int yPlayer = (int) BaseHiv.INSTANCE.mc.thePlayer.posY;
        int zPlayer = (int) BaseHiv.INSTANCE.mc.thePlayer.posZ;

        int count = 0;
        int startX = xPlayer - xRadius;
        int startY = yPlayer + yRadius; // from top to bottom
        int startZ = zPlayer - zRadius;

        int endX = xPlayer + xRadius;
        int endY = yPlayer - yRadius; // from top to bottom
        int endZ = zPlayer + zRadius;

        for (int y = startY; y > endY; y--) { // from top to bottom
            for (int x = startX; x < endX; x++) {
                for (int z = startZ; z < endZ; z++) {
                    Block b = world.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (b.getLocalizedName().toLowerCase().replace(" ", "_").equals(args[0])) {
                        foundBlockPos.add(new BlockPos(x, y, z));
                        //System.out.println("Found " + b.getLocalizedName() + " at " + x + " " + y + " " + z);
                        count++;
                        amount--;
                        if (amount == 0)
                            break;
                    }
                    if (amount == 0)
                        break;
                }
                if (amount == 0)
                    break;
            }
            if (amount == 0)
                break;
        }
        if (count > 0){
            module.setEnabled(true);
            //System.out.println("Now rendering " + count + " ores");
        }else {
            BaseHiv.addChatMessage("Nothing found");
            module.setEnabled(false);
        }
    }
}
