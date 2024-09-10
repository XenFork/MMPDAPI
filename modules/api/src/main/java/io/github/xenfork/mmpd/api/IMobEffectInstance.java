package io.github.xenfork.mmpd.api;

import io.github.xenfork.mmpd.api.resources.IResourceLocation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class IMobEffectInstance {
    IResourceLocation effectLocation;
    int duration;
    int amplifier;
    /**
     * 1.14.4
     */
    boolean splash;
    boolean ambient;
    /**
     * 1.14.4
     * only client
     */
    boolean noCounter;
    boolean visible;
    boolean showIcon;
    /**
     * ! 1.14.4
     */
    IMobEffectInstance hiddenEffect;
}
