package xyz.chlamydomonos.f_a_r.loaders;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.chlamydomonos.f_a_r.FireAndRot;
import xyz.chlamydomonos.f_a_r.entities.ChildProjectileEntity;
import xyz.chlamydomonos.f_a_r.entities.MainProjectileEntity;
import xyz.chlamydomonos.f_a_r.entities.models.ChildProjectileModel;
import xyz.chlamydomonos.f_a_r.entities.models.MainProjectileModel;
import xyz.chlamydomonos.f_a_r.entities.renderers.ChildProjectileRenderer;
import xyz.chlamydomonos.f_a_r.entities.renderers.MainProjectileRenderer;

@Mod.EventBusSubscriber(modid = FireAndRot.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityLoader
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, FireAndRot.MODID);

    public static final RegistryObject<EntityType<?>> MAIN_PROJECTILE = ENTITIES.register("main_projectile", () -> EntityType.Builder.of(MainProjectileEntity::new, MobCategory.MISC).build("main_projectile"));
    public static final RegistryObject<EntityType<?>> CHILD_PROJECTILE = ENTITIES.register("child_projectile", () -> EntityType.Builder.of(ChildProjectileEntity::new, MobCategory.MISC).build("child_projectile"));

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(MainProjectileModel.LAYER_LOCATION, MainProjectileModel::createBodyLayer);
        event.registerLayerDefinition(ChildProjectileModel.LAYER_LOCATION, ChildProjectileModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer((EntityType<? extends MainProjectileEntity>) MAIN_PROJECTILE.get(), MainProjectileRenderer::new);
        event.registerEntityRenderer((EntityType<? extends ChildProjectileEntity>) CHILD_PROJECTILE.get(), ChildProjectileRenderer::new);
    }
}
