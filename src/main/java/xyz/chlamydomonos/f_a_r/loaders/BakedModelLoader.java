package xyz.chlamydomonos.f_a_r.loaders;

import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.chlamydomonos.f_a_r.FireAndRot;
import xyz.chlamydomonos.f_a_r.bakedmodels.RottenMycelialSoilModel;
import xyz.chlamydomonos.f_a_r.blocks.utils.FARProperties;

@Mod.EventBusSubscriber(modid = FireAndRot.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BakedModelLoader
{
    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event)
    {
        for(BlockState state : BlockLoader.ROTTEN_MYCELIAL_SOIL.get().getStateDefinition().getPossibleStates())
        {
            int phase = state.getValue(FARProperties.PHASE);
            if(phase >= 18)
                break;

            var oldLocation = BlockModelShaper.stateToModelLocation(state);
            var newState = BlockLoader.ROTTEN_MYCELIAL_SOIL_HIDDEN
                    .get()
                    .defaultBlockState()
                    .setValue(FARProperties.PHASE, phase);
            var newLocation = BlockModelShaper.stateToModelLocation(newState);

            var newModel = event.getModelRegistry().get(newLocation);
            event.getModelRegistry().put(oldLocation, new RottenMycelialSoilModel(newModel));
        }
    }
}
