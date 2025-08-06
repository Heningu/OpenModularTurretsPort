package eu.xaru.openmodularturrets.util;

import net.minecraft.nbt.CompoundTag;

import java.util.Objects;

/**
 * Class for declaring targeting settings to the targeting system for turrets.
 * Ported from omtteam.omlib.util.TargetingSettings for modern Minecraft/Forge.
 */
public class TargetingSettings {
    private boolean targetPlayers;
    private boolean targetMobs;
    private boolean targetPassive;
    private int range;
    private int maxRange;

    private TargetingSettings() {
    }

    public TargetingSettings(boolean targetPlayers, boolean targetMobs, boolean targetPassive, int range,
                             int maxRange) {
        this.targetPlayers = targetPlayers;
        this.targetMobs = targetMobs;
        this.targetPassive = targetPassive;
        this.range = range;
        this.maxRange = maxRange;
    }

    /**
     * Creates a default targeting settings configuration.
     */
    public static TargetingSettings createDefault() {
        return new TargetingSettings(false, true, false, 16, 32);
    }

    /**
     * Reads targeting settings from NBT data.
     * @param nbtTagCompound The NBT compound to read from
     * @return The targeting settings
     */
    public static TargetingSettings readFromNBT(CompoundTag nbtTagCompound) {
        TargetingSettings settings = new TargetingSettings();
        if (nbtTagCompound.contains("targetingSettings")) {
            CompoundTag nbt = nbtTagCompound.getCompound("targetingSettings");
            settings.targetPlayers = nbt.getBoolean("targetPlayers");
            settings.targetMobs = nbt.getBoolean("targetMobs");
            settings.targetPassive = nbt.getBoolean("targetPassive");
            settings.range = nbt.getInt("range");
            settings.maxRange = nbt.getInt("maxRange");
        } else {
            // Legacy compatibility
            settings.targetPlayers = nbtTagCompound.getBoolean("attacksPlayers");
            settings.targetMobs = nbtTagCompound.getBoolean("attacksMobs");
            settings.targetPassive = nbtTagCompound.getBoolean("attacksNeutrals");
            settings.range = nbtTagCompound.getInt("currentMaxRange");
            settings.maxRange = nbtTagCompound.getInt("upperBoundMaxRange");
        }
        return settings;
    }

    /**
     * Writes targeting settings to NBT data.
     * @param nbtTagCompound The NBT compound to write to
     * @return The modified NBT compound
     */
    public CompoundTag writeToNBT(CompoundTag nbtTagCompound) {
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("targetPlayers", this.targetPlayers);
        nbt.putBoolean("targetMobs", this.targetMobs);
        nbt.putBoolean("targetPassive", this.targetPassive);
        nbt.putInt("range", this.range);
        nbt.putInt("maxRange", this.maxRange);
        nbtTagCompound.put("targetingSettings", nbt);
        return nbtTagCompound;
    }

    public boolean isTargetPlayers() {
        return targetPlayers;
    }

    public TargetingSettings setTargetPlayers(boolean targetPlayers) {
        this.targetPlayers = targetPlayers;
        return this;
    }

    public boolean isTargetMobs() {
        return targetMobs;
    }

    public TargetingSettings setTargetMobs(boolean targetMobs) {
        this.targetMobs = targetMobs;
        return this;
    }

    public boolean isTargetPassive() {
        return targetPassive;
    }

    public TargetingSettings setTargetPassive(boolean targetPassive) {
        this.targetPassive = targetPassive;
        return this;
    }

    public int getRange() {
        return range;
    }

    public TargetingSettings setRange(int range) {
        if (range <= maxRange && range > -1) {
            this.range = range;
        }
        return this;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public TargetingSettings setMaxRange(int maxRange) {
        if (maxRange >= this.range && maxRange > -1) {
            this.maxRange = maxRange;
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TargetingSettings that = (TargetingSettings) o;
        return targetPlayers == that.targetPlayers &&
                targetMobs == that.targetMobs &&
                targetPassive == that.targetPassive &&
                range == that.range &&
                maxRange == that.maxRange;
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetPlayers, targetMobs, targetPassive, range, maxRange);
    }

    @Override
    public String toString() {
        return "TargetingSettings{" +
                "targetPlayers=" + targetPlayers +
                ", targetMobs=" + targetMobs +
                ", targetPassive=" + targetPassive +
                ", range=" + range +
                ", maxRange=" + maxRange +
                '}';
    }
}
