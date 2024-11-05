package games.alejandrocoria.mapfrontiers.client.gui.dialog;

import games.alejandrocoria.mapfrontiers.client.gui.ColorConstants;

import java.util.function.Consumer;

public class DeleteConfirmationDialog extends ConfirmationDialog {
    private static final String confirmKey = "mapfrontiers.delete";
    private static final String cancelKey = "gui.cancel";
    private static final String confirmDontAskKey = "mapfrontiers.delete_frontier_dont_ask";

    public DeleteConfirmationDialog(String titleKey, Consumer<Response> callback) {
        super(titleKey, null, confirmKey, cancelKey, confirmDontAskKey, callback);
    }

    @Override
    protected void initScreen() {
        super.initScreen();
        confirmButton.setTextColors(ColorConstants.SIMPLE_BUTTON_TEXT_DELETE, ColorConstants.SIMPLE_BUTTON_TEXT_DELETE_HIGHLIGHT);
        if (confirmAlternativeButton != null) {
            confirmAlternativeButton.setTextColors(ColorConstants.SIMPLE_BUTTON_TEXT_DELETE, ColorConstants.SIMPLE_BUTTON_TEXT_DELETE_HIGHLIGHT);
        }
    }
}
