package net.vounty.vanatic.detection;

import net.vounty.vanatic.utils.annotations.DetectionInformation;
import net.vounty.vanatic.utils.enums.DetectionCategory;
import net.vounty.vanatic.utils.enums.DetectionStatus;
import net.vounty.vanatic.utils.enums.DetectionType;

public class VanaticDetectionDescription implements DetectionDescription {

    private final String name;
    private final DetectionType type;
    private final DetectionCategory category;
    private final DetectionStatus status;

    VanaticDetectionDescription(DetectionInformation information) {
        this.name = information.name();
        this.type = information.type();
        this.category = information.category();
        this.status = information.status();
    }

    public static VanaticDetectionDescription of(DetectionInformation information) {
        return new VanaticDetectionDescription(information);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public DetectionType getType() {
        return this.type;
    }

    @Override
    public DetectionCategory getCategory() {
        return this.category;
    }

    @Override
    public DetectionStatus getStatus() {
        return this.status;
    }

}