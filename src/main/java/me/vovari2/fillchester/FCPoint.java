package me.vovari2.fillchester;

import org.bukkit.Location;
import org.bukkit.World;

public class FCPoint {
    private final World world;
    private final int x;
    private final int y;
    private final int z;

    private FCPoint(World world, int x, int y, int z){
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static FCPoint at(World world, int x, int y, int z){
        return new FCPoint(world, x, y, z);
    }
    public static FCPoint at(FCPoint point){
        return new FCPoint(point.world, point.x, point.y, point.z);
    }
    public static FCPoint adapt(Location loc){
        return new FCPoint(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public boolean equals(FCPoint point){
        return this.world == point.world && this.x == point.x && this.y == point.y && this.z == point.z;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getZ() {
        return z;
    }
    public World getWorld(){
        return world;
    }

    public FCPoint add(int x, int y, int z){
        return new FCPoint(world, this.x + x, this.y + y, this.z + z);
    }
    public FCPoint subtract(int x, int y, int z){
        return new FCPoint(world, this.x - x, this.y - y, this.z - z);
    }
}
