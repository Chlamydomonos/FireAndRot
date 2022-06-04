package xyz.chlamydomonos.f_a_r.loaders;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;

import java.util.function.BiFunction;

public class DamageSourceLoader
{
    public static final BiFunction<Entity, Entity, DamageSource> ELDEN_STARS = ((projectile, owner) -> new IndirectEntityDamageSource("elden_stars", projectile, owner).bypassArmor().setMagic());
}
