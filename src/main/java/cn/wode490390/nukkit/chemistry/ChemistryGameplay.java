package cn.wode490390.nukkit.chemistry;

import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockUnknown;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.item.Item;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.resourcepacks.ResourcePack;
import cn.nukkit.resourcepacks.ResourcePackManager;
import cn.wode490390.nukkit.chemistry.block.*;
import cn.wode490390.nukkit.chemistry.entity.*;
import cn.wode490390.nukkit.chemistry.item.*;
import cn.wode490390.nukkit.chemistry.resourcepacks.ChemistryBehaviorPack;
import cn.wode490390.nukkit.chemistry.resourcepacks.ChemistryResourcePack;
import cn.wode490390.nukkit.chemistry.util.MetricsLite;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ChemistryGameplay extends PluginBase implements Listener {

    private static ChemistryGameplay INSTANCE;

    public final Map<Long, Integer> iceBombCooldown = new Long2IntOpenHashMap();

    @Override
    public void onLoad() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        try {
            new MetricsLite(this, 6157);
        } catch (Throwable ignore) {

        }

        int protocol = ProtocolInfo.CURRENT_PROTOCOL;
        if (protocol >= 261) { //1.4
            List<ResourcePack> packs = Lists.newArrayList(new ChemistryResourcePack(), new ChemistryBehaviorPack());
            ResourcePackManager manager = Server.getInstance().getResourcePackManager();
            synchronized (manager) {
                try {
                    Field f1 = ResourcePackManager.class.getDeclaredField("resourcePacksById");
                    f1.setAccessible(true);
                    Map<UUID, ResourcePack> byId = (Map<UUID, ResourcePack>) f1.get(manager);
                    packs.forEach(pack -> byId.put(pack.getPackId(), pack));

                    Field f2 = ResourcePackManager.class.getDeclaredField("resourcePacks");
                    f2.setAccessible(true);
                    packs.addAll(Arrays.asList((ResourcePack[]) f2.get(manager)));
                    f2.set(manager, packs.toArray(new ResourcePack[0]));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            Entity.registerEntity("TNT", EntityPrimedTNT.class);
            Entity.registerEntity("NPC", EntityNpc.class);
            Entity.registerEntity("Agent", EntityAgent.class);
            if (protocol >= 388) { //1.13
                Entity.registerEntity("Camera", EntityCamera.class);
            }
            Entity.registerEntity("IceBomb", EntityIceBomb.class);
            Entity.registerEntity("Balloon", EntityBalloon.class);

            this.registerBlock(BlockId.ELEMENT_0, BlockElementUnknown.class);
            this.registerBlock(Block.TNT, BlockTNT.class);
            this.registerBlock(ItemId.GLOW_STICK, BlockGlowStick.class); //hack
            this.registerBlock(BlockId.HARD_GLASS_PANE, BlockGlassPaneHardened.class);
            this.registerBlock(BlockId.HARD_STAINED_GLASS_PANE, BlockGlassPaneStainedHardened.class);
            this.registerBlock(BlockId.CHEMICAL_HEAT, BlockHeatBlock.class);
            this.registerBlock(BlockId.COLORED_TORCH_RG, BlockTorchColoredRG.class);
            this.registerBlock(BlockId.COLORED_TORCH_BP, BlockTorchColoredBP.class);
            this.registerBlock(BlockId.CHEMISTRY_TABLE, BlockChemistryTable.class);
            this.registerBlock(BlockId.UNDERWATER_TORCH, BlockTorchUnderwater.class);
            if (protocol >= 388) { //1.13
                this.registerBlock(BlockId.CAMERA, BlockCamera.class);
            }
            this.registerBlock(BlockId.HARD_GLASS, BlockGlassHardened.class);
            this.registerBlock(BlockId.HARD_STAINED_GLASS, BlockGlassStainedHardened.class);

            Item.list[ItemId.GLOW_STICK] = ItemGlowStick.class;
            Item.list[ItemId.SPARKLER] = ItemSparkler.class;
            Item.list[ItemId.MEDICINE] = ItemMedicine.class;
            Item.list[ItemId.BALLOON] = ItemBalloon.class;
            Item.list[ItemId.RAPID_FERTILIZER] = ItemSuperFertilizer.class;
            Item.list[ItemId.BLEACH] = ItemBleach.class;
            Item.list[ItemId.ICE_BOMB] = ItemIceBomb.class;
            if (protocol >= 388) { //1.13
                Item.list[ItemId.CAMERA] = ItemCamera.class;
            }
            Item.list[ItemId.COMPOUND] = ItemCompound.class;
        }
    }

    public void registerBlock(int id, Class<? extends Block> clazz) {
        Item.list[id] = clazz;
        Block.list[id] = clazz;
        Block block;
        try {
            block = clazz.newInstance();
            try {
                Constructor constructor = clazz.getDeclaredConstructor(int.class);
                constructor.setAccessible(true);
                for (int data = 0; data < 16; ++data) {
                    Block.fullList[(id << 4) | data] = (Block) constructor.newInstance(data);
                }
                Block.hasMeta[id] = true;
            } catch (NoSuchMethodException ignore) {
                for (int data = 0; data < 16; ++data) {
                    Block.fullList[(id << 4) | data] = block;
                }
            }
        } catch (Exception e) {
            this.getLogger().alert("Error while registering " + clazz.getName(), e);
            for (int data = 0; data < 16; ++data) {
                Block.fullList[(id << 4) | data] = new BlockUnknown(id, data);
            }
            return;
        }
        Block.solid[id] = block.isSolid();
        Block.transparent[id] = block.isTransparent();
        Block.hardness[id] = block.getHardness();
        Block.light[id] = block.getLightLevel();
        if (block.isSolid()) {
            if (block.isTransparent()) {
                Block.lightFilter[id] = 1;
            } else {
                Block.lightFilter[id] = 15;
            }
        } else {
            Block.lightFilter[id] = 1;
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.iceBombCooldown.remove(event.getPlayer().getId());
    }

    public static ChemistryGameplay getInstance() {
        return INSTANCE;
    }
}
