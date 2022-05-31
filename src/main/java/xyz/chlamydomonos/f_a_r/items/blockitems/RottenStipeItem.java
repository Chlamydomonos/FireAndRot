package xyz.chlamydomonos.f_a_r.items.blockitems;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.chlamydomonos.f_a_r.blocks.utils.FARProperties;
import xyz.chlamydomonos.f_a_r.loaders.BlockLoader;
import xyz.chlamydomonos.f_a_r.loaders.CreativeTabLoader;

import java.util.Objects;

public class RottenStipeItem extends BlockItem
{
    public RottenStipeItem()
    {
        super(BlockLoader.ROTTEN_STIPE.get(), new Properties().tab(CreativeTabLoader.FIRE_AND_ROT));
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(@NotNull BlockPlaceContext context)
    {
        if(super.getPlacementState(context) == null)
            return null;
        return Objects.requireNonNull(super.getPlacementState(context)).setValue(PipeBlock.DOWN, true);
    }
}
