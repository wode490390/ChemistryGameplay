package cn.wode490390.nukkit.chemistry.block;

import cn.nukkit.entity.Entity;
import cn.nukkit.level.Sound;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;

import java.util.concurrent.ThreadLocalRandom;

public class BlockTNT extends cn.nukkit.block.BlockTNT {

    private int meta;

    public BlockTNT() {
        this(0);
    }

    public BlockTNT(int meta) {
        this.meta = meta;
    }

    @Override
    public void prime(int fuse, Entity source) {
        this.level.setBlock(this, get(AIR), true);

        double motion = ThreadLocalRandom.current().nextFloat() * Math.PI * 2;
        CompoundTag nbt = new CompoundTag()
                .putList(new ListTag<DoubleTag>("Pos")
                        .add(new DoubleTag("", this.x + 0.5))
                        .add(new DoubleTag("", this.y))
                        .add(new DoubleTag("", this.z + 0.5)))
                .putList(new ListTag<DoubleTag>("Motion")
                        .add(new DoubleTag("", -Math.sin(motion) * 0.02))
                        .add(new DoubleTag("", 0.2))
                        .add(new DoubleTag("", -Math.cos(motion) * 0.02)))
                .putList(new ListTag<FloatTag>("Rotation")
                        .add(new FloatTag("", 0))
                        .add(new FloatTag("", 0)))
                .putShort("Fuse", fuse)
                .putBoolean("AllowUnderwater", (this.getDamage() | 1) != 1);

        Entity tnt = Entity.createEntity("TNT", this.level.getChunk(this.getChunkX(), this.getChunkZ()), nbt, source);
        if (tnt != null) {
            tnt.spawnToAll();
            this.level.addSound(this, Sound.RANDOM_FUSE);
        }
    }

    @Override
    public int getFullId() {
        return (this.getId() << 4) + this.getDamage();
    }

    @Override
    public int getDamage() {
        return this.meta;
    }

    @Override
    public void setDamage(int meta) {
        this.meta = meta;
    }
}
