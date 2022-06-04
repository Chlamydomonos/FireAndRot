package xyz.chlamydomonos.f_a_r.loaders;

import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.chlamydomonos.f_a_r.FireAndRot;
import xyz.chlamydomonos.f_a_r.particles.types.ProjectileParticleType;

public class ParticleLoader
{
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, FireAndRot.MODID);

    public static final RegistryObject<ParticleType<?>> PROJECTILE = PARTICLES.register("projectile", ProjectileParticleType::new);
}
