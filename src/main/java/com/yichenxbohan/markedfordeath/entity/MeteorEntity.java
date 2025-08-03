package com.yichenxbohan.markedfordeath.entity;

import com.yichenxbohan.markedfordeath.Config;
import com.yichenxbohan.markedfordeath.entity.boss.TowerGuardianEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.Optional;
import java.util.Random;
import java.util.List;

public class MeteorEntity extends Entity {

    private static final float DAMAGE = 15.0F;
    private static final float DAMAGE_ON_ENTITY = 30.0f;
    private Optional<Player> summoner = Optional.empty();

    public MeteorEntity(EntityType<? extends MeteorEntity> type, Level level, Optional<Player> summoner) {
        super(type, level);
        this.noPhysics = false;
        this.summoner = summoner;
    }

    public MeteorEntity(Level level, double x, double y, double z, Optional<Player> summoner) {
        this(ModEntities.METEOR.get(), level, summoner);
        this.setPos(x, y, z);
        this.setDeltaMovement(0, -1.5, 0);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level.isClientSide) return;

        this.setNoGravity(false);

        Vec3 motion = this.getDeltaMovement();

        motion = motion.multiply(0, 0.9, 0);

        if(!this.isNoGravity()){
            motion = motion.add(0, -0.10, 0);
        }

        this.setDeltaMovement(motion);

        this.move(MoverType.SELF, motion);

        if (this.level instanceof ServerLevel serverLevel) {
            double x = this.getX();
            double y = this.getY() - 50;  // 向下偏移 0.5 格
            double z = this.getZ();

            serverLevel.sendParticles(
                    ParticleTypes.FLAME,           // 火焰粒子
                    x, y, z, // 中心座標（可再加偏移）
                    150,                             // 粒子數量
                    10, 10, 10, // X、Y、Z 偏移範圍（生成範圍）
                    0.02                            // 速度（擴散程度）
            );

            Random random = new Random();
            BlockPos center = this.blockPosition();
            for (int dx = -8; dx <= 8; dx++) {
                for (int dz = -8; dz <= 8; dz++) {
                    BlockPos targetPos = new BlockPos(center.getX() + dx, y, center.getZ() + dz);
                    BlockPos targetPos1 = new BlockPos(center.getX() + dx, y + 1, center.getZ() + dz);
                    if (level.getBlockState(targetPos1).getBlock() != Blocks.LIGHT_GRAY_STAINED_GLASS) break;
                    if (random.nextInt() % 2 == 1) break;
                    serverLevel.setBlockAndUpdate(targetPos, Blocks.AIR.defaultBlockState());
                    serverLevel.setBlockAndUpdate(targetPos1, Blocks.AIR.defaultBlockState());
                }
            }
        }


        if (this.isOnGround() || this.getY() < this.level.getMinBuildHeight() + 2) {
            explode();
            this.discard();
        }
    }

    private void explode() {
        if (!(this.level instanceof ServerLevel serverLevel)) return;
        serverLevel.playSound(null, this.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 3.0F, 1.0F);

        serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.EXPLOSION,
                this.getX(), this.getY(), this.getZ(), 10, 0.5, 0.5, 0.5, 0.1);

        AABB damageArea = this.getBoundingBox().inflate(20.0D);
        List<Player> players = this.level.getEntitiesOfClass(Player.class, damageArea);
        for (Player player : this.level.players()) {
            if (!damageArea.contains(player.position())) continue;
            if (this.summoner.isPresent() && this.summoner.get() == player) continue;
            if (!Config.WhetherMeteorSummonedByPlayerCanHurtPlayer){
                if (this.summoner.isPresent() && summoner.get() instanceof Player) continue;
            }
            if (Config.WhetherMeteorCanHurtCreativeAndSpectator){
                if (player.isCreative()){
                    player.getPersistentData().putBoolean("MarkedByMeteor", true);
                    player.hurt(DamageSource.OUT_OF_WORLD, 20.0F);
                    player.getPersistentData().remove("MarkedByMeteor");
                    break;
                }
                if (player instanceof ServerPlayer serverplayer){
                    GameType gamemode = serverplayer.gameMode.getGameModeForPlayer();
                    if(gamemode == GameType.SPECTATOR){
                        serverplayer.setGameMode(GameType.CREATIVE);
                        player.getPersistentData().putBoolean("MarkedByMeteor", true);
                        player.hurt(DamageSource.OUT_OF_WORLD, 20.0F);
                        player.getPersistentData().remove("MarkedByMeteor");
                        serverplayer.setGameMode(GameType.SPECTATOR);
                        break;
                    }
                }
            }
            player.getPersistentData().putBoolean("MarkedByMeteor", true);
            player.hurt(DamageSource.MAGIC, DAMAGE);
            player.getPersistentData().remove("MarkedByMeteor");
            }
        List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class, damageArea);
        for (LivingEntity entity : entities) {
            if (entity instanceof Player || entity instanceof TowerGuardianEntity) continue;
            if (entity instanceof Witch){
                entity.hurt(DamageSource.OUT_OF_WORLD, DAMAGE_ON_ENTITY);
                break;
            }
            if (entity instanceof EnderDragon && players instanceof ServerPlayer serverplayer){
                entity.hurt(DamageSource.playerAttack(serverplayer), 50);
                break;
            }
            entity.hurt(DamageSource.MAGIC, DAMAGE_ON_ENTITY);
        }



        List<EndCrystal> crystals = this.level.getEntitiesOfClass(EndCrystal.class, damageArea);
        for (EndCrystal crystal : crystals) {
            crystal.hurt(DamageSource.MAGIC, DAMAGE);
        }
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
    }

    @Override
    protected void addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}


