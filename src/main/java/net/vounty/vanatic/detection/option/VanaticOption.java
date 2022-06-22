package net.vounty.vanatic.detection.option;

import com.google.gson.Gson;

public class VanaticOption implements Option {

    private transient final Gson gson = new Gson();

    private final String key;
    private Object value;

    VanaticOption(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public static VanaticOption of(String key, Object value) {
        return new VanaticOption(key, value);
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public String getStringValue() {
        return (String) this.value;
    }

    @Override
    public Integer getIntegerValue() {
        return (Integer) this.value;
    }

    @Override
    public Double getDoubleValue() {
        return (Double) this.value;
    }

    @Override
    public Float getFloatValue() {
        return (Float) this.value;
    }

    @Override
    public Long getLongValue() {
        return (Long) this.value;
    }

    @Override
    public <Type> Type getValue(Class<Type> clazz) {
        return this.gson.fromJson(this.gson.toJson(this.value), clazz);
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

}
