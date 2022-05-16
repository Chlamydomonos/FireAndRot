package xyz.chlamydomonos.f_a_r.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import xyz.chlamydomonos.f_a_r.blocks.utils.FARProperties;

public class RottenMycelialSoilHiddenBlock extends Block
{
    public RottenMycelialSoilHiddenBlock()
    {
        super(Properties.of(Material.GLASS));
        registerDefaultState(getStateDefinition().any().setValue(FARProperties.PHASE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FARProperties.PHASE);
    }
}
