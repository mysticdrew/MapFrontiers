package games.alejandrocoria.mapfrontiers.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import games.alejandrocoria.mapfrontiers.client.ClientProxy;
import games.alejandrocoria.mapfrontiers.common.ConfigData;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.display.Context;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class GuiNewFrontier extends Screen {
    private IClientAPI jmAPI;

    private GuiOptionButton buttonFrontierType;
    private GuiOptionButton buttonAddVertexInPosition;
    private GuiOptionButton buttonAfterCreate;
    private GuiSettingsButton buttonCreateFrontier;
    private GuiSettingsButton buttonCancel;

    private final List<GuiSimpleLabel> labels;
    private final Map<GuiSimpleLabel, List<Component>> labelTooltips;

    public GuiNewFrontier(IClientAPI jmAPI) {
        super(TextComponent.EMPTY);
        this.jmAPI = jmAPI;
        labels = new ArrayList<>();
        labelTooltips = new HashMap<>();
    }

    @Override
    public void init() {
        labels.add(new GuiSimpleLabel(font, width / 2 - 150, height / 2 - 64, GuiSimpleLabel.Align.Left,
                new TranslatableComponent("mapfrontiers.frontier_type"), GuiColors.SETTINGS_TEXT));
        buttonFrontierType = new GuiOptionButton(font, width / 2 + 20, height / 2 - 66, 130, this::buttonPressed);
        buttonFrontierType.addOption(new TranslatableComponent("mapfrontiers.global"));
        buttonFrontierType.addOption(new TranslatableComponent("mapfrontiers.personal"));
        buttonFrontierType.setSelected(0);

        labels.add(new GuiSimpleLabel(font, width / 2 - 150, height / 2 - 48, GuiSimpleLabel.Align.Left,
                new TranslatableComponent("mapfrontiers.add_vertex_in_position"), GuiColors.SETTINGS_TEXT));
        buttonAddVertexInPosition = new GuiOptionButton(font, width / 2 + 20, height / 2 - 50, 130, this::buttonPressed);
        buttonAddVertexInPosition.addOption(new TranslatableComponent("options.on"));
        buttonAddVertexInPosition.addOption(new TranslatableComponent("options.off"));
        buttonAddVertexInPosition.setSelected(ConfigData.addVertexToNewFrontier ? 0 : 1);

        labels.add(new GuiSimpleLabel(font, width / 2 - 150, height / 2 - 32, GuiSimpleLabel.Align.Left,
                new TranslatableComponent("mapfrontiers.after_creating"), GuiColors.SETTINGS_TEXT));
        buttonAfterCreate = new GuiOptionButton(font, width / 2 + 20, height / 2 - 34, 130, this::buttonPressed);
        buttonAfterCreate.addOption(new TranslatableComponent("mapfrontiers.go_info"));
        buttonAfterCreate.addOption(new TranslatableComponent("mapfrontiers.go_edit"));
        buttonAfterCreate.addOption(new TranslatableComponent("mapfrontiers.do_nothing"));
        buttonAfterCreate.setSelected(ConfigData.afterCreatingFrontier.ordinal());

        buttonCreateFrontier = new GuiSettingsButton(font, width / 2 - 110, height / 2 + 50, 100,
                new TranslatableComponent("mapfrontiers.create"), this::buttonPressed);
        buttonCancel = new GuiSettingsButton(font, width / 2 + 10, height / 2 + 50, 100,
                new TranslatableComponent("gui.cancel"), this::buttonPressed);

        addRenderableWidget(buttonFrontierType);
        addRenderableWidget(buttonAddVertexInPosition);
        addRenderableWidget(buttonAfterCreate);
        addRenderableWidget(buttonCreateFrontier);
        addRenderableWidget(buttonCancel);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack, 0);

        for (GuiSimpleLabel label : labels) {
            label.render(matrixStack, mouseX, mouseY, partialTicks);
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);

        for (GuiSimpleLabel label : labels) {
            if (label.isHoveredOrFocused()) {
                List<Component> tooltip = labelTooltips.get(label);
                if (tooltip == null) {
                    continue;
                }

                renderTooltip(matrixStack, tooltip, Optional.empty(), mouseX, mouseY);
            }
        }
    }

    protected void buttonPressed(Button button) {
        if (button == buttonFrontierType) {

        } else if (button == buttonAddVertexInPosition) {
            ConfigData.addVertexToNewFrontier = buttonAddVertexInPosition.getSelected() == 0;
            ClientProxy.configUpdated();
        } else if (button == buttonAfterCreate) {
            ConfigData.afterCreatingFrontier = ConfigData.AfterCreatingFrontier.values()[buttonAfterCreate.getSelected()];
            ClientProxy.configUpdated();
        } else if (button == buttonCreateFrontier) {
            boolean personal = buttonFrontierType.getSelected() == 1;
            ClientProxy.getFrontiersOverlayManager(personal).clientCreateNewfrontier(jmAPI.getUIState(Context.UI.Fullscreen).dimension);
            ForgeHooksClient.popGuiLayer(minecraft);
        } else if (button == buttonCancel) {
            ForgeHooksClient.popGuiLayer(minecraft);
        }
    }

    private void addLabelWithTooltip(GuiSimpleLabel label, @Nullable List<Component> tooltip) {
        labels.add(label);
        if (tooltip != null) {
            labelTooltips.put(label, tooltip);
        }
    }
}