package com.yichenxbohan.markedfordeath.entity;

import com.yichenxbohan.markedfordeath.util.TargetUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import java.util.UUID;

public class TrackingEyeEntity extends EyeOfEnder {

    private UUID ownerUUID;

    public TrackingEyeEntity(EntityType<? extends EyeOfEnder> type, Level level) {
        super(type, level);
    }

    public void setOwner(ServerPlayer player) {
        this.ownerUUID = player.getUUID();
    }

    public ServerPlayer getOwnerPlayer() {
        if (ownerUUID == null || !(level instanceof ServerLevel serverLevel)) return null;
        return serverLevel.getServer().getPlayerList().getPlayer(ownerUUID);
    }

    @Override
    public void tick() {
        super.tick();

        if (!level.isClientSide && tickCount == 60) {
            ServerPlayer owner = getOwnerPlayer();
            if (owner == null) {
                this.discard();
                return;
            }

            ServerPlayer target = TargetUtils.getTarget(owner);
            if (target != null) {
                BlockPos pos = target.blockPosition();
                this.signalTo(pos);
            } else {
                this.discard();
            }
        }
    }

    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
