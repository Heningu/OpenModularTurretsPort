package eu.xaru.openmodularturrets.util.player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Utility class for player-related operations.
 * Ported from omtteam.omlib.util.player.PlayerUtil for modern Minecraft/Forge.
 */
public class PlayerUtil {

    // Cache for username -> UUID mapping
    private static final Map<String, UUID> USERNAME_UUID_CACHE = new HashMap<>();
    private static final Map<UUID, String> UUID_USERNAME_CACHE = new HashMap<>();

    /**
     * Checks if a player has operator permissions.
     * @param player The player to check
     * @return True if the player is an operator
     */
    public static boolean isPlayerOP(@Nullable Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            return serverPlayer.hasPermissions(4);
        }
        return false;
    }

    /**
     * Checks if a player has operator permissions.
     * @param playerWrapper The player wrapper to check
     * @return True if the player is an operator
     */
    public static boolean isPlayerOP(@Nullable PlayerWrapper playerWrapper) {
        if (playerWrapper != null) {
            ServerPlayer entityPlayer = playerWrapper.getEntityPlayer();
            return isPlayerOP(entityPlayer);
        }
        return false;
    }

    /**
     * Gets a player's UUID from their username.
     * Uses server cache for accuracy.
     * @param username The username to look up
     * @return The UUID, or null if not found
     */
    @Nullable
    public static UUID getPlayerUUID(String username) {
        // Check our cache first
        if (USERNAME_UUID_CACHE.containsKey(username.toLowerCase())) {
            return USERNAME_UUID_CACHE.get(username.toLowerCase());
        }

        // Check server player list
        var server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            var playerList = server.getPlayerList();
            for (ServerPlayer player : playerList.getPlayers()) {
                if (player.getName().getString().equalsIgnoreCase(username)) {
                    UUID uuid = player.getUUID();
                    // Cache the result
                    USERNAME_UUID_CACHE.put(username.toLowerCase(), uuid);
                    UUID_USERNAME_CACHE.put(uuid, player.getName().getString());
                    return uuid;
                }
            }

            // Check user cache
            var profileCache = server.getProfileCache();
            if (profileCache != null) {
                var profile = profileCache.get(username);
                if (profile.isPresent()) {
                    UUID uuid = profile.get().getId();
                    // Cache the result
                    USERNAME_UUID_CACHE.put(username.toLowerCase(), uuid);
                    UUID_USERNAME_CACHE.put(uuid, profile.get().getName());
                    return uuid;
                }
            }
        }

        return null;
    }

    /**
     * Attempts to parse a string as either a UUID or username.
     * @param possibleUUID The string that might be a UUID or username
     * @return The UUID, or null if not found
     */
    @Nullable
    public static UUID getPlayerUIDUnstable(String possibleUUID) {
        if (possibleUUID == null || possibleUUID.isEmpty()) {
            return null;
        }

        UUID uuid;
        try {
            // Try parsing as UUID first
            uuid = UUID.fromString(possibleUUID);
        } catch (IllegalArgumentException e) {
            // If that fails, try looking up by username
            uuid = getPlayerUUID(possibleUUID);
        }
        return uuid;
    }

    /**
     * Gets a player's username from their UUID.
     * @param uuidString The UUID as a string
     * @return The username, or null if not found
     */
    @Nullable
    public static String getPlayerNameFromUUID(String uuidString) {
        if (uuidString == null || uuidString.isEmpty()) {
            return null;
        }

        try {
            UUID uuid = UUID.fromString(uuidString);
            return getPlayerNameFromUUID(uuid);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Gets a player's username from their UUID.
     * @param uuid The UUID
     * @return The username, or null if not found
     */
    @Nullable
    public static String getPlayerNameFromUUID(UUID uuid) {
        if (uuid == null) {
            return null;
        }

        // Check our cache first
        if (UUID_USERNAME_CACHE.containsKey(uuid)) {
            return UUID_USERNAME_CACHE.get(uuid);
        }

        // Check server player list
        var server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            var playerList = server.getPlayerList();
            for (ServerPlayer player : playerList.getPlayers()) {
                if (player.getUUID().equals(uuid)) {
                    String name = player.getName().getString();
                    // Cache the result
                    UUID_USERNAME_CACHE.put(uuid, name);
                    USERNAME_UUID_CACHE.put(name.toLowerCase(), uuid);
                    return name;
                }
            }

            // Check user cache
            var profileCache = server.getProfileCache();
            if (profileCache != null) {
                var profile = profileCache.get(uuid);
                if (profile.isPresent()) {
                    String name = profile.get().getName();
                    // Cache the result
                    UUID_USERNAME_CACHE.put(uuid, name);
                    USERNAME_UUID_CACHE.put(name.toLowerCase(), uuid);
                    return name;
                }
            }
        }

        return null;
    }

    /**
     * Creates a PlayerWrapper from a Minecraft Player entity.
     * @param player The player entity
     * @return The player wrapper
     */
    public static PlayerWrapper wrapPlayer(Player player) {
        return new PlayerWrapper(player);
    }

    /**
     * Creates a PlayerWrapper from UUID and name.
     * @param uuid The player UUID
     * @param name The player name
     * @return The player wrapper
     */
    public static PlayerWrapper wrapPlayer(UUID uuid, String name) {
        return new PlayerWrapper(uuid, name);
    }

    /**
     * Clears the player cache (useful for testing or memory management).
     */
    public static void clearCache() {
        USERNAME_UUID_CACHE.clear();
        UUID_USERNAME_CACHE.clear();
    }

    /**
     * Gets an online player by UUID.
     * @param uuid The player's UUID
     * @return The ServerPlayer, or null if not online
     */
    @Nullable
    public static ServerPlayer getOnlinePlayer(UUID uuid) {
        var server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            return server.getPlayerList().getPlayer(uuid);
        }
        return null;
    }

    /**
     * Gets an online player by username.
     * @param username The player's username
     * @return The ServerPlayer, or null if not online
     */
    @Nullable
    public static ServerPlayer getOnlinePlayer(String username) {
        var server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                if (player.getName().getString().equalsIgnoreCase(username)) {
                    return player;
                }
            }
        }
        return null;
    }
}
