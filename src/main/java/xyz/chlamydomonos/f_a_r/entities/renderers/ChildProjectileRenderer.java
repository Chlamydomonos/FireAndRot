package xyz.chlamydomonos.f_a_r.entities.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import xyz.chlamydomonos.f_a_r.FireAndRot;
import xyz.chlamydomonos.f_a_r.entities.ChildProjectileEntity;
import xyz.chlamydomonos.f_a_r.entities.models.ChildProjectileModel;

public class ChildProjectileRenderer extends EntityRenderer<ChildProjectileEntity>
{
    private final ChildProjectileModel model;

    public ChildProjectileRenderer(EntityRendererProvider.Context pContext)
    {
        super(pContext);
        model = new ChildProjectileModel(pContext.bakeLayer(ChildProjectileModel.LAYER_LOCATION));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ChildProjectileEntity pEntity)
    {
        return new ResourceLocation(FireAndRot.MODID, "textures/entity/main_projectile.png");
    }

    @Override
    public void render(@NotNull ChildProjectileEntity pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight)
    {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        pMatrixStack.pushPose();
        var buffer = pBuffer.getBuffer(model.renderType(getTextureLocation(pEntity)));
        model.renderToBuffer(pMatrixStack, buffer, pPackedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 0.5f);
        pMatrixStack.popPose();
    }
}
