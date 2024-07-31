package codenamed.ornaments.block.entity;

import codenamed.ornaments.block.custom.RackBlock;
import com.mojang.logging.LogUtils;
import java.util.Objects;
import java.util.function.Predicate;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.GameEvent.Emitter;
import org.slf4j.Logger;

public class RackBlockEntity extends BlockEntity implements Inventory {
    public static final int MAX_BOOKS = 6;
    private static final Logger LOGGER = LogUtils.getLogger();
    private final DefaultedList<ItemStack> inventory;
    private int lastInteractedSlot;

    public RackBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityType.CHISELED_BOOKSHELF, pos, state);
        this.inventory = DefaultedList.ofSize(6, ItemStack.EMPTY);
        this.lastInteractedSlot = -1;
    }

    private void updateState(int interactedSlot) {
        if (interactedSlot >= 0 && interactedSlot < 6) {
            this.lastInteractedSlot = interactedSlot;
            BlockState blockState = this.getCachedState();

            for(int i = 0; i < RackBlock.SLOT_OCCUPIED_PROPERTIES.size(); ++i) {
                boolean bl = !this.getStack(i).isEmpty();
                BooleanProperty booleanProperty = (BooleanProperty)RackBlock.SLOT_OCCUPIED_PROPERTIES.get(i);
                blockState = (BlockState)blockState.with(booleanProperty, bl);
            }

            ((World)Objects.requireNonNull(this.world)).setBlockState(this.pos, blockState, 3);
            this.world.emitGameEvent(GameEvent.BLOCK_CHANGE, this.pos, Emitter.of(blockState));
        } else {
            LOGGER.error("Expected slot 0-5, got {}", interactedSlot);
        }
    }

    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.inventory.clear();
        Inventories.readNbt(nbt, this.inventory, registryLookup);
        this.lastInteractedSlot = nbt.getInt("last_interacted_slot");
    }

    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, this.inventory, true, registryLookup);
        nbt.putInt("last_interacted_slot", this.lastInteractedSlot);
    }

    public int getFilledSlotCount() {
        return (int)this.inventory.stream().filter(Predicate.not(ItemStack::isEmpty)).count();
    }

    public void clear() {
        this.inventory.clear();
    }

    public int size() {
        return 6;
    }

    public boolean isEmpty() {
        return this.inventory.stream().allMatch(ItemStack::isEmpty);
    }

    public ItemStack getStack(int slot) {
        return (ItemStack)this.inventory.get(slot);
    }

    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = (ItemStack)Objects.requireNonNullElse((ItemStack)this.inventory.get(slot), ItemStack.EMPTY);
        this.inventory.set(slot, ItemStack.EMPTY);
        if (!itemStack.isEmpty()) {
            this.updateState(slot);
        }

        return itemStack;
    }

    public ItemStack removeStack(int slot) {
        return this.removeStack(slot, 1);
    }

    public void setStack(int slot, ItemStack stack) {
        if (stack.isOf(Items.GLASS_BOTTLE)) {
            codenamed.ornaments.Ornaments.LOGGER.info("entity");
            this.inventory.set(slot, stack);
            this.updateState(slot);
        } else if (stack.isEmpty()) {
            this.removeStack(slot, 1);
        }

    }

    public boolean canTransferTo(Inventory hopperInventory, int slot, ItemStack stack) {
        return hopperInventory.containsAny((stack2) -> {
            if (stack2.isEmpty()) {
                return true;
            } else {
                return ItemStack.areItemsAndComponentsEqual(stack, stack2) && stack2.getCount() + stack.getCount() <= hopperInventory.getMaxCount(stack2);
            }
        });
    }

    public int getMaxCountPerStack() {
        return 1;
    }

    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }

    public boolean isValid(int slot, ItemStack stack) {
        return stack.isOf(Items.GLASS_BOTTLE) && this.getStack(slot).isEmpty() && stack.getCount() == this.getMaxCountPerStack();
    }

    public int getLastInteractedSlot() {
        return this.lastInteractedSlot;
    }

    protected void readComponents(BlockEntity.ComponentsAccess components) {
        super.readComponents(components);
        ((ContainerComponent)components.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)).copyTo(this.inventory);
    }

    protected void addComponents(ComponentMap.Builder componentMapBuilder) {
        super.addComponents(componentMapBuilder);
        componentMapBuilder.add(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(this.inventory));
    }

    public void removeFromCopiedStackNbt(NbtCompound nbt) {
        nbt.remove("Items");
    }
}