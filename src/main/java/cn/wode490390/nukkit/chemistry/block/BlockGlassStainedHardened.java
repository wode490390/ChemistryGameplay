package cn.wode490390.nukkit.chemistry.block;

import cn.nukkit.block.BlockGlassStained;
import cn.nukkit.item.Item;

public class BlockGlassStainedHardened extends BlockGlassStained {

    public BlockGlassStainedHardened() {
        this(0);
    }

    public BlockGlassStainedHardened(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return BlockId.HARD_STAINED_GLASS;
    }

    @Override
    public String getName() {
        return "Hardened " + super.getName();
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
