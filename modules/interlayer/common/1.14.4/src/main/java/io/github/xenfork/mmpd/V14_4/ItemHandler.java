package io.github.xenfork.mmpd.V14_4;

import io.github.xenfork.mmpd.api.IFood;
import io.github.xenfork.mmpd.api.IItem;
import io.github.xenfork.mmpd.api.IMobEffectInstance;
import io.github.xenfork.mmpd.api.resources.IResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class ItemHandler extends Item {
    public ItemHandler(Properties properties) {
        super(properties);
    }

    public ItemHandler(IItem item) {
        super(apply(new Properties(), item));

    }

    public static Properties apply(Properties props, IItem item) {
        IFood food = item.getFood();
        FoodProperties.Builder builder = new FoodProperties.Builder();
        if (food.isCanAlwaysEat()) builder.alwaysEat();
        if (food.isFastFood()) builder.fast();
        builder.nutrition(food.getNutrition()).saturationMod(food.getSaturation());
        food.effects.forEach((key, value) -> builder.effect(mobEffectInstance(key), value));
        props.food(builder.build());
        if (item.getMaxDamage() > 0) props.durability(item.getMaxDamage());
        else if (item.getMaxCount() > 0) props.stacksTo(item.getMaxCount());

        return props;
    }

    public static ResourceLocation location(IResourceLocation location) {
        return ResourceLocation.tryParse(location.getModid() + ":" + location.getPath());
    }

    public static MobEffectInstance mobEffectInstance(IMobEffectInstance instance) {
        return new MobEffectInstance(Registry.MOB_EFFECT.get(location(instance.getEffectLocation())), instance.getDuration(), instance.getAmplifier(), instance.isAmbient(), instance.isVisible(), instance.isShowIcon());
    }
}
