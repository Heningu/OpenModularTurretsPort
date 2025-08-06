package eu.xaru.openmodularturrets.energy;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * Modern energy storage implementation for turrets.
 * Ported from omtteam.omlib.power.OMEnergyStorage with improvements.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class TurretEnergyStorage implements IEnergyStorage {
    protected int energy;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public TurretEnergyStorage(int capacity) {
        this(capacity, capacity, capacity);
    }

    public TurretEnergyStorage(int capacity, int maxTransfer) {
        this(capacity, maxTransfer, maxTransfer);
    }

    public TurretEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.energy = 0;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive())
            return 0;

        int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate && energyReceived > 0)
            energy += energyReceived;
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract())
            return 0;

        int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate && energyExtracted > 0)
            energy -= energyExtracted;
        return energyExtracted;
    }

    @Override
    public int getEnergyStored() {
        return energy;
    }

    /**
     * Sets the energy stored directly.
     * @param stored The amount of energy to store
     */
    public void setEnergyStored(int stored) {
        energy = stored > 0 ? Math.min(stored, capacity) : 0;
    }

    @Override
    public int getMaxEnergyStored() {
        return capacity;
    }

    /**
     * Gets the maximum energy that can be received per tick.
     * @return The max receive rate
     */
    public int getMaxReceive() {
        return maxReceive;
    }

    /**
     * Sets the maximum energy that can be received per tick.
     * @param max The max receive rate
     */
    public void setMaxReceive(int max) {
        maxReceive = Math.max(0, max);
    }

    /**
     * Gets the maximum energy that can be extracted per tick.
     * @return The max extract rate
     */
    public int getMaxExtract() {
        return maxExtract;
    }

    /**
     * Sets the maximum energy that can be extracted per tick.
     * @param max The max extract rate
     */
    public void setMaxExtract(int max) {
        maxExtract = Math.max(0, max);
    }

    /**
     * Sets the maximum capacity of this energy storage.
     * @param max The max capacity
     */
    public void setCapacity(int max) {
        capacity = Math.max(0, max);
        // Ensure current energy doesn't exceed new capacity
        if (energy > capacity) {
            energy = capacity;
        }
    }

    /**
     * Modifies the energy stored by a certain amount.
     * @param change The change in energy (positive = add, negative = subtract)
     */
    public void modifyEnergyStored(int change) {
        if (change > 0) {
            energy = Math.min(energy + change, capacity);
        } else if (change < 0) {
            energy = Math.max(energy + change, 0);
        }
    }

    /**
     * Checks if energy can be consumed (used for turret firing).
     * @param amount The amount to consume
     * @return True if there's enough energy
     */
    public boolean canConsume(int amount) {
        return energy >= amount;
    }

    /**
     * Consumes energy if available.
     * @param amount The amount to consume
     * @param simulate Whether to simulate the consumption
     * @return The amount actually consumed
     */
    public int consumeEnergy(int amount, boolean simulate) {
        int consumed = Math.min(energy, amount);
        if (!simulate && consumed > 0) {
            energy -= consumed;
        }
        return consumed;
    }

    /**
     * Gets the energy fill percentage.
     * @return The fill percentage (0.0 to 1.0)
     */
    public float getEnergyPercentage() {
        return capacity > 0 ? (float) energy / (float) capacity : 0.0f;
    }

    @Override
    public boolean canExtract() {
        return this.maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return this.maxReceive > 0;
    }

    /**
     * Saves this energy storage to NBT.
     * @param tag The NBT tag to write to
     * @return The modified NBT tag
     */
    public CompoundTag writeToNBT(CompoundTag tag) {
        tag.putInt("energy", energy);
        tag.putInt("capacity", capacity);
        tag.putInt("maxReceive", maxReceive);
        tag.putInt("maxExtract", maxExtract);
        return tag;
    }

    /**
     * Loads this energy storage from NBT.
     * @param tag The NBT tag to read from
     */
    public void readFromNBT(CompoundTag tag) {
        energy = tag.getInt("energy");
        capacity = tag.getInt("capacity");
        maxReceive = tag.getInt("maxReceive");
        maxExtract = tag.getInt("maxExtract");
        
        // Validate data
        energy = Math.max(0, Math.min(energy, capacity));
        capacity = Math.max(0, capacity);
        maxReceive = Math.max(0, maxReceive);
        maxExtract = Math.max(0, maxExtract);
    }

    /**
     * Creates a copy of this energy storage.
     * @return A copy with the same configuration
     */
    public TurretEnergyStorage copy() {
        TurretEnergyStorage copy = new TurretEnergyStorage(capacity, maxReceive, maxExtract);
        copy.energy = this.energy;
        return copy;
    }

    @Override
    public String toString() {
        return String.format("TurretEnergyStorage{energy=%d, capacity=%d, maxReceive=%d, maxExtract=%d}", 
                           energy, capacity, maxReceive, maxExtract);
    }
}
