package xyz.chlamydomonos.f_a_r.entities;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import xyz.chlamydomonos.f_a_r.entities.utils.MainProjectileUtil;
import xyz.chlamydomonos.f_a_r.loaders.DamageSourceLoader;
import xyz.chlamydomonos.f_a_r.loaders.EntityLoader;

import java.util.Optional;
import java.util.UUID;

public class MainProjectileEntity extends ThrowableProjectile
{
    public static final EntityDataAccessor<Byte> TYPE = SynchedEntityData.defineId(MainProjectileEntity.class, EntityDataSerializers.BYTE);
    public static final EntityDataAccessor<Float> INITIAL_HEIGHT = SynchedEntityData.defineId(MainProjectileEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Optional<UUID>> TARGET = SynchedEntityData.defineId(MainProjectileEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    public static final EntityDataAccessor<Optional<UUID>> OWNER = SynchedEntityData.defineId(MainProjectileEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    private int lifeTime;
    private Entity owner;

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
        this.entityData.define(TYPE, (byte)0);
        this.entityData.define(INITIAL_HEIGHT, (float)0);
        this.entityData.define(TARGET, Optional.empty());
        this.entityData.define(OWNER, Optional.empty());
    }

    @Override
    public void tick()
    {
        super.tick();

        if(!level.isClientSide)
        {
            if(initialVelocity == null)
                initialVelocity = getDeltaMovement();

            lifeTime--;

            float initialHeight = entityData.get(INITIAL_HEIGHT);
            float height = (float) position().y;
            if(height < initialHeight && lifeTime > 0)
            {
                var delta = getDeltaMovement();

                if(delta.y < 0)
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

        if(!level.isClientSide)
        {
            if(owner == null && entityData.get(OWNER).isPresent())
            {
                var ownerUUID = entityData.get(OWNER).get();
                owner = ((ServerLevel) level).getEntity(ownerUUID);
            }

            level.explode(
                    this,
                    DamageSourceLoader.ELDEN_STARS.apply(this, owner),
                    null,
                    hitResult.getBlockPos().getX(),
                    hitResult.getBlockPos().getY(),
                    hitResult.getBlockPos().getZ(),
                    5,
                    false,
                    Explosion.BlockInteraction.BREAK
                         );
        }

        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult hitResult)
    {
        super.onHitEntity(hitResult);

        if(!level.isClientSide)
        {
            if(owner == null && entityData.get(OWNER).isPresent())
            {
                var ownerUUID = entityData.get(OWNER).get();
                owner = ((ServerLevel) level).getEntity(ownerUUID);
            }

            if(lifeTime == 0)
            {
                level.explode(
                        this,
                        DamageSourceLoader.ELDEN_STARS.apply(this, owner),
                        null,
                        hitResult.getEntity().getX(),
                        hitResult.getEntity().getY(),
                        hitResult.getEntity().getZ(),
                        5,
                        false,
                        Explosion.BlockInteraction.BREAK
                             );
            }
        }
    }

    private void genParticles()
    {
        var circleDrawer = MainProjectileUtil.getCircleDrawer(position(), getDeltaMovement());

        for(double r = 0.05; r <= 0.21; r += 0.05)
        {
            for (double theta = 0; theta <= Math.PI * 2; theta += r * 0.5)
            {
                var particlePos = circleDrawer.apply(r, theta);
                //level.addParticle();
            }
        }
    }

    public int getLifeTime()
    {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime)
    {
        this.lifeTime = lifeTime;
    }
}
