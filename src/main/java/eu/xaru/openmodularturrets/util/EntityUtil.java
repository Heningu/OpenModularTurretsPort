package eu.xaru.openmodularturrets.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Utility class for entity-related operations.
 * Ported from omtteam.omlib.util.EntityUtil for modern Minecraft/Forge.
 */
public class EntityUtil {
    
    /**
     * Finds an entity class by its registry ID.
     * Updated for modern entity registration system.
     * 
     * @param id The entity registry ID (e.g., "minecraft:zombie")
     * @return The entity class, or null if not found
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends Entity> findClassById(String id) {
        var entityType = ForgeRegistries.ENTITY_TYPES.getValue(ResourceLocation.tryParse(id));
        if (entityType != null) {
            // This is a workaround since EntityType doesn't directly expose the entity class
            // In modern Minecraft, you would typically use the EntityType directly
            return (Class<? extends Entity>) entityType.getBaseClass();
        }
        return null;
    }

    /**
     * Calculates the total armor value of a living entity.
     * Updated for modern armor and equipment system.
     * 
     * @param entity The entity to check
     * @return The total armor value
     */
    public static int getEntityArmor(LivingEntity entity) {
        int armor = 0;
        
        // Check all armor slots
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.HAND) {
                continue; // Skip hand slots, only check armor
            }
            if (slot == EquipmentSlot.HEAD || slot == EquipmentSlot.CHEST || 
                slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET) {
                ItemStack itemStack = entity.getItemBySlot(slot);
                if (!itemStack.isEmpty() && itemStack.getItem() instanceof ArmorItem armorItem) {
                    armor += armorItem.getDefense();
                }
            }
        }
        
        return armor;
    }

    /**
     * Gets the total health of a living entity.
     * 
     * @param entity The entity to check
     * @return The entity's health
     */
    public static float getEntityHealth(LivingEntity entity) {
        return entity.getHealth();
    }

    /**
     * Gets the maximum health of a living entity.
     * 
     * @param entity The entity to check
     * @return The entity's maximum health
     */
    public static float getEntityMaxHealth(LivingEntity entity) {
        return entity.getMaxHealth();
    }

    /**
     * Checks if an entity is considered hostile.
     * 
     * @param entity The entity to check
     * @return True if the entity is hostile
     */
    public static boolean isHostile(LivingEntity entity) {
        return entity.getType().getCategory() == net.minecraft.world.entity.MobCategory.MONSTER;
    }

    /**
     * Checks if an entity is considered passive.
     * 
     * @param entity The entity to check
     * @return True if the entity is passive
     */
    public static boolean isPassive(LivingEntity entity) {
        var category = entity.getType().getCategory();
        return category == net.minecraft.world.entity.MobCategory.CREATURE ||
               category == net.minecraft.world.entity.MobCategory.AMBIENT ||
               category == net.minecraft.world.entity.MobCategory.WATER_CREATURE ||
               category == net.minecraft.world.entity.MobCategory.WATER_AMBIENT;
    }

    /**
     * Checks if an entity is a player.
     * 
     * @param entity The entity to check
     * @return True if the entity is a player
     */
    public static boolean isPlayer(Entity entity) {
        return entity instanceof net.minecraft.world.entity.player.Player;
    }

    /**
     * Gets the distance between two entities.
     * 
     * @param entity1 First entity
     * @param entity2 Second entity
     * @return The distance between them
     */
    public static double getDistance(Entity entity1, Entity entity2) {
        return entity1.distanceTo(entity2);
    }

    /**
     * Checks if an entity is within a certain range of another entity.
     * 
     * @param entity1 First entity
     * @param entity2 Second entity
     * @param range The maximum range
     * @return True if within range
     */
    public static boolean isWithinRange(Entity entity1, Entity entity2, double range) {
        return getDistance(entity1, entity2) <= range;
    }
}
