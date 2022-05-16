package xyz.chlamydomonos.f_a_r.tileentities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import xyz.chlamydomonos.f_a_r.loaders.TileEntityLoader;

public class RottenMycelialSoilTileEntity extends BlockEntity
{
    private Block previousBlock;

    public RottenMycelialSoilTileEntity(BlockPos pos, BlockState state)
    {
        super(TileEntityLoader.ROTTEN_MYCELIAL_SOIL.get(), pos, state);
        previousBlock = Blocks.DIRT;
    }

    public Block getPreviousBlock()
    {
        return previousBlock;
    }

    public void setPreviousBlock(Block previousBlock)
    {
        this.previousBlock = previousBlock;
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.putString("previous_block", previousBlock.getRegistryName().toString());
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        var name = tag.getString("previous_block");
        previousBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name));
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
