package xyz.chlamydomonos.f_a_r.items;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.chlamydomonos.f_a_r.items.utils.StyleUtil;
import xyz.chlamydomonos.f_a_r.loaders.BlockLoader;
import xyz.chlamydomonos.f_a_r.loaders.CreativeTabLoader;
import xyz.chlamydomonos.f_a_r.tags.FARBlockTags;

import java.util.List;

public class AlienOrbItem extends Item
{
    public AlienOrbItem()
    {
        super(new Properties().tab(CreativeTabLoader.FIRE_AND_ROT));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> text, @NotNull TooltipFlag flag)
    {
        super.appendHoverText(stack, level, text, flag);
        text.add(new TranslatableComponent("tooltip.f_a_r.alien_orb.line1").setStyle(StyleUtil.TOOLTIP));
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context)
    {
        if(!context.getLevel().isClientSide)
        {
            var tags = context.getLevel().getBlockState(context.getClickedPos()).getTags();
            if(tags.anyMatch(tag -> tag == FARBlockTags.ROTTEN_PHASE_1))
                context.getLevel().setBlock(
                        context.getClickedPos(),
                        BlockLoader.ALIEN_EXPLOSIVE.get().defaultBlockState(),
                        3
                                           );
            return InteractionResult.SUCCESS;
        }

        return Items.FLINT_AND_STEEL.useOn(context);
    }
}
