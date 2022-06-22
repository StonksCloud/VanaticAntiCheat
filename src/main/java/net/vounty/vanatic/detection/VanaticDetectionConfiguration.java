package net.vounty.vanatic.detection;

import net.vounty.vanatic.detection.option.VanaticOption;
import net.vounty.vanatic.utils.annotations.DetectionInformation;
import net.vounty.vanatic.utils.enums.DetectionMode;

import java.util.List;

public class VanaticDetectionConfiguration implements DetectionConfiguration {

    private DetectionMode mode;
    private final Settings settings;
    private final List<VanaticOption> options;

    public VanaticDetectionConfiguration(DetectionMode mode, Settings settings, List<VanaticOption> options) {
        this.mode = mode;
        this.settings = settings;
        this.options = options;
    }

    public static VanaticDetectionConfiguration of(Detection detection) {
        return new VanaticDetectionConfiguration(DetectionMode.ENABLED,
                Settings.getDefaultConfiguration(detection.getKickAmount(), detection.getBanAmount()), detection.getOptions());
    }

    @Override
    public DetectionMode getMode() {
        return this.mode;
    }

    @Override
    public Settings getSettings() {
        return this.settings;
    }

    @Override
    public void setMode(DetectionMode mode) {
        this.mode = mode;
    }

    @Override
    public List<VanaticOption> getOptions() {
        return this.options;
    }

    public static class Settings {

        private Boolean isKickEnabled, isBanEnabled;
        private final Integer kickAmount, banAmount;

        public Settings(Boolean isKickEnabled, Boolean isBanEnabled, Integer kickAmount, Integer banAmount) {
            this.isKickEnabled = isKickEnabled;
            this.isBanEnabled = isBanEnabled;
            this.kickAmount = kickAmount;
            this.banAmount = banAmount;
        }

        public static Settings getDefaultConfiguration(Integer kickAmount, Integer banAmount) {
            return new Settings(true, false, kickAmount, banAmount);
        }

        public void setKickEnabled(Boolean kickEnabled) {
            isKickEnabled = kickEnabled;
        }

        public void setBanEnabled(Boolean banEnabled) {
            isBanEnabled = banEnabled;
        }

        public Boolean isKickEnabled() {
            return this.isKickEnabled;
        }

        public Boolean isBanEnabled() {
            return this.isBanEnabled;
        }

        public Integer getKickAmount() {
            return this.kickAmount;
        }

        public Integer getBanAmount() {
            return this.banAmount;
        }

    }

}
