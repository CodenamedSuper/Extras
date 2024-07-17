package codenamed.extras.registry;


import codenamed.extras.Extras;
import codenamed.extras.block.custom.PlantPotBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;


public class ExtrasBlocks {


    public static final Block PLANT_POT = registerBlock("plant_pot",
            new PlantPotBlock(AbstractBlock.Settings.copy(Blocks.DECORATED_POT).strength(1.5F, 3.0F).sounds(BlockSoundGroup.STONE).nonOpaque().requiresTool()));





    private static Item registerPlaceableBlockItem(String name, Block block) {
        Item item = Registry.register(Registries.ITEM, Identifier.of(Extras.MOD_ID, name),
                new BlockItem(block, new Item.Settings().maxCount(1)));
        return item;
    }

    private static Item registerBlockItem(String name, Block block) {
        Item item = Registry.register(Registries.ITEM, Identifier.of(Extras.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
        return item;
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Extras.MOD_ID, name), block);
    }

    private static Block registerBlockWithoutItem(String name, Block block) {

        return Registry.register(Registries.BLOCK, Identifier.of(Extras.MOD_ID, name), block);
    }

    public static void registerModBlocks() {
        Extras.LOGGER.info("Registering ModBlocks for " + Extras.MOD_ID);
    }
}
