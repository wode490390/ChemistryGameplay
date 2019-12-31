package cn.wode490390.nukkit.chemistry.item;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.math.BlockFace;

public class ItemSuperFertilizer extends Item {

    public ItemSuperFertilizer() {
        this(0, 1);
    }

    public ItemSuperFertilizer(Integer meta) {
        this(meta, 1);
    }

    public ItemSuperFertilizer(Integer meta, int count) {
        super(ItemId.RAPID_FERTILIZER, meta, count, "Super Fertilizer");
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public boolean onActivate(Level level, Player player, Block block, Block target, BlockFace face, double fx, double fy, double fz) {
        if (target.onActivate(get(DYE, 0xf) , player)) {
            if (player != null && (player.gamemode & 0x1) == 0) {
                this.count--;
            }
            return true;
        }
        return false;
    }
}
