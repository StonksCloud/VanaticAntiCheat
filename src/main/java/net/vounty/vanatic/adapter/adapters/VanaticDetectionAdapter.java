package net.vounty.vanatic.adapter.adapters;

import net.vounty.vanatic.adapter.VanaticAdapter;
import net.vounty.vanatic.api.API;
import net.vounty.vanatic.detection.Detection;
import net.vounty.vanatic.detection.DetectionDescription;
import net.vounty.vanatic.detection.VanaticDetection;
import net.vounty.vanatic.detection.detections.exploit.*;
import net.vounty.vanatic.detection.detections.movement.*;
import net.vounty.vanatic.utils.enums.DetectionCategory;
import net.vounty.vanatic.utils.enums.DetectionMode;
import net.vounty.vanatic.utils.enums.DetectionToggleCallback;
import net.vounty.vanatic.utils.enums.DetectionType;

import java.util.*;
import java.util.function.Consumer;

public class VanaticDetectionAdapter extends VanaticAdapter implements DetectionAdapter {

    private final List<Detection> detections;
    private final Map<Detection, DetectionMode> detectionMode;

    public VanaticDetectionAdapter(API api) {
        super(api);
        this.detections = new LinkedList<>();
        this.detectionMode = new LinkedHashMap<>();
    }

    @Override
    public void registerDetections() {
        if (this.getDetections().size() > 0)
            return;
        this.registerDetection(new ExploitA());
        this.registerDetection(new ExploitB());
        this.registerDetection(new ExploitC());
        this.registerDetection(new WalkSpeedA());
        this.toggleAutoDetectionModes();
    }

    @Override
    public void unregisterDetections() {
        if (this.getDetections().size() == 0)
            return;
        this.getDetections().forEach(detection ->
                this.unregisterDetection(detection.getDescription()));
        this.getAPI().getPacketEvents().unregisterAllListeners();
    }

    @Override
    public void resetDetectionConfigurations(Consumer<Boolean> runThen) {
        try {
            this.getDetections().forEach(Detection::resetConfiguration);
            runThen.accept(true);
        } catch (Exception exception) {
            runThen.accept(false);
            exception.printStackTrace();
        }
    }

    @Override
    public void toggleDetectionModes(DetectionMode mode) {
        this.getDetections().forEach(detection -> this.toggleDetectionMode(detection, mode));
    }

    @Override
    public void toggleAutoDetectionModes() {
        this.getDetections().forEach(detection -> this.toggleDetectionMode(detection, detection.getConfiguration().getMode()));
    }

    @Override
    public DetectionToggleCallback toggleDetectionMode(Detection detection, DetectionMode mode) {
        final Optional<Detection> optionalDetection = this.getDetection(detection.getDescription().getName(), detection.getDescription().getType());
        if (optionalDetection.isPresent()) {
            final Detection targetDetection = optionalDetection.get();
            targetDetection.getConfiguration().setMode(mode);
            this.getAPI().getMessageAdapter().sendConsoleMessage("vanatic.adapter.detection", mode.equals(DetectionMode.ENABLED) ? 0 : 1, targetDetection.getAbsoluteName());
            if (!this.getMode(detection).equals(mode)) {
                this.getDetectionMode().put(targetDetection, mode);
                switch (mode) {
                    case ENABLED -> targetDetection.onMount();
                    case DISABLED -> targetDetection.onTerminate();
                }
                return DetectionToggleCallback.SUCCESS;
            }
            return DetectionToggleCallback.ALREADY_SET;
        }
        return DetectionToggleCallback.NOT_EXIST;
    }

    private void registerDetection(VanaticDetection detection) {
        final Optional<Detection> optionalDetection = this.getDetection(detection.getDescription().getName(), detection.getDescription().getType());
        if (optionalDetection.isEmpty()) {
            this.getDetections().add(detection);
            detection.onInitialize();
            this.getAPI().getPacketEvents().registerListener(detection);
            this.getAPI().getVanatic().registerEvent(detection);
            detection.saveConfiguration(false);
            this.getAPI().getMessageAdapter().sendConsoleMessage("vanatic.adapter.detection", 2, detection.getAbsoluteName());
        }
    }

    private void unregisterDetection(DetectionDescription description) {
        final Optional<Detection> optionalDetection = this.getDetection(description.getName(), description.getType());
        optionalDetection.ifPresent(detection -> {
            detection.saveConfiguration(false);
            detection.onTerminate();
            this.getAPI().getMessageAdapter().sendConsoleMessage("vanatic.adapter.detection", 3, detection.getAbsoluteName());
            this.toggleDetectionMode(detection, DetectionMode.DISABLED);
            this.getDetections().remove(detection);
        });
    }

    @Override
    public Optional<Detection> getDetection(String name, DetectionType type) {
        for (final Detection detection : this.getDetections()) {
            final DetectionDescription description = detection.getDescription();
            if (description.getName().equals(name) &&
                description.getType().equals(type)) {
                return Optional.of(detection);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Detection> getDetection(String name) {
        for (final Detection detection : this.getDetections()) {
            if (detection.getAbsoluteName().equals(name)) {
                return Optional.of(detection);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Detection> getDetections(String name) {
        return this.getDetections().stream().filter(detection ->
                detection.getDescription().getName().equals(name)).toList();
    }

    @Override
    public List<Detection> getDetections(DetectionCategory category) {
        return this.getDetections().stream().filter(detection ->
                detection.getDescription().getCategory().equals(category)).toList();
    }

    @Override
    public DetectionMode getMode(Detection detection) {
        return this.getDetectionMode().getOrDefault(detection, DetectionMode.DISABLED);
    }

    @Override
    public Map<Detection, DetectionMode> getDetectionMode() {
        return this.detectionMode;
    }

    /**
     * Get all registered detections from Vanatic.
     * @return - Returns all registered detections.
     */
    @Override
    public List<Detection> getDetections() {
        return this.detections;
    }

}