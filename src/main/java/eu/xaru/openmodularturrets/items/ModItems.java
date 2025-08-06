package eu.xaru.openmodularturrets.items;

import eu.xaru.openmodularturrets.OpenModularTurrets;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = 
            DeferredRegister.create(ForgeRegistries.ITEMS, OpenModularTurrets.MODID);
    
    public static final RegistryObject<Item> TURRET_BASE = ITEMS.register("turret_base",
            () -> new Item(new Item.Properties()));
            
    public static final RegistryObject<Item> TURRET_BARREL = ITEMS.register("turret_barrel",
            () -> new Item(new Item.Properties()));
    
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
