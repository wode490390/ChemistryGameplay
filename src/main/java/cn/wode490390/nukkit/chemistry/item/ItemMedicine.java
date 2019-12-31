package cn.wode490390.nukkit.chemistry.item;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerItemConsumeEvent;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import cn.nukkit.potion.Effect;

public class ItemMedicine extends Item {

    public static final int EYE_DROPS = 0;
    public static final int TONIC = 1;
    public static final int ANTIDOTE = 2;
    public static final int ELIXIR = 3;

    public ItemMedicine() {
        this(0, 1);
    }

    public ItemMedicine(Integer meta) {
        this(meta, 1);
    }

    public ItemMedicine(Integer meta, int count) {
        super(ItemId.MEDICINE, meta, 1, "Medicine");
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean onClickAir(Player player, Vector3 directionVector) {
        switch (this.getDamage()) {
            case EYE_DROPS:
                return player.hasEffect(Effect.BLINDNESS);
            case TONIC:
                return player.hasEffect(Effect.NAUSEA);
            case ANTIDOTE:
                return player.hasEffect(Effect.POISON);
            case ELIXIR:
                return player.hasEffect(Effect.WEAKNESS);
        }
        return false;
    }

    @Override
    public boolean onUse(Player player, int ticksUsed) {
        PlayerItemConsumeEvent consumeEvent = new PlayerItemConsumeEvent(player, this);
        player.getServer().getPluginManager().callEvent(consumeEvent);
        if (consumeEvent.isCancelled()) {
            return false;
        }

        if (!player.isCreative()) {
            --this.count;
            player.getInventory().setItemInHand(this);
            player.getInventory().addItem(get(GLASS_BOTTLE));
        }

        int effect = -1;
        switch (this.getDamage()) {
            case EYE_DROPS:
                effect = Effect.BLINDNESS;
                break;
            case TONIC:
                effect = Effect.NAUSEA;
                break;
            case ANTIDOTE:
                effect = Effect.POISON;
                break;
            case ELIXIR:
                effect = Effect.WEAKNESS;
                break;
        }
        if (effect != -1) {
            player.removeEffect(effect);
        }

        return true;
    }
}
