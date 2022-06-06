package xyz.chlamydomonos.f_a_r.particles.providers;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.chlamydomonos.f_a_r.particles.ProjectileParticle;
import xyz.chlamydomonos.f_a_r.particles.options.ProjectileParticleOptions;

public class ProjectileParticleProvider implements ParticleProvider<ProjectileParticleOptions>
{
    private final SpriteSet spriteSet;

    public ProjectileParticleProvider(SpriteSet spriteSet)
    {
        this.spriteSet = spriteSet;
    }

    @Nullable
    @Override
    public Particle createParticle(@NotNull ProjectileParticleOptions type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
    {
        return new ProjectileParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, type.getR(), type.getG(), type.getB(), type.getA(), type.getFade(), type.getLifetime(), spriteSet);
    }
}
