package xyz.chlamydomonos.f_a_r.entities;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.NotNull;
import xyz.chlamydomonos.f_a_r.entities.utils.MainProjectileUtil;
import xyz.chlamydomonos.f_a_r.loaders.DamageSourceLoader;
import xyz.chlamydomonos.f_a_r.loaders.EntityLoader;
import xyz.chlamydomonos.f_a_r.particles.options.ProjectileParticleOptions;

import java.util.Optional;
import java.util.UUID;

public class MainProjectileEntity extends ThrowableProjectile
{
    public static final EntityDataAccessor<Byte> TYPE = SynchedEntityData.defineId(MainProjectileEntity.class, EntityDataSerializers.BYTE);
    public static final EntityDataAccessor<Float> INITIAL_HEIGHT = SynchedEntityData.defineId(MainProjectileEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Optional<UUID>> TARGET = SynchedEntityData.defineId(MainProjectileEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    public static final EntityDataAccessor<Integer> LIFETIME = SynchedEntityData.defineId(MainProjectileEntity.class, EntityDataSerializers.INT);

    private static final float MAX_DEFLECTION_ANGLE = 15f;

    private int lifeTime;
    private Entity target;

    private Vec3 initialVelocity;

    public MainProjectileEntity(EntityType<? extends ThrowableProjectile> entityType, Level level)
    {
        super(entityType, level);
        noPhysics = true;
    }

    public static MainProjectileEntity create(Level level)
    {
        return new MainProjectileEntity((EntityType<? extends ThrowableProjectile>) EntityLoader.MAIN_PROJECTILE.get(), level);
    }

    @Override
    protected void defineSynchedData()
    {
        this.entityData.define(TYPE, (byte) 0);
        this.entityData.define(INITIAL_HEIGHT, (float) 0);
        this.entityData.define(TARGET, Optional.empty());
        this.entityData.define(LIFETIME, 0);
    }

    @Override
    public void tick()
    {
        super.tick();

        lifeTime = entityData.get(LIFETIME);

        if (!level.isClientSide)
        {
            if (initialVelocity == null)
                initialVelocity = getDeltaMovement();
            if (lifeTime > 0)
                lifeTime--;
            entityData.set(LIFETIME, lifeTime);

            if (lifeTime > 0)
                deflectToTarget();
            else
                trackTarget();

            genChildren();

            float initialHeight = entityData.get(INITIAL_HEIGHT);
            float height = (float) position().y;
            if (height < initialHeight && lifeTime > 0)
            {
                var delta = getDeltaMovement();

                if (delta.y < 0)
                {
                    push(initialVelocity.x - delta.x, delta.y * -1.75f, initialVelocity.z - delta.z);
                }
            }
        }
        else
        {
            genParticles();
        }
    }

    @Override
    protected float getGravity()
    {
        return 0.01f;
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult hitResult)
    {
        super.onHitBlock(hitResult);

        if (!level.isClientSide)
        {

            level.explode(
                    this,
                    DamageSourceLoader.ELDEN_STARS.apply(this, getOwner()),
                    null,
                    hitResult.getBlockPos().getX(),
                    hitResult.getBlockPos().getY(),
                    hitResult.getBlockPos().getZ(),
                    5,
                    false,
                    Explosion.BlockInteraction.BREAK
                         );

            var entities = level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(5.0));

            for (var entity : entities)
            {
                if (entity != getOwner())
                    entity.hurt(DamageSourceLoader.ELDEN_STARS.apply(this, getOwner()), 15f);
            }
        }

        remove(RemovalReason.DISCARDED);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult hitResult)
    {
        super.onHitEntity(hitResult);

        if (!level.isClientSide)
        {
            if (lifeTime == 0)
            {
                level.explode(
                        this,
                        DamageSourceLoader.ELDEN_STARS.apply(this, getOwner()),
                        null,
                        hitResult.getEntity().getX(),
                        hitResult.getEntity().getY(),
                        hitResult.getEntity().getZ(),
                        5,
                        false,
                        Explosion.BlockInteraction.BREAK
                             );

                var entities = level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(5.0));

                for (var entity : entities)
                {
                    if (entity != getOwner())
                        entity.hurt(DamageSourceLoader.ELDEN_STARS.apply(this, getOwner()), 15f);
                }

                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    public void onRemovedFromWorld()
    {
        super.onRemovedFromWorld();

        if (level.isClientSide)
        {
            genExplosionParticles(1000, 1);
            genExplosionParticles(500, 2);
            genExplosionParticles(250, 3);
        }
    }

    private void genExplosionParticles(int particleAmount, double radius)
    {
        for (int i = 0; i < particleAmount; i++)
        {
            double phi = Math.acos(-1.0 + (2.0 * i) / particleAmount);
            double theta = Math.sqrt(Math.PI * particleAmount) * phi;
            level.addParticle(
                    new ProjectileParticleOptions(
                            1f, 0.9f, 0f, 1f,
                            0.05f, 20
                    ),
                    position().x + radius * Math.cos(theta) * Math.sin(phi),
                    position().y + radius * Math.sin(theta) * Math.sin(phi),
                    position().z + radius * Math.cos(phi),
                    0, 0, 0
                             );
        }
    }

    private void genChildren()
    {
        if (Math.random() < 0.3)
            return;

        var circleDrawer = MainProjectileUtil.getCircleDrawer(position(), getDeltaMovement());
        var point = circleDrawer.apply(0.5, Math.random() * Math.PI * 2);
        var delta = position().vectorTo(point);
        var child = ChildProjectileEntity.create(level);
        child.setOwner(getOwner());
        child.setPos(position());
        child.getEntityData().set(ChildProjectileEntity.TARGET, entityData.get(TARGET));
        child.getEntityData().set(ChildProjectileEntity.TARGET_X, (float) getDeltaMovement().x);
        child.getEntityData().set(ChildProjectileEntity.TARGET_Z, (float) getDeltaMovement().z);
        level.addFreshEntity(child);
        child.shoot(delta.x, delta.y, delta.z, 0.5f, 0.1f);
    }

    private void genParticles()
    {
        var circleDrawer = MainProjectileUtil.getCircleDrawer(position(), getDeltaMovement());

        var centerParticlePos = position();
        var centerParticleLifetime = 40;
        var centerParticleFade = 1f / centerParticleLifetime;
        level.addParticle(
                new ProjectileParticleOptions(
                        1f, 0.9f, 0f, 1f,
                        centerParticleFade, centerParticleLifetime
                ),
                centerParticlePos.x,
                centerParticlePos.y,
                centerParticlePos.z,
                0, 0, 0
                         );

        for (int i = 0; i < 4; i++)
        {
            double radius = 0.05 * (i + 1);
            for (double theta = 0; theta < Math.PI * 2; theta += 0.05)
            {
                var particlePos = circleDrawer.apply(radius, theta);
                var velocityNormalizedRandom = getDeltaMovement().scale(Math.random());
                particlePos = particlePos.add(velocityNormalizedRandom);
                float x = (float) (particlePos.x + Math.random() * 0.1 - 0.05);
                float y = (float) (particlePos.y + Math.random() * 0.1 - 0.05);
                float z = (float) (particlePos.z + Math.random() * 0.1 - 0.05);
                int lifeTime = (int) (Math.random() * 10 + 20) - i * 4;
                float fade = 1f / lifeTime;
                float r = (float) (Math.random() * 0.05 + 0.95);
                float g = (float) (Math.random() * 0.05 + 0.85);
                float b = (float) (Math.random() * 0.05);

                level.addParticle(
                        new ProjectileParticleOptions(r, g, b, 1f, fade, lifeTime),
                        x, y, z, 0, 0, 0
                                 );

            }
        }
    }

    private void deflectToTarget()
    {
        if (target == null && entityData.get(TARGET).isPresent())
            target = ((ServerLevel) level).getEntity(entityData.get(TARGET).get());


        if (target != null)
        {
            var initialXZVelocity = initialVelocity.with(Direction.Axis.Y, 0);
            var targetPos = target.position();
            var relativePos = position().vectorTo(targetPos).with(Direction.Axis.Y, 0);

            var angleToTarget = (relativePos.dot(initialXZVelocity) / (relativePos.length() * initialXZVelocity.length()));
            angleToTarget = Math.abs(angleToTarget);
            var axisToTarget = initialXZVelocity.cross(relativePos);
            angleToTarget *= 180 / Math.PI;
            if (angleToTarget > MAX_DEFLECTION_ANGLE)
                angleToTarget = MAX_DEFLECTION_ANGLE;

            var rotation = new Quaternion(new Vector3f(axisToTarget.normalize()), (float) angleToTarget, true);

            var newVelocityF = new Vector3f(initialXZVelocity);
            newVelocityF.transform(rotation);

            initialXZVelocity = new Vec3(newVelocityF);

            initialVelocity = initialXZVelocity.with(Direction.Axis.Y, initialVelocity.y);

            push(initialVelocity.x - getDeltaMovement().x, 0, initialVelocity.z - getDeltaMovement().z);
        }
        else if (getOwner() != null)
        {
            target = level.getNearestEntity(
                    LivingEntity.class,
                    TargetingConditions.DEFAULT.range(20),
                    (LivingEntity) getOwner(),
                    getX(), getY(), getZ(),
                    getBoundingBox().inflate(50)
                                           );

            if (target != null)
                entityData.set(TARGET, Optional.of(target.getUUID()));
            else
                entityData.set(TARGET, Optional.empty());
        }
    }

    private void trackTarget()
    {
        if (target == null && entityData.get(TARGET).isPresent())
            target = ((ServerLevel) level).getEntity(entityData.get(TARGET).get());

        if (target != null)
        {
            setNoGravity(true);
            initialVelocity = initialVelocity.scale(1.05);
            var targetPos = target.position();
            var relativePos = position().vectorTo(targetPos);

            var angleToTarget = (relativePos.dot(initialVelocity) / (relativePos.length() * initialVelocity.length()));
            angleToTarget = Math.abs(angleToTarget);
            var axisToTarget = initialVelocity.cross(relativePos);
            angleToTarget *= 180 / Math.PI;
            if (angleToTarget > MAX_DEFLECTION_ANGLE)
                angleToTarget = MAX_DEFLECTION_ANGLE;

            var rotation = new Quaternion(new Vector3f(axisToTarget.normalize()), (float) angleToTarget, true);

            var newVelocityF = new Vector3f(initialVelocity);
            newVelocityF.transform(rotation);

            initialVelocity = new Vec3(newVelocityF);
            push(initialVelocity.x - getDeltaMovement().x, initialVelocity.y - getDeltaMovement().y, initialVelocity.z - getDeltaMovement().z);
        }
        else if (getOwner() != null)
        {
            target = level.getNearestEntity(
                    LivingEntity.class,
                    TargetingConditions.DEFAULT.range(20),
                    (LivingEntity) getOwner(),
                    getX(), getY(), getZ(),
                    getBoundingBox().inflate(20)
                                           );

            if (target != null)
                entityData.set(TARGET, Optional.of(target.getUUID()));
            else
                entityData.set(TARGET, Optional.empty());
        }
    }
}
