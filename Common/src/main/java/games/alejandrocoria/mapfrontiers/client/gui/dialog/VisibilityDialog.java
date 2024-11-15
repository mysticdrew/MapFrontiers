package games.alejandrocoria.mapfrontiers.client.gui.dialog;

import games.alejandrocoria.mapfrontiers.client.gui.ColorConstants;
import games.alejandrocoria.mapfrontiers.client.gui.component.StringWidget;
import games.alejandrocoria.mapfrontiers.client.gui.component.button.CheckBoxButton;
import games.alejandrocoria.mapfrontiers.client.gui.component.button.OptionButton;
import games.alejandrocoria.mapfrontiers.client.gui.component.button.SimpleButton;
import games.alejandrocoria.mapfrontiers.client.gui.screen.AutoScaledScreen;
import games.alejandrocoria.mapfrontiers.common.FrontierData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.BiConsumer;

@ParametersAreNonnullByDefault
public class VisibilityDialog extends AutoScaledScreen {
    private static final Component generalLabel = Component.translatable("mapfrontiers.general");
    private static final Component showFrontierLabel = Component.translatable("mapfrontiers.show_frontier");
    private static final Component announceInChatLabel = Component.translatable("mapfrontiers.announce_in_chat");
    private static final Component announceInTitleLabel = Component.translatable("mapfrontiers.announce_in_title");
    private static final Component fullscreenLabel = Component.translatable("mapfrontiers.fullscreen");
    private static final Component showNameLabel = Component.translatable("mapfrontiers.show_name");
    private static final Component showOwnerLabel = Component.translatable("mapfrontiers.show_owner");
    private static final Component minimapLabel = Component.translatable("mapfrontiers.minimap");
    private static final Component dayLabel = Component.translatable("mapfrontiers.day");
    private static final Component nightLabel = Component.translatable("mapfrontiers.night");
    private static final Component undergroundLabel = Component.translatable("mapfrontiers.underground");
    private static final Component topoLabel = Component.translatable("mapfrontiers.topo");
    private static final Component biomeLabel = Component.translatable("mapfrontiers.biome");
    private static final Component doneLabel = Component.translatable("gui.done");
    private static final Component onLabel = Component.translatable("options.on");
    private static final Component offLabel = Component.translatable("options.off");

    private final FrontierData.VisibilityData visibilityData;
    @Nullable
    private final FrontierData.VisibilityData visibilityMask;
    private final BiConsumer<FrontierData.VisibilityData, FrontierData.VisibilityData> afterDoneCallback;
    protected SimpleButton doneButton;

    public VisibilityDialog(FrontierData.VisibilityData visibilityData, BiConsumer<FrontierData.VisibilityData, FrontierData.VisibilityData> afterDoneCallback) {
        super(Component.empty());
        this.visibilityData = new FrontierData.VisibilityData(visibilityData);
        this.visibilityMask = null;
        this.afterDoneCallback = afterDoneCallback;
    }

    public VisibilityDialog(FrontierData.VisibilityData visibilityData, FrontierData.VisibilityData visibilityDataMask, BiConsumer<FrontierData.VisibilityData, FrontierData.VisibilityData> afterDoneCallback) {
        super(Component.empty());
        this.visibilityData = new FrontierData.VisibilityData(visibilityData);
        this.visibilityMask = new FrontierData.VisibilityData(visibilityDataMask);
        this.afterDoneCallback = afterDoneCallback;
    }

