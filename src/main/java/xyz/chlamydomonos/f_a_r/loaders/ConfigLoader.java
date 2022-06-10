package xyz.chlamydomonos.f_a_r.loaders;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigLoader
{
    public static ForgeConfigSpec COMMON;

    public static ForgeConfigSpec.BooleanValue ROT_EXPAND;

    static
    {
        var commonBuilder = new ForgeConfigSpec.Builder();

        commonBuilder.comment("腐败部分的配置").push("rot");
        ROT_EXPAND = commonBuilder.comment("腐败是否扩散").define("rot_expand", true);
        commonBuilder.pop();
        COMMON = commonBuilder.build();
    }
}
