package games.alejandrocoria.mapfrontiers.common.util;

import com.mojang.authlib.GameProfile;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

@ParametersAreNonnullByDefault
public class UUIDHelper {
    public static UUID getUUIDFromName(String username, @Nullable MinecraftServer server) {
        GameProfile profile = null;

        if (server != null) {
            profile = server.getProfileCache().get(username).orElse(null);
        } else {
            ClientPacketListener handler = Minecraft.getInstance().getConnection();
            if (handler != null) {
                PlayerInfo playerInfo = handler.getPlayerInfo(username);
                if (playerInfo != null) {
                    profile = playerInfo.getProfile();
                }
            }
        }

        if (profile != null)
            return profile.getId();

        return null;
    }

    public static String getNameFromUUID(UUID uuid, @Nullable MinecraftServer server) {
        GameProfile profile = null;

        if (server != null) {
            profile = server.getProfileCache().get(uuid).orElse(null);
        } else {
            ClientPacketListener handler = Minecraft.getInstance().getConnection();
            if (handler != null) {
                PlayerInfo playerInfo = handler.getPlayerInfo(uuid);
                if (playerInfo != null) {
                    profile = playerInfo.getProfile();
                }
            }
        }

        if (profile != null)
            return profile.getName();

        return null;
    }

    public static UUID fromBytes(ByteBuf buf) {
        return new UUID(buf.readLong(), buf.readLong());
    }

    public static void toBytes(ByteBuf buf, UUID uuid) {
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
    }

    private UUIDHelper() {

    }
}
