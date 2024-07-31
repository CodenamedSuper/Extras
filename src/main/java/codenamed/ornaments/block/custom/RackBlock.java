package codenamed.ornaments.block.custom;

import codenamed.ornaments.block.entity.RackBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class RackBlock  extends  BlockWithEntity{
    public static final MapCodec<RackBlock> CODEC = createCodec(RackBlock::new);
    private static final int MAX_BOTTLE_COUNT = 6;
    public static final int BOTTLE_HEIGHT = 3;
    public static final List<BooleanProperty> SLOT_OCCUPIED_PROPERTIES;

    public MapCodec<RackBlock> getCodec() {
        return CODEC;
    }

    public RackBlock(AbstractBlock.Settings settings) {
        super(settings);
        BlockState blockState = (BlockState)((BlockState)this.stateManager.getDefaultState()).with(HorizontalFacingBlock.FACING, Direction.NORTH);

        BooleanProperty booleanProperty;
        for(Iterator var3 = SLOT_OCCUPIED_PROPERTIES.iterator(); var3.hasNext(); blockState = (BlockState)blockState.with(booleanProperty, false)) {
            booleanProperty = (BooleanProperty)var3.next();
        }

        this.setDefaultState(blockState);
    }

    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity var9 = world.getBlockEntity(pos);
        if (var9 instanceof RackBlockEntity rackBlockEntity) {
            if (!stack.isOf(Items.GLASS_BOTTLE)) {
                return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            } else {
                OptionalInt optionalInt = this.getSlotForHitPos(hit, state);
                if (optionalInt.isEmpty()) {
                    return ItemActionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
                } else if ((Boolean)state.get((Property)SLOT_OCCUPIED_PROPERTIES.get(optionalInt.getAsInt()))) {

                    return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                } else {

                    tryAddBottle(world, pos, player, rackBlockEntity, stack, optionalInt.getAsInt());
                    return ItemActionResult.success(world.isClient);
                }
            }
        } else {
            return ItemActionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }
    }

    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        BlockEntity var7 = world.getBlockEntity(pos);
        if (var7 instanceof RackBlockEntity rackBlockEntity) {
            OptionalInt optionalInt = this.getSlotForHitPos(hit, state);
            if (optionalInt.isEmpty()) {
                return ActionResult.PASS;
            } else if (!(Boolean)state.get((Property)SLOT_OCCUPIED_PROPERTIES.get(optionalInt.getAsInt()))) {
                return ActionResult.CONSUME;
            } else {
                tryRemoveBottle(world, pos, player, rackBlockEntity, optionalInt.getAsInt());
                return ActionResult.success(world.isClient);
            }
        } else {
            return ActionResult.PASS;
        }
    }

    private OptionalInt getSlotForHitPos(BlockHitResult hit, BlockState state) {
        return (OptionalInt)getHitPos(hit, (Direction)state.get(HorizontalFacingBlock.FACING)).map((hitPos) -> {
            int i = hitPos.y >= 0.5F ? 0 : 1;
            int j = getColumn(hitPos.x);
            return OptionalInt.of(j + i * 3);
        }).orElseGet(OptionalInt::empty);
    }

    private static Optional<Vec2f> getHitPos(BlockHitResult hit, Direction facing) {
        Direction direction = hit.getSide();
        if (facing != direction) {
            return Optional.empty();
        } else {
            BlockPos blockPos = hit.getBlockPos().offset(direction);
            Vec3d vec3d = hit.getPos().subtract((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ());
            double d = vec3d.getX();
            double e = vec3d.getY();
            double f = vec3d.getZ();
            Optional var10000;
            switch (direction) {
                case NORTH:
                    var10000 = Optional.of(new Vec2f((float)(1.0 - d), (float)e));
                    break;
                case SOUTH:
                    var10000 = Optional.of(new Vec2f((float)d, (float)e));
                    break;
                case WEST:
                    var10000 = Optional.of(new Vec2f((float)f, (float)e));
                    break;
                case EAST:
                    var10000 = Optional.of(new Vec2f((float)(1.0 - f), (float)e));
                    break;
                case DOWN:
                case UP:
                    var10000 = Optional.empty();
                    break;
                default:
                    throw new MatchException((String)null, (Throwable)null);
            }

            return var10000;
        }
    }

    private static int getColumn(float x) {
        float f = 0.0625F;
        float g = 0.375F;
        if (x < 0.375F) {
            return 0;
        } else {
            float h = 0.6875F;
            return x < 0.6875F ? 1 : 2;
        }
    }

    private static void tryAddBottle(World world, BlockPos pos, PlayerEntity player, RackBlockEntity blockEntity, ItemStack stack, int slot) {

            player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
            SoundEvent soundEvent = SoundEvents.BLOCK_CHISELED_BOOKSHELF_INSERT;
            blockEntity.setStack(slot, stack.splitUnlessCreative(1, player));
            world.playSound((PlayerEntity)null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);


    }

    private static void tryRemoveBottle(World world, BlockPos pos, PlayerEntity player, RackBlockEntity blockEntity, int slot) {
        if (!world.isClient) {
            ItemStack itemStack = blockEntity.removeStack(slot, 1);
            SoundEvent soundEvent = SoundEvents.BLOCK_CHISELED_BOOKSHELF_PICKUP;
            world.playSound((PlayerEntity)null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!player.getInventory().insertStack(itemStack)) {
                player.dropItem(itemStack, false);
            }

            world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        }
    }

    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RackBlockEntity(pos, state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{HorizontalFacingBlock.FACING});
        List var10000 = SLOT_OCCUPIED_PROPERTIES;
        Objects.requireNonNull(builder);
        var10000.forEach((property) -> {
            builder.add(new Property[]{(Property) property});
        });
    }

    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof RackBlockEntity) {
                RackBlockEntity rackBlockEntity = (RackBlockEntity)blockEntity;
                if (!rackBlockEntity.isEmpty()) {
                    for(int i = 0; i < 6; ++i) {
                        ItemStack itemStack = rackBlockEntity.getStack(i);
                        if (!itemStack.isEmpty()) {
                            ItemScatterer.spawn(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), itemStack);
                        }
                    }

                    rackBlockEntity.clear();
                    world.updateComparators(pos, this);
                }
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(HorizontalFacingBlock.FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(HorizontalFacingBlock.FACING, rotation.rotate((Direction)state.get(HorizontalFacingBlock.FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.get(HorizontalFacingBlock.FACING)));
    }

    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        if (world.isClient()) {
            return 0;
        } else {
            BlockEntity var5 = world.getBlockEntity(pos);
            if (var5 instanceof RackBlockEntity) {
                RackBlockEntity rackBlockEntity = (RackBlockEntity)var5;
                return rackBlockEntity.getLastInteractedSlot() + 1;
            } else {
                return 0;
            }
        }
    }

    static {
        SLOT_OCCUPIED_PROPERTIES = List.of(net.minecraft.state.property.Properties.SLOT_0_OCCUPIED, net.minecraft.state.property.Properties.SLOT_1_OCCUPIED, net.minecraft.state.property.Properties.SLOT_2_OCCUPIED, net.minecraft.state.property.Properties.SLOT_3_OCCUPIED, net.minecraft.state.property.Properties.SLOT_4_OCCUPIED, Properties.SLOT_5_OCCUPIED);
    }
}
