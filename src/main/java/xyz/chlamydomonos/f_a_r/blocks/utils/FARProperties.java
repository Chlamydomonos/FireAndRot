package xyz.chlamydomonos.f_a_r.blocks.utils;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class FARProperties
{
    public static final IntegerProperty PHASE = IntegerProperty.create("phase", 0, 31);
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 4);
    public static final IntegerProperty HEIGHT = IntegerProperty.create("height", 0, 4);
    public static final BooleanProperty CAN_GROW = BooleanProperty.create("can_grow");
}
