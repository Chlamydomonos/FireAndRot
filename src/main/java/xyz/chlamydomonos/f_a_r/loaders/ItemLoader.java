package xyz.chlamydomonos.f_a_r.loaders;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.chlamydomonos.f_a_r.FireAndRot;
import xyz.chlamydomonos.f_a_r.items.*;

public class ItemLoader
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FireAndRot.MODID);

    public static final RegistryObject<Item> FIRE_AND_ROT_ICON = ITEMS.register("fire_and_rot_icon", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ROT_DEBUG_STICK = ITEMS.register("rot_debug_stick", RotDebugStickItem::new);
}
