package games.alejandrocoria.mapfrontiers.common.network;

import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import games.alejandrocoria.mapfrontiers.MapFrontiers;
import games.alejandrocoria.mapfrontiers.client.MapFrontiersClient;
import games.alejandrocoria.mapfrontiers.client.event.ClientEventHandler;
import games.alejandrocoria.mapfrontiers.common.util.UUIDHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

@ParametersAreNonnullByDefault
public class PacketFrontierDeleted {
    public static final ResourceLocation CHANNEL = ResourceLocation.fromNamespaceAndPath(MapFrontiers.MODID, "packet_frontier_deleted");
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketFrontierDeleted> STREAM_CODEC = StreamCodec.ofMember(PacketFrontierDeleted::encode, PacketFrontierDeleted::new);

    private ResourceKey<Level> dimension = Level.OVERWORLD;
    private UUID frontierID;
    private boolean personal;
    private int playerID = -1;

    public PacketFrontierDeleted(ResourceKey<Level> dimension, UUID frontierID, boolean personal, int playerID) {
        this.dimension = dimension;
        this.frontierID = frontierID;
        this.personal = personal;
        this.playerID = playerID;
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type() {
        return new CustomPacketPayload.Type<>(CHANNEL);
    }

    public PacketFrontierDeleted(FriendlyByteBuf buf) {
        try {
            if (buf.readableBytes() > 1) {
                this.dimension = ResourceKey.create(Registries.DIMENSION, buf.readResourceLocation());
                this.frontierID = UUIDHelper.fromBytes(buf);
                this.personal = buf.readBoolean();
                this.playerID = buf.readInt();
            }
        } catch (Throwable t) {
            MapFrontiers.LOGGER.error(String.format("Failed to read message for PacketFrontierDeleted: %s", t));
        }
    }

    public void encode(FriendlyByteBuf buf) {
        try {
            buf.writeResourceLocation(dimension.location());
            UUIDHelper.toBytes(buf, frontierID);
            buf.writeBoolean(personal);
            buf.writeInt(playerID);
        } catch (Throwable t) {
            MapFrontiers.LOGGER.error(String.format("Failed to write message for PacketFrontierDeleted: %s", t));
        }
    }

    public static void handle(PacketContext<PacketFrontierDeleted> ctx) {
        if (Side.CLIENT.equals(ctx.side())) {
            PacketFrontierDeleted message = ctx.message();
            boolean deleted = MapFrontiersClient.getFrontiersOverlayManager(message.personal).deleteFrontier(message.dimension,message.frontierID);

            if (deleted) {
                ClientEventHandler.postDeletedFrontierEvent(message.frontierID);
            }
        }
    }
}
