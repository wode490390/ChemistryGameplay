package cn.wode490390.nukkit.chemistry.item;

import cn.nukkit.item.Item;

public class ItemBleach extends Item {

    public ItemBleach() {
        this(0, 1);
    }

    public ItemBleach(Integer meta) {
        this(meta, 1);
    }

    public ItemBleach(Integer meta, int count) {
        super(ItemId.BLEACH, meta, count, "Bleach");
    }
}
