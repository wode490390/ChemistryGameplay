package cn.wode490390.nukkit.chemistry.block;

import cn.nukkit.block.BlockGlass;
import cn.nukkit.item.Item;

public class BlockGlassHardened extends BlockGlass {

    public BlockGlassHardened() {

    }

    @Override
    public int getId() {
        return BlockId.HARD_GLASS;
    }

    @Override
    public String getName() {
        return "Hardened Glass";
    }

    @Override
    public double getHardness() {
        return 10;
    }

    @Override
    public double getResistance() {
        return 50;
    }

    @Override
    public Item[] getDrops(Item item) {
        return new Item[]{
                this.toItem()
        };
    }

    @Override
    public boolean canSilkTouch() {
        return false;
    }
}
