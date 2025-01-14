package games.alejandrocoria.mapfrontiers.platform;

import games.alejandrocoria.mapfrontiers.MapFrontiers;
import games.alejandrocoria.mapfrontiers.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.util.Optional;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public String getModVersion() {
        Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(MapFrontiers.MODID);
        if (modContainer.isPresent()) {
            return modContainer.get().getMetadata().getVersion().getFriendlyString();
        }
        return "";
    }
}
