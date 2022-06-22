package net.vounty.vanatic.detection;

import net.vounty.vanatic.detection.option.VanaticOption;
import net.vounty.vanatic.utils.enums.DetectionMode;

import java.util.List;

public interface DetectionConfiguration {

    void setMode(DetectionMode mode);

    DetectionMode getMode();
    VanaticDetectionConfiguration.Settings getSettings();
    List<VanaticOption> getOptions();

}
