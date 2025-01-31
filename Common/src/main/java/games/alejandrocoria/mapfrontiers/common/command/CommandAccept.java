package games.alejandrocoria.mapfrontiers.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import games.alejandrocoria.mapfrontiers.common.FrontierData;
import games.alejandrocoria.mapfrontiers.common.FrontiersManager;
import games.alejandrocoria.mapfrontiers.common.PendingShareFrontier;
import games.alejandrocoria.mapfrontiers.common.network.PacketFrontierCreated;
import games.alejandrocoria.mapfrontiers.common.network.PacketFrontierUpdated;
import games.alejandrocoria.mapfrontiers.common.network.PacketHandler;
import games.alejandrocoria.mapfrontiers.common.settings.SettingsUser;
import games.alejandrocoria.mapfrontiers.common.settings.SettingsUserShared;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public class CommandAccept {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> literalcommandnode = dispatcher
                .register(Commands.literal("mapfrontiersaccept").requires(
                        (commandSource) -> commandSource.hasPermission(0) && checkPermission(commandSource))
                        .then(Commands.argument("invitation id", IntegerArgumentType.integer(0)).executes(
                                (commandSource) -> acceptInvitation(commandSource.getSource(),
                                        IntegerArgumentType.getInteger(commandSource, "invitation id")))
                        )
                );

        dispatcher.register(Commands.literal("mfaccept").requires(
                (commandSource) -> commandSource.hasPermission(0) && checkPermission(commandSource))
                .redirect(literalcommandnode)
        );
    }

    public static int acceptInvitation(CommandSourceStack source, int messageID) throws CommandSyntaxException {
        Level world = source.getLevel();
        if (!world.isClientSide()) {
            PendingShareFrontier pending = FrontiersManager.instance.getPendingShareFrontier(messageID);

            if (pending == null) {
                source.sendFailure(Component.literal("Invitation expired"));
            } else if (pending.targetUser.equals(new SettingsUser(source.getPlayerOrException()))) {
                FrontierData frontier = FrontiersManager.instance.getFrontierFromID(pending.frontierID);
                if (frontier == null) {
                    FrontiersManager.instance.removePendingShareFrontier(messageID);
                    source.sendFailure(Component.literal("The frontier no longer exists"));
                    return messageID;
                }

                SettingsUserShared userShared = frontier.getUserShared(pending.targetUser);
                if (userShared == null) {
                    source.sendFailure(Component.literal(""));
                    return messageID;
                }

                if (FrontiersManager.instance.hasPersonalFrontier(pending.targetUser, frontier.getId())) {
                    FrontiersManager.instance.removePendingShareFrontier(messageID);
                    source.sendFailure(Component.literal("You already have the frontier"));
                    return messageID;
                } else {
                    FrontiersManager.instance.addPersonalFrontier(pending.targetUser, frontier);
                }

                userShared.setPending(false);
                frontier.addChange(FrontierData.Change.Shared);

                FrontiersManager.instance.removePendingShareFrontier(messageID);

                PacketHandler.sendTo(new PacketFrontierCreated(frontier), source.getPlayerOrException());
                PacketHandler.sendToUsersWithAccess(new PacketFrontierUpdated(frontier), frontier, source.getServer());

                frontier.removeChange(FrontierData.Change.Shared);

                // @Note: improve message and localize
                source.sendSuccess(() ->
                        Component.literal("Accepting frontier " + frontier.getName1() + " " + frontier.getName2()),
                        false);
                return messageID;
            } else {
                source.sendFailure(Component.literal("The invitation is for another player"));
                return messageID;
            }
        }

        return messageID;
    }

    public static boolean checkPermission(CommandSourceStack source) {
        try {
            return FrontiersManager.instance.canSendCommandAcceptFrontier(source.getPlayerOrException());
        } catch (CommandSyntaxException e) {
            return false;
        }
    }
}
