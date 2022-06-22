package net.vounty.vanatic.adapter.adapters;

import net.vounty.vanatic.detection.Detection;
import net.vounty.vanatic.utils.enums.DetectionCategory;
import net.vounty.vanatic.utils.enums.DetectionMode;
import net.vounty.vanatic.utils.enums.DetectionToggleCallback;
import net.vounty.vanatic.utils.enums.DetectionType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public interface DetectionAdapter {

    void registerDetections();
    void unregisterDetections();
    void resetDetectionConfigurations(Consumer<Boolean> runThen);

    void toggleDetectionModes(DetectionMode mode);
    void toggleAutoDetectionModes();
    DetectionToggleCallback toggleDetectionMode(Detection detection, DetectionMode mode);

    Optional<Detection> getDetection(String name, DetectionType type);
    Optional<Detection> getDetection(String name);
    List<Detection> getDetections(String name);
    List<Detection> getDetections(DetectionCategory category);

    DetectionMode getMode(Detection detection);
    Map<Detection, DetectionMode> getDetectionMode();

    /**
     * Get all registered detections from Vanatic.
     * @return - Returns all registered detections.
     */
    List<Detection> getDetections();

}
