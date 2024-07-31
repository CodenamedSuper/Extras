package codenamed.ornaments.registry;


import codenamed.ornaments.block.custom.JarBlock;
import codenamed.ornaments.block.custom.PlantPotBlock;
import codenamed.ornaments.block.custom.RackBlock;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;


public class ExtrasBlocks {


    public static final Block PLANT_POT = registerBlock("plant_pot",
            new PlantPotBlock(AbstractBlock.Settings.copy(Blocks.DECORATED_POT).strength(1.5F, 3.0F).sounds(BlockSoundGroup.STONE).nonOpaque().requiresTool()));


    public static final Block RACK = registerBlock("rack",
            new RackBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));

    public static final Block JAR = registerBlock("jar",
            new JarBlock(AbstractBlock.Settings.copy(Blocks.GLASS).nonOpaque()));

    private static Item registerPlaceableBlockItem(String name, Block block) {
        Item item = Registry.register(Registries.ITEM, Identifier.of(codenamed.ornaments.Ornaments.MOD_ID, name),
                new BlockItem(block, new Item.Settings().maxCount(1)));
        return item;
    }

    private static Item registerBlockItem(String name, Block block) {
        Item item = Registry.register(Registries.ITEM, Identifier.of(codenamed.ornaments.Ornaments.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
        return item;
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(codenamed.ornaments.Ornaments.MOD_ID, name), block);
    }

    private static Block registerBlockWithoutItem(String name, Block block) {

        return Registry.register(Registries.BLOCK, Identifier.of(codenamed.ornaments.Ornaments.MOD_ID, name), block);
    }

    public static void registerModBlocks() {
        codenamed.ornaments.Ornaments.LOGGER.info("Registering ModBlocks for " + codenamed.ornaments.Ornaments.MOD_ID);
    }
}
