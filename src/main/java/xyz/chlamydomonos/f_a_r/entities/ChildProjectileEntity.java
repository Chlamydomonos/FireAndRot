package xyz.chlamydomonos.f_a_r.entities;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import xyz.chlamydomonos.f_a_r.loaders.DamageSourceLoader;
import xyz.chlamydomonos.f_a_r.loaders.EntityLoader;
import xyz.chlamydomonos.f_a_r.particles.options.ProjectileParticleOptions;

import java.util.Optional;
import java.util.UUID;

public class ChildProjectileEntity extends ThrowableProjectile
{
    public static final EntityDataAccessor<Byte> TYPE = SynchedEntityData.defineId(ChildProjectileEntity.class, EntityDataSerializers.BYTE);
    public static final EntityDataAccessor<Optional<UUID>> TARGET = SynchedEntityData.defineId(ChildProjectileEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    public static final EntityDataAccessor<Float> TARGET_X = SynchedEntityData.defineId(ChildProjectileEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> TARGET_Z = SynchedEntityData.defineId(ChildProjectileEntity.class, EntityDataSerializers.FLOAT);

    private static final float MAX_DEFLECTION_ANGLE = 25f;

    private Entity target;

    private Vec3 initialVelocity;

    public ChildProjectileEntity(EntityType<? extends ThrowableProjectile> type, Level level)
    {
        super(type, level);
        noPhysics = true;
        setNoGravity(true);
    }

    public static ChildProjectileEntity create(Level level)
    {
        return new ChildProjectileEntity((EntityType<? extends ThrowableProjectile>) EntityLoader.CHILD_PROJECTILE.get(), level);
    }

    @Override
    protected void defineSynchedData()
    {
        this.entityData.define(TYPE, (byte)0);
        this.entityData.define(TARGET, Optional.empty());
        this.entityData.define(TARGET_X, 0f);
        this.entityData.define(TARGET_Z, 0f);
    }

    @Override
    protected float getGravity()
    {
        return 0f;
    }

    @Override
    public void tick()
    {
        super.tick();

        if(initialVelocity == null)
            initialVelocity = getDeltaMovement();

        if(!level.isClientSide)
        {

            if (target == null && entityData.get(TARGET).isPresent())
                target = ((ServerLevel) level).getEntity(entityData.get(TARGET).get());

            initialVelocity = initialVelocity.scale(1.05);

            Vec3 relativePos;

            if(target != null)
            {
                var targetPos = target.position();
                relativePos = position().vectorTo(targetPos);
            }
            else
                relativePos = new Vec3(entityData.get(TARGET_X), 0, entityData.get(TARGET_Z));

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

            if (target == null && getOwner() != null)
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
        else
        {
            for(int i = 0; i < 10; i++)
            {
                level.addParticle(
                        new ProjectileParticleOptions(1f, 0.9f, 0f, 0.5f, 0.1f, 20),
                        position().x - getDeltaMovement().x * i / 10,
                        position().y - getDeltaMovement().y * i / 10,
                        position().z - getDeltaMovement().z * i / 10,
                        0, 0, 0
                                 );
            }
        }
    }

    @Override
    protected void onHit(@NotNull HitResult result)
    {
        super.onHit(result);

        if(!level.isClientSide)
        {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result)
    {
        super.onHitEntity(result);

        result.getEntity().hurt(DamageSourceLoader.ELDEN_STARS.apply(this, getOwner()), 1);
    }
}
