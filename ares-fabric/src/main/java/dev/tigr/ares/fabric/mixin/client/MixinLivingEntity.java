package dev.tigr.ares.fabric.mixin.client;

import dev.tigr.ares.core.Ares;
import dev.tigr.ares.fabric.event.client.LivingDeathEvent;
import dev.tigr.ares.fabric.event.movement.ElytraMoveEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Tigermouthbear 9/26/20
 */
@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    private final LivingEntity entity = ((LivingEntity) (Object) this);

    @Inject(method = "onDeath", at = @At("HEAD"))
    public void death(DamageSource damageSource, CallbackInfo ci) {
        Ares.EVENT_MANAGER.post(new LivingDeathEvent(entity, damageSource));
    }

    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V", ordinal = 2))
    public void elytraMove(LivingEntity livingEntity, MovementType movementType, Vec3d vec3d) {
        ElytraMoveEvent elytraMoveEvent = Ares.EVENT_MANAGER.post(new ElytraMoveEvent(vec3d.x, vec3d.y, vec3d.z));
        livingEntity.move(movementType, new Vec3d(elytraMoveEvent.x, elytraMoveEvent.y, elytraMoveEvent.z));
    }
}
