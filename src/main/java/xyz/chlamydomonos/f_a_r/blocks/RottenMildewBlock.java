package xyz.chlamydomonos.f_a_r.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import xyz.chlamydomonos.f_a_r.blocks.utils.FARProperties;

import java.util.Random;

@SuppressWarnings("deprecation")
public class RottenMildewBlock extends Block
{
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);


    public RottenMildewBlock()
    {
        super(
                Properties.of(Material.PLANT)
                        .noCollission()
                        .noOcclusion()
                        .randomTicks()
                        .instabreak()
                        .sound(SoundType.GRASS)
             );

        registerDefaultState(getStateDefinition().any().setValue(FARProperties.AGE, 0));
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context)
    {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder)
    {
        builder.add(FARProperties.AGE);
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random random)
    {
        int age = state.getValue(FARProperties.AGE);
        if(age < 4 && random.nextInt(5) == 0)
            level.setBlock(pos, state.cycle(FARProperties.AGE), 3);

        if(age == 4 && random.nextInt(10) == 0)
            tick(state, level, pos, random);
    }

    @Override
    public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random random)
    {
        int age = state.getValue(FARProperties.AGE);
        if(age == 4)
        {
            level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 2f, Explosion.BlockInteraction.DESTROY);
        }
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion)
    {
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        level.scheduleTick(pos, state.getBlock(), 5);
    }
}
