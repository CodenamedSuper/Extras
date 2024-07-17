package codenamed.extras;

import codenamed.extras.registry.ExtrasBlocks;
import codenamed.extras.registry.ExtrasItemGroup;
import codenamed.extras.registry.ExtrasItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Extras implements ModInitializer {
	public  static  final  String MOD_ID = "extras";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");


		ExtrasBlocks.registerModBlocks();
		ExtrasItems.registerModItems();
		ExtrasItemGroup.registerItemGroups();
	}

	//Hello
}