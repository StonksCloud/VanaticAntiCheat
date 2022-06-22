package net.vounty.vanatic.utils.enums;

public enum ConsoleColor {

    RESET("\033[0m", "§r", "&r"),

    BLACK("\033[0;30m", "§0", "&0"),
    RED("\033[0;31m", "§4", "&4"),
    GREEN("\033[0;32m", "§2", "&2"),
    YELLOW("\033[0;33m", "§e", "&e"),
    BLUE("\033[0;34m", "§1", "&1"),
    PURPLE("\033[0;35m", "§5", "&5"),
    CYAN("\033[0;36m", "§b", "&b"),
    WHITE("\033[0;37m", "§f", "&f"),

    BLACK_BOLD("\033[1;30m", "§0§l", "&0&l"),
    RED_BOLD("\033[1;31m", "§4§l", "&4&l"),
    GREEN_BOLD("\033[1;32m", "§2§l", "&2&l"),
    YELLOW_BOLD("\033[1;33m", "§6§l", "&6&l"),
    BLUE_BOLD("\033[1;34m", "§1§l", "&1&l"),
    PURPLE_BOLD("\033[1;35m", "§5§l", "&5&l"),
    CYAN_BOLD("\033[1;36m", "§3§l", "&3&l"),
    WHITE_BOLD("\033[1;37m", "§f§l", "&f&l"),

    BLACK_BRIGHT("\033[0;90m", "§8", "&8"),
    RED_BRIGHT("\033[0;91m", "§c", "&c"),
    GREEN_BRIGHT("\033[0;92m", "§e", "&e"),
    YELLOW_BRIGHT("\033[0;93m", "§e", "&e"),
    BLUE_BRIGHT("\033[0;94m", "§1", "&1"),
    PURPLE_BRIGHT("\033[0;95m", "§d", "&d"),
    CYAN_BRIGHT("\033[0;96m", "§b", "&b"),
    WHITE_BRIGHT("\033[0;97m", "§7", "&7"),

    BLACK_BRIGHT_BOLD("\033[1;90m", "§8§l", "&8&l"),
    RED_BRIGHT_BOLD("\033[1;91m", "§c§l", "&c&l"),
    GREEN_BRIGHT_BOLD("\033[1;92m", "§e§l", "&e&l"),
    YELLOW_BRIGHT_BOLD("\033[1;93m", "§e§l", "&e&l"),
    BLUE_BRIGHT_BOLD("\033[1;94m", "§1§l", "&1&l"),
    PURPLE_BRIGHT_BOLD("\033[1;95m", "§d§l", "&d&l"),
    CYAN_BRIGHT_BOLD("\033[1;96m", "§b§l", "&b&l"),
    WHITE_BRIGHT_BOLD("\033[1;97m", "§7§l", "&7&l"),

    BLACK_UNDERLINE("\033[4;30m", "§0§n", "&0&n"),
    RED_UNDERLINE("\033[4;31m", "§4§n", "&4&n"),
    GREEN_UNDERLINE("\033[4;32m", "§2§n", "&2&n"),
    YELLOW_UNDERLINE("\033[4;33m", "§e§n", "&e&n"),
    BLUE_UNDERLINE("\033[4;34m", "§1§n", "&1&n"),
    PURPLE_UNDERLINE("\033[4;35m", "§5§n", "&5&n"),
    CYAN_UNDERLINE("\033[4;36m", "§b§n", "&b&n"),
    WHITE_UNDERLINE("\033[4;37m", "§f§n", "&f&n");

    private final String ansi;
    private final String[] codes;

    ConsoleColor(String ansi, String... codes) {
        this.ansi = ansi;
        this.codes = codes;
    }

    public static String replace(String message) {
        for (ConsoleColor value : ConsoleColor.values()) {
            for (String code : value.getCodes()) {
                if (message.contains(code)) {
                    message = message.replace(code, value.getAnsi());
                }
            }
        }
        return message;
    }

    public String getAnsi() {
        return ansi;
    }

    public String[] getCodes() {
        return codes;
    }

}