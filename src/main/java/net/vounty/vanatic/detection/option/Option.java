package net.vounty.vanatic.detection.option;

public interface Option {

    void setValue(Object value);

    String getKey();
    Object getValue();
    String getStringValue();
    Integer getIntegerValue();
    Double getDoubleValue();
    Float getFloatValue();
    Long getLongValue();
    <Type> Type getValue(Class<Type> clazz);

}
