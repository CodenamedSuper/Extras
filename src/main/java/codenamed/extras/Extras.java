package codenamed.extras;

import codenamed.extras.registry.ExtrasBlocks;
import codenamed.extras.registry.ExtrasItemGroup;
import codenamed.extras.registry.ExtrasItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Extras implements ModInitializer {
	public  static  final  String MOD_ID = "extras";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static void registerModelPredicateProviders() {
		// For versions before 1.21, replace ''Identifier.ofVanilla'' replace ''new Identifier''.
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