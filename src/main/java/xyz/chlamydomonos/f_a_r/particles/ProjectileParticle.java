package xyz.chlamydomonos.f_a_r.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import org.jetbrains.annotations.NotNull;

public class ProjectileParticle extends TextureSheetParticle
{
    private final float fade;

    private final SpriteSet spriteSet;

    public ProjectileParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float r, float g, float b, float a, float fade, int lifetime, SpriteSet spriteSet)
    {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        setColor(r, g, b);
        setAlpha(a);
        this.fade = fade;
        this.lifetime = lifetime;
        this.spriteSet = spriteSet;
        this.gravity = 0f;
        this.hasPhysics = false;
        this.setSpriteFromAge(spriteSet);

        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;

        scale(0.25f);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType()
    {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick()
    {
        super.tick();
        setSpriteFromAge(spriteSet);
        setAlpha(alpha - fade);
        if(alpha < 0)
            setAlpha(0f);
    }

    public SpriteSet getSpriteSet()
    {
        return spriteSet;
    }
}
