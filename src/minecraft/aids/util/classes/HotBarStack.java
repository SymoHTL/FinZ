package aids.util.classes;

import net.minecraft.item.ItemBlock;

public class HotBarStack {
    public int slot;
    public ItemBlock block;

    public HotBarStack(int slot, ItemBlock block) {
        this.slot = slot;
        this.block = block;
    }
}
