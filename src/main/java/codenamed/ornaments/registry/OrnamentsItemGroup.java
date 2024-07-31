package codenamed.ornaments.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class OrnamentsItemGroup {

    public static ItemGroup FLAVORED_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of(codenamed.ornaments.Ornaments.MOD_ID, "ornaments_group"),
            FabricItemGroup.builder().displayName(Text.translatable("Ornaments"))
                    .icon(() -> new ItemStack(Items.SADDLE)).entries((displayContext, entries) -> {


                        entries.add(OrnamentsBlocks.PLANT_POT);
                        entries.add(OrnamentsBlocks.RACK);
                        entries.add(OrnamentsBlocks.JAR);
                        entries.add(OrnamentsItems.TOTEM_OF_REGENERATION);













                    }).build());

    public static void registerItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {

        });
    }
}
