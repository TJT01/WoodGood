package net.mehvahdjukaar.every_compat.api;

import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Pair;
import net.mehvahdjukaar.every_compat.EveryCompat;
import net.mehvahdjukaar.every_compat.configs.WoodConfigs;
import net.mehvahdjukaar.every_compat.misc.ResourcesUtils;
import net.mehvahdjukaar.moonlight.api.events.AfterLanguageLoadEvent;
import net.mehvahdjukaar.moonlight.api.item.BlockTypeBasedBlockItem;
import net.mehvahdjukaar.moonlight.api.misc.Registrator;
import net.mehvahdjukaar.moonlight.api.platform.ClientPlatformHelper;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.mehvahdjukaar.moonlight.api.resources.BlockTypeResTransformer;
import net.mehvahdjukaar.moonlight.api.resources.RPUtils;
import net.mehvahdjukaar.moonlight.api.resources.ResType;
import net.mehvahdjukaar.moonlight.api.resources.SimpleTagBuilder;
import net.mehvahdjukaar.moonlight.api.resources.assets.LangBuilder;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynClientResourcesProvider;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynamicDataPack;
import net.mehvahdjukaar.moonlight.api.resources.textures.Palette;
import net.mehvahdjukaar.moonlight.api.resources.textures.Respriter;
import net.mehvahdjukaar.moonlight.api.resources.textures.TextureImage;
import net.mehvahdjukaar.moonlight.api.set.BlockSetAPI;
import net.mehvahdjukaar.moonlight.api.set.BlockType;
import net.mehvahdjukaar.moonlight.api.set.leaves.LeavesType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//contrary to popular belief this class is indeed not simple. Its usage however is
public class SimpleEntrySet<T extends BlockType, B extends Block> extends EntrySet<T, B> {

    private final Class<T> type;

    private final Pattern nameScheme;

    protected final Supplier<T> baseType;
    protected final Supplier<@Nullable B> baseBlock;

    public final String postfix;
    @Nullable
    public final String prefix;

    protected final Function<T, @Nullable B> blockFactory;
    @Nullable
    protected final TriFunction<T, B, Item.Properties, @Nullable Item> itemFactory;
    @Nullable
    protected final SimpleEntrySet.ITileHolder<?> tileHolder;

    protected final Supplier<CreativeModeTab> tab;
    protected final boolean copyLoot;
    protected final Map<ResourceLocation, Set<ResourceKey<?>>> tags = new HashMap<>();
    protected final Set<Supplier<ResourceLocation>> recipeLocations = new HashSet<>();
    protected final Set<Pair<ResourceLocation, @Nullable ResourceLocation>> textures = new HashSet<>();
    @Nullable
    protected final BiFunction<T, ResourceManager, Pair<List<Palette>, @Nullable AnimationMetadataSection>> paletteSupplier;
    @Nullable
    protected final Supplier<Supplier<RenderType>> renderType;
    @Nullable
    protected final Consumer<BlockTypeResTransformer<T>> extraTransform;

    private final Supplier<Boolean> disabled;


    public SimpleEntrySet(Class<T> type,
                          String name, @Nullable String prefix,
                          Function<T, B> blockSupplier,
                          Supplier<@Nullable B> baseBlock,
                          Supplier<T> baseType,
                          Supplier<CreativeModeTab> tab,
                          boolean copyLoot,
                          @Nullable TriFunction<T, B, Item.Properties, Item> itemFactory,
                          @Nullable SimpleEntrySet.ITileHolder<?> tileFactory,
                          @Nullable Supplier<Supplier<RenderType>> renderType,
                          @Nullable BiFunction<T, ResourceManager, Pair<List<Palette>, @Nullable AnimationMetadataSection>> paletteSupplier,
                          @Nullable Consumer<BlockTypeResTransformer<T>> extraTransform) {
        super((prefix == null ? "" : prefix + (name.isEmpty() ? "" : "_")) + name);
        this.postfix = name;
        this.blockFactory = blockSupplier;
        this.prefix = prefix;
        this.tileHolder = tileFactory;
        this.tab = tab;
        this.copyLoot = copyLoot;
        this.baseBlock = baseBlock;
        this.baseType = baseType;
        this.itemFactory = itemFactory;
        this.type = type;

        this.extraTransform = extraTransform;
        this.renderType = renderType;
        this.paletteSupplier = paletteSupplier;

        if (this.prefix != null) {
            if (postfix.isEmpty()) {
                nameScheme = Pattern.compile("^" + prefix + "_(.+?)$");
            } else {
                nameScheme = Pattern.compile("^" + prefix + "_(.+?)_" + postfix + "$");
            }
        } else {
            nameScheme = Pattern.compile("^(.+?)_" + postfix + "$");
        }

        disabled = Suppliers.memoize(() -> this.getBaseBlock() == null);
    }

