package xyz.chlamydomonos.f_a_r.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.chlamydomonos.f_a_r.blocks.utils.FARProperties;
import xyz.chlamydomonos.f_a_r.blocks.utils.RottenMycelialSoilUtil;
import xyz.chlamydomonos.f_a_r.tileentities.RottenMycelialSoilTileEntity;

import java.util.Random;

public class RottenMycelialSoilBlock extends Block implements EntityBlock
{

    public RottenMycelialSoilBlock()
    {
        super(
                Properties.of(Material.DIRT)
                        .randomTicks()
                        .strength(1f)
                        .explosionResistance(1f)
                        .sound(SoundType.FUNGUS)
             );

        registerDefaultState(getStateDefinition().any().setValue(FARProperties.PHASE, 0));
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
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new RottenMycelialSoilTileEntity(pos, state);
    }
}
