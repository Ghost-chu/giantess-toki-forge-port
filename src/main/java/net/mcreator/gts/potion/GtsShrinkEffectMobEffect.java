
package net.mcreator.gts.potion;

import net.mcreator.gts.GtsAttributes;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.gts.GtsMod;


public class GtsShrinkEffectMobEffect extends MobEffect {
	public GtsShrinkEffectMobEffect() {
		super(MobEffectCategory.HARMFUL, -1);
		this.addAttributeModifier(GtsAttributes.SCALE.get(), "4c7b2286-1e8c-4a41-8f86-30f80ff38f31", -0.5, AttributeModifier.Operation.MULTIPLY_TOTAL);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE, "75c49f76-8e47-4278-9e6a-4a76c3ac9f5f", -0.5, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}
}


