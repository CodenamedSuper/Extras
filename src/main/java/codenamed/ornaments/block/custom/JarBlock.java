package codenamed.ornaments.block.custom;

import codenamed.ornaments.Ornaments;
import codenamed.ornaments.block.entity.JarBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
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
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {

        BlockEntity e = world.getBlockEntity(pos);

        if (e instanceof JarBlockEntity jarBlockEntity) {

                ItemScatterer.spawn(world, pos, jarBlockEntity);

        }
        super.onBreak(world, pos, state, player);

        return state;
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        BlockEntity e = world.getBlockEntity(pos);

        if (e instanceof JarBlockEntity jarBlockEntity) {

            if (player.getStackInHand(hand) != ItemStack.EMPTY) {
                tryAddItem(jarBlockEntity, player, hand);
                return ItemActionResult.SUCCESS;

            }
            else if (jarBlockEntity.getCurrentSize() > 0){
                player.getInventory().insertStack(new ItemStack((jarBlockEntity.getStack().getItem()), 1));
                return ItemActionResult.SUCCESS;

            }
            return ItemActionResult.SUCCESS;
        }
        return  ItemActionResult.SUCCESS;
    }

    public void tryAddItem(JarBlockEntity entity, PlayerEntity player, Hand hand) {
        if (entity.getCurrentSize() < entity.size() && player.getStackInHand(hand) != ItemStack.EMPTY) {
            Ornaments.LOGGER.info(player.getStackInHand(hand).toString());
            entity.setStack(entity.getCurrentSize(), player.getStackInHand(hand).getItem());
            player.getStackInHand(hand).decrement(1);

        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new JarBlockEntity(pos, state);
    }


}
