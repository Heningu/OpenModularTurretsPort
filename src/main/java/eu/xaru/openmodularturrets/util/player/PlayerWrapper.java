package eu.xaru.openmodularturrets.util.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

/**
 * Wrapper class for player information in multiplayer.
 * Ported from omtteam.omlib.util.player.Player for modern Minecraft/Forge.
 */
public class PlayerWrapper {
    private final UUID uuid;
    private final String name;
    private String teamName = "";

    public PlayerWrapper(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.teamName = getTeamNameFromServer();
    }

    public PlayerWrapper(UUID uuid, String name, String teamName) {
        this.uuid = uuid;
        this.name = name;
        this.teamName = teamName;
    }

    public PlayerWrapper(Player player) {
        this.uuid = player.getUUID();
        this.name = player.getName().getString();
        PlayerTeam team = player.getTeam();
        if (team != null) {
            this.teamName = team.getName();
        }
    }

    /**
     * Writes a PlayerWrapper to a network buffer.
     * @param player The player to write
     * @param buf The buffer to write to
     */
    public static void writeToByteBuf(PlayerWrapper player, FriendlyByteBuf buf) {
        buf.writeUtf(player.name);
        buf.writeUUID(player.uuid);
        buf.writeUtf(player.teamName);
    }

    /**
     * Reads a PlayerWrapper from a network buffer.
     * @param buf The buffer to read from
     * @return The player wrapper
     */
    public static PlayerWrapper readFromByteBuf(FriendlyByteBuf buf) {
        String name = buf.readUtf();
        UUID uuid = buf.readUUID();
        String teamName = buf.readUtf();
        return new PlayerWrapper(uuid, name, teamName);
    }

    /**
     * Reads a PlayerWrapper from NBT data.
     * @param tag The NBT tag to read from
     * @return The player wrapper
     */
    public static PlayerWrapper readFromNBT(CompoundTag tag) {
        return new PlayerWrapper(
            tag.getUUID("uuid"),
            tag.getString("name"),
            tag.getString("team_name")
        );
    }

    /**
     * Writes this PlayerWrapper to NBT data.
     * @param tag The NBT tag to write to
     * @return The modified NBT tag
     */
    public CompoundTag writeToNBT(CompoundTag tag) {
        tag.putString("name", this.name);
        tag.putUUID("uuid", this.uuid);
        if (!this.teamName.isEmpty()) {
            tag.putString("team_name", this.teamName);
        }
        return tag;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * Gets the actual EntityPlayer from the server if online.
     * @return The player entity, or null if offline
     */
    @Nullable
    public ServerPlayer getEntityPlayer() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            return server.getPlayerList().getPlayer(this.uuid);
        }
        return null;
    }

    /**
     * Checks if this player is currently online.
     * @return True if the player is online
     */
    public boolean isOnline() {
        return getEntityPlayer() != null;
    }

    /**
     * Gets the team name from the server for this player.
     * @return The team name, or empty string if no team
     */
    private String getTeamNameFromServer() {
        ServerPlayer player = getEntityPlayer();
        if (player != null && player.getTeam() != null) {
            return player.getTeam().getName();
        }
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerWrapper that = (PlayerWrapper) o;
        // In modern Minecraft, we should always use UUID for comparison
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name.toLowerCase());
    }

    @Override
    public String toString() {
        return "PlayerWrapper{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
