package io.github.xenfork.mmpd.api;

import io.github.xenfork.mmpd.api.resources.IResourceLocation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Builder()
@Getter
@Setter
public class IFood {
    int nutrition;
    float saturation;
    boolean canAlwaysEat;
    /**
     * 1.14.4 ~ 1.16.5
     */
    boolean isMeat;
    boolean fastFood;
    public Map<IMobEffectInstance, Float> effects;
}