    public ITileHolder<?> getTileHolder() {
        return tileHolder;
    }

    public Class<T> getTypeClass() {
        return type;
    }

    public B getBaseBlock() {
        return baseBlock.get();
    }

    public T getBaseType() {
        return baseType.get();
    }

    public String getEquivalentBlock(CompatModule module, String oldName, String woodFrom) {
        String wood = parseWoodType(oldName);
        if (wood != null) {
            var w = BlockSetAPI.getBlockSet(this.getTypeClass()).get(new ResourceLocation(woodFrom, wood));
            if (w != null) {
                return module.shortenedId() + "/" + w.getNamespace() + "/" + oldName;
            }
        }
        return null;
    }

    //gets the wood type of the given name if it is in this entry set name format
    @Nullable
    public String parseWoodType(String oldName) {
        Matcher m = nameScheme.matcher(oldName);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    public void addTranslations(CompatModule module, AfterLanguageLoadEvent lang) {
        blocks.forEach((w, v) -> LangBuilder.addDynamicEntry(lang, "block_type." + module.getModId() + "." + typeName, w, v));
    }

    public void registerWoodBlocks(CompatModule module, Registrator<Block> registry, Collection<WoodType> woodTypes) {
        if (WoodType.class == getTypeClass()) {
            registerBlocks(module, registry, (Collection<T>) woodTypes);
        }
    }

    public void registerLeavesBlocks(CompatModule module, Registrator<Block> registry, Collection<LeavesType> leavesTypes) {
        if (LeavesType.class == getTypeClass()) {
            registerBlocks(module, registry, (Collection<T>) leavesTypes);
        }
    }

    @Override
    public void registerBlocks(CompatModule module, Registrator<Block> registry, Collection<T> woodTypes) {
        if (disabled.get()) return;
        Block base = getBaseBlock();
        if (base == null || base == Blocks.AIR)
            throw new UnsupportedOperationException("Base block cant be null ("+this.typeName+ " for " +module.modId+" module)");
        baseType.get().addChild(getChildKey(module), (Object) base);

        for (T w : woodTypes) {
            String name = getBlockName(w);
            String fullName = module.shortenedId() + "/" + w.getNamespace() + "/" + name;
            if (w.isVanilla() || module.isEntryAlreadyRegistered(name, w, Registry.BLOCK)) continue;

            B block = blockFactory.apply(w);
            //for blocks that fail
            if (block != null) {
                this.blocks.put(w, block);

                registry.register(EveryCompat.res(fullName), block);
                w.addChild(getChildKey(module), (Object) block);
            }
        }
    }

    @NotNull
    public String getBlockName(T w) {
        String name;
        if (prefix != null) {
            name = this.prefix + "_" + w.getTypeName();
            if (!this.postfix.isEmpty()) name += "_" + this.postfix;
        } else {
            name = w.getTypeName() + "_" + this.postfix;
        }
        return name;
    }

    protected CreativeModeTab getTab(T w, B b) {
        return WoodConfigs.isEntryEnabled(w, b) ?
                (EveryCompat.MOD_TAB != null ? EveryCompat.MOD_TAB : this.tab.get()) : null;
    }

    @Override
    public void registerItems(CompatModule module, Registrator<Item> registry) {
        blocks.forEach((w, value) -> {
            Item i;
            CreativeModeTab tab = getTab(w, value);

            if (itemFactory != null) {
                i = itemFactory.apply(w, value, new Item.Properties().tab(tab));
            } else {
                i = new BlockTypeBasedBlockItem<>(value, new Item.Properties().tab(tab), w);
            }
            //for ones that don't have item
            if (i != null) {
                this.items.put(w, i);
                registry.register(Utils.getID(value), i);
            }
        });
    }

    @Override
    public void registerTiles(CompatModule module, Registrator<BlockEntityType<?>> registry) {
        if (disabled.get()) return;
        if (tileHolder instanceof NewTileHolder<?> nt) {
            var tile = nt.createInstance(blocks.values().toArray(Block[]::new));
            registry.register(EveryCompat.res(module.shortenedId() + "_" + this.getName()), tile);
        }
    }

    @Override
    public void registerEntityRenderers(CompatModule simpleModule, ClientPlatformHelper.BlockEntityRendererEvent event) {
        if (this.tileHolder != null) {
            //this.tileHolder.registerRenderer(event);
        }
    }

    @Override
    public void setupExistingTiles() {
        if (disabled.get()) return;
        if (tileHolder instanceof ExistingTileHolder<?> et) {
            SimpleModule.appendTileEntityBlocks(et.get(), blocks.values());
        }
    }

    @Override
    public void setRenderLayer() {
        if (disabled.get()) return;
        if (renderType != null) {
            blocks.values().forEach(t -> ClientPlatformHelper.registerRenderType(t, renderType.get().get()));
        }
    }

    @Override
    public void generateTags(CompatModule module, DynamicDataPack pack, ResourceManager manager) {
        if (disabled.get()) return;
        if (!tags.isEmpty()) {
            for (var tb : tags.entrySet()) {
                SimpleTagBuilder builder = SimpleTagBuilder.of(tb.getKey());
                for (var b : blocks.entrySet()) {
                    if (WoodConfigs.isEntryEnabled(b.getKey(), b.getValue())) {
                        builder.addEntry(b.getValue());
                    }
                }
                for (var t : tb.getValue()) {
                    pack.addTag(builder, t);
                }
            }
        }
    }

    @Override
    public void generateLootTables(CompatModule module, DynamicDataPack pack, ResourceManager manager) {
        if (disabled.get()) return;
        if (copyLoot) {
            ResourceLocation reg = Utils.getID(getBaseBlock());
            ResourcesUtils.addBlockResources(module.getModId(), manager, pack, blocks, baseType.get().getTypeName(),
                    ResType.BLOCK_LOOT_TABLES.getPath(reg));

        } else {
            //drop self
            blocks.forEach((wood, value) -> pack.addSimpleBlockLootTable(value));
        }
    }

    @Override
    public void generateRecipes(CompatModule module, DynamicDataPack pack, ResourceManager manager) {
        if (disabled.get()) return;
        this.recipeLocations.forEach(r -> {
            var res = r.get();
            try {
                ResourcesUtils.addBlocksRecipes(manager, pack, items, res, baseType.get());
            } catch (Exception e) {
                EveryCompat.LOGGER.error("Failed to generate recipes for template at location {} ", res);
            }
        });
    }

    @Override
    public void generateModels(CompatModule module, DynClientResourcesProvider handler, ResourceManager manager) {
        if (disabled.get()) return;
        ResourcesUtils.addStandardResources(module.getModId(), manager, handler, blocks, baseType.get(), extraTransform);
    }

    @Override
    public void generateTextures(CompatModule module, DynClientResourcesProvider handler, ResourceManager manager) {
        if (disabled.get()) return;
        if (textures.isEmpty()) return;

        List<TextureImage> images = new ArrayList<>();
        try {
            Map<ResourceLocation, Respriter> respriters = new HashMap<>();
            for (var p : textures) {
                ResourceLocation textureId = p.getFirst();
                try {
                    TextureImage main = TextureImage.open(manager, textureId);
                    images.add(main);
                    ResourceLocation m = p.getSecond();
                    Respriter r;
                    if (m != null) {
                        TextureImage mask = TextureImage.open(manager, m);
                        images.add(main);
                        r = Respriter.masked(main, mask);
                    } else {
                        r = Respriter.of(main);
                    }
                    respriters.put(textureId, r);
                } catch (Exception e) {
                    EveryCompat.LOGGER.error("Failed to read block texture at: {}", p, e);
                }
            }

            for (var entry : blocks.entrySet()) {
                B b = entry.getValue();
                T w = entry.getKey();
                //skips disabled ones

                if (!WoodConfigs.isEntryEnabled(w, b)) continue;

                ResourceLocation blockId = Utils.getID(b);

                List<Palette> targetPalette = null;
                AnimationMetadataSection animation = null;
                if (paletteSupplier != null) {
                    var pal = paletteSupplier.apply(w, manager);
                    animation = pal.getSecond();
                    targetPalette = pal.getFirst();
                } else {
                    var m = w.mainChild();
                    Block mainBlock = null;
                    if (m instanceof Block bb) mainBlock = bb;
                    else if (m instanceof BlockItem bii) mainBlock = bii.getBlock();
                    if (mainBlock == null) {
                        throw new UnsupportedOperationException("You need to provide a palette supplier for non wood type based blocks");
                    }

                    try (TextureImage plankTexture = TextureImage.open(manager,
                            RPUtils.findFirstBlockTextureLocation(manager, mainBlock))) {
                        targetPalette = Palette.fromAnimatedImage(plankTexture);
                        animation = plankTexture.getMetadata();
                    } catch (Exception ignored) {
                    }
                }
                if (targetPalette == null) {
                    EveryCompat.LOGGER.error("Could not get texture palette for block {} : ", b);
                    continue;
                }
                AnimationMetadataSection finalAnimation = animation;
                List<Palette> finalTargetPalette = targetPalette;

                //sanity check to verity that palette isnt changed. can be removed
                int oldSize = finalTargetPalette.get(0).size();

                for (var re : respriters.entrySet()) {
                    if (oldSize != finalTargetPalette.get(0).size()) {
                        throw new RuntimeException("This should not happen");
                    }
                    String oldPath = re.getKey().getPath();

                    String newId = BlockTypeResTransformer.replaceTypeNoNamespace(oldPath, w, blockId, baseType.get().getTypeName());

                    Respriter respriter = re.getValue();
                    if (type == WoodType.class) {
                        module.addWoodTexture((WoodType) w, handler, manager, newId, () ->
                                respriter.recolorWithAnimation(finalTargetPalette, finalAnimation));

                    } else {
                        handler.addTextureIfNotPresent(manager, newId, () ->
                                respriter.recolorWithAnimation(finalTargetPalette, finalAnimation));

                    }
                }
            }

        } catch (Exception e) {
            EveryCompat.LOGGER.error("Could not generate any block texture for entry set {} : ", module.modRes(this.getName()), e);
        } finally {
            for (var t : images) {
                t.close();
            }
        }

    }

    //ok...
    public static <T extends BlockType, B extends Block> Builder<T, B> builder(Class<T> type,
                                                                               String name, Supplier<B> baseBlock, Supplier<T> baseType, Function<T, B> blockSupplier) {

        return new Builder<>(type, name, null, baseType, baseBlock, blockSupplier);
    }

    public static <T extends BlockType, B extends Block> Builder<T, B> builder(Class<T> type,
                                                                               String name, String prefix, Supplier<B> baseBlock, Supplier<T> baseType, Function<T, B> blockSupplier) {

        return new Builder<>(type, name, prefix, baseType, baseBlock, blockSupplier);
    }

    public static class Builder<T extends BlockType, B extends Block> {
        protected final Class<T> type;
        protected final Supplier<T> baseType;
        protected final Supplier<@Nullable B> baseBlock;
        protected final String name;
        @Nullable
        protected final String prefix;
        protected Supplier<CreativeModeTab> tab = () -> CreativeModeTab.TAB_DECORATIONS;
        protected boolean copyLoot = false;
        protected final Function<T, B> blockFactory;
        @Nullable
        protected TriFunction<T, B, Item.Properties, Item> itemFactory;
        @Nullable
        protected SimpleEntrySet.ITileHolder<?> tileHolder;
        @Nullable
        protected Supplier<Supplier<RenderType>> renderType = null;
        @Nullable
        protected BiFunction<T, ResourceManager, Pair<List<Palette>, @Nullable AnimationMetadataSection>> palette = null;
        protected final Map<ResourceLocation, Set<ResourceKey<?>>> tags = new HashMap<>();
        protected final Set<Supplier<ResourceLocation>> recipes = new HashSet<>();
        protected final Set<Pair<ResourceLocation, @Nullable ResourceLocation>> textures = new HashSet<>();
        protected Consumer<BlockTypeResTransformer<T>> extraModelTransform = null;

        protected Builder(Class<T> type, String name, @Nullable String prefix, Supplier<T> baseType, Supplier<B> baseBlock, Function<T, B> blockFactory) {
            this.baseType = baseType;
            this.baseBlock = baseBlock;
            this.name = name;
            this.prefix = prefix;
            this.blockFactory = blockFactory;
            this.type = type;
        }

        public SimpleEntrySet<T, B> build() {
            var e = new SimpleEntrySet<>(type, name, prefix, blockFactory, baseBlock, baseType, tab, copyLoot,
                    itemFactory, tileHolder, renderType, palette, extraModelTransform);
            e.recipeLocations.addAll(this.recipes);
            e.tags.putAll(this.tags);
            e.textures.addAll(textures);
            return e;
        }

        public <H extends BlockEntity> Builder<T, B> addTile(Supplier<BlockEntityType<H>> tile) {
            this.tileHolder = new ExistingTileHolder<>(tile);
            return this;
        }

        public <H extends BlockEntity> Builder<T, B> addTile(BiFunction<BlockPos, BlockState, H> tileFactory) {
            this.tileHolder = new NewTileHolder<>(tileFactory);
            return this;
        }

        public Builder<T, B> addCustomItem(TriFunction<T, B, Item.Properties, Item> itemFactory) {
            this.itemFactory = itemFactory;
            return this;
        }

        //adds an extra model transform
        public Builder<T, B> addModelTransform(Consumer<BlockTypeResTransformer<T>> transform) {
            this.extraModelTransform = transform;
            return this;
        }

        public Builder<T, B> noItem() {
            this.itemFactory = (a, b, c) -> null;
            return this;
        }

        public Builder<T, B> setTab(Supplier<CreativeModeTab> tab) {
            this.tab = tab;
            return this;
        }

        /**
         * As opposed to just dropping itself
         */
        public Builder<T, B> useLootFromBase() {
            this.copyLoot = true;
            return this;
        }

        public Builder<T, B> setRenderType(Supplier<Supplier<RenderType>> renderType) {
            this.renderType = renderType;
            return this;
        }

        public Builder<T, B> addTag(ResourceLocation location, ResourceKey<?> registry) {
            var s = this.tags.computeIfAbsent(location, b -> new HashSet<>());
            s.add(registry);
            return this;
        }

        public Builder<T, B> addTag(TagKey<?> tag, ResourceKey<?> registry) {
            addTag(tag.location(), registry);
            return this;
        }

        public Builder<T, B> defaultRecipe() {
            this.recipes.add(() -> Utils.getID(this.baseBlock.get()));
            return this;
        }

        public Builder<T, B> addRecipe(ResourceLocation resourceLocation) {
            this.recipes.add(() -> resourceLocation);
            return this;
        }

        public Builder<T, B> addTexture(ResourceLocation resourceLocation) {
            this.textures.add(Pair.of(resourceLocation, null));
            return this;
        }

        public Builder<T, B> addTextureM(ResourceLocation textureLocation, ResourceLocation maskLocation) {
            this.textures.add(Pair.of(textureLocation, maskLocation));
            return this;
        }

        //by default, they all use planks palette
        public Builder<T, B> setPalette(BiFunction<T, ResourceManager, Pair<List<Palette>, @Nullable AnimationMetadataSection>> paletteProvider) {
            this.palette = paletteProvider;
            return this;
        }

        //only works for oak type. Will fail if its used on leaves
        public Builder<T, B> createPaletteFromOak(Consumer<Palette> paletteTransform) {
            return this.setPalette((w, m) -> {
                try (TextureImage plankTexture = TextureImage.open(m,
                        RPUtils.findFirstBlockTextureLocation(m, ((WoodType) w).planks))) {

                    List<Palette> targetPalette = Palette.fromAnimatedImage(plankTexture);
                    targetPalette.forEach(paletteTransform);
                    return Pair.of(targetPalette, plankTexture.getMetadata());
                } catch (Exception e) {
                    throw new RuntimeException(String.format("Failed to generate palette for %s : %s", w, e));
                }
            });
        }
    }


    public interface ITileHolder<H extends BlockEntity> {

        BlockEntityType<? extends H> get();
    }

    public record ExistingTileHolder<H extends BlockEntity>(
            Supplier<BlockEntityType<H>> supplier) implements ITileHolder<H> {

        @Override
        public BlockEntityType<? extends H> get() {
            return supplier.get();
        }
    }

    public static class NewTileHolder<H extends BlockEntity> implements ITileHolder<H> {

        protected final BiFunction<BlockPos, BlockState, H> tileFactory;
        protected Supplier<BlockEntityRendererProvider<H>> renderer = null;
        public BlockEntityType<? extends H> tile = null;


        public NewTileHolder(BiFunction<BlockPos, BlockState, H> tileFactory) {
            this.tileFactory = tileFactory;
        }

        public BlockEntityType<? extends H> get() {
            return tile;
        }

        public BlockEntityType<? extends H> createInstance(Block... blocks) {
            if (tile != null) throw new UnsupportedOperationException("tile has already been created");
            this.tile = PlatformHelper.newBlockEntityType(tileFactory::apply, blocks);
            return tile;
        }
    }

}
