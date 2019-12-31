package cn.wode490390.nukkit.chemistry.block;

public class BlockTorchColoredRG extends BlockTorchColored {

    public BlockTorchColoredRG() {
        this(0);
    }

    public BlockTorchColoredRG(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return BlockId.COLORED_TORCH_RG;
    }

    @Override
    public String getName() {
        return (this.getDamage() & 0x8) == 0 ? "Red Torch" : "Green Torch";
    }
}
