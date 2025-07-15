package com.yichenxbohan.markedfordeath.entity;

import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;

public class MeteorEntity extends Entity {

    private static final float DAMAGE = 10.0F;

    public MeteorEntity(EntityType<? extends MeteorEntity> type, Level level) {
        super(type, level);
        this.noPhysics = false;
    }

    public MeteorEntity(Level level, double x, double y, double z) {
        this(ModEntities.METEOR.get(), level);
        this.setPos(x, y, z);
        this.setDeltaMovement(0, -1.5, 0);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level.isClientSide) return;

        this.move(net.minecraft.world.entity.MoverType.SELF, this.getDeltaMovement());

        if (this.isOnGround() || this.getY() < this.level.getMinBuildHeight() + 1) {
            explode();
            this.discard();
        }
    }

    private void explode() {
        if (!(this.level instanceof ServerLevel serverLevel)) return;

        serverLevel.playSound(null, this.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 3.0F, 1.0F);

        serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.EXPLOSION,
                this.getX(), this.getY(), this.getZ(), 10, 0.5, 0.5, 0.5, 0.1);

        AABB damageArea = this.getBoundingBox().inflate(3.0D);
        List<Player> players = this.level.getEntitiesOfClass(Player.class, damageArea);
        for (Player player : players) {
            player.hurt(DamageSource.MAGIC, DAMAGE);
        }
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {}

    @Override
    protected void addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {}

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
