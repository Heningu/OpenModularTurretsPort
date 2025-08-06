package eu.xaru.openmodularturrets.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Mathematical utility functions ported from OMLib.
 * Updated for Minecraft 1.21.1 API.
 */
@SuppressWarnings({"unused"})
public class MathUtil {
    
    /**
     * Truncates a double value to an integer.
     * @param number The double to truncate
     * @return The truncated integer value
     */
    public static int truncateDoubleToInt(double number) {
        return Mth.floor(number);
    }

    /**
     * Rotates an AABB (bounding box) based on the given direction.
     * Updated for modern Direction enum.
     * @param box The bounding box to rotate
     * @param facing The direction to rotate towards
     * @return The rotated bounding box
     */
    @ParametersAreNonnullByDefault
    public static AABB rotateAABB(AABB box, Direction facing) {
        return switch (facing) {
            case NORTH -> new AABB(box.minX, box.minY, box.maxZ, box.maxX, box.maxY, box.minZ);
            case EAST -> new AABB(box.minZ, box.minY, box.minX, box.maxZ, box.maxY, box.maxX);
            case SOUTH -> box;
            case WEST -> new AABB(box.minZ, box.minY, box.maxX, box.maxZ, box.maxY, box.minX);
            case UP -> new AABB(box.minY, box.minZ, box.minX, box.maxY, box.maxZ, box.maxX);
            case DOWN -> new AABB(box.maxY, box.minZ, box.minX, box.minY, box.maxZ, box.maxX);
        };
    }
    
    /**
     * Creates an AABB around a BlockPos with the specified range.
     * @param pos The center position
     * @param range The range in blocks
     * @return The bounding box
     */
    public static AABB getAABBAroundBlockPos(BlockPos pos, int range) {
        return getAABBAroundPos(Vec3.atCenterOf(pos), range);
    }

    /**
     * Creates an AABB around an Entity with the specified range.
     * @param entity The center entity
     * @param range The range in blocks
     * @return The bounding box
     */
    public static AABB getAABBAroundEntity(Entity entity, int range) {
        return getAABBAroundPos(entity.position(), range);
    }

    /**
     * Creates an AABB around a Vec3 position with the specified range.
     * @param pos The center position
     * @param range The range in blocks
     * @return The bounding box
     */
    public static AABB getAABBAroundPos(Vec3 pos, int range) {
        return new AABB(
            pos.x - range, pos.y - range, pos.z - range,
            pos.x + range, pos.y + range, pos.z + range
        );
    }

    /**
     * Calculates the distance between two positions.
     * @param pos1 First position
     * @param pos2 Second position
     * @return The distance between the positions
     */
    public static double getDistance(Vec3 pos1, Vec3 pos2) {
        return pos1.distanceTo(pos2);
    }

    /**
     * Calculates the distance between two block positions.
     * @param pos1 First block position
     * @param pos2 Second block position
     * @return The distance between the positions
     */
    public static double getDistance(BlockPos pos1, BlockPos pos2) {
        return Math.sqrt(pos1.distSqr(pos2));
    }

    /**
     * Clamps a value between min and max.
     * @param value The value to clamp
     * @param min The minimum value
     * @param max The maximum value
     * @return The clamped value
     */
    public static double clamp(double value, double min, double max) {
        return Mth.clamp(value, min, max);
    }

    /**
     * Clamps an integer value between min and max.
     * @param value The value to clamp
     * @param min The minimum value
     * @param max The maximum value
     * @return The clamped value
     */
    public static int clamp(int value, int min, int max) {
        return Mth.clamp(value, min, max);
    }

    /**
     * Converts degrees to radians.
     * @param degrees The angle in degrees
     * @return The angle in radians
     */
    public static double toRadians(double degrees) {
        return Math.toRadians(degrees);
    }

    /**
     * Converts radians to degrees.
     * @param radians The angle in radians
     * @return The angle in degrees
     */
    public static double toDegrees(double radians) {
        return Math.toDegrees(radians);
    }
}
