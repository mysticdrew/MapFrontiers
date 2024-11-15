package games.alejandrocoria.mapfrontiers.common;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import games.alejandrocoria.mapfrontiers.MapFrontiers;
import games.alejandrocoria.mapfrontiers.client.event.ClientEventHandler;
import games.alejandrocoria.mapfrontiers.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.ForgeConfigSpec.ValueSpec;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class Config {
    public static final ClientConfig CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;
    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new Builder().configure(ClientConfig::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
        ClientEventHandler.subscribeUpdatedConfigEvent(Config.class, Config::save);
    }

    public enum AfterCreatingFrontier {
        Info, Edit, Nothing
    }

    public enum Visibility {
        Custom, Always, Never
    }

    public enum FilterFrontierType {
        All, Global, Personal
    }

    public enum FilterFrontierOwner {
        All, You, Others
    }

    public enum HUDAnchor {
        ScreenTop, ScreenTopRight, ScreenRight, ScreenBottomRight, ScreenBottom, ScreenBottomLeft, ScreenLeft, ScreenTopLeft,
        Minimap, MinimapHorizontal, MinimapVertical
    }

    public enum HUDSlot {
        None, Name, Owner, Banner
    }

    // New Frontier
    public static int newFrontierShape;
    public static int newFrontierCount;
    public static int newFrontierShapeWidth;
    public static int newFrontierShapeRadius;
    public static int newFrontierChunkShape;
    public static int newFrontierChunkShapeWidth;
    public static int newFrontierChunkShapeLength;
    public static FrontierData.Mode newFrontierMode;
    public static AfterCreatingFrontier afterCreatingFrontier;

    // Paste
    public static boolean pasteName;
    public static boolean pasteVisibility;
    public static boolean pasteColor;
    public static boolean pasteBanner;
    public static boolean pasteOptionsVisible;

    // Frontiers
    public static Visibility frontierVisibility;
    public static Visibility announceInChat;
    public static Visibility announceInTitle;
    public static Visibility fullscreenVisibility;
    public static Visibility fullscreenNameVisibility;
    public static Visibility fullscreenOwnerVisibility;
    public static Visibility fullscreenDayVisibility;
    public static Visibility fullscreenNightVisibility;
    public static Visibility fullscreenUndergroundVisibility;
    public static Visibility fullscreenTopoVisibility;
    public static Visibility fullscreenBiomeVisibility;
    public static Visibility minimapVisibility;
    public static Visibility minimapNameVisibility;
    public static Visibility minimapOwnerVisibility;
    public static Visibility minimapDayVisibility;
    public static Visibility minimapNightVisibility;
    public static Visibility minimapUndergroundVisibility;
    public static Visibility minimapTopoVisibility;
    public static Visibility minimapBiomeVisibility;

    public static boolean titleAnnouncementAboveHotbar;
    public static boolean announceUnnamedFrontiers;
    public static boolean hideNamesThatDontFit;
    public static double polygonsOpacity;
    public static int snapDistance;

    // GUI
    public static boolean fullscreenButtons;
    public static boolean askConfirmationFrontierDelete;
    public static boolean askConfirmationGroupDelete;
    public static boolean askConfirmationUserDelete;

    // Filters
    public static FilterFrontierType filterFrontierType;
    public static FilterFrontierOwner filterFrontierOwner;
    public static String filterFrontierDimension;

    // HUD
    public static boolean hudEnabled;
    public static boolean hudAutoAdjustAnchor;
    public static boolean hudSnapToBorder;
    public static int hudBannerSize;
    public static HUDSlot hudSlot1;
    public static HUDSlot hudSlot2;
    public static HUDSlot hudSlot3;
    public static HUDAnchor hudAnchor;
    public static int hudXPosition;
    public static int hudYPosition;

    public static void bakeConfig() {
        newFrontierShape = CLIENT.newFrontierShape.get();
        newFrontierCount = CLIENT.newFrontierCount.get();
        newFrontierShapeWidth = CLIENT.newFrontierShapeWidth.get();
        newFrontierShapeRadius = CLIENT.newFrontierShapeRadius.get();
        newFrontierChunkShape = CLIENT.newFrontierChunkShape.get();
        newFrontierChunkShapeWidth = CLIENT.newFrontierChunkShapeWidth.get();
        newFrontierChunkShapeLength = CLIENT.newFrontierChunkShapeLength.get();
        newFrontierMode = CLIENT.newFrontierMode.get();
        afterCreatingFrontier = CLIENT.afterCreatingFrontier.get();

        pasteName = CLIENT.pasteName.get();
        pasteVisibility = CLIENT.pasteVisibility.get();
        pasteColor = CLIENT.pasteColor.get();
        pasteBanner = CLIENT.pasteBanner.get();
        pasteOptionsVisible = CLIENT.pasteOptionsVisible.get();

        frontierVisibility = CLIENT.frontierVisibility.get();
        announceInChat = CLIENT.announceInChat.get();
        announceInTitle = CLIENT.announceInTitle.get();
        fullscreenVisibility = CLIENT.fullscreenVisibility.get();
        fullscreenNameVisibility = CLIENT.fullscreenNameVisibility.get();
        fullscreenOwnerVisibility = CLIENT.fullscreenOwnerVisibility.get();
        fullscreenDayVisibility = CLIENT.fullscreenDayVisibility.get();
        fullscreenNightVisibility = CLIENT.fullscreenNightVisibility.get();
        fullscreenUndergroundVisibility = CLIENT.fullscreenUndergroundVisibility.get();
        fullscreenTopoVisibility = CLIENT.fullscreenTopoVisibility.get();
        fullscreenBiomeVisibility = CLIENT.fullscreenBiomeVisibility.get();
        minimapVisibility = CLIENT.minimapVisibility.get();
        minimapNameVisibility = CLIENT.minimapNameVisibility.get();
        minimapOwnerVisibility = CLIENT.minimapOwnerVisibility.get();
        minimapDayVisibility = CLIENT.minimapDayVisibility.get();
        minimapNightVisibility = CLIENT.minimapNightVisibility.get();
        minimapUndergroundVisibility = CLIENT.minimapUndergroundVisibility.get();
        minimapTopoVisibility = CLIENT.minimapTopoVisibility.get();
        minimapBiomeVisibility = CLIENT.minimapBiomeVisibility.get();

        titleAnnouncementAboveHotbar = CLIENT.titleAnnouncementAboveHotbar.get();
        announceUnnamedFrontiers = CLIENT.announceUnnamedFrontiers.get();
        hideNamesThatDontFit = CLIENT.hideNamesThatDontFit.get();
        polygonsOpacity = CLIENT.polygonsOpacity.get();
        snapDistance = CLIENT.snapDistance.get();

        fullscreenButtons = CLIENT.fullscreenButtons.get();
        askConfirmationFrontierDelete = CLIENT.askConfirmationFrontierDelete.get();
        askConfirmationGroupDelete = CLIENT.askConfirmationGroupDelete.get();
        askConfirmationUserDelete = CLIENT.askConfirmationUserDelete.get();

        filterFrontierType = CLIENT.filterFrontierType.get();
        filterFrontierOwner = CLIENT.filterFrontierOwner.get();
        filterFrontierDimension = CLIENT.filterFrontierDimension.get();

        hudEnabled = CLIENT.hudEnabled.get();
        hudAutoAdjustAnchor = CLIENT.hudAutoAdjustAnchor.get();
        hudSnapToBorder = CLIENT.hudSnapToBorder.get();
        hudBannerSize = CLIENT.hudBannerSize.get();
        hudSlot1 = CLIENT.hudSlot1.get();
        hudSlot2 = CLIENT.hudSlot2.get();
        hudSlot3 = CLIENT.hudSlot3.get();
        hudAnchor = CLIENT.hudAnchor.get();
        hudXPosition = CLIENT.hudXPosition.get();
        hudYPosition = CLIENT.hudYPosition.get();
    }

    public static class ClientConfig {
        public final IntValue newFrontierShape;
        public final IntValue newFrontierCount;
        public final IntValue newFrontierShapeWidth;
        public final IntValue newFrontierShapeRadius;
        public final IntValue newFrontierChunkShape;
        public final IntValue newFrontierChunkShapeWidth;
        public final IntValue newFrontierChunkShapeLength;
        public final EnumValue<FrontierData.Mode> newFrontierMode;
        public final EnumValue<AfterCreatingFrontier> afterCreatingFrontier;

        public final BooleanValue pasteName;
        public final BooleanValue pasteVisibility;
        public final BooleanValue pasteColor;
        public final BooleanValue pasteBanner;
        public final BooleanValue pasteOptionsVisible;

        public final EnumValue<Visibility> frontierVisibility;
        public final EnumValue<Visibility> announceInChat;
        public final EnumValue<Visibility> announceInTitle;
        public final EnumValue<Visibility> fullscreenVisibility;
        public final EnumValue<Visibility> fullscreenNameVisibility;
        public final EnumValue<Visibility> fullscreenOwnerVisibility;
        public final EnumValue<Visibility> fullscreenDayVisibility;
        public final EnumValue<Visibility> fullscreenNightVisibility;
        public final EnumValue<Visibility> fullscreenUndergroundVisibility;
        public final EnumValue<Visibility> fullscreenTopoVisibility;
        public final EnumValue<Visibility> fullscreenBiomeVisibility;
        public final EnumValue<Visibility> minimapVisibility;
        public final EnumValue<Visibility> minimapNameVisibility;
        public final EnumValue<Visibility> minimapOwnerVisibility;
        public final EnumValue<Visibility> minimapDayVisibility;
        public final EnumValue<Visibility> minimapNightVisibility;
        public final EnumValue<Visibility> minimapUndergroundVisibility;
        public final EnumValue<Visibility> minimapTopoVisibility;
        public final EnumValue<Visibility> minimapBiomeVisibility;

        public final BooleanValue titleAnnouncementAboveHotbar;
        public final BooleanValue announceUnnamedFrontiers;
        public final BooleanValue hideNamesThatDontFit;
        public final DoubleValue polygonsOpacity;
        public final IntValue snapDistance;

        public final BooleanValue fullscreenButtons;
        public final BooleanValue askConfirmationFrontierDelete;
        public final BooleanValue askConfirmationGroupDelete;
        public final BooleanValue askConfirmationUserDelete;

        public final EnumValue<FilterFrontierType> filterFrontierType;
        public final EnumValue<FilterFrontierOwner> filterFrontierOwner;
        public final ConfigValue<String> filterFrontierDimension;

        public final BooleanValue hudEnabled;
        public final BooleanValue hudAutoAdjustAnchor;
        public final BooleanValue hudSnapToBorder;
        public final IntValue hudBannerSize;
        public final EnumValue<HUDSlot> hudSlot1;
        public final EnumValue<HUDSlot> hudSlot2;
        public final EnumValue<HUDSlot> hudSlot3;
        public final EnumValue<HUDAnchor> hudAnchor;
        public final IntValue hudXPosition;
        public final IntValue hudYPosition;

        public ClientConfig(Builder builder) {
            newFrontierShape = builder.defineInRange("newFrontierShape", 0, 0, 11);
            newFrontierCount = builder.defineInRange("newFrontierVertexCount", 16, 3, 999);
            newFrontierShapeWidth = builder.defineInRange("newFrontierShapeWidth", 10, 0, 999);
            newFrontierShapeRadius = builder.defineInRange("newFrontierShapeRadius", 20, 0, 999);
            newFrontierChunkShape = builder.defineInRange("newFrontierChunkShape", 0, 0, 7);
            newFrontierChunkShapeWidth = builder.defineInRange("newFrontierChunkShapeWidth", 5, 0, 32);
            newFrontierChunkShapeLength = builder.defineInRange("newFrontierChunkShapeLength", 5, 0, 32);
            newFrontierMode = builder.defineEnum("newFrontierMode", FrontierData.Mode.Vertex);
            afterCreatingFrontier = builder.defineEnum("afterCreatingFrontier", AfterCreatingFrontier.Info);

            pasteName = builder.define("pasteName", false);
            pasteVisibility = builder.define("pasteVisibility", true);
            pasteColor = builder.define("pasteColor", true);
            pasteBanner = builder.define("pasteBanner", true);
            pasteOptionsVisible = builder.define("pasteOptionsVisible", false);

            frontierVisibility = builder.comment(
                    "Force all frontier to be shown or hidden. In Custom you can decide for each frontier.")
                    .translation(MapFrontiers.MODID + ".config." + "frontierVisibility")
                    .defineEnum("frontierVisibility", Visibility.Custom);
            announceInChat = builder.comment(
                            "Force all frontier to be announced in chat. In Custom you can decide for each frontier.")
                    .translation(MapFrontiers.MODID + ".config." + "announceInChat")
                    .defineEnum("announceInChat", Visibility.Custom);
            announceInTitle = builder.comment(
                            "Force all frontier to be announced as a title. In Custom you can decide for each frontier.")
                    .translation(MapFrontiers.MODID + ".config." + "announceInTitle")
                    .defineEnum("announceInTitle", Visibility.Custom);
            fullscreenVisibility = builder.comment(
                    "Force all frontier to be shown or hidden on the fullscreen map. In Custom you can decide for each frontier.")
                    .translation(MapFrontiers.MODID + ".config." + "fullscreenVisibility")
                    .defineEnum("fullscreenVisibility", Visibility.Custom);
            fullscreenNameVisibility = builder.comment(
                    "Force all frontier names to be shown or hidden on the fullscreen map. In Custom you can decide for each frontier.")
                    .translation(MapFrontiers.MODID + ".config." + "fullscreenNameVisibility")
                    .defineEnum("fullscreenNameVisibility", Visibility.Custom);
            fullscreenOwnerVisibility = builder.comment(
                    "Force all frontier owners to be shown or hidden on the fullscreen map. In Custom you can decide for each frontier.")
                    .translation(MapFrontiers.MODID + ".config." + "fullscreenOwnerVisibility")
                    .defineEnum("fullscreenOwnerVisibility", Visibility.Custom);
            fullscreenDayVisibility = builder.comment(
                    "Force all frontier to be shown or hidden on the day fullscreen map. In Custom you can decide for each frontier.")
                    .translation(MapFrontiers.MODID + ".config." + "fullscreenDayVisibility")
                    .defineEnum("fullscreenDayVisibility", Visibility.Custom);
            fullscreenNightVisibility = builder.comment(
                    "Force all frontier to be shown or hidden on the night fullscreen map. In Custom you can decide for each frontier.")
                    .translation(MapFrontiers.MODID + ".config." + "fullscreenNightVisibility")
                    .defineEnum("fullscreenNightVisibility", Visibility.Custom);
            fullscreenUndergroundVisibility = builder.comment(
                            "Force all frontier to be shown or hidden on the underground fullscreen map. In Custom you can decide for each frontier.")
                    .translation(MapFrontiers.MODID + ".config." + "fullscreenUndergroundVisibility")
                    .defineEnum("fullscreenUndergroundVisibility", Visibility.Custom);
            fullscreenTopoVisibility = builder.comment(
                            "Force all frontier to be shown or hidden on the topo fullscreen map. In Custom you can decide for each frontier.")
                    .translation(MapFrontiers.MODID + ".config." + "fullscreenTopoVisibility")
                    .defineEnum("fullscreenTopoVisibility", Visibility.Custom);
            fullscreenBiomeVisibility = builder.comment(
                            "Force all frontier to be shown or hidden on the biome fullscreen map. In Custom you can decide for each frontier.")
                    .translation(MapFrontiers.MODID + ".config." + "fullscreenBiomeVisibility")
                    .defineEnum("fullscreenBiomeVisibility", Visibility.Custom);
            minimapVisibility = builder.comment(
                    "Force all frontier to be shown or hidden on the minimap. In Custom you can decide for each frontier.")
                    .translation(MapFrontiers.MODID + ".config." + "minimapVisibility")
                    .defineEnum("minimapVisibility", Visibility.Custom);
            minimapNameVisibility = builder.comment(
                    "Force all frontier names to be shown or hidden on the minimap. In Custom you can decide for each frontier.")
                    .translation(MapFrontiers.MODID + ".config." + "minimapNameVisibility")
                    .defineEnum("minimapNameVisibility", Visibility.Custom);
            minimapOwnerVisibility = builder.comment(
                    "Force all frontier owners to be shown or hidden on the minimap. In Custom you can decide for each frontier.")
                    .translation(MapFrontiers.MODID + ".config." + "minimapOwnerVisibility")
                    .defineEnum("minimapOwnerVisibility", Visibility.Custom);
            minimapDayVisibility = builder.comment(
                            "Force all frontier to be shown or hidden on the day minimap. In Custom you can decide for each frontier.")
                    .translation(MapFrontiers.MODID + ".config." + "minimapDayVisibility")
                    .defineEnum("minimapDayVisibility", Visibility.Custom);
            minimapNightVisibility = builder.comment(
                            "Force all frontier to be shown or hidden on the night minimap. In Custom you can decide for each frontier.")
                    .translation(MapFrontiers.MODID + ".config." + "minimapNightVisibility")
                    .defineEnum("minimapNightVisibility", Visibility.Custom);
            minimapUndergroundVisibility = builder.comment(
                            "Force all frontier to be shown or hidden on the underground minimap. In Custom you can decide for each frontier.")
                    .translation(MapFrontiers.MODID + ".config." + "minimapUndergroundVisibility")
                    .defineEnum("minimapUndergroundVisibility", Visibility.Custom);
            minimapTopoVisibility = builder.comment(
                            "Force all frontier to be shown or hidden on the topo minimap. In Custom you can decide for each frontier.")
                    .translation(MapFrontiers.MODID + ".config." + "minimapTopoVisibility")
                    .defineEnum("minimapTopoVisibility", Visibility.Custom);
            minimapBiomeVisibility = builder.comment(
                            "Force all frontier to be shown or hidden on the biome minimap. In Custom you can decide for each frontier.")
                    .translation(MapFrontiers.MODID + ".config." + "minimapBiomeVisibility")
                    .defineEnum("minimapBiomeVisibility", Visibility.Custom);

            titleAnnouncementAboveHotbar = builder.comment(
                    "Show the frontier announcement above the hotbar instead of showing it as a title.")
                    .translation(MapFrontiers.MODID + ".config." + "titleAnnouncementAboveHotbar")
                    .define("titleAnnouncementAboveHotbar", false);
            announceUnnamedFrontiers = builder.comment(
                    "Announce unnamed frontiers in chat/title.")
                    .translation(MapFrontiers.MODID + ".config." + "announceUnnamedFrontiers")
                    .define("announceUnnamedFrontiers", false);
            hideNamesThatDontFit = builder.comment(
                    "Hides the name if it is wider than the frontier at the zoom level it is being viewed.")
                    .translation(MapFrontiers.MODID + ".config." + "hideNamesThatDontFit")
                    .define("hideNamesThatDontFit", true);
            polygonsOpacity = builder
                    .comment("Transparency of the frontier polygons. 0.0 is fully transparent and 1.0 is opaque.")
                    .translation(MapFrontiers.MODID + ".config." + "polygonsOpacity")
                    .defineInRange("polygonsOpacity", 0.4, 0.0, 1.0);
            snapDistance = builder.comment("Distance at which vertices are attached to nearby vertices.")
                    .translation(MapFrontiers.MODID + ".config." + "snapDistance").defineInRange("snapDistance", 8, 0, 16);

            fullscreenButtons = builder.comment("Show buttons on fullscreen map.")
                    .translation(MapFrontiers.MODID + ".config." + "fullscreenButtons")
                    .define("fullscreenButtons", true);
            askConfirmationFrontierDelete = builder.comment("Show a confirmation dialog before deleting a frontier.")
                    .translation(MapFrontiers.MODID + ".config." + "askConfirmationFrontierDelete")
                    .define("askConfirmationFrontierDelete", true);
            askConfirmationGroupDelete = builder.comment("Show a confirmation dialog before deleting a group.")
                    .translation(MapFrontiers.MODID + ".config." + "askConfirmationGroupDelete")
                    .define("askConfirmationGroupDelete", true);
            askConfirmationUserDelete = builder.comment("Show a confirmation dialog before deleting an user.")
                    .translation(MapFrontiers.MODID + ".config." + "askConfirmationUserDelete")
                    .define("askConfirmationUserDelete", true);

            filterFrontierType = builder.defineEnum("filterFrontierType", FilterFrontierType.All);
            filterFrontierOwner = builder.defineEnum("filterFrontierOwner", FilterFrontierOwner.All);
            filterFrontierDimension = builder.define("filterFrontierDimension", "all");

            builder.push("hud");
            hudEnabled = builder.comment("Show the HUD on screen.").translation(MapFrontiers.MODID + ".config.hud." + "enabled")
                    .define("enabled", true);
            hudAutoAdjustAnchor = builder
                    .comment("Automatically switch to nearest anchor when HUD position is edited (on settings screen).")
                    .translation(MapFrontiers.MODID + ".config.hud." + "autoAdjustAnchor").define("autoAdjustAnchor", true);
            hudSnapToBorder = builder
                    .comment("Automatically snap to closest border when HUD position is edited (on settings screen).")
                    .translation(MapFrontiers.MODID + ".config.hud." + "snapToBorder").define("snapToBorder", true);
            hudBannerSize = builder.comment("Size of the HUD banner.")
                    .translation(MapFrontiers.MODID + ".config.hud." + "bannerSize").defineInRange("bannerSize", 3, 1, 8);
            hudSlot1 = builder.comment("HUD element on slot 1.").translation(MapFrontiers.MODID + ".config.hud." + "slot1")
                    .defineEnum("slot1", HUDSlot.Name);
            hudSlot2 = builder.comment("HUD element on slot 2.").translation(MapFrontiers.MODID + ".config.hud." + "slot2")
                    .defineEnum("slot2", HUDSlot.Owner);
            hudSlot3 = builder.comment("HUD element on slot 3.").translation(MapFrontiers.MODID + ".config.hud." + "slot3")
                    .defineEnum("slot3", HUDSlot.Banner);
            hudAnchor = builder.comment(
                    "Anchor point of the HUD. In the case of choosing the minimap as an anchor, its default position will be used as a reference in the coordinates.")
                    .translation(MapFrontiers.MODID + ".config.hud." + "anchor")
                    .defineEnum("anchor", HUDAnchor.MinimapHorizontal);
            hudXPosition = builder.defineInRange("xPosition", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
            hudYPosition = builder.defineInRange("yPosition", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
            builder.pop();
        }
    }

    private static void save() {
        CLIENT.newFrontierShape.set(newFrontierShape);
        CLIENT.newFrontierCount.set(newFrontierCount);
        CLIENT.newFrontierShapeWidth.set(newFrontierShapeWidth);
        CLIENT.newFrontierShapeRadius.set(newFrontierShapeRadius);
        CLIENT.newFrontierChunkShape.set(newFrontierChunkShape);
        CLIENT.newFrontierChunkShapeWidth.set(newFrontierChunkShapeWidth);
        CLIENT.newFrontierChunkShapeLength.set(newFrontierChunkShapeLength);
        CLIENT.newFrontierMode.set(newFrontierMode);
        CLIENT.afterCreatingFrontier.set(afterCreatingFrontier);

        CLIENT.pasteName.set(pasteName);
        CLIENT.pasteVisibility.set(pasteVisibility);
        CLIENT.pasteColor.set(pasteColor);
        CLIENT.pasteBanner.set(pasteBanner);
        CLIENT.pasteOptionsVisible.set(pasteOptionsVisible);

        CLIENT.frontierVisibility.set(frontierVisibility);
        CLIENT.announceInChat.set(announceInChat);
        CLIENT.announceInTitle.set(announceInTitle);
        CLIENT.fullscreenVisibility.set(fullscreenVisibility);
        CLIENT.fullscreenNameVisibility.set(fullscreenNameVisibility);
        CLIENT.fullscreenOwnerVisibility.set(fullscreenOwnerVisibility);
        CLIENT.fullscreenDayVisibility.set(fullscreenDayVisibility);
        CLIENT.fullscreenNightVisibility.set(fullscreenNightVisibility);
        CLIENT.fullscreenUndergroundVisibility.set(fullscreenUndergroundVisibility);
        CLIENT.fullscreenTopoVisibility.set(fullscreenTopoVisibility);
        CLIENT.fullscreenBiomeVisibility.set(fullscreenBiomeVisibility);
        CLIENT.minimapVisibility.set(minimapVisibility);
        CLIENT.minimapNameVisibility.set(minimapNameVisibility);
        CLIENT.minimapOwnerVisibility.set(minimapOwnerVisibility);
        CLIENT.minimapDayVisibility.set(minimapDayVisibility);
        CLIENT.minimapNightVisibility.set(minimapNightVisibility);
        CLIENT.minimapUndergroundVisibility.set(minimapUndergroundVisibility);
        CLIENT.minimapTopoVisibility.set(minimapTopoVisibility);
        CLIENT.minimapBiomeVisibility.set(minimapBiomeVisibility);

        CLIENT.titleAnnouncementAboveHotbar.set(titleAnnouncementAboveHotbar);
        CLIENT.announceUnnamedFrontiers.set(announceUnnamedFrontiers);
        CLIENT.hideNamesThatDontFit.set(hideNamesThatDontFit);
        CLIENT.polygonsOpacity.set(polygonsOpacity);
        CLIENT.snapDistance.set(snapDistance);

        CLIENT.fullscreenButtons.set(fullscreenButtons);
        CLIENT.askConfirmationFrontierDelete.set(askConfirmationFrontierDelete);
        CLIENT.askConfirmationGroupDelete.set(askConfirmationGroupDelete);
        CLIENT.askConfirmationUserDelete.set(askConfirmationUserDelete);

        CLIENT.filterFrontierType.set(filterFrontierType);
        CLIENT.filterFrontierOwner.set(filterFrontierOwner);
        CLIENT.filterFrontierDimension.set(filterFrontierDimension);

        CLIENT.hudEnabled.set(hudEnabled);
        CLIENT.hudAutoAdjustAnchor.set(hudAutoAdjustAnchor);
        CLIENT.hudSnapToBorder.set(hudSnapToBorder);
        CLIENT.hudBannerSize.set(hudBannerSize);
        CLIENT.hudSlot1.set(hudSlot1);
        CLIENT.hudSlot2.set(hudSlot2);
        CLIENT.hudSlot3.set(hudSlot3);
        CLIENT.hudAnchor.set(hudAnchor);
        CLIENT.hudXPosition.set(hudXPosition);
        CLIENT.hudYPosition.set(hudYPosition);

        CLIENT_SPEC.save();
    }

    public static boolean getVisibilityValue(Visibility visibility, boolean custom) {
        return switch (visibility) {
            case Always -> true;
            case Never -> false;
            default -> custom;
        };
    }

    public static Component getTranslatedName(String name) {
        ValueSpec valueSpec = getValueSpec(name);
        if (valueSpec != null) {
            return Component.translatable(valueSpec.getTranslationKey());
        }

        return CommonComponents.EMPTY;
    }

    public static <E extends Enum<E>> Component getTranslatedEnum(E value) {
        return Component.translatable("mapfrontiers.config." + value.name());
    }

    @Nullable
    public static Tooltip getTooltip(String name) {
        ValueSpec valueSpec = getValueSpec(name);
        if (valueSpec != null) {
            MutableComponent tooltip = Component.translatable(valueSpec.getTranslationKey() + ".tooltip");
            tooltip.append("\n\n");
            tooltip.append(Component.translatable("mapfrontiers.default", getDefault(valueSpec)).withStyle(Style.EMPTY.withBold(true)));
            return Tooltip.create(tooltip);
        }

        return null;
    }

    public static Component getDefault(ValueSpec valueSpec) {
        Object defaultValue = valueSpec.getDefault();
        if (Enum.class.isAssignableFrom(valueSpec.getClazz())) {
            return getTranslatedEnum((Enum) defaultValue);
        } else if (valueSpec.getClazz() == Boolean.class) {
            return Component.translatable((Boolean) defaultValue ? "options.on" : "options.off");
        } else {
            return Component.literal(defaultValue.toString());
        }
    }

    public static boolean isInRange(String name, Object value) {
        ValueSpec valueSpec = getValueSpec(name);
        if (valueSpec != null) {
            return valueSpec.test(value);
        }

        return false;
    }

    private static ValueSpec getValueSpec(String name) {
        return getValueSpec(Arrays.asList(name.split("\\.")), CLIENT_SPEC.getSpec());
    }

    private static ValueSpec getValueSpec(List<String> path, UnmodifiableConfig valueMap) {
        if (path.isEmpty()) {
            return null;
        }

        Object value = valueMap.valueMap().get(path.getFirst());
        if (value == null) {
            return null;
        }

        if (value instanceof com.electronwill.nightconfig.core.Config) {
            return getValueSpec(path.subList(1, path.size()), (com.electronwill.nightconfig.core.Config) value);
        } else {
            return (ValueSpec) value;
        }
    }

    public static Point getHUDAnchor(HUDAnchor anchor) {
        Minecraft mc = Minecraft.getInstance();
        Point p = new Point();
        int displayWidth = mc.getWindow().getWidth();
        int displayHeight = mc.getWindow().getHeight();

        switch (anchor) {
            case ScreenTop -> p.x = displayWidth / 2;
            case ScreenTopRight -> p.x = displayWidth;
            case ScreenRight -> {
                p.x = displayWidth;
                p.y = displayHeight / 2;
            }
            case ScreenBottomRight -> {
                p.x = displayWidth;
                p.y = displayHeight;
            }
            case ScreenBottom -> {
                p.x = displayWidth / 2;
                p.y = displayHeight;
            }
            case ScreenBottomLeft -> p.y = displayHeight;
            case ScreenLeft -> p.y = displayHeight / 2;
            case ScreenTopLeft -> {}
            case Minimap -> p = getMinimapCorner();
            case MinimapHorizontal -> {
                p = getMinimapCorner();
                if (p.y < displayHeight / 2) {
                    p.y = 0;
                } else if (p.y > displayHeight / 2) {
                    p.y = displayHeight;
                }
            }
            case MinimapVertical -> {
                p = getMinimapCorner();
                if (p.x < displayWidth / 2) {
                    p.x = 0;
                } else if (p.x > displayWidth / 2) {
                    p.x = displayWidth;
                }
            }
        }

        return p;
    }

    public static Point getHUDOrigin(HUDAnchor anchor, int hudWidth, int hudHeight) {
        Point p = new Point();

        switch (anchor) {
            case ScreenTop -> p.x = hudWidth / 2;
            case ScreenTopRight -> p.x = hudWidth;
            case ScreenRight -> {
                p.x = hudWidth;
                p.y = hudHeight / 2;
            }
            case ScreenBottomRight -> {
                p.x = hudWidth;
                p.y = hudHeight;
            }
            case ScreenBottom -> {
                p.x = hudWidth / 2;
                p.y = hudHeight;
            }
            case ScreenBottomLeft -> p.y = hudHeight;
            case ScreenLeft -> p.y = hudHeight / 2;
            case ScreenTopLeft -> {}
            case Minimap, MinimapHorizontal, MinimapVertical -> p = getHUDOriginFromMinimap(hudWidth, hudHeight);
        }

        return p;
    }

    public static Point getMinimapCorner() {
        Minecraft mc = Minecraft.getInstance();

        Point corner = new Point();
        int displayWidth = mc.getWindow().getWidth();
        int displayHeight = mc.getWindow().getHeight();

        switch (Services.JOURNEYMAP.getMinimapPosition()) {
            case TopRight -> corner.x = displayWidth;
            case BottomRight -> {
                corner.x = displayWidth;
                corner.y = displayHeight;
            }
            case BottomLeft -> corner.y = displayHeight;
            case TopLeft -> {}
            case TopCenter -> corner.x = displayWidth / 2;
            case Center -> {
                corner.x = displayWidth / 2;
                corner.y = displayHeight / 2;
            }
        }

        if (Services.JOURNEYMAP.isMinimapEnabled()) {
            try {
                int minimapWidth = Services.JOURNEYMAP.getMinimapWidth();
                int minimapHeight = Services.JOURNEYMAP.getMinimapHeight();
                int translateX = Services.JOURNEYMAP.getMinimapTranslateX();
                int translateY = Services.JOURNEYMAP.getMinimapTranslateY();
                int margin = Services.JOURNEYMAP.getMinimapMargin();
                minimapWidth += margin * 2;
                minimapHeight += margin * 2;
                translateX += displayWidth / 2;
                translateY += displayHeight / 2;

                switch (Services.JOURNEYMAP.getMinimapPosition()) {
                    case TopRight -> {
                        corner.x = translateX - minimapWidth / 2;
                        corner.y = translateY + minimapHeight / 2;
                    }
                    case BottomRight -> {
                        corner.x = translateX - minimapWidth / 2;
                        corner.y = translateY - minimapHeight / 2;
                    }
                    case BottomLeft -> {
                        corner.x = translateX + minimapWidth / 2;
                        corner.y = translateY - minimapHeight / 2;
                    }
                    case TopLeft -> {
                        corner.x = translateX + minimapWidth / 2;
                        corner.y = translateY + minimapHeight / 2;
                    }
                    case TopCenter -> {
                        corner.x = translateX;
                        corner.y = translateY + minimapHeight / 2;
                    }
                    case Center -> {
                        corner.x = translateX;
                        corner.y = translateY;
                    }
                }
            } catch (Exception e) {
                MapFrontiers.LOGGER.warn(e.getMessage(), e);
            }
        }

        return corner;
    }

    public static Point getHUDOriginFromMinimap(int hudWidth, int hudHeight) {
        Point origin = new Point();
        switch (Services.JOURNEYMAP.getMinimapPosition()) {
            case TopRight -> origin.x = hudWidth;
            case BottomRight -> {
                origin.x = hudWidth;
                origin.y = hudHeight;
            }
            case BottomLeft -> origin.y = hudHeight;
            case TopLeft -> {}
            case TopCenter -> origin.x = hudWidth / 2;
            case Center -> {
                origin.x = hudWidth / 2;
                origin.y = hudHeight / 2;
            }
        }

        return origin;
    }

    public static class Point {
        public int x = 0;
        public int y = 0;
    }
}
