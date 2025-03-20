package com.nationsandkings.entity.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;

public class GenericVillagerData {

    public static final int MIN_LEVEL = 1;
    public static final int MAX_LEVEL = 5;

    public static final Codec<GenericVillagerData> CODEC = RecordCodecBuilder.create((instance) -> {

    });
    public static final PacketCodec<RegistryByteBuf, VillagerData> PACKET_CODEC;
    private final VillagerType type;
    private final VillagerProfession profession;
    private final int level;

    public GenericVillagerData(VillagerType type, VillagerProfession profession, int level) {
        this.type = type;
        this.profession = profession;
        this.level = Math.max(1, level);
    }

    static {
        PACKET_CODEC = PacketCodec.tuple(PacketCodecs.registryValue(RegistryKeys.VILLAGER_TYPE), (data) -> {
            return data.type;
        }, PacketCodecs.registryValue(RegistryKeys.VILLAGER_PROFESSION), (data) -> {
            return data.profession;
        }, PacketCodecs.VAR_INT, (data) -> {
            return data.level;
        }, VillagerData::new);
    }
}
