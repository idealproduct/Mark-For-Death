package com.yichenxbohan.markedfordeath.entity;

import com.yichenxbohan.markedfordeath.util.TargetUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
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
    ServerPlayer owner,target;
    Vec3 targetPos,currentPos,direction,velocity;
    @Override
    public void tick() {
        super.baseTick();

        if (!level.isClientSide) {
            if(tickCount == 1){
                owner = getOwnerPlayer();
                target = TargetUtils.getTarget();
                if (owner == null||target==null) {
                    this.discard();
                    return;
                }
                targetPos = target.position();
                //targetPos = new Vec3(0,60,0);
                currentPos = this.position();
                direction = targetPos.subtract(currentPos).normalize();
                double speed = 0.4;
                velocity = direction.scale(speed);
            }


            // 更新移動向量
            owner.sendSystemMessage(Component.literal(velocity.toString()));

            this.setDeltaMovement(velocity);
            this.move(MoverType.SELF, this.getDeltaMovement());


        }
        if(tickCount > 40){
            this.discard();
        }
    }

    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
