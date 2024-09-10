package io.github.xenfork.mmpd.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class IItem {
    IFood food;
    int maxCount;
    int maxDamage;


}
