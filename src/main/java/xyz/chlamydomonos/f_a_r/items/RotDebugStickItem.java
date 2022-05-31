package xyz.chlamydomonos.f_a_r.items;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.chlamydomonos.f_a_r.blocks.utils.RottenMycelialSoilUtil;
import xyz.chlamydomonos.f_a_r.loaders.BlockLoader;
import xyz.chlamydomonos.f_a_r.loaders.CreativeTabLoader;
import xyz.chlamydomonos.f_a_r.tileentities.RottenMycelialSoilTileEntity;

import java.util.List;

public class RotDebugStickItem extends Item
{
    public RotDebugStickItem()
    {
        super(new Properties().tab(CreativeTabLoader.FIRE_AND_ROT));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> text, @NotNull TooltipFlag flag)
    {
        super.appendHoverText(stack, level, text, flag);
        text.add(new TranslatableComponent("tooltip.f_a_r.rot_debug_stick.line1")
                         .setStyle(Style.EMPTY.withFont(Style.DEFAULT_FONT).withColor(0x808080)));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context)
    {
        var pos = context.getClickedPos();
        var level = context.getLevel();

        if(level.isClientSide)
            return InteractionResult.PASS;

        if(!RottenMycelialSoilUtil.canRot(level.getBlockState(pos)))
            return InteractionResult.PASS;

        var block = level.getBlockState(pos).getBlock();

        level.setBlock(pos, BlockLoader.ROTTEN_MYCELIAL_SOIL.get().defaultBlockState(), 0);

        var te = level.getBlockEntity(pos);
        if(te != null)
            ((RottenMycelialSoilTileEntity) te).setPreviousBlock(block);

        level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);

        return InteractionResult.SUCCESS;
    }
}
