package me.vovari2.fillchester;

import org.bukkit.Location;
import org.bukkit.World;

public record FCPoint(World world, int x, int y, int z) {

    public static FCPoint at(World world, int x, int y, int z) {
        return new FCPoint(world, x, y, z);
    }
    public static FCPoint at(FCPoint point) {
        return new FCPoint(point.world, point.x, point.y, point.z);
    }
    public static FCPoint adapt(String name){
        String[] strings = name.split("_");
        return FCPoint.at(FC.getInstance().getServer().getWorld(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]));
    }
    public static FCPoint adapt(Location loc) {
        return new FCPoint(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
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
    public World getWorld() {
        return world;
    }
    public Location getLocation() {
        return new Location(world, x, y, z);
    }
    public String getString(){
        return world.getName() + "_" + x + "_" + y + "_" + z;
    }

    public boolean equals(FCPoint point) {
        return this.world == point.world && this.x == point.x && this.y == point.y && this.z == point.z;
    }
    public FCPoint add(int x, int y, int z) {
        return new FCPoint(world, this.x + x, this.y + y, this.z + z);
    }
    public FCPoint subtract(int x, int y, int z) {
        return new FCPoint(world, this.x - x, this.y - y, this.z - z);
    }
}
