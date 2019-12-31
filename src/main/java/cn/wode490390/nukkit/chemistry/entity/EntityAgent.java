package cn.wode490390.nukkit.chemistry.entity;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityLiving;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDamageEvent.DamageCause;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.SimpleAxisAlignedBB;
import cn.nukkit.nbt.tag.CompoundTag;

public class EntityAgent extends EntityLiving {

    public static final int NETWORK_ID = EntityId.AGENT;

    public EntityAgent(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 0.6f;
    }

    @Override
    public float getHeight() {
        return 0.93f;
    }

    @Override
    public void initEntity() {
        super.initEntity();
        this.setMaxHealth(Integer.MAX_VALUE);
        this.setHealth(Integer.MAX_VALUE);
        this.setNameTagVisible(true);
        this.setNameTagAlwaysVisible(true);
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        DamageCause cause = source.getCause();
        return (cause == EntityDamageEvent.DamageCause.VOID || cause == EntityDamageEvent.DamageCause.CUSTOM) && super.attack(source);
    }

    @Override
    public boolean canCollide() {
        return false;
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return false;
    }

    @Override
    public boolean onUpdate(int currentTick) {
        if (this.closed) {
            return false;
        }

        int tickDiff = currentTick - this.lastUpdate;
        if (tickDiff <= 0 && !this.justCreated) {
            return true;
        }

        this.lastUpdate = currentTick;

        this.timing.startTiming();

        boolean hasUpdate = this.entityBaseTick(tickDiff);

        if (this.isAlive()) {
            int maxDistance = 6;
            for (Entity entity : this.level.getNearbyEntities(new SimpleAxisAlignedBB(this.x - maxDistance, this.y - maxDistance, this.z - maxDistance, this.x + maxDistance, this.y + maxDistance, this.z + maxDistance), this)) {
                if (entity instanceof Player && entity.isAlive()) {
                    this.pitch = -Math.toDegrees(Math.asin((entity.y - this.y) / this.distance(entity)));
                    this.yaw = Math.toDegrees(Math.atan2(entity.z - this.z, entity.x - this.x)) - 90;
                }
            }

            this.updateMovement();
        }

        this.timing.stopTiming();

        return hasUpdate;
    }
}