    @Override
    protected void initScreen() {
        LinearLayout mainLayout = LinearLayout.vertical().spacing(8);
        mainLayout.defaultCellSetting().alignHorizontallyCenter();
        content.addChild(mainLayout);

        LinearLayout mainColumns = LinearLayout.horizontal().spacing(16);
        mainLayout.addChild(mainColumns);

        LinearLayout generalColumn = LinearLayout.vertical().spacing(6);
        generalColumn.defaultCellSetting().alignHorizontallyCenter();
        mainColumns.addChild(generalColumn);
        generalColumn.addChild(new StringWidget(generalLabel.copy().withStyle(Style.EMPTY.withBold(true)), font).setColor(ColorConstants.TEXT));

        GridLayout generalGrid = new GridLayout().spacing(4);
        generalColumn.addChild(generalGrid);
        createWidgets(generalGrid, 1, showFrontierLabel, FrontierData.VisibilityData.Visibility.Frontier);
        createWidgets(generalGrid, 2, announceInChatLabel, FrontierData.VisibilityData.Visibility.AnnounceInChat);
        createWidgets(generalGrid, 3, announceInTitleLabel, FrontierData.VisibilityData.Visibility.AnnounceInTitle);

        LinearLayout fullscreenColumn = LinearLayout.vertical().spacing(6);
        fullscreenColumn.defaultCellSetting().alignHorizontallyCenter();
        mainColumns.addChild(fullscreenColumn);
        fullscreenColumn.addChild(new StringWidget(fullscreenLabel.copy().withStyle(Style.EMPTY.withBold(true)), font).setColor(ColorConstants.TEXT));

        GridLayout fullscreenGrid = new GridLayout().spacing(4);
        fullscreenColumn.addChild(fullscreenGrid);
        createWidgets(fullscreenGrid, 1, showFrontierLabel, FrontierData.VisibilityData.Visibility.Fullscreen);
        createWidgets(fullscreenGrid, 2, showNameLabel, FrontierData.VisibilityData.Visibility.FullscreenName);
        createWidgets(fullscreenGrid, 3, showOwnerLabel, FrontierData.VisibilityData.Visibility.FullscreenOwner);
        createWidgets(fullscreenGrid, 4, dayLabel, FrontierData.VisibilityData.Visibility.FullscreenDay);
        createWidgets(fullscreenGrid, 5, nightLabel, FrontierData.VisibilityData.Visibility.FullscreenNight);
        createWidgets(fullscreenGrid, 6, undergroundLabel, FrontierData.VisibilityData.Visibility.FullscreenUnderground);
        createWidgets(fullscreenGrid, 7, topoLabel, FrontierData.VisibilityData.Visibility.FullscreenTopo);
        createWidgets(fullscreenGrid, 8, biomeLabel, FrontierData.VisibilityData.Visibility.FullscreenBiome);

        LinearLayout minimapColumn = LinearLayout.vertical().spacing(6);
        minimapColumn.defaultCellSetting().alignHorizontallyCenter();
        mainColumns.addChild(minimapColumn);
        minimapColumn.addChild(new StringWidget(minimapLabel.copy().withStyle(Style.EMPTY.withBold(true)), font).setColor(ColorConstants.TEXT));

        GridLayout minimapGrid = new GridLayout().spacing(4);
        minimapColumn.addChild(minimapGrid);
        createWidgets(minimapGrid, 1, showFrontierLabel, FrontierData.VisibilityData.Visibility.Minimap);
        createWidgets(minimapGrid, 2, showNameLabel, FrontierData.VisibilityData.Visibility.MinimapName);
        createWidgets(minimapGrid, 3, showOwnerLabel, FrontierData.VisibilityData.Visibility.MinimapOwner);
        createWidgets(minimapGrid, 4, dayLabel, FrontierData.VisibilityData.Visibility.MinimapDay);
        createWidgets(minimapGrid, 5, nightLabel, FrontierData.VisibilityData.Visibility.MinimapNight);
        createWidgets(minimapGrid, 6, undergroundLabel, FrontierData.VisibilityData.Visibility.MinimapUnderground);
        createWidgets(minimapGrid, 7, topoLabel, FrontierData.VisibilityData.Visibility.MinimapTopo);
        createWidgets(minimapGrid, 8, biomeLabel, FrontierData.VisibilityData.Visibility.MinimapBiome);

        doneButton = mainLayout.addChild(new SimpleButton(font, 100, doneLabel, (b) -> onClose()));
    }

    private void createWidgets(GridLayout layout, int row, Component label, FrontierData.VisibilityData.Visibility visibility) {
        layout.addChild(new StringWidget(label, font).setColor(ColorConstants.TEXT), row, 0);

        OptionButton button = new OptionButton(font, 28, (b) -> {
            visibilityData.setValue(visibility, b.getSelected() == 0);
        });
        button.addOption(onLabel);
        button.addOption(offLabel);
        button.setSelected(visibilityData.getValue(visibility) ? 0 : 1);
        layout.addChild(button, row, 2);

        if (visibilityMask != null) {
            CheckBoxButton checkBox = new CheckBoxButton(visibilityMask.getValue(visibility), (b) -> {
                visibilityMask.setValue(visibility, b.isChecked());
                button.active = b.isChecked();
            });
            layout.addChild(checkBox, row, 1);
            button.active = checkBox.isChecked();
        }
    }

    @Override
    public void renderScaledBackgroundScreen(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        drawCenteredBoxBackground(graphics, content.getWidth() + 20, content.getHeight() + 20);
    }

    public void onClose() {
        super.onClose();
        afterDoneCallback.accept(visibilityData, visibilityMask);
    }
}
