package xyz.chlamydomonos.f_a_r.items;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.chlamydomonos.f_a_r.entities.MainProjectileEntity;
import xyz.chlamydomonos.f_a_r.items.utils.StyleUtil;
import xyz.chlamydomonos.f_a_r.loaders.CreativeTabLoader;

import java.util.List;
import java.util.Optional;

public class TestProjectileItem extends Item
{
    public TestProjectileItem()
    {
        super(new Properties().tab(CreativeTabLoader.FIRE_AND_ROT));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> text, @NotNull TooltipFlag flag)
    {
        super.appendHoverText(stack, level, text, flag);
        text.add(new TranslatableComponent("tooltip.f_a_r.test_projectile.line1").setStyle(StyleUtil.TOOLTIP));
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack)
    {
        return 72000;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand)
    {
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void releaseUsing(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, int pTimeCharged)
    {
        if (!(pLivingEntity instanceof Player) || pLevel.isClientSide)
            return;

        float pX = 45f;
        float pY = pLivingEntity.getYRot();
        float pZ = 0f;

        float x = -Mth.sin(pY * ((float) Math.PI / 180F)) * Mth.cos(pX * ((float) Math.PI / 180F));
        float y = -Mth.sin((pX + pZ) * ((float) Math.PI / 180F));
        float z = Mth.cos(pY * ((float) Math.PI / 180F)) * Mth.cos(pX * ((float) Math.PI / 180F));

        var mainProjectile = MainProjectileEntity.create(pLevel);

        mainProjectile.setPos(pLivingEntity.getEyePosition());
        mainProjectile.getEntityData().set(MainProjectileEntity.LIFETIME, 100);
        mainProjectile.getEntityData().set(MainProjectileEntity.INITIAL_HEIGHT, (float) pLivingEntity.getEyePosition().y);
        mainProjectile.setOwner(pLivingEntity);
        pLevel.addFreshEntity(mainProjectile);

        mainProjectile.shoot(x, y, z, 0.5f, 0f);


        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }
}
