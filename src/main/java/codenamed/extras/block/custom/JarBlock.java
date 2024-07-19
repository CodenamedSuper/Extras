package codenamed.extras.block.custom;

import codenamed.extras.Extras;
import codenamed.extras.block.entity.JarBlockEntity;
import codenamed.extras.block.entity.RackBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class JarBlock extends BlockWithEntity {

    public static final MapCodec<JarBlock> CODEC = createCodec(JarBlock::new);

    public JarBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.combineAndSimplify(Block.createCuboidShape(0, 0, 0, 16, 14, 16),
                Block.createCuboidShape(2, 14, 2, 14, 16, 14), BooleanBiFunction.OR);
    }


    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        Extras.LOGGER.info("1212");

        BlockEntity e = world.getBlockEntity(pos);

        if (e instanceof JarBlockEntity jarBlockEntity) {

            if (player.getStackInHand(hand) != ItemStack.EMPTY) {
                tryAddItem(jarBlockEntity, player, hand);
            }
            else if (jarBlockEntity.getCurrentSize() > 0){
                player.getInventory().insertStack(jarBlockEntity.getStack((jarBlockEntity).getCurrentSize()-1));
                jarBlockEntity.removeStack();
            }
            return ItemActionResult.SUCCESS;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    public void tryAddItem(JarBlockEntity entity, PlayerEntity player, Hand hand) {
        if (entity.getCurrentSize() < entity.size() && player.getStackInHand(hand) != ItemStack.EMPTY) {
            player.getStackInHand(hand).decrement(1);
            entity.setStack(entity.getCurrentSize(), player.getStackInHand(hand));
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new JarBlockEntity(pos, state);
    }


}
