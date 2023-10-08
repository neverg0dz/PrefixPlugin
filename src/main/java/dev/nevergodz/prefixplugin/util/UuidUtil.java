package dev.nevergodz.prefixplugin.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidUtil {
    public static byte[] uuidToBytes(UUID uuid) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        return buffer.array();
    }
}
