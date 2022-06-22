package net.vounty.vanatic.detection.value;

public class VanaticValue implements Value {

    private final String prefix;
    private final Object value;

    VanaticValue(String prefix, Object value) {
        this.prefix = prefix;
        this.value = value;
    }

    public static VanaticValue of(String prefix, Object value) {
        return new VanaticValue(prefix, value);
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

}
