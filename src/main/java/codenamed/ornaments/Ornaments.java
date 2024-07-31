package codenamed.ornaments;

import codenamed.ornaments.registry.ExtrasBlocks;
import codenamed.ornaments.registry.ExtrasItemGroup;
import codenamed.ornaments.registry.ExtrasItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ornaments implements ModInitializer {
	public  static  final  String MOD_ID = "ornaments";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static void registerModelPredicateProviders() {

		ModelPredicateProviderRegistry.register(ExtrasItems.TOTEM_OF_REGENERATION, Identifier.ofVanilla("custom_model_data"), (itemStack, clientWorld, livingEntity, var) -> 2);

	}
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		registerModelPredicateProviders();


		ExtrasBlocks.registerModBlocks();
		ExtrasItems.registerModItems();
		ExtrasItemGroup.registerItemGroups();
	}

	//Hello
}