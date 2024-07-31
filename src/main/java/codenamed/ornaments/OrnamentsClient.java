package codenamed.ornaments;

import codenamed.ornaments.block.entity.renderer.JarBlockEntityRenderer;
import codenamed.ornaments.registry.OrnamentsBlockEntityType;
import codenamed.ornaments.registry.OrnamentsBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class OrnamentsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        BlockRenderLayerMap.INSTANCE.putBlock(OrnamentsBlocks.JAR, RenderLayer.getCutout());


        BlockEntityRendererFactories.register(OrnamentsBlockEntityType.JAR, JarBlockEntityRenderer::new);

    }
}
