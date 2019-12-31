package cn.wode490390.nukkit.chemistry.item;

import cn.nukkit.item.Item;

public class ItemCompound extends Item {

    public ItemCompound() {
        this(0, 1);
    }

    public ItemCompound(Integer meta) {
        this(meta, 1);
    }

    public ItemCompound(Integer meta, int count) {
        super(ItemId.COMPOUND, meta, count, "Compound");
    }
}
