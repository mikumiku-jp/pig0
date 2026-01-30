package jp.mikumiku.pig0.entity;

import jp.mikumiku.pig0.init.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;

public class HealingSnowball extends Snowball {
    @Nullable
    private WeakReference<LivingEntity> targetRef;

    public HealingSnowball(EntityType<? extends HealingSnowball> type, Level level) {
        super(type, level);
    }

    public HealingSnowball(Level level, LivingEntity shooter) {
        super(level, shooter);
    }

    public HealingSnowball(Level level, LivingEntity shooter, @Nullable LivingEntity target) {
        super(level, shooter);
        this.targetRef = target != null ? new WeakReference<>(target) : null;
    }

    @Nullable
    public LivingEntity getTarget() {
        return targetRef != null ? targetRef.get() : null;
    }

    public void setTarget(@Nullable LivingEntity target) {
        this.targetRef = target != null ? new WeakReference<>(target) : null;
    }

    @Override
    public EntityType<?> getType() {
        return ModEntities.HEALING_SNOWBALL.get();
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SNOWBALL;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            LivingEntity target = getTarget();
            if (target != null && !target.isAlive()) {
                this.discard();
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (!this.level().isClientSide() && result.getEntity() instanceof LivingEntity hitEntity) {
            if (hitEntity.is(this.getOwner())) {
                return;
            }

            LivingEntity target = getTarget();
            if (target != null && !hitEntity.is(target)) {
                return;
            }

            hitEntity.setHealth(hitEntity.getMaxHealth());

            if (hitEntity instanceof Player player) {
                player.getFoodData().setFoodLevel(20);
                player.getFoodData().setSaturation(5.0F);
            }

            ((ServerLevel) this.level()).sendParticles(
                ParticleTypes.HEART,
                hitEntity.getX(), hitEntity.getY() + hitEntity.getBbHeight() / 2.0, hitEntity.getZ(),
                5, 0.3, 0.3, 0.3, 0.0
            );
            this.level().playSound(null, hitEntity.getX(), hitEntity.getY(), hitEntity.getZ(),
                SoundEvents.PLAYER_LEVELUP, SoundSource.NEUTRAL, 0.5F, 1.5F);

            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.discard();
    }
}
