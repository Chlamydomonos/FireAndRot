package xyz.chlamydomonos.f_a_r.loaders;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.chlamydomonos.f_a_r.FireAndRot;
import xyz.chlamydomonos.f_a_r.items.*;
import xyz.chlamydomonos.f_a_r.items.blockitems.*;

@SuppressWarnings("unused")
public class ItemLoader
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FireAndRot.MODID);

    public static final RegistryObject<Item> FIRE_AND_ROT_ICON = ITEMS.register("fire_and_rot_icon", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ROT_DEBUG_STICK = ITEMS.register("rot_debug_stick", RotDebugStickItem::new);
    public static final RegistryObject<Item> ROTTEN_STIPE = ITEMS.register("rotten_stipe", RottenStipeItem::new);
    public static final RegistryObject<Item> TEST_PROJECTILE = ITEMS.register("test_projectile", TestProjectileItem::new);
    public static final RegistryObject<Item> ROTTEN_RESIDUE = ITEMS.register("rotten_residue", RottenResidueItem::new);
    public static final RegistryObject<Item> ALIEN_ORB = ITEMS.register("alien_orb", AlienOrbItem::new);
}
