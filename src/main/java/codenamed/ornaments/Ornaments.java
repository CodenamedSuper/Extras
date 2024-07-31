package codenamed.ornaments;

import codenamed.ornaments.registry.OrnamentsBlocks;
import codenamed.ornaments.registry.OrnamentsItemGroup;
import codenamed.ornaments.registry.OrnamentsItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ornaments implements ModInitializer {
	public  static  final  String MOD_ID = "ornaments";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static void registerModelPredicateProviders() {

		ModelPredicateProviderRegistry.register(OrnamentsItems.TOTEM_OF_REGENERATION, Identifier.ofVanilla("custom_model_data"), (itemStack, clientWorld, livingEntity, var) -> 2);

	}
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		registerModelPredicateProviders();


		OrnamentsBlocks.registerModBlocks();
		OrnamentsItems.registerModItems();
		OrnamentsItemGroup.registerItemGroups();
	}

	//Hello
}