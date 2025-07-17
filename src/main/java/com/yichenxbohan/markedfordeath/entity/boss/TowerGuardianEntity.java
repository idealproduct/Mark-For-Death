package com.yichenxbohan.markedfordeath.entity.boss;

import com.yichenxbohan.markedfordeath.entity.MeteorEntity;
import com.yichenxbohan.markedfordeath.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class TowerGuardianEntity extends Monster {

    private final ServerBossEvent bossEvent = new ServerBossEvent(
            this.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);

    private int phase = 1;
    private int laserCooldown = 0;
    private int summonCooldown = 0;
    private int soundWaveCooldown = 0;
    private int meteorCooldown = 0;

    public TowerGuardianEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setPersistenceRequired();
    }

    // ===== BOSS 屬性定義 =====
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 300.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    // ===== 每 tick 處理攻擊技能與階段判定 =====
    @Override
    public void tick() {
        super.tick();

        if (this.level.isClientSide) return;

        bossEvent.setProgress(this.getHealth() / this.getMaxHealth());

        List<Player> players = this.level.getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(32));
        if (players.isEmpty()) return;

        // 高頻雷射（每 0.25 秒）
       /* if (laserCooldown-- <= 0) {
            for (Player p : players) {
                shootLaserAt(p);
            }
            laserCooldown = 5;
        }

        // 召喚殭屍與骷髏（每 10 秒）
        if (summonCooldown-- <= 0) {
            summonMinions();
            summonCooldown = 200;
        }

        // 聲波攻擊（每 20 秒）
        if (soundWaveCooldown-- <= 0) {
            soundWaveAttack();
            soundWaveCooldown = 400;
        }*/

        // 隕石轉階段（血量過半）
        if (this.getHealth() < this.getMaxHealth() / 2 && phase == 1) {
            phase = 2;
            this.level.playSound(null, this.blockPosition(), SoundEvents.WARDEN_SONIC_CHARGE, SoundSource.HOSTILE, 2.0F, 1.0F);
        }

        if (phase == 2 && meteorCooldown-- <= 0) {
            summonMeteorShower();
            meteorCooldown = 200;
        }
    }

    // ===== 雷射攻擊邏輯（用小火球） =====
    private void shootLaserAt(Player target) {
        Vec3 dir = target.position().subtract(this.position()).normalize();
        SmallFireball fireball = new SmallFireball(this.level, this, dir.x, dir.y, dir.z);
        fireball.setPos(this.getX(), this.getEyeY(), this.getZ());
        this.level.addFreshEntity(fireball);
    }

    // ===== 小怪召喚邏輯 =====
    private void summonMinions() {
        if (!(this.level instanceof ServerLevel serverLevel)) return;
        RandomSource random = this.getRandom();
        BlockPos base = this.blockPosition();

        Zombie zombie = EntityType.ZOMBIE.create(level);
        if (zombie != null) {
            BlockPos spawnPos = base.offset(random.nextInt(5) - 2, 0, random.nextInt(5) - 2);
            zombie.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5);
            serverLevel.addFreshEntity(zombie);
        }

        Skeleton skeleton = EntityType.SKELETON.create(level);
        if (skeleton != null) {
            BlockPos spawnPos = base.offset(random.nextInt(5) - 2, 0, random.nextInt(5) - 2);
            skeleton.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5);
            serverLevel.addFreshEntity(skeleton);
        }
    }

    // ===== 聲波攻擊邏輯（範圍傷害） =====
    private void soundWaveAttack() {
        List<Player> players = this.level.getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(10));
        for (Player p : players) {
            p.hurt(DamageSource.mobAttack(this), 6.0F);
        }
        this.level.playSound(null, this.blockPosition(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 3.0F, 1.0F);
    }

    // ===== 隕石召喚邏輯（要自定義 MeteorEntity） =====
    private void summonMeteorShower() {
        if (!(this.level instanceof ServerLevel serverLevel)) return;
        RandomSource random = this.getRandom();

        for (int i = 0; i < 5; i++) {
            double x = this.getX() + random.nextInt(15) - 7;
            double z = this.getZ() + random.nextInt(15) - 7;
            double y = this.getY() + 170;

            this.level.playSound(null, this.blockPosition(), new SoundEvent(new ResourceLocation("markedfordeath", "meteorfall")), SoundSource.HOSTILE, 10.0F, 0.8F);
            MeteorEntity meteor = new MeteorEntity(serverLevel, x, y, z);
            serverLevel.addFreshEntity(meteor);
        }
    }

    // ===== BossBar 註冊控制 =====
    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        bossEvent.removePlayer(player);
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        bossEvent.removeAllPlayers();
    }

    // ===== 死亡掉落物 =====
    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        this.spawnAtLocation(ModItems.SOUL_DEVOURER.get()); // 你要事先註冊這個物品
    }

    // ===== 初始 BossBar 滿血狀態 =====
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData data, CompoundTag tag) {
        bossEvent.setProgress(1.0F);
        return super.finalizeSpawn(accessor, difficulty, reason, data, tag);
    }
}
