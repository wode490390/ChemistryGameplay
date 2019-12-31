package cn.wode490390.nukkit.chemistry.entity;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityLiving;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDamageEvent.DamageCause;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.SimpleAxisAlignedBB;
import cn.nukkit.nbt.tag.CompoundTag;

public class EntityNpc extends EntityLiving {

    public static final int NETWORK_ID = EntityId.NPC;

    public EntityNpc(FullChunk chunk, CompoundTag nbt) {
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
        return 2.1f;
    }

    @Override
    public void initEntity() {
        super.initEntity();
        this.setMaxHealth(20);
        this.setHealth(20);
        this.setNameTagVisible(true);
        this.setNameTagAlwaysVisible(true);
        //TODO: more skin
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
            this.motionY -= this.getGravity();

            this.move(this.motionX, this.motionY, this.motionZ);

            float friction = 1 - this.getDrag();

            this.motionX *= friction;
            this.motionY *= friction;
            this.motionZ *= friction;

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
