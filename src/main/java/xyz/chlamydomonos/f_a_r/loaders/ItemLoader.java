package xyz.chlamydomonos.f_a_r.loaders;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.chlamydomonos.f_a_r.FireAndRot;
import xyz.chlamydomonos.f_a_r.items.*;
import xyz.chlamydomonos.f_a_r.items.abstractitems.SimpleWithoutTabItem;
import xyz.chlamydomonos.f_a_r.items.blockitems.*;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ItemLoader
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FireAndRot.MODID);

    public static final RegistryObject<Item> FIRE_AND_ROT_ICON = ITEMS.register("fire_and_rot_icon", SimpleWithoutTabItem::new);
    public static final RegistryObject<Item> ROT_DEBUG_STICK = ITEMS.register("rot_debug_stick", RotDebugStickItem::new);
    public static final RegistryObject<Item> ROTTEN_STIPE = ITEMS.register("rotten_stipe", RottenStipeItem::new);
    public static final RegistryObject<Item> TEST_PROJECTILE = ITEMS.register("test_projectile", TestProjectileItem::new);
    public static final RegistryObject<Item> ROTTEN_RESIDUE = ITEMS.register("rotten_residue", RottenResidueItem::new);
    public static final RegistryObject<Item> ALIEN_ORB = ITEMS.register("alien_orb", AlienOrbItem::new);
    public static final RegistryObject<Item> ROTTEN_SOIL = ITEMS.register("rotten_soil", normalBlockItem(BlockLoader.ROTTEN_SOIL));
    public static final RegistryObject<Item> POISON_FLOWER = ITEMS.register("poison_flower", normalBlockItem(BlockLoader.POISON_FLOWER));
    public static final RegistryObject<Item> PALE_MUSHROOM = ITEMS.register("pale_mushroom", normalBlockItem(BlockLoader.PALE_MUSHROOM));

    public static Supplier<? extends Item> normalBlockItem(Block block)
    {
        return () -> new NormalBlockItem(block);
    }

    public static Supplier<? extends Item> normalBlockItem(RegistryObject<Block> block)
    {
        return () -> new NormalBlockItem(block.get());
    }
}
