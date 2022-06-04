package xyz.chlamydomonos.f_a_r.particles.types;

import com.mojang.datafixers.util.Pair;
import com.mojang.math.Vector3f;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.NotNull;
import xyz.chlamydomonos.f_a_r.particles.options.ProjectileParticleOptions;

public class ProjectileParticleType extends ParticleType<ProjectileParticleOptions>
{
    private static final Codec<ProjectileParticleOptions> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Vector3f.CODEC.fieldOf("rgb").forGetter(ProjectileParticleOptions::getRGB),
                    Codec.FLOAT.fieldOf("a").forGetter(ProjectileParticleOptions::getA),
                    Codec.FLOAT.fieldOf("fade").forGetter(ProjectileParticleOptions::getFade),
                    Codec.INT.fieldOf("lifetime").forGetter(ProjectileParticleOptions::getLifetime)
                                  ).apply(instance, ProjectileParticleOptions::new));

    public ProjectileParticleType()
    {
        super(false, ProjectileParticleOptions.DESERIALIZER);
    }

    @Override
    public @NotNull Codec<ProjectileParticleOptions> codec()
    {
        return CODEC;
    }
}
