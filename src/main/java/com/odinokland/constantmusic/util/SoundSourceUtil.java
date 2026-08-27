package com.odinokland.constantmusic.util;

import net.minecraft.sounds.SoundSource;
import sun.misc.Unsafe;
import java.lang.reflect.Field;

public class SoundSourceUtil {

	/**
	 * Safely allocates a completely isolated, dummy SoundSource instance.
	 */
	public static SoundSource createDummySource() {
		try {
			Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
			unsafeField.setAccessible(true);
			Unsafe unsafe = (Unsafe) unsafeField.get(null);

			// Allocate an instance without breaking enum array structures
			SoundSource dummy = (SoundSource) unsafe.allocateInstance(SoundSource.class);

			// Flag our duck-interface field immediately
			((ICustomSoundSource) (Object) dummy).setMusicDelay(true);

			return dummy;
		} catch (Exception e) {
			throw new RuntimeException("Failed to allocate custom SoundSource dummy", e);
		}
	}
}
