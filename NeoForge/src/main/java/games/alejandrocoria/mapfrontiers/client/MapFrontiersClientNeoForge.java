package games.alejandrocoria.mapfrontiers.client;

import com.mojang.blaze3d.platform.InputConstants;
import games.alejandrocoria.mapfrontiers.MapFrontiers;
import games.alejandrocoria.mapfrontiers.MapFrontiersNeoForge;
import games.alejandrocoria.mapfrontiers.client.event.ClientEventHandler;
import games.alejandrocoria.mapfrontiers.common.Config;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingIn;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RenderFrameEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.NeoForge;
import org.lwjgl.glfw.GLFW;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.neoforged.neoforge.client.gui.VanillaGuiLayers.EFFECTS;

@ParametersAreNonnullByDefault
public class MapFrontiersClientNeoForge extends MapFrontiersClient {
    public MapFrontiersClientNeoForge() {
    }

    public static void clientSetup(FMLClientSetupEvent event, IEventBus eventBus) {
        init();
        NeoForge.EVENT_BUS.addListener(MapFrontiersClientNeoForge::livingUpdateEvent);
        NeoForge.EVENT_BUS.addListener(MapFrontiersClientNeoForge::onRenderTick);
        NeoForge.EVENT_BUS.addListener(MapFrontiersClientNeoForge::RenderGameOverlayEvent);
        NeoForge.EVENT_BUS.addListener(MapFrontiersClientNeoForge::clientConnectedToServer);
        NeoForge.EVENT_BUS.addListener(MapFrontiersClientNeoForge::clientDisconnectionFromServer);
        NeoForge.EVENT_BUS.addListener(MapFrontiersClientNeoForge::mouseEvent);

        MapFrontiersNeoForge.LOGGER.info("NeoForge clientSetup done");
    }

    public static void livingUpdateEvent(ClientTickEvent.Post event) {
        Minecraft client = Minecraft.getInstance();
        if (client.level != null) {
            Player player = client.player;
            ClientEventHandler.postPlayerTickEvent(client, player);
        }
    }

    public static void onRenderTick(RenderFrameEvent.Post event) {
        ClientEventHandler.postClientTickEvent(Minecraft.getInstance());
    }

    public static void RenderGameOverlayEvent(RenderGuiLayerEvent.Pre event) {
        if (EFFECTS.equals(event.getName())) {
            ClientEventHandler.postHudRenderEvent(event.getGuiGraphics(), event.getPartialTick());
        }
    }

    public static void clientConnectedToServer(LoggingIn event) {
        ClientEventHandler.postClientConnectedEvent();
    }

    public static void clientDisconnectionFromServer(LoggingOut event) {
        ClientEventHandler.postClientDisconnectedEvent();
    }

    public static void mouseEvent(InputEvent.MouseButton.Pre event) {
        if (event.getAction() == GLFW.GLFW_RELEASE) {
            ClientEventHandler.postMouseReleaseEvent(event.getButton());
        }
    }

    @EventBusSubscriber(value = Dist.CLIENT, modid = MapFrontiers.MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class KeyMappingsEventHandler {
        @SubscribeEvent
        public static void registerKeyMappingsEvent(RegisterKeyMappingsEvent event) {
            openSettingsKey = new KeyMapping("mapfrontiers.key.open_settings", KeyConflictContext.IN_GAME,
                    InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F8, "mapfrontiers.key.category");
            event.register(openSettingsKey);
        }
    }

    @EventBusSubscriber(modid = MapFrontiers.MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class ConfigEventHandler {
        @SubscribeEvent
        public static void onModConfigEvent(ModConfigEvent.Loading configEvent) {
            if (configEvent.getConfig().getModId().equals(MapFrontiersNeoForge.MODID) && configEvent.getConfig().getType() == ModConfig.Type.CLIENT) {
                Config.bakeConfig();
            }
        }
    }
}
