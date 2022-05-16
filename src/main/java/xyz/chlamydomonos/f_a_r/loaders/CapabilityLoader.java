package xyz.chlamydomonos.f_a_r.loaders;

import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityLoader
{
    @SubscribeEvent
    public void onRegisterCapabilities(RegisterCapabilitiesEvent event)
    {
    }
}
