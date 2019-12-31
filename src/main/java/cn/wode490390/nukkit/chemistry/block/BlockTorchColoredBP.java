package cn.wode490390.nukkit.chemistry.block;

public class BlockTorchColoredBP extends BlockTorchColored {

    public BlockTorchColoredBP() {
        this(0);
    }

    public BlockTorchColoredBP(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return BlockId.COLORED_TORCH_BP;
    }

    @Override
    public String getName() {
        return (this.getDamage() & 0x8) == 0 ? "Blue Torch" : "Purple Torch";
    }
}
