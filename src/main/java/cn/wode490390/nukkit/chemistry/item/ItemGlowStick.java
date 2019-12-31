package cn.wode490390.nukkit.chemistry.item;

import cn.nukkit.block.Block;
import cn.nukkit.item.ItemDurable;
import cn.nukkit.item.ItemEdible;

public class ItemGlowStick extends ItemEdible implements ItemDurable {

    public static final int DURABILITY_GLOW_STICK = 100;

    public ItemGlowStick() {
        this(0, 1);
    }

    public ItemGlowStick(Integer meta) {
        this(meta, 1);
    }

    public ItemGlowStick(Integer meta, int count) {
        super(ItemId.GLOW_STICK, meta, 1, "Glow Stick");
        this.block = Block.get(ItemId.GLOW_STICK);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public int getMaxDurability() {
        return DURABILITY_GLOW_STICK;
    }

    //TODO: durability
}
