package cn.wode490390.nukkit.chemistry.block;

import cn.nukkit.block.BlockTransparent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;

public class BlockHeatBlock extends BlockTransparent {

    public BlockHeatBlock() {

    }

    @Override
    public int getId() {
        return BlockId.CHEMICAL_HEAT;
    }

    @Override
    public String getName() {
        return "Heat Block";
    }

    @Override
    public double getHardness() {
        return 2.5;
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public Item[] getDrops(Item item) {
        if (item.isPickaxe()) {
            return new Item[]{
                    this.toItem()
            };
        }
        return new Item[0];
    }
}
