package net.mehvahdjukaar.every_compat.modules.mrcrayfish_furniture;

import com.mrcrayfish.furniture.FurnitureMod;
import com.mrcrayfish.furniture.FurnitureModTab;
import com.mrcrayfish.furniture.block.*;
import com.mrcrayfish.furniture.client.renderer.tileentity.KitchenSinkBlockEntityRenderer;
import com.mrcrayfish.furniture.common.ModTags;
import com.mrcrayfish.furniture.core.ModBlocks;
import com.mrcrayfish.furniture.tileentity.KitchenSinkBlockEntity;
import net.mehvahdjukaar.every_compat.WoodGood;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.api.SimpleModule;
import net.mehvahdjukaar.every_compat.dynamicpack.ClientDynamicResourcesHandler;
import net.mehvahdjukaar.selene.block_set.BlockType;
import net.mehvahdjukaar.selene.block_set.leaves.LeavesType;
import net.mehvahdjukaar.selene.block_set.wood.WoodType;
import net.mehvahdjukaar.selene.client.asset_generators.LangBuilder;
import net.mehvahdjukaar.selene.resourcepack.AfterLanguageLoadEvent;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class MrCrayfishFurnitureModule extends SimpleModule {

    public final SimpleEntrySet<LeavesType, Block> HEDGES;
    public final SimpleEntrySet<WoodType, Block> BEDSIDE_CABINETS;
    public final SimpleEntrySet<WoodType, Block> BENCHES;
    public final SimpleEntrySet<WoodType, Block> BLINDS;
    public final SimpleEntrySet<WoodType, Block> CABINETS;
    public final SimpleEntrySet<WoodType, Block> CHAIRS;
    public final SimpleEntrySet<WoodType, Block> COFFEE_TABLES;
    public final SimpleEntrySet<WoodType, Block> CRATES;
    public final SimpleEntrySet<WoodType, Block> DESKS;
    public final SimpleEntrySet<WoodType, Block> DESK_CABINETS;
    public final SimpleEntrySet<WoodType, Block> KITCHEN_COUNTERS;
    public final SimpleEntrySet<WoodType, Block> KITCHEN_DRAWERS;
    public final SimpleEntrySet<WoodType, Block> KITCHEN_SINK_DARK;
    public final SimpleEntrySet<WoodType, Block> KITCHEN_SINK_LIGHT;
    public final SimpleEntrySet<WoodType, Block> MAIL_BOXES;
    public final SimpleEntrySet<WoodType, Block> STRIPPED_BEDSIDE_CABINETS;
    public final SimpleEntrySet<WoodType, Block> STRIPPED_BENCHES;
    public final SimpleEntrySet<WoodType, Block> STRIPPED_BLINDS;
    public final SimpleEntrySet<WoodType, Block> STRIPPED_CABINETS;
    public final SimpleEntrySet<WoodType, Block> STRIPPED_CHAIRS;
    public final SimpleEntrySet<WoodType, Block> STRIPPED_COFFEE_TABLES;
    public final SimpleEntrySet<WoodType, Block> STRIPPED_CRATES;
    public final SimpleEntrySet<WoodType, Block> STRIPPED_DESKS;
    public final SimpleEntrySet<WoodType, Block> STRIPPED_DESK_CABINETS;
    public final SimpleEntrySet<WoodType, Block> STRIPPED_KITCHEN_COUNTERS;
    public final SimpleEntrySet<WoodType, Block> STRIPPED_KITCHEN_DRAWERS;
    public final SimpleEntrySet<WoodType, Block> STRIPPED_KITCHEN_SINK_DARK;
    public final SimpleEntrySet<WoodType, Block> STRIPPED_KITCHEN_SINK_LIGHT;
    public final SimpleEntrySet<WoodType, Block> STRIPPED_MAIL_BOXES;
    public final SimpleEntrySet<WoodType, Block> STRIPPED_TABLES;
    public final SimpleEntrySet<WoodType, Block> TABLES;
    public final SimpleEntrySet<WoodType, Block> UPGRADED_FENCES;
    public final SimpleEntrySet<WoodType, Block> UPGRADED_GATES;

    public MrCrayfishFurnitureModule(String modId) {
        super(modId, "cfm");

        BEDSIDE_CABINETS = SimpleEntrySet.builder("bedside_cabinet",
                        ModBlocks.BEDSIDE_CABINET_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new BedsideCabinetBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.BEDROOM, Registry.ITEM_REGISTRY)
                .addTag(ModTags.Items.STORAGE, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(BEDSIDE_CABINETS);

        STRIPPED_BEDSIDE_CABINETS = SimpleEntrySet.builder("bedside_cabinet", "stripped",
                        ModBlocks.BEDSIDE_CABINET_STRIPPED_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new BedsideCabinetBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.BEDROOM, Registry.ITEM_REGISTRY)
                .addTag(ModTags.Items.STORAGE, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(STRIPPED_BEDSIDE_CABINETS);

        BENCHES = SimpleEntrySet.builder("park_bench",
                        ModBlocks.PARK_BENCH_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new ParkBenchBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.OUTDOORS, Registry.ITEM_REGISTRY)
                .addTag(ModTags.Items.STORAGE, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(BENCHES);

        STRIPPED_BENCHES = SimpleEntrySet.builder("park_bench", "stripped",
                        ModBlocks.PARK_BENCH_STRIPPED_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new ParkBenchBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.OUTDOORS, Registry.ITEM_REGISTRY)
                .addTag(ModTags.Items.STORAGE, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(STRIPPED_BENCHES);

        BLINDS = SimpleEntrySet.builder("blinds",
                        ModBlocks.BLINDS_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new BlindsBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.BEDROOM, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(BLINDS);

        STRIPPED_BLINDS = SimpleEntrySet.builder("blinds", "stripped",
                        ModBlocks.BLINDS_STRIPPED_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new BlindsBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.BEDROOM, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(STRIPPED_BLINDS);

        CABINETS = SimpleEntrySet.builder("cabinet",
                        ModBlocks.CABINET_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new CabinetBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.STORAGE, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(CABINETS);

        STRIPPED_CABINETS = SimpleEntrySet.builder("cabinet", "stripped",
                        ModBlocks.CABINET_STRIPPED_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new CabinetBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.STORAGE, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(STRIPPED_CABINETS);

        CHAIRS = SimpleEntrySet.builder("chair",
                        ModBlocks.CHAIR_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new ChairBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.GENERAL, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(CHAIRS);

        STRIPPED_CHAIRS = SimpleEntrySet.builder("chair", "stripped",
                        ModBlocks.CHAIR_STRIPPED_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new ChairBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.GENERAL, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(STRIPPED_CHAIRS);

        COFFEE_TABLES = SimpleEntrySet.builder("coffee_table",
                        ModBlocks.COFFEE_TABLE_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new CoffeeTableBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.GENERAL, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(COFFEE_TABLES);

        STRIPPED_COFFEE_TABLES = SimpleEntrySet.builder("coffee_table", "stripped",
                        ModBlocks.COFFEE_TABLE_STRIPPED_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new CoffeeTableBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.GENERAL, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(STRIPPED_COFFEE_TABLES);

        CRATES = SimpleEntrySet.builder("crate",
                        ModBlocks.CRATE_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new CrateBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.STORAGE, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(CRATES);

        STRIPPED_CRATES = SimpleEntrySet.builder("crate", "stripped",
                        ModBlocks.CRATE_STRIPPED_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new CrateBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.STORAGE, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(STRIPPED_CRATES);

        DESKS = SimpleEntrySet.builder("desk",
                        ModBlocks.DESK_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new DeskBlock(BlockBehaviour.Properties.copy(w.planks), DeskBlock.MaterialType.OAK))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.BEDROOM, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(DESKS);

        STRIPPED_DESKS = SimpleEntrySet.builder("desk", "stripped",
                        ModBlocks.DESK_STRIPPED_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new DeskBlock(BlockBehaviour.Properties.copy(w.planks), DeskBlock.MaterialType.STRIPPED_OAK))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.BEDROOM, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(STRIPPED_DESKS);

        DESK_CABINETS = SimpleEntrySet.builder("desk_cabinet",
                        ModBlocks.DESK_CABINET_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new DeskCabinetBlock(BlockBehaviour.Properties.copy(w.planks), DeskBlock.MaterialType.OAK))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.BEDROOM, Registry.ITEM_REGISTRY)
                .addTag(ModTags.Items.STORAGE, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(DESK_CABINETS);

        STRIPPED_DESK_CABINETS = SimpleEntrySet.builder("desk_cabinet", "stripped",
                        ModBlocks.DESK_CABINET_STRIPPED_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new DeskCabinetBlock(BlockBehaviour.Properties.copy(w.planks), DeskBlock.MaterialType.STRIPPED_OAK))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.BEDROOM, Registry.ITEM_REGISTRY)
                .addTag(ModTags.Items.STORAGE, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(STRIPPED_DESK_CABINETS);

        KITCHEN_COUNTERS = SimpleEntrySet.builder("kitchen_counter",
                        ModBlocks.KITCHEN_COUNTER_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new KitchenCounterBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.KITCHEN, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(KITCHEN_COUNTERS);

        STRIPPED_KITCHEN_COUNTERS = SimpleEntrySet.builder("kitchen_counter", "stripped",
                        ModBlocks.KITCHEN_COUNTER_STRIPPED_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new KitchenCounterBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.KITCHEN, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(STRIPPED_KITCHEN_COUNTERS);

        KITCHEN_DRAWERS = SimpleEntrySet.builder("kitchen_drawer",
                        ModBlocks.KITCHEN_DRAWER_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new KitchenDrawerBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.KITCHEN, Registry.ITEM_REGISTRY)
                .addTag(ModTags.Items.STORAGE, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(KITCHEN_DRAWERS);

        STRIPPED_KITCHEN_DRAWERS = SimpleEntrySet.builder("kitchen_drawer", "stripped",
                        ModBlocks.KITCHEN_DRAWER_STRIPPED_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new KitchenDrawerBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.KITCHEN, Registry.ITEM_REGISTRY)
                .addTag(ModTags.Items.STORAGE, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(STRIPPED_KITCHEN_DRAWERS);

        KITCHEN_SINK_DARK = SimpleEntrySet.builder("kitchen_sink_dark",
                        ModBlocks.KITCHEN_SINK_DARK_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new CompatKitchenSinkBlock(BlockBehaviour.Properties.copy(w.planks), true))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.KITCHEN, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .addTile(CompatKitchenSinkBlockEntity::new)
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(KITCHEN_SINK_DARK);

        STRIPPED_KITCHEN_SINK_DARK = SimpleEntrySet.builder("kitchen_sink_dark", "stripped",
                        ModBlocks.KITCHEN_SINK_DARK_STRIPPED_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new CompatKitchenSinkBlock(BlockBehaviour.Properties.copy(w.planks), true))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.KITCHEN, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(STRIPPED_KITCHEN_SINK_DARK);

        KITCHEN_SINK_LIGHT = SimpleEntrySet.builder("kitchen_sink_light",
                        ModBlocks.KITCHEN_SINK_LIGHT_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new CompatKitchenSinkBlock(BlockBehaviour.Properties.copy(w.planks), true))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.KITCHEN, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(KITCHEN_SINK_LIGHT);

        STRIPPED_KITCHEN_SINK_LIGHT = SimpleEntrySet.builder("kitchen_sink_light", "stripped",
                        ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new CompatKitchenSinkBlock(BlockBehaviour.Properties.copy(w.planks), true))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.KITCHEN, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(STRIPPED_KITCHEN_SINK_LIGHT);

        MAIL_BOXES = SimpleEntrySet.builder("mail_box",
                        ModBlocks.MAIL_BOX_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new MailBoxBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.OUTDOORS, Registry.ITEM_REGISTRY)
                .addTag(ModTags.Items.STORAGE, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(MAIL_BOXES);

        STRIPPED_MAIL_BOXES = SimpleEntrySet.builder("mail_box", "stripped",
                        ModBlocks.MAIL_BOX_STRIPPED_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new MailBoxBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.OUTDOORS, Registry.ITEM_REGISTRY)
                .addTag(ModTags.Items.STORAGE, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(STRIPPED_MAIL_BOXES);

        STRIPPED_TABLES = SimpleEntrySet.builder("table", "stripped",
                        ModBlocks.TABLE_STRIPPED_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new TableBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.GENERAL, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .setTab(FurnitureModTab.TAB_BUILDING_BLOCKS)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(STRIPPED_TABLES);

        TABLES = SimpleEntrySet.builder("table",
                        ModBlocks.TABLE_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new TableBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.GENERAL, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .setTab(FurnitureModTab.TAB_BUILDING_BLOCKS)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(TABLES);

        UPGRADED_FENCES = SimpleEntrySet.builder("upgraded_fence",
                        ModBlocks.UPGRADED_FENCE_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new UpgradedFenceBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.FENCES, Registry.BLOCK_REGISTRY)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Blocks.UPGRADED_FENCES, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.OUTDOORS, Registry.ITEM_REGISTRY)
                .addTag(ModTags.Items.UPGRADED_FENCES, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(UPGRADED_FENCES);

        UPGRADED_GATES = SimpleEntrySet.builder("upgraded_gate",
                        ModBlocks.UPGRADED_GATE_OAK, ()-> WoodType.OAK_WOOD_TYPE,
                        w -> new UpgradedGateBlock(BlockBehaviour.Properties.copy(w.planks)))
                .addTag(BlockTags.FENCE_GATES, Registry.BLOCK_REGISTRY)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(BlockTags.UNSTABLE_BOTTOM_CENTER, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Blocks.UPGRADED_FENCE_GATES, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.OUTDOORS, Registry.ITEM_REGISTRY)
                .addTag(ModTags.Items.UPGRADED_FENCE_GATES, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(UPGRADED_GATES);

        HEDGES = SimpleEntrySet.builder("hedge",
                        ModBlocks.HEDGE_OAK, ()-> LeavesType.OAK_LEAVES_TYPE,
                        w -> new HedgeBlock(BlockBehaviour.Properties.copy(w.leaves)))
                .addTag(ModTags.Blocks.HEDGES, Registry.BLOCK_REGISTRY)
                .addTag(ModTags.Items.HEDGES, Registry.ITEM_REGISTRY)
                .addTag(ModTags.Items.OUTDOORS, Registry.ITEM_REGISTRY)
                .setTab(FurnitureMod.GROUP)
                .defaultRecipe()
                .setRenderType(()->RenderType::cutout)
                .build();

        this.addEntry(HEDGES);
    }

    @Override
    public void addTranslations(ClientDynamicResourcesHandler clientDynamicResourcesHandler, AfterLanguageLoadEvent lang) {
        HEDGES.items.forEach((w, v) -> LangBuilder.addDynamicEntry(lang, "block.everycomp.cfm_hedge",(BlockType) w, v));
        STRIPPED_BEDSIDE_CABINETS.items.forEach((w, v) -> LangBuilder.addDynamicEntry(lang, "block.everycomp.stripped_bedside_cabinet",(BlockType) w, v));
        STRIPPED_BENCHES.items.forEach((w, v) -> LangBuilder.addDynamicEntry(lang, "block.everycomp.stripped_park_bench",(BlockType) w, v));
        STRIPPED_BLINDS.items.forEach((w, v) -> LangBuilder.addDynamicEntry(lang, "block.everycomp.stripped_blinds",(BlockType) w, v));
        STRIPPED_CABINETS.items.forEach((w, v) -> LangBuilder.addDynamicEntry(lang, "block.everycomp.stripped_cabinet",(BlockType) w, v));
        STRIPPED_CHAIRS.items.forEach((w, v) -> LangBuilder.addDynamicEntry(lang, "block.everycomp.stripped_chair",(BlockType) w, v));
        STRIPPED_COFFEE_TABLES.items.forEach((w, v) -> LangBuilder.addDynamicEntry(lang, "block.everycomp.stripped_coffee_table",(BlockType) w, v));
        STRIPPED_CRATES.items.forEach((w, v) -> LangBuilder.addDynamicEntry(lang, "block.everycomp.stripped_crates",(BlockType) w, v));
        STRIPPED_DESKS.items.forEach((w, v) -> LangBuilder.addDynamicEntry(lang, "block.everycomp.stripped_desk",(BlockType) w, v));
        STRIPPED_DESK_CABINETS.items.forEach((w, v) -> LangBuilder.addDynamicEntry(lang, "block.everycomp.stripped_desk_cabinet",(BlockType) w, v));
        STRIPPED_KITCHEN_COUNTERS.items.forEach((w, v) -> LangBuilder.addDynamicEntry(lang, "block.everycomp.stripped_kitchen_counter",(BlockType) w, v));
        STRIPPED_KITCHEN_DRAWERS.items.forEach((w, v) -> LangBuilder.addDynamicEntry(lang, "block.everycomp.stripped_kitchen_drawer",(BlockType) w, v));
        STRIPPED_KITCHEN_SINK_DARK.items.forEach((w, v) -> LangBuilder.addDynamicEntry(lang, "block.everycomp.stripped_kitchen_sink_dark",(BlockType) w, v));
        STRIPPED_KITCHEN_SINK_LIGHT.items.forEach((w, v) -> LangBuilder.addDynamicEntry(lang, "block.everycomp.stripped_kitchen_sink_light",(BlockType) w, v));
        STRIPPED_MAIL_BOXES.items.forEach((w, v) -> LangBuilder.addDynamicEntry(lang, "block.everycomp.stripped_mail_box",(BlockType) w, v));
        STRIPPED_TABLES.items.forEach((w, v) -> LangBuilder.addDynamicEntry(lang, "block.everycomp.stripped_table",(BlockType) w, v));
        super.addTranslations(clientDynamicResourcesHandler, lang);
    }

    public static BlockEntityType<?> COMPAT_SINK;

    @Override
    public void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer((BlockEntityType<KitchenSinkBlockEntity>) (KITCHEN_SINK_DARK.getTileHolder().tile),
                KitchenSinkBlockEntityRenderer::new);
    }

    @Override
    public void registerTiles(IForgeRegistry<BlockEntityType<?>> registry) {
        List<Block> blocks = new ArrayList<>();
        blocks.addAll(KITCHEN_SINK_LIGHT.blocks.values());
        blocks.addAll(KITCHEN_SINK_DARK.blocks.values());
        blocks.addAll(STRIPPED_KITCHEN_SINK_DARK.blocks.values());
        blocks.addAll(STRIPPED_KITCHEN_SINK_LIGHT.blocks.values());

        COMPAT_SINK = KITCHEN_SINK_DARK.getTileHolder().createInstance(blocks.toArray(Block[]::new));
        registry.register(COMPAT_SINK.setRegistryName(WoodGood.res(this.shortenedId() + "_sink")));
    }

    @Override
    public void registerColors(ColorHandlerEvent.Item event) {
        List<Block> blocks = new ArrayList<>();
        blocks.addAll(HEDGES.blocks.values());
        blocks.forEach((l) -> {
            ItemColors colors = event.getItemColors();
            ItemStack leafStack = new ItemStack(l.asItem());
            colors.register((stack, tintIndex) -> colors.getColor(leafStack, tintIndex));
        });
    }

    @SubscribeEvent
    public void registerBlockColors(ColorHandlerEvent.Item event) {
        WoodGood.forAllModules(m -> m.registerColors(event));
    }

    class CompatKitchenSinkBlockEntity extends KitchenSinkBlockEntity {

        public CompatKitchenSinkBlockEntity(BlockPos pos, BlockState state) {
            super(pos, state);
        }

        @Override
        public BlockEntityType<?> getType() {
            return KITCHEN_SINK_DARK.getTileHolder().tile;
        }
    }

    private class CompatKitchenSinkBlock extends KitchenSinkBlock {
        private boolean bigSink;
        public CompatKitchenSinkBlock(Properties properties, boolean bigSink) {
            super(properties, bigSink);
            this.bigSink = bigSink;
        }

        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new CompatKitchenSinkBlockEntity(pos, state);
        }
    }

//    class CompatKitchenSinkLightBlockEntity extends KitchenSinkBlockEntity {
//
//        public CompatKitchenSinkLightBlockEntity(BlockPos pos, BlockState state) {
//            super(pos, state);
//        }
//
//        @Override
//        public BlockEntityType<?> getType() {
//            return KITCHEN_SINK_LIGHT.getTileHolder().tile;
//        }
//    }
//
//    private class CompatKitchenSinkLightBlock extends KitchenSinkBlock {
//        private boolean bigSink;
//        public CompatKitchenSinkLightBlock(Properties properties, boolean bigSink) {
//            super(properties, bigSink);
//            this.bigSink = bigSink;
//        }
//
//        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
//            return new CompatKitchenSinkLightBlockEntity(pos, state);
//        }
//    }
}
