package jp.mikumiku.pig0.init;

import jp.mikumiku.pig0.Pig0Mod;
import jp.mikumiku.pig0.entity.HealingSnowball;
import jp.mikumiku.pig0.entity.Pig0Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Pig0Mod.MODID);

    public static final RegistryObject<EntityType<Pig0Entity>> PIG0 = ENTITIES.register("pig0",
            () -> EntityType.Builder.of(Pig0Entity::new, MobCategory.CREATURE)
                    .sized(0.45F, 0.45F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(Pig0Mod.MODID, "pig0").toString()));

    public static final RegistryObject<EntityType<HealingSnowball>> HEALING_SNOWBALL = ENTITIES.register("healing_snowball",
            () -> EntityType.Builder.<HealingSnowball>of(HealingSnowball::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(new ResourceLocation(Pig0Mod.MODID, "healing_snowball").toString()));
}
