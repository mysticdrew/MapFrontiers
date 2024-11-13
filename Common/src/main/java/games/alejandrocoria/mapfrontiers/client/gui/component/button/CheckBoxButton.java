package games.alejandrocoria.mapfrontiers.client.gui.component.button;

import games.alejandrocoria.mapfrontiers.client.gui.ColorConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class CheckBoxButton extends Button {
    private boolean checked;

    public CheckBoxButton(boolean initialValue, OnPress pressedAction) {
        super(0, 0, 12, 12, Component.empty(), (b) -> pressedAction.onPress((CheckBoxButton) b), Button.DEFAULT_NARRATION);
        checked = initialValue;
    }

    public void toggle() {
        checked = !checked;
    }

    public boolean isChecked() {
        return checked;
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.fill(getX(), getY(), getX() + 12, getY() + 12, isFocused() ? ColorConstants.WHITE : ColorConstants.CHECKBOX_BORDER);
        graphics.fill(getX() + 1, getY() + 1, getX() + 11, getY() + 11, ColorConstants.CHECKBOX_BG);
        if (checked) {
            graphics.fill(getX() + 2, getY() + 2, getX() + 10, getY() + 10, ColorConstants.CHECKBOX_CHECK);
        }
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        toggle();
        onPress();
    }


    public interface OnPress {
        void onPress(CheckBoxButton button);
    }
}
