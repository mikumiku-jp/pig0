package jp.mikumiku.pig0.event;

import jp.mikumiku.pig0.Pig0Mod;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Pig0Mod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerAttackEventHandler {

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
    }
}
