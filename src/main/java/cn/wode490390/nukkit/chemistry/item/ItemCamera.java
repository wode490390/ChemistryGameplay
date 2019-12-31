package cn.wode490390.nukkit.chemistry.item;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.LevelSoundEventPacket;
import cn.wode490390.nukkit.chemistry.entity.EntityCamera;

public class ItemCamera extends Item {

    public ItemCamera() {
        this(0, 1);
    }

    public ItemCamera(Integer meta) {
        this(meta, 1);
    }

    public ItemCamera(Integer meta, int count) {
        super(ItemId.CAMERA, meta, count, "Camera");
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public boolean onActivate(Level level, Player player, Block block, Block target, BlockFace face, double fx, double fy, double fz) {
        FullChunk chunk = block.getChunk();
        if (chunk == null) {
            return false;
        }

        Vector3 vec = block.add(0.5, 0, 0.5);
        AxisAlignedBB aabb = target.getBoundingBox();
        if (aabb != null) {
            vec.y = aabb.getMaxY() + 0.0001f;
        }
        CompoundTag nbt = Entity.getDefaultNBT(vec);
        if (this.hasCustomName()) {
            nbt.putString("CustomName", this.getCustomName());
        }

        Entity entity = Entity.createEntity("Camera", chunk, nbt);
        if (entity != null) {
            if (!player.isCreative()) {
                player.getInventory().decreaseCount(player.getInventory().getHeldItemIndex());
            }
            level.addLevelSoundEvent(vec, LevelSoundEventPacket.SOUND_TRIPOD);
            level.addLevelSoundEvent(vec, LevelSoundEventPacket.SOUND_SPAWN, -1, EntityCamera.NETWORK_ID);
            entity.spawnToAll();
            return true;
        }

        return false;
    }
}
