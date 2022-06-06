package xyz.chlamydomonos.f_a_r.loaders;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.chlamydomonos.f_a_r.FireAndRot;
import xyz.chlamydomonos.f_a_r.particles.providers.ProjectileParticleProvider;
import xyz.chlamydomonos.f_a_r.particles.types.ProjectileParticleType;

@Mod.EventBusSubscriber(modid = FireAndRot.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleLoader
{
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, FireAndRot.MODID);

    public static final RegistryObject<ParticleType<?>> PROJECTILE = PARTICLES.register("projectile", ProjectileParticleType::new);

    @SubscribeEvent
    public static void onRegisterParticleProviders(ParticleFactoryRegisterEvent event)
    {
        Minecraft.getInstance().particleEngine.register((ProjectileParticleType) PROJECTILE.get(), ProjectileParticleProvider::new);
    }
}
