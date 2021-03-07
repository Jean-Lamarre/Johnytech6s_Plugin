package io.github.johnytech6.dm;

import io.github.johnytech6.Handler.PluginHandler;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("fast")
class DmTest {

	@Test
	@DisplayName("")
	void myFirstTest(TestInfo testInfo) {
		assertEquals("text", "text");
	}

	@Test
	void testPlugin(TestInfo testInfo){

		Plugin plugin = mock(Plugin.class);
		PluginManager pManager = mock(PluginManager.class);

		Server server = mock(Server.class);
		when(server.getPluginManager()).thenReturn(pManager);

		when(plugin.getServer()).thenReturn(server);

		Player player = mock(Player.class);
		when(player.getName()).thenReturn("Johnytech6");

		//TODO ????????
		Bukkit.getServer().dispatchCommand(player, "dm mode_toggle");

		assertTrue(plugin.isEnabled());
	}

}
