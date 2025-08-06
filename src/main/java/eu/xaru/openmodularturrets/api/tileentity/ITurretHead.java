package eu.xaru.openmodularturrets.api.tileentity;

import eu.xaru.openmodularturrets.util.TargetingSettings;

/**
 * Interface for turret head tile entities.
 * Ported from the original omtteam.openmodularturrets.api.tileentity.ITurretHead
 */
public interface ITurretHead {
    /**
     * This should give back the TargetingSettings that this turret uses.
     *
     * @return the corresponding TargetingSettings.
     */
    TargetingSettings getTargetingSettings();

    /**
     * This should give back the Shooting Priorities that this turret uses.
     *
     * @return the corresponding Integer Array.
     */
    Integer[] getPriorities();

    /**
     * Which base does this turret belong to?
     *
     * @return the corresponding base.
     */
    ITurretBase getBase();

    /**
     * Is the turret on cooldown after activating?
     *
     * @return cooldown state.
     */
    boolean isOnCooldown();

    /**
     * Is the turret set to automatically fire?
     *
     * @return autofire state.
     */
    boolean getAutoFire();

    /**
     * Set turret set to automatically fire.
     *
     * @param autoFire the autofire state.
     */
    void setAutoFire(boolean autoFire);

    /**
     * How much power does the turret use per activation?
     *
     * @return the power usage per shot.
     */
    int getPowerUsage();

    /**
     * How much accuracy does this turret have? (higher = more accurate)
     *
     * @return the accuracy value.
     */
    float getAccuracy();

    /**
     * How much ammo does this turret use per shot?
     *
     * @return the ammo usage per shot.
     */
    int getAmmoUsage();

    /**
     * Get the tier of this turret (affects power usage, damage, etc.)
     *
     * @return the turret tier.
     */
    int getTurretTier();

    /**
     * Forces the turret to fire.
     */
    void forceFire();

    /**
     * Checks if the turret can currently fire.
     *
     * @return true if the turret can fire.
     */
    boolean canFire();

    /**
     * Gets the sound to play when this turret fires.
     *
     * @return the firing sound.
     */
    net.minecraft.sounds.SoundEvent getFiringSound();

    /**
     * Gets the current target of this turret.
     *
     * @return the target entity, or null if no target.
     */
    net.minecraft.world.entity.LivingEntity getCurrentTarget();
}
