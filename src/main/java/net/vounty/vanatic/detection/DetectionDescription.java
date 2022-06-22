package net.vounty.vanatic.detection;

import net.vounty.vanatic.utils.enums.DetectionCategory;
import net.vounty.vanatic.utils.enums.DetectionStatus;
import net.vounty.vanatic.utils.enums.DetectionType;

public interface DetectionDescription {

    String getName();
    DetectionType getType();
    DetectionCategory getCategory();
    DetectionStatus getStatus();

}
