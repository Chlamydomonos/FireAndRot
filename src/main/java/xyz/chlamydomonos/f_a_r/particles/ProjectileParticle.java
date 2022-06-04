package xyz.chlamydomonos.f_a_r.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import org.jetbrains.annotations.NotNull;

public class ProjectileParticle extends TextureSheetParticle
{
    private final float fade;

    protected ProjectileParticle(ClientLevel level, double x, double y, double z, float r, float g, float b, float a, float fade, int lifetime)
    {
        super(level, x, y, z);
        setColor(r, g, b);
        setAlpha(a);
        this.fade = fade;
        this.lifetime = lifetime;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType()
    {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}
