package codenamed.extras.block.entity.renderer;

import codenamed.extras.block.entity.JarBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class JarBlockEntityRenderer implements BlockEntityRenderer<JarBlockEntity> {
    public JarBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }


    @Override
    public void render(JarBlockEntity entity, float tickDelta,
                       MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        int currentSize = entity.getCurrentSize();
        int maxSize = entity.size();


            for (int i = 0; i < currentSize; i++) {


                ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
                ItemStack stack = entity.getRenderStack(i);
                matrices.push();
                matrices.translate(0.5f, (float) i / 10, 0.5f);
                matrices.scale(0.8f, 1f, 0.8f);
                if (i % 2 == 0) {
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
                }
                else {
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));

                }
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(270));

                if (stack.isEmpty()) {
                    stack = Items.AIR.getDefaultStack();
                }

                itemRenderer.renderItem(stack, ModelTransformationMode.GUI, getLightLevel(entity.getWorld(), entity.getPos()),
                        OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);


                matrices.pop();
            }
    }


    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}