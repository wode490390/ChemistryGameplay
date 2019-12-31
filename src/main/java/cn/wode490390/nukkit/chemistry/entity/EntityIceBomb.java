package cn.wode490390.nukkit.chemistry.entity;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockWater;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.projectile.EntityProjectile;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;

public class EntityIceBomb extends EntityProjectile {

    public static final int NETWORK_ID = EntityId.ICE_BOMB;

    public EntityIceBomb(FullChunk chunk, CompoundTag nbt) {
        this(chunk, nbt, null);
    }

    public EntityIceBomb(FullChunk chunk, CompoundTag nbt, Entity shootingEntity) {
        super(chunk, nbt, shootingEntity);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 0.25f;
    }

    @Override
    public float getLength() {
        return 0.25f;
    }

    @Override
    public float getHeight() {
        return 0.25f;
    }

    @Override
    public void initEntity() {
        super.initEntity();
        this.setMaxHealth(1);
        this.setHealth(1);
    }

    @Override
    protected float getGravity() {
        return 0.025f;
    }

    @Override
    protected float getDrag() {
        return 0.01f;
    }

    @Override
    public boolean onUpdate(int currentTick) {
        if (this.closed) {
            return false;
        }

        this.timing.startTiming();

        boolean hasUpdate = super.onUpdate(currentTick);

        for (Block block : this.getCollisionBlocks()) {
            if (block instanceof BlockWater) {
                this.isCollided = true;
                break;
            }
        }

        if (this.isCollided) {
            Vector3 vec = this.floor();
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    for (int y = -1; y <= 1; y++) {
                        Vector3 block = vec.add(x, y, z);
                        if (this.level.getBlock(block) instanceof BlockWater) {
                            this.level.setBlock(block, Block.get(Block.ICE), true);
                        }
                    }
                }
            }

            this.kill();
            hasUpdate = true;
        } else if (this.age > 1200) {
            this.kill();
            hasUpdate = true;
        }

        this.timing.stopTiming();

        return hasUpdate;
    }
}
