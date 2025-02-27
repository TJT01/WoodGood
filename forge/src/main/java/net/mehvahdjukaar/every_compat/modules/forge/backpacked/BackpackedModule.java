package net.mehvahdjukaar.every_compat.modules.forge.backpacked;

import com.mrcrayfish.backpacked.Backpacked;
import com.mrcrayfish.backpacked.block.ShelfBlock;
import com.mrcrayfish.backpacked.client.renderer.entity.layers.ShelfRenderer;
import com.mrcrayfish.backpacked.core.ModBlockEntities;
import com.mrcrayfish.backpacked.core.ModBlocks;
import com.mrcrayfish.backpacked.tileentity.ShelfBlockEntity;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.api.SimpleModule;
import net.mehvahdjukaar.moonlight.api.platform.ClientPlatformHelper;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodTypeRegistry;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;

//credit to WenXin2
public class BackpackedModule extends SimpleModule {

    public final SimpleEntrySet<WoodType, Block> shelves;

    public BackpackedModule(String modId) {
        super(modId, "bp");

        shelves = SimpleEntrySet.builder(WoodType.class, "backpack_shelf",
                        ModBlocks.OAK_BACKPACK_SHELF, () -> WoodTypeRegistry.OAK_TYPE,
                ifHasChild(w -> new ShelfBlock(Utils.copyPropertySafe(w.log)), "stripped_log"))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .setTab(() -> Backpacked.TAB)
                .addRecipe(modRes("oak_backpack_shelf"))
                .addTile(ModBlockEntities.SHELF)
                .setRenderType(() -> RenderType::cutout)
                .build();

        this.addEntry(shelves);
    }
}
