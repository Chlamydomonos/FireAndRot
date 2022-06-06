package xyz.chlamydomonos.f_a_r.entities.utils;

import net.minecraft.world.phys.Vec3;

import java.util.function.BiFunction;

public class MainProjectileUtil
{
    public static BiFunction<Double, Double, Vec3> getCircleDrawer(Vec3 center, Vec3 norm)
    {
        var temp = norm.cross(new Vec3(1, 0, 0));
        if(temp.length() <= 0.0001)
            temp = norm.cross(new Vec3(0, 1, 0));

        var a = temp.normalize();
        var b = norm.cross(temp).normalize();

        return (r, theta) -> {
            double x = center.x
                       + r * Math.cos(theta) * a.x
                       + r * Math.sin(theta) * b.x;
            double y = center.y
                       + r * Math.cos(theta) * a.y
                       + r * Math.sin(theta) * b.y;
            double z = center.z
                       + r * Math.cos(theta) * a.z
                       + r * Math.sin(theta) * b.z;

            return new Vec3(x, y, z);
        };
    }
}
