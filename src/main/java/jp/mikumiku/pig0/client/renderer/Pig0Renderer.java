package jp.mikumiku.pig0.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import jp.mikumiku.pig0.entity.Pig0Entity;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class Pig0Renderer extends MobRenderer<Pig0Entity, PigModel<Pig0Entity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/entity/pig/pig.png");

    public Pig0Renderer(EntityRendererProvider.Context context) {
        super(context, new PigModel<>(context.bakeLayer(ModelLayers.PIG)), 0.35F);
    }

    @Override
    protected void scale(Pig0Entity entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(0.5F, 0.5F, 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(Pig0Entity entity) {
        return TEXTURE;
    }
}
