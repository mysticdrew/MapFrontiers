package games.alejandrocoria.mapfrontiers.client.gui.component.button;

import games.alejandrocoria.mapfrontiers.MapFrontiers;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class IconButton extends ButtonBase {
    private static final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(MapFrontiers.MODID, "textures/gui/buttons.png");
    private static final int textureSizeX = 34;
    private static final int textureSizeY = 111;

    public enum Type {
        Add       ( 0,  0, 13,  0, 13, 13),
        Remove    ( 0, 13, 13, 13, 13, 13),
        Copy      ( 0, 26, 17, 26, 17, 17),
        Paste     ( 0, 43, 17, 43, 17, 17),
        ArrowUp   ( 0, 60,  8, 60,  8, 17),
        ArrowDown (18, 60, 26, 60,  8, 17),
        Undo      ( 0, 77, 17, 77, 17, 17),
        Redo      ( 0, 94, 17, 94, 17, 17);

        final int texX;
        final int texY;
        final int texHoverX;
        final int texHoverY;
        final int width;
        final int height;

        Type(int texX, int texY, int texHoverX, int texHoverY, int width, int height) {
            this.texX = texX;
            this.texY = texY;
            this.texHoverX = texHoverX;
            this.texHoverY = texHoverY;
            this.width = width;
            this.height = height;
        }
    }

    private Type type;

    public IconButton(Type type, OnPress pressedAction) {
        super(0, 0, type.width, type.height, Component.empty(), pressedAction, Button.DEFAULT_NARRATION);
        this.type = type;
    }

    public void setType(Type type) {
        this.type = type;
        setSize(type.width, type.height);
    }

    @Override
    public void playDownSound(SoundManager soundHandlerIn) {

    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (isHoveredOrKeyboardFocused()) {
            graphics.blit(RenderType::guiTextured, texture, getX(), getY(), type.texHoverX, type.texHoverY, width, height, textureSizeX, textureSizeY);
        } else {
            graphics.blit(RenderType::guiTextured, texture, getX(), getY(), type.texX, type.texY, width, height, textureSizeX, textureSizeY);
        }
    }
}
