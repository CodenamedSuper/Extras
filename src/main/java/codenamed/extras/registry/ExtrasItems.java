package codenamed.extras.registry;
import codenamed.extras.Extras;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ExtrasItems {

    public static final Item TEMPLATE_ITEM = registerItem("template",
            new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Extras.MOD_ID, name), item);

    }


    public static void registerModItems() {
        Extras.LOGGER.info("Registering Mod Items for " + Extras.MOD_ID);


    }
}
