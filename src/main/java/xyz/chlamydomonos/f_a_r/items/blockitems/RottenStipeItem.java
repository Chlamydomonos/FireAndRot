package xyz.chlamydomonos.f_a_r.items.blockitems;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.chlamydomonos.f_a_r.blocks.utils.FARProperties;
import xyz.chlamydomonos.f_a_r.items.utils.StyleUtil;
import xyz.chlamydomonos.f_a_r.loaders.BlockLoader;
import xyz.chlamydomonos.f_a_r.loaders.CreativeTabLoader;

import java.util.List;
import java.util.Objects;

public class RottenStipeItem extends BlockItem
{
    public RottenStipeItem()
    {
        super(BlockLoader.ROTTEN_STIPE.get(), new Properties().tab(CreativeTabLoader.FIRE_AND_ROT));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag)
    {
        super.appendHoverText(stack, level, components, flag);

        components.add(new TranslatableComponent("tooltip.f_a_r.rotten_stipe.line1").setStyle(StyleUtil.TOOLTIP));
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(@NotNull BlockPlaceContext context)
    {
        if(super.getPlacementState(context) == null)
            return null;

        if(context.getClickedFace() != Direction.UP)
            return null;

        return Objects.requireNonNull(super.getPlacementState(context)).setValue(PipeBlock.DOWN, true);
    }
}
