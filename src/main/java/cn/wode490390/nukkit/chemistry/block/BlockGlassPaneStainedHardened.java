package cn.wode490390.nukkit.chemistry.block;

import cn.nukkit.block.BlockGlassPaneStained;
import cn.nukkit.item.Item;

public class BlockGlassPaneStainedHardened extends BlockGlassPaneStained {

    public BlockGlassPaneStainedHardened() {
        this(0);
    }

    public BlockGlassPaneStainedHardened(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return BlockId.HARD_STAINED_GLASS_PANE;
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
