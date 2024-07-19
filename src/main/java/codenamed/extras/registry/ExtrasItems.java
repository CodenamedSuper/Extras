package codenamed.extras.registry;
import codenamed.extras.Extras;
import codenamed.extras.item.custom.TotemOfRegenerationItem;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ExtrasItems {

    public static final Item TOTEM_OF_REGENERATION = registerItem("totem_of_regeneration",
            new TotemOfRegenerationItem(new Item.Settings().rarity(Rarity.UNCOMMON)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Extras.MOD_ID, name), item);

    }


    public static void registerModItems() {
        Extras.LOGGER.info("Registering Mod Items for " + Extras.MOD_ID);


    }
}
