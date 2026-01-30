package jp.mikumiku.pig0.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

public class Pig0Entity extends PathfinderMob implements RangedAttackMob {
    private boolean dying = false;
    @Nullable
    private LivingEntity healTarget = null;

    public Pig0Entity(EntityType<? extends Pig0Entity> type, Level level) {
        super(type, level);
        this.setCustomName(Component.literal("pig0"));
        this.setCustomNameVisible(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.0D, 10, 32.0F));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            updateHealTarget();
        }
    }

    private void updateHealTarget() {
        double range = this.getAttributeValue(Attributes.FOLLOW_RANGE);
        AABB searchArea = this.getBoundingBox().inflate(range);

        List<LivingEntity> candidates = this.level().getEntitiesOfClass(
            LivingEntity.class,
            searchArea,
            e -> e != this && !(e instanceof Pig0Entity) && e.isAlive() && e.getHealth() < e.getMaxHealth()
        );

        if (candidates.isEmpty()) {
            this.healTarget = null;
            this.setTarget(null);
            return;
        }

        LivingEntity bestTarget = candidates.stream()
            .max(Comparator.comparingDouble(e -> 1.0 - (e.getHealth() / e.getMaxHealth())))
            .orElse(null);

        if (bestTarget != null) {
            if (healTarget == null || !healTarget.isAlive() || healTarget.getHealth() >= healTarget.getMaxHealth()) {
                this.healTarget = bestTarget;
                this.setTarget(bestTarget);
            } else {
                double currentDamageRatio = 1.0 - (healTarget.getHealth() / healTarget.getMaxHealth());
                double bestDamageRatio = 1.0 - (bestTarget.getHealth() / bestTarget.getMaxHealth());
                if (bestDamageRatio > currentDamageRatio) {
                    this.healTarget = bestTarget;
                    this.setTarget(bestTarget);
                }
            }
        }
    }

    @Nullable
    public LivingEntity getHealTarget() {
        return healTarget;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        LivingEntity actualTarget = healTarget != null ? healTarget : target;
        if (actualTarget == null || !actualTarget.isAlive()) {
            return;
        }

        HealingSnowball snowball = new HealingSnowball(this.level(), this, actualTarget);
        double dx = actualTarget.getX() - this.getX();
        double dy = actualTarget.getEyeY() - 1.1D - snowball.getY();
        double dz = actualTarget.getZ() - this.getZ();
        double dist = Math.sqrt(dx * dx + dz * dz) * 0.2D;
        snowball.shoot(dx, dy + dist, dz, 1.6F, 12.0F);
        this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(snowball);

        this.healTarget = null;
        this.setTarget(null);
        updateHealTarget();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (dying) {
            return super.hurt(source, amount);
        }
        if (!this.level().isClientSide()) {
            dying = true;
            return super.hurt(source, Float.MAX_VALUE);
        }
        return super.hurt(source, amount);
    }
}
