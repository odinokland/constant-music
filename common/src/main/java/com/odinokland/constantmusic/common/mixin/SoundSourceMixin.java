//? if < 1.19.3 {
/*package com.odinokland.constantmusic.common.mixin;

import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;

/^*
 * The type Sound source mixin.
 ^/
@Mixin(SoundSource.class)
@Unique
public class SoundSourceMixin {
    @Shadow
    @Final
    @Mutable
    private static SoundSource[] $VALUES;

    private static final SoundSource MUSIC_DELAY = soundSourceExpansion$addVariant("MUSIC_DELAY", "music_delay");

    /^*
     * Sound source expansion invoke init sound source.
     *
     * @param internalName the internal name
     * @param internalId   the internal id
     * @param name         the name
     * @return the sound source
     ^/
    @Invoker("<init>")
    public static SoundSource soundSourceExpansion$invokeInit(String internalName, int internalId, String name) {
        throw new AssertionError();
    }

    private static SoundSource soundSourceExpansion$addVariant(String internalName, String name) {
        ArrayList<SoundSource> variants = new ArrayList<SoundSource>(Arrays.asList(SoundSourceMixin.$VALUES));
        SoundSource source = soundSourceExpansion$invokeInit(internalName, variants.get(variants.size() - 1).ordinal() + 1, name);
        variants.add(source);
        SoundSourceMixin.$VALUES = variants.toArray(new SoundSource[0]);
        return source;
    }
}
*///?}