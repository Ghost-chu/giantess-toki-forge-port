
package net.mcreator.gts.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.minecraft.world.entity.ai.attributes.Attributes;

import net.mcreator.gts.GtsAttributes;
import net.mcreator.gts.entity.model.TamedTokiModel;
import net.mcreator.gts.entity.layer.TamedTokiLayer;
import net.mcreator.gts.entity.TamedTokiEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class TamedTokiRenderer extends GeoEntityRenderer<TamedTokiEntity> {
	public TamedTokiRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new TamedTokiModel());
		this.shadowRadius = 0.5f;
		this.addRenderLayer(new TamedTokiLayer(this));
	}

	@Override
	public RenderType getRenderType(TamedTokiEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, TamedTokiEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		float scale = (float) entity.getAttribute(GtsAttributes.SCALE.get()).getBaseValue();
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

		protected float getShadowRadius(TamedTokiEntity entity) {
        return ((float) entity.getAttribute(GtsAttributes.SCALE.get()).getBaseValue()) - 1.0f;
    }
}

