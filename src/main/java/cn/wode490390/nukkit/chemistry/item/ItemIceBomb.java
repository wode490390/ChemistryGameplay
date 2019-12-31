package cn.wode490390.nukkit.chemistry.item;

import cn.nukkit.Player;
import cn.nukkit.item.ProjectileItem;
import cn.nukkit.math.Vector3;
import cn.wode490390.nukkit.chemistry.ChemistryGameplay;
import java.util.Map;

public class ItemIceBomb extends ProjectileItem {

    public ItemIceBomb() {
        this(0, 1);
    }

    public ItemIceBomb(Integer meta) {
        this(meta, 1);
    }

    public ItemIceBomb(Integer meta, int count) {
        super(ItemId.ICE_BOMB, meta, count, "Ice Bomb");
    }

    @Override
    public int getMaxStackSize() {
        return 16;
    }

    @Override
    public String getProjectileEntityType() {
        return "IceBomb";
    }

    @Override
    public float getThrowForce() {
        return 1.5f;
    }

    @Override
    public boolean onClickAir(Player player, Vector3 directionVector) {
        Map<Long, Integer> iceBombCooldown = ChemistryGameplay.getInstance().iceBombCooldown;
        long eid = player.getId();
        int currentTick = player.getServer().getTick();
        if (currentTick - iceBombCooldown.getOrDefault(eid, 0) >= 10 && super.onClickAir(player, directionVector)) {
            iceBombCooldown.put(eid, currentTick);
            return true;
        }
        return false;
    }
}
