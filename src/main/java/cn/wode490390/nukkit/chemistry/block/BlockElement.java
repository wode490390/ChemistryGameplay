package cn.wode490390.nukkit.chemistry.block;

import cn.nukkit.block.BlockSolid;

public abstract class BlockElement extends BlockSolid {

    @Override
    public double getHardness() {
        return 0;
    }
}
