package jp.mikumiku.pig0.client.model;

import jp.mikumiku.pig0.Pig0Mod;
import jp.mikumiku.pig0.entity.Pig0Entity;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;

public class Pig0Model extends PigModel<Pig0Entity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Pig0Mod.MODID, "pig0"), "main");

    public Pig0Model(ModelPart root) {
        super(root);
    }
}
