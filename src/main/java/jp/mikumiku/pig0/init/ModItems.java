package jp.mikumiku.pig0.init;

import jp.mikumiku.pig0.Pig0Mod;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Pig0Mod.MODID);

    public static final RegistryObject<Item> PIG0_SPAWN_EGG = ITEMS.register("pig0_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.PIG0, 0x000000, 0xFFFFFF, new Item.Properties()));
}
