package dev.shoroa.mod.moonlight.features.registers;

import dev.shoroa.mod.moonlight.Moonlight;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.mixin.itemgroup.client.CreativeInventoryScreenMixin;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.javatuples.Tuple;

import java.util.ArrayList;

public class BlockRegister {
    public static final ArrayList<Triplet<String, String,Block>> blocks = new ArrayList<>();
    public static final ArrayList<Item> blockItems = new ArrayList<>();
    public static void register() {
        Moonlight.logger.info("Registering blocks! (" + Moonlight.MOD_ID + ")");
        Block twilight_embrace_planks;
        registerBlock("twilight_embrace_log", "",new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_LOG)));
        registerBlock("twilight_embrace_planks", "twilight_embrace_planks",twilight_embrace_planks = new Block(FabricBlockSettings.copy(Blocks.OAK_PLANKS)));
        registerBlock("twilight_embrace_stairs", "twilight_embrace_planks",new StairsBlock(twilight_embrace_planks.getDefaultState(), FabricBlockSettings.copy(Blocks.OAK_STAIRS)));
        registerBlock("twilight_embrace_slab", "twilight_embrace_planks",new SlabBlock(FabricBlockSettings.copy(Blocks.OAK_SLAB)));
    }

    private static Block registerBlock(String name, String textureName, Block block) {
        registerBlockItem(name, block);
        Block b = Registry.register(Registries.BLOCK, new Identifier(Moonlight.MOD_ID, name), block);
        blocks.add(new Triplet<>(name,textureName,b));
        return b;
    }
    private static Item registerBlockItem(String name, Block block) {
        Item item = Registry.register(Registries.ITEM, new Identifier(Moonlight.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
        blockItems.add(item);
        return item;
    }
}
