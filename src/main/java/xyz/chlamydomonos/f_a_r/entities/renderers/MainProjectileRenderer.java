package xyz.chlamydomonos.f_a_r.entities.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import xyz.chlamydomonos.f_a_r.FireAndRot;
import xyz.chlamydomonos.f_a_r.entities.MainProjectileEntity;
import xyz.chlamydomonos.f_a_r.entities.models.MainProjectileModel;

public class MainProjectileRenderer extends EntityRenderer<MainProjectileEntity>
{
    private final MainProjectileModel model;

    public MainProjectileRenderer(EntityRendererProvider.Context pContext)
    {
        super(pContext);
        model = new MainProjectileModel(pContext.bakeLayer(MainProjectileModel.LAYER_LOCATION));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull MainProjectileEntity pEntity)
    {
        return new ResourceLocation(FireAndRot.MODID, "textures/entity/main_projectile.png");
    }

    @Override
    public void render(@NotNull MainProjectileEntity pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight)
    {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        pMatrixStack.pushPose();
        var buffer = pBuffer.getBuffer(model.renderType(getTextureLocation(pEntity)));
        model.renderToBuffer(pMatrixStack, buffer, pPackedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 0.5f);
        pMatrixStack.popPose();
    }
}
