package dev.nevergodz.prefixplugin.data;

import lombok.Data;

@Data
public class PlayerData {
    private byte[] playerUuid;
    private String inventory;
    private String armor;
}
