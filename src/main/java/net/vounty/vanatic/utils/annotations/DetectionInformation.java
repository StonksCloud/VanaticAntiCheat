package net.vounty.vanatic.utils.annotations;

import net.vounty.vanatic.utils.enums.DetectionCategory;
import net.vounty.vanatic.utils.enums.DetectionStatus;
import net.vounty.vanatic.utils.enums.DetectionType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DetectionInformation {

    String name();
    DetectionType type();
    DetectionCategory category();
    DetectionStatus status();
    int kickAmount();
    int banAmount();

}