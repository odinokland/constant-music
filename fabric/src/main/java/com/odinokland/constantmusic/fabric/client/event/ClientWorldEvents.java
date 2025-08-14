package com.odinokland.constantmusic.fabric.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class ClientWorldEvents {
	private ClientWorldEvents() {
	}

	/**
	 * An event which is called after the client world has been changed.
	 */
	public static final Event<AfterClientWorldChange> AFTER_CLIENT_WORLD_CHANGE = EventFactory.createArrayBacked(AfterClientWorldChange.class, callbacks -> (client, world) -> {
		for (AfterClientWorldChange callback : callbacks) {
			callback.afterWorldChange(client, world);
		}
	});

	@FunctionalInterface
	public interface AfterClientWorldChange {
		/**
		 * Called after the client world has been changed.
		 *
		 * @param client the client instance
		 * @param world the new world instance
		 */
		void afterWorldChange(Minecraft client, ClientLevel world);
	}
}