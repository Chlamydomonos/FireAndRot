package xyz.chlamydomonos.f_a_r.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import xyz.chlamydomonos.f_a_r.blocks.utils.FARProperties;
import xyz.chlamydomonos.f_a_r.blocks.utils.RottenMycelialSoilUtil;

import java.util.Random;

@SuppressWarnings("deprecation")
public class RottenMycelialSoilWithoutTEBlock extends Block
{
    public RottenMycelialSoilWithoutTEBlock()
    {
        super(
                Properties.of(Material.DIRT)
                        .randomTicks()
                        .strength(1f)
                        .explosionResistance(1f)
                        .sound(SoundType.FUNGUS)
             );

        registerDefaultState(getStateDefinition().any().setValue(FARProperties.PHASE, 18));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FARProperties.PHASE);
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random random)
    {
        RottenMycelialSoilUtil.rot(state, level, pos, random);
        RottenMycelialSoilUtil.genStipe(state, level, pos, random);
    }
}
