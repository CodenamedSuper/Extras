package codenamed.extras;

import codenamed.extras.block.entity.JarBlockEntity;
import codenamed.extras.block.entity.renderer.JarBlockEntityRenderer;
import codenamed.extras.registry.ExtrasBlockEntityType;
import codenamed.extras.registry.ExtrasBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class ExtrasClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        BlockRenderLayerMap.INSTANCE.putBlock(ExtrasBlocks.JAR, RenderLayer.getCutout());


        BlockEntityRendererFactories.register(ExtrasBlockEntityType.JAR, JarBlockEntityRenderer::new);

    }
}
