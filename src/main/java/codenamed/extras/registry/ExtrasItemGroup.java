package codenamed.extras.registry;

import codenamed.extras.Extras;
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

public class ExtrasItemGroup {

    public static ItemGroup FLAVORED_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of(Extras.MOD_ID, "extras_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.extras_group"))
                    .icon(() -> new ItemStack(Items.SADDLE)).entries((displayContext, entries) -> {


                        entries.add(ExtrasBlocks.PLANT_POT);
                        entries.add(ExtrasBlocks.RACK);
                        entries.add(ExtrasBlocks.JAR);
                        entries.add(ExtrasItems.TOTEM_OF_REGENERATION);













                    }).build());

    public static void registerItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {

        });
    }
}
