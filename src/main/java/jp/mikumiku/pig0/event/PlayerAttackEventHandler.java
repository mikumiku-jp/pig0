package jp.mikumiku.pig0.event;

import jp.mikumiku.pig0.Pig0Mod;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Pig0Mod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerAttackEventHandler {

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            if (!player.level().isClientSide()) {
                FoodData foodData = player.getFoodData();
                foodData.setFoodLevel(20);
                foodData.setSaturation(20.0F);
            }
        }
    }
}
