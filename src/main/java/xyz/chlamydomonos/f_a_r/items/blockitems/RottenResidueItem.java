package xyz.chlamydomonos.f_a_r.items.blockitems;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.chlamydomonos.f_a_r.items.utils.StyleUtil;
import xyz.chlamydomonos.f_a_r.loaders.BlockLoader;
import xyz.chlamydomonos.f_a_r.loaders.CreativeTabLoader;

import java.util.List;

public class RottenResidueItem extends BlockItem
{
    public RottenResidueItem()
    {
        super(
                BlockLoader.ROTTEN_RESIDUE.get(),
                new Properties().tab(CreativeTabLoader.FIRE_AND_ROT)
             );
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag)
    {
        super.appendHoverText(stack, level, components, flag);

        components.add(new TranslatableComponent("tooltip.f_a_r.rotten_residue.line1").setStyle(StyleUtil.TOOLTIP));
    }
}
