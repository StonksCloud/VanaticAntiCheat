package net.vounty.vanatic.utils.enums;

public enum BukkitColor {

    BLACK('0'),
    WHITE('f'),
    INDIGO('9'),

    DARK_BLUE('1'),
    DARK_GREEN('2'),
    DARK_CYAN('3'),
    DARK_RED('4'),
    DARK_PURPLE('5'),
    DARK_YELLOW('6'),
    DARK_GRAY('8'),

    LIGHT_GRAY('7'),
    LIGHT_GREEN('a'),
    LIGHT_BLUE('b'),
    LIGHT_RED('c'),
    LIGHT_PURPLE('d'),
    LIGHT_YELLOW('e');

    private final char code;

    BukkitColor(char code) {
        this.code = code;
    }

    public static String replace(String message) {
        return message.replaceAll("&", "§");
    }

    public static BukkitColor getOrDefault(String value, BukkitColor defaultValue) {
        for (final BukkitColor bukkitColor : BukkitColor.values()) {
            if (bukkitColor.name().equals(value)) {
                return bukkitColor;
            }
        }
        return defaultValue;
    }

    public char getCode() {
        return this.code;
    }

}
