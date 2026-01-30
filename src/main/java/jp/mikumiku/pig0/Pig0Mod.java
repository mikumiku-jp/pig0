package jp.mikumiku.pig0;

import jp.mikumiku.pig0.client.renderer.Pig0Renderer;
import jp.mikumiku.pig0.entity.Pig0Entity;
import jp.mikumiku.pig0.init.ModEntities;
import jp.mikumiku.pig0.init.ModItems;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Pig0Mod.MODID)
public class Pig0Mod {
    public static final String MODID = "pig0";

    public Pig0Mod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEntities.ENTITIES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        modEventBus.addListener(this::registerAttributes);
        modEventBus.addListener(this::addCreative);
    }

    private void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.PIG0.get(), Pig0Entity.createAttributes().build());
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ModItems.PIG0_SPAWN_EGG);
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntities.PIG0.get(), Pig0Renderer::new);
            event.registerEntityRenderer(ModEntities.HEALING_SNOWBALL.get(), ThrownItemRenderer::new);
        }
    }
}
