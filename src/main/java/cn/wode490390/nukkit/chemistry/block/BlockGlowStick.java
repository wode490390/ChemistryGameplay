package cn.wode490390.nukkit.chemistry.block;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockFlowable;
import cn.nukkit.item.Item;
import cn.nukkit.math.BlockFace;
import cn.wode490390.nukkit.chemistry.item.ItemId;

public class BlockGlowStick extends BlockFlowable {

    public BlockGlowStick() {
        this(0);
    }

    public BlockGlowStick(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return ItemId.GLOW_STICK;
    }

    @Override
    public String getName() {
        return "Glow Stick";
    }

    @Override
    public Item toItem() {
        return Item.get(AIR);
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        //wtf microjang???
        return false;
    }
}
