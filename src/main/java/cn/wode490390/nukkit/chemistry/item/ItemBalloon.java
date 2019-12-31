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
import cn.nukkit.utils.DyeColor;

public class ItemBalloon extends Item {

    public ItemBalloon() {
        this(0, 1);
    }

    public ItemBalloon(Integer meta) {
        this(meta, 1);
    }

    public ItemBalloon(Integer meta, int count) {
        super(ItemId.BALLOON, meta, count, DyeColor.getByWoolData(meta).getName() + " Balloon");
    }

    @Override
    public int getMaxStackSize() {
        return 16;
    }

    public DyeColor getDyeColor() {
        return DyeColor.getByDyeData(this.getDamage());
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

        Vector3 vec = block.add(0.5, 1, 0.5);
        AxisAlignedBB aabb = target.getBoundingBox();
        if (aabb != null) {
            vec.y = aabb.getMaxY() + 1;
        }
        CompoundTag nbt = Entity.getDefaultNBT(vec);
        if (this.hasCustomName()) {
            nbt.putString("CustomName", this.getCustomName());
        }
        nbt.putByte("Color", this.getDamage() & 0xf);

        Entity entity = Entity.createEntity("Balloon", chunk, nbt);
        //TODO: leash knot
        if (entity != null) {
            if (!player.isCreative()) {
                player.getInventory().decreaseCount(player.getInventory().getHeldItemIndex());
            }
            level.addLevelSoundEvent(vec, LevelSoundEventPacket.SOUND_LEASHKNOT_PLACE);
            entity.spawnToAll();
            return true;
        }

        return false;
    }
}
