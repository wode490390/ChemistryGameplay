package cn.wode490390.nukkit.chemistry.item;

import cn.nukkit.Player;
import cn.nukkit.item.ItemDurable;
import cn.nukkit.item.ItemEdible;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;

public class ItemSparkler extends ItemEdible implements ItemDurable {

    public static final int DURABILITY_SPARKLER = 95;

    public ItemSparkler() {
        this(0, 1);
    }

    public ItemSparkler(Integer meta) {
        this(meta, 1);
    }

    public ItemSparkler(Integer meta, int count) {
        super(ItemId.SPARKLER, meta, 1, "Sparkler");
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public int getMaxDurability() {
        return DURABILITY_SPARKLER;
    }

    @Override
    public boolean isTool() {
        return true;
    }

    @Override
    public boolean onClickAir(Player player, Vector3 directionVector) {
        return this.getNamedTagEntry("active_time") == null;
    }

    @Override
    public boolean onUse(Player player, int ticksUsed) {
        if (this.getNamedTagEntry("active_time") == null) {
            CompoundTag tag = this.getNamedTag();
            if (tag == null) {
                tag = new CompoundTag();
            }
            tag.putLong("active_time", player.level.getCurrentTick());
            this.setNamedTag(tag);

            ++this.meta;

            player.getInventory().setItemInHand(this);
            return true;
        }

        return false;
    }

    //TODO: duration 1200 ticks
}
