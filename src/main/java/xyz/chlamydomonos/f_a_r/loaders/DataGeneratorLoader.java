package xyz.chlamydomonos.f_a_r.loaders;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import xyz.chlamydomonos.f_a_r.FireAndRot;
import xyz.chlamydomonos.f_a_r.datagen.BlockTagProvider;

@Mod.EventBusSubscriber(modid = FireAndRot.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneratorLoader
{
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event)
    {
        event.getGenerator().addProvider(new BlockTagProvider(event.getGenerator(), event.getExistingFileHelper()));
    }
}
