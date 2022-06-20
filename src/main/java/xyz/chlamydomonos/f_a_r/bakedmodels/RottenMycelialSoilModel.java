package xyz.chlamydomonos.f_a_r.bakedmodels;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.IForgeBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.chlamydomonos.f_a_r.tileentities.RottenMycelialSoilTileEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public record RottenMycelialSoilModel(
        BakedModel mycelium) implements IForgeBakedModel, BakedModel
{
    public static final ModelProperty<BakedModel> BLOCK = new ModelProperty<>();
    public static final ModelProperty<BlockState> STATE = new ModelProperty<>();

    @NotNull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull Random rand, @NotNull IModelData extraData)
    {
        var block = extraData.getData(BLOCK);
        var preState = extraData.getData(STATE);
        List<BakedQuad> newQuads = new ArrayList<>();
        newQuads.addAll(Objects.requireNonNull(block).getQuads(state, side, rand, extraData));
        newQuads.addAll(mycelium.getQuads(preState, side, rand, extraData));
        return newQuads;
    }

    @NotNull
    @Override
    public IModelData getModelData(@NotNull BlockAndTintGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull IModelData modelData)
    {
        var te = (RottenMycelialSoilTileEntity) level.getBlockEntity(pos);
        var preState = Objects.requireNonNull(te).getPreviousBlock().defaultBlockState();
        var model = Minecraft.getInstance().getBlockRenderer().getBlockModel(preState);
        var dataMap = new ModelDataMap.Builder()
                .withInitial(BLOCK, null)
                .withInitial(STATE, null)
                .build();
        dataMap.setData(BLOCK, model);
        dataMap.setData(STATE, preState);
        return dataMap;
    }

    @Override
    @Deprecated
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState p_119123_, @Nullable Direction p_119124_, @NotNull Random p_119125_)
    {
        return mycelium.getQuads(p_119123_, p_119124_, p_119125_);
    }

    @Override
    public boolean useAmbientOcclusion()
    {
        return mycelium.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d()
    {
        return mycelium.isGui3d();
    }

    @Override
    public boolean usesBlockLight()
    {
        return mycelium.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer()
    {
        return mycelium.isCustomRenderer();
    }

    @Override
    @Deprecated
    public @NotNull TextureAtlasSprite getParticleIcon()
    {
        return mycelium.getParticleIcon();
    }

    @Override
    public @NotNull ItemOverrides getOverrides()
    {
        return mycelium.getOverrides();
    }
}
