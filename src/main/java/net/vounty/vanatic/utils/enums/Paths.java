package net.vounty.vanatic.utils.enums;

import java.io.File;
import java.util.Arrays;

public enum Paths {

    VANATIC(".//Vanatic//"),

    VANATIC_DETECTIONS(VANATIC.getPath() + "//detections//"),
    VANATIC_DETECTIONS_COMBAT(VANATIC_DETECTIONS.getPath() + "//combat//"),
    VANATIC_DETECTIONS_EXPLOIT(VANATIC_DETECTIONS.getPath() + "//exploit//"),
    VANATIC_DETECTIONS_MOVEMENT(VANATIC_DETECTIONS.getPath() + "//movement//"),
    VANATIC_DETECTIONS_OTHER(VANATIC_DETECTIONS.getPath() + "//other//"),

    VANATIC_CONFIGURATION(VANATIC.getPath() + "//configuration.json");

    private final String path;

    Paths(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public static void createPaths() {
        Arrays.stream(Paths.values())
                .filter(path -> path.getPath().endsWith("//"))
                .forEach(path -> {
            final File pathFolder = new File(path.getPath());
            if (!pathFolder.exists()) pathFolder.mkdirs();
        });
    }

    public static Paths getPathCategory(DetectionCategory category) {
        for (final Paths path : Paths.values()) {
            if (path.name().endsWith(category.name())) {
                return path;
            }
        }
        return Paths.VANATIC_DETECTIONS;
    }

}
