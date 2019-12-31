package cn.wode490390.nukkit.chemistry.block;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockFence;
import cn.nukkit.block.BlockTorch;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.level.Level;
import cn.nukkit.math.BlockFace;

public abstract class BlockTorchColored extends BlockTorch {

    protected BlockTorchColored() {
        this(0);
    }

    protected BlockTorchColored(int meta) {
        super(meta);
    }

    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_NORMAL) {
            Block below = this.down();
            int side = this.getDamage() & 0x7;
            int[] faces = new int[]{
                    0, //0
                    4, //1
                    5, //2
                    2, //3
                    3, //4
                    0, //5
                    0  //6
            };

            if (this.getSide(BlockFace.fromIndex(faces[side])).isTransparent() && !(side == 0 && (below instanceof BlockFence || below.getId() == COBBLE_WALL))) {
                this.getLevel().useBreakOn(this);

                return Level.BLOCK_UPDATE_NORMAL;
            }
        }

        return 0;
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        Block below = this.down();

        if (!target.isTransparent() && face != BlockFace.DOWN) {
            int[] faces = new int[]{
                    0, //0, nerver used
                    5, //1
                    4, //2
                    3, //3
                    2, //4
                    1, //5
            };
            this.setDamage((this.getDamage() & 0x8) + faces[face.getIndex()]);
            this.getLevel().setBlock(block, this, true);

            return true;
        } else if (!below.isTransparent() || below instanceof BlockFence || below.getId() == COBBLE_WALL) {
            this.setDamage(this.getDamage() & 0x8);
            this.getLevel().setBlock(block, this, true);

            return true;
        }

        return false;
    }

    @Override
    public Item toItem() {
        return new ItemBlock(this, this.getDamage() & 0x8);
    }
}
