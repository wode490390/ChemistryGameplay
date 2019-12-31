package cn.wode490390.nukkit.chemistry.entity;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityInteractable;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDamageEvent.DamageCause;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.CameraPacket;
import cn.nukkit.network.protocol.EntityEventPacket;

public class EntityCamera extends Entity implements EntityInteractable {

    public static final int NETWORK_ID = EntityId.TRIPOD_CAMERA;

    protected Player photographer;
    protected int delay;

    public EntityCamera(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 0.75f;
    }

    @Override
    public float getHeight() {
        return 1.8f;
    }

    @Override
    protected float getGravity() {
        return 0.08f;
    }

    @Override
    protected float getDrag() {
        return 0.02f;
    }

    @Override
    public boolean canDoInteraction() {
        return true;
    }

    @Override
    public String getInteractButtonText() {
        return "action.interact.takepicture";
    }

    @Override
    public void initEntity() {
        super.initEntity();
        this.setMaxHealth(4);
        this.setHealth(4);
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        DamageCause cause = source.getCause();
        if ((cause == DamageCause.ENTITY_ATTACK || cause == DamageCause.PROJECTILE
                || cause == DamageCause.MAGIC || cause == DamageCause.ENTITY_EXPLOSION
                || cause == DamageCause.BLOCK_EXPLOSION || cause == DamageCause.VOID
                || cause == DamageCause.CUSTOM) && super.attack(source)) {

            EntityEventPacket pk = new EntityEventPacket();
            pk.eid = this.getId();
            pk.event = 61; //DEATH_SMOKE_CLOUD
            Server.broadcastPacket(this.getViewers().values(), pk);

            this.close();
            return true;
        }

        return false;
    }

    @Override
    public boolean onInteract(Player player, Item item) {
        return onInteract(player, item, null);
    }

    @Override
    public boolean onInteract(Player player, Item item, Vector3 clickedPos) {
        if (this.photographer == null) {
            this.photographer = player;

            CameraPacket pk = new CameraPacket();
            pk.cameraUniqueId = this.getId();
            pk.playerUniqueId = player.getId();
            Server.broadcastPacket(this.getViewers().values(), pk);
        }
        return true;
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

            if (this.photographer != null) {
                this.yaw = Math.toDegrees(Math.atan2(this.photographer.z - this.z, this.photographer.x - this.x)) - 90;

                this.delay += tickDiff;
                if (this.delay > 100) {
                    this.close();
                }
            }

            this.updateMovement();
        }

        this.timing.stopTiming();

        return hasUpdate;
    }
}
