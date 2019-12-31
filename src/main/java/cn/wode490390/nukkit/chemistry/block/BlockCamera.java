package cn.wode490390.nukkit.chemistry.block;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockTransparent;
import cn.nukkit.item.Item;
import cn.nukkit.math.BlockFace;

public class BlockCamera extends BlockTransparent {

    public BlockCamera() {

    }

    @Override
    public int getId() {
        return BlockId.CAMERA;
    }

    @Override
    public String getName() {
        return "Camera";
    }

    @Override
    public double getHardness() {
        return 0;
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        //TODO: palette
        return false;
    }
}
