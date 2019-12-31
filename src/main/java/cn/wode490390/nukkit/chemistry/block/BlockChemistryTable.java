package cn.wode490390.nukkit.chemistry.block;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockTransparentMeta;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.math.BlockFace;

public class BlockChemistryTable extends BlockTransparentMeta {

    public BlockChemistryTable() {
        this(0);
    }

    public BlockChemistryTable(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return BlockId.CHEMISTRY_TABLE;
    }

    @Override
    public String getName() {
        int meta = this.getDamage();
        if (meta >= 12) {
            return "Lab Table";
        } else if (meta >= 8) {
            return "Element Constructor";
        } else if (meta >= 4) {
            return "Material Reducer";
        }
        return "Compound Creator";
    }

    @Override
    public double getHardness() {
        return 2.5;
    }

    @Override
    public Item toItem() {
        return new ItemBlock(this, (this.getDamage() >> 2) << 2);
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public boolean onActivate(Item item, Player player) {
        //TODO: UI
        return true;
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        this.setDamage(((this.getDamage() >> 2) << 2) + (player != null ? player.getDirection().getHorizontalIndex() : 0));
        this.getLevel().setBlock(block, this, true);
        return true;
    }
}
