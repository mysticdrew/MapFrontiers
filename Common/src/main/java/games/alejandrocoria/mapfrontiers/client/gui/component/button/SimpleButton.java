package games.alejandrocoria.mapfrontiers.client.gui.component.button;

import com.mojang.blaze3d.systems.RenderSystem;
import games.alejandrocoria.mapfrontiers.client.gui.ColorConstants;
import games.alejandrocoria.mapfrontiers.client.gui.component.StringWidget;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SimpleButton extends ButtonBase {
    private final StringWidget label;
    private int textColor = ColorConstants.SIMPLE_BUTTON_TEXT;
    private int textColorHighlight = ColorConstants.SIMPLE_BUTTON_TEXT_HIGHLIGHT;
    private int textColorInactive = ColorConstants.SIMPLE_BUTTON_TEXT_INACTIVE;

    public SimpleButton(Font font, int width, Component text, OnPress pressedAction) {
        super(0, 0, width, 16, text, (b) -> pressedAction.onPress((SimpleButton) b), Button.DEFAULT_NARRATION);
        this.label = new StringWidget(text, font, StringWidget.Align.Center);
    }

    @Override
    public void setX(int x) {
        super.setX(x);
        this.label.setX(x + width / 2);
    }

    @Override
    public void setY(int y) {
        super.setY(y);
        this.label.setY(y + 4);
    }

    @Override
    public void setMessage(Component text) {
        this.label.setMessage(text);
        setX(getX());
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (!active) {
            label.setColor(textColorInactive);
        } else if (isHoveredOrKeyboardFocused()) {
            label.setColor(textColorHighlight);
        } else {
            label.setColor(textColor);
        }

        RenderSystem.setShaderColor(1.f, 1.f, 1.f, 1.f);

        int borderColor = isKeyboardFocused() ? ColorConstants.SIMPLE_BUTTON_BORDER_FOCUSED : active ? ColorConstants.SIMPLE_BUTTON_BORDER : ColorConstants.SIMPLE_BUTTON_BORDER_DISABLED;
        graphics.hLine(getX(), getX() + width - 1, getY(), borderColor);
        graphics.hLine(getX(), getX() + width - 1, getY() + 15, borderColor);
        graphics.vLine(getX(), getY(), getY() + 15, borderColor);
        graphics.vLine(getX() + width - 1, getY(), getY() + 15, borderColor);
        graphics.fill(getX() + 1, getY() + 1, getX() + width - 1, getY() + 15, ColorConstants.SIMPLE_BUTTON_BG);

        label.render(graphics, mouseX, mouseY, partialTicks);
    }

    public void setTextColors(int color, int highlight) {
        textColor = color;
        textColorHighlight = highlight;
    }


    public interface OnPress {
        void onPress(SimpleButton button);
    }
}
