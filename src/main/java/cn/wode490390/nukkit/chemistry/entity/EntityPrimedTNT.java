package cn.wode490390.nukkit.chemistry.entity;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.IntEntityData;
import cn.nukkit.event.entity.EntityExplosionPrimeEvent;
import cn.nukkit.level.Explosion;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.wode490390.nukkit.chemistry.util.UnderwaterTNTExplosion;

public class EntityPrimedTNT extends cn.nukkit.entity.item.EntityPrimedTNT {

    protected boolean allowUnderwater;

    public EntityPrimedTNT(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    public EntityPrimedTNT(FullChunk chunk, CompoundTag nbt, Entity source) {
        super(chunk, nbt, source);
    }

    @Override
    protected void initEntity() {
        super.initEntity();

        if (this.namedTag.contains("AllowUnderwater")) {
            this.allowUnderwater = this.namedTag.getBoolean("AllowUnderwater");
        } else {
            this.allowUnderwater = false;
        }

        this.setDataProperty(new IntEntityData(DATA_VARIANT, this.allowUnderwater ? 1 : 0));
    }

    @Override
    public void saveNBT() {
        super.saveNBT();
        this.namedTag.putBoolean("AllowUnderwater", this.allowUnderwater);
    }

    @Override
    public void explode() {
        EntityExplosionPrimeEvent event = new EntityExplosionPrimeEvent(this, 4);
        this.server.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            Explosion explosion = this.allowUnderwater ? new UnderwaterTNTExplosion(this, event.getForce(), this) : new Explosion(this, event.getForce(), this);
            if (event.isBlockBreaking()) {
                explosion.explodeA();
            }
            explosion.explodeB();
        }
    }
}
