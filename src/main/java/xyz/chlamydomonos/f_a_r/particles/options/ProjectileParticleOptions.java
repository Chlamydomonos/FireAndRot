package xyz.chlamydomonos.f_a_r.particles.options;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import xyz.chlamydomonos.f_a_r.loaders.ParticleLoader;

import java.util.Locale;

public class ProjectileParticleOptions implements ParticleOptions
{
    public static class Deserializer implements ParticleOptions.Deserializer<ProjectileParticleOptions>
    {
        @Override
        public @NotNull ProjectileParticleOptions fromCommand(@NotNull ParticleType<ProjectileParticleOptions> type, @NotNull StringReader reader) throws CommandSyntaxException
        {
            reader.expect(' ');
            float r = reader.readFloat();
            reader.expect(' ');
            float g = reader.readFloat();
            reader.expect(' ');
            float b = reader.readFloat();
            reader.expect(' ');
            float a = reader.readFloat();
            reader.expect(' ');
            float fade = reader.readFloat();
            reader.expect(' ');
            int lifetime = reader.readInt();
            return new ProjectileParticleOptions(r, g, b, a, fade, lifetime);
        }

        @Override
        public @NotNull ProjectileParticleOptions fromNetwork(@NotNull ParticleType<ProjectileParticleOptions> type, @NotNull FriendlyByteBuf buffer)
        {
            float r = buffer.readFloat();
            float g = buffer.readFloat();
            float b = buffer.readFloat();
            float a = buffer.readFloat();
            float fade = buffer.readFloat();
            int lifeTime = buffer.readVarInt();
            return new ProjectileParticleOptions(r, g, b, a, fade, lifeTime);
        }
    }

    public static final Deserializer DESERIALIZER = new Deserializer();

    private final float r;
    private final float g;
    private final float b;
    private final float a;
    private final float fade;
    private final int lifetime;

    public ProjectileParticleOptions(float r, float g, float b, float a, float fade, int lifetime)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.fade = fade;
        this.lifetime = lifetime;
    }

    public ProjectileParticleOptions(Vector3f rgb, float a, float fade, int lifetime)
    {
        this(rgb.x(), rgb.y(), rgb.z(), a, fade, lifetime);
    }

    public ProjectileParticleOptions(Vector4f color, float fade, int lifetime)
    {
        this(color.x(), color.y(), color.z(), color.w(), fade, lifetime);
    }

    public Vector3f getRGB()
    {
        return new Vector3f(r, g, b);
    }

    public float getA()
    {
        return a;
    }

    public float getFade()
    {
        return fade;
    }

    public int getLifetime()
    {
        return lifetime;
    }

    @Override
    public @NotNull ParticleType<?> getType()
    {
        return ParticleLoader.PROJECTILE.get();
    }

    @Override
    public void writeToNetwork(@NotNull FriendlyByteBuf buffer)
    {
        buffer.writeFloat(r);
        buffer.writeFloat(g);
        buffer.writeFloat(b);
        buffer.writeFloat(a);
        buffer.writeFloat(fade);
        buffer.writeVarInt(lifetime);
    }

    @Override
    public @NotNull String writeToString()
    {
        return String.format(
                Locale.ROOT,
                "%s %.2f %.2f %.2f %.2f %.2f %d",
                ForgeRegistries.PARTICLE_TYPES.getKey(getType()),
                r,
                g,
                b,
                a,
                fade,
                lifetime
                            );
    }
}
