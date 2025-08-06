package eu.xaru.openmodularturrets.api.tileentity;

import net.minecraft.world.item.ItemStack;

/**
 * Interface for turret base tile entities.
 * Ported from the original omtteam.openmodularturrets.api.tileentity.ITurretBase
 */
public interface ITurretBase {
    
    /**
     * Returns whether this base is concealed.
     *
     * @return true if concealed.
     */
    boolean isConcealed();

    /**
     * Sets the concealed state of this base.
     *
     * @param concealed the concealed state.
     */
    void setConcealed(boolean concealed);

    /**
     * Gets the currently installed turret head.
     *
     * @return the turret head tile entity, or null if none.
     */
    ITurretHead getTurretHead();

    /**
     * Sets the turret head for this base.
     *
     * @param turretHead the turret head to set.
     */
    void setTurretHead(ITurretHead turretHead);

    /**
     * Gets the energy stored in this base.
     *
     * @return the stored energy.
     */
    int getStoredEnergy();

    /**
     * Gets the maximum energy capacity of this base.
     *
     * @return the max energy capacity.
     */
    int getMaxEnergy();

    /**
     * Tries to extract energy from this base.
     *
     * @param amount the amount to extract.
     * @param simulate whether to simulate the extraction.
     * @return the amount actually extracted.
     */
    int extractEnergy(int amount, boolean simulate);

    /**
     * Tries to insert energy into this base.
     *
     * @param amount the amount to insert.
     * @param simulate whether to simulate the insertion.
     * @return the amount actually inserted.
     */
    int insertEnergy(int amount, boolean simulate);

    /**
     * Gets the upgrade in the specified slot.
     *
     * @param slot the upgrade slot.
     * @return the upgrade item stack.
     */
    ItemStack getUpgrade(int slot);

    /**
     * Sets an upgrade in the specified slot.
     *
     * @param slot the upgrade slot.
     * @param upgrade the upgrade item stack.
     */
    void setUpgrade(int slot, ItemStack upgrade);

    /**
     * Gets the number of upgrade slots.
     *
     * @return the number of upgrade slots.
     */
    int getUpgradeSlots();

    /**
     * Gets the ammo from the turret base.
     *
     * @return the ammo item stack.
     */
    ItemStack getAmmo();

    /**
     * Sets the ammo in the turret base.
     *
     * @param ammo the ammo item stack.
     */
    void setAmmo(ItemStack ammo);

    /**
     * Gets the owner UUID of this turret base.
     *
     * @return the owner UUID string.
     */
    String getOwnerUUID();

    /**
     * Sets the owner UUID of this turret base.
     *
     * @param ownerUUID the owner UUID string.
     */
    void setOwnerUUID(String ownerUUID);
}
