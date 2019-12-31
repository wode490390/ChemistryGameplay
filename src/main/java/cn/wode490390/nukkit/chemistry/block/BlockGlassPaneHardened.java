package cn.wode490390.nukkit.chemistry.block;

import cn.nukkit.block.BlockGlassPane;
import cn.nukkit.item.Item;

public class BlockGlassPaneHardened extends BlockGlassPane {

    public BlockGlassPaneHardened() {

    }

    @Override
    public int getId() {
        return BlockId.HARD_GLASS_PANE;
    }

    @Override
    public String getName() {
        return "Hardened Glass Pane";
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
