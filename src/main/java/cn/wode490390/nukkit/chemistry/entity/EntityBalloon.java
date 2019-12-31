package cn.wode490390.nukkit.chemistry.entity;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityInteractable;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDamageEvent.DamageCause;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

public class EntityBalloon extends Entity implements EntityInteractable {

    public static final int NETWORK_ID = EntityId.BALLOON;

    public EntityBalloon(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 0.8f;
    }

    @Override
    public float getHeight() {
        return 1.8f;
    }

    @Override
    protected float getGravity() {
        return -1.6f;
    }

    @Override
    public boolean canDoInteraction() {
        return false;
    }

    @Override
    public String getInteractButtonText() {
        return "action.interact.balloon";
    }

    @Override
    public void initEntity() {
        super.initEntity();
        this.setMaxHealth(1);
        this.setHealth(1);

        if (this.namedTag.contains("Color")) {
            this.dataProperties.putByte(DATA_COLOR, this.namedTag.getByte("Color"));
        } else {
            this.dataProperties.putByte(DATA_COLOR, 0);
        }

        this.setDataFlag(DATA_FLAGS, DATA_FLAG_GRAVITY, false);
    }

    @Override
    public void saveNBT() {
        super.saveNBT();
        this.namedTag.putByte("Color", this.dataProperties.getByte(DATA_COLOR));
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        DamageCause cause = source.getCause();
        if ((cause == EntityDamageEvent.DamageCause.PROJECTILE || cause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION
                || cause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || cause == EntityDamageEvent.DamageCause.VOID
                || cause == EntityDamageEvent.DamageCause.CUSTOM) && super.attack(source)) {

            this.close();
            return true;
        }

        return false;
    }
}
