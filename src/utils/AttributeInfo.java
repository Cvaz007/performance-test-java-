package utils;

public class AttributeInfo {
    private final String attributeName;
    private final String attributeType;
    private Object value;

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public AttributeInfo(String attributeName, String attributeType) {
        this.attributeName = attributeName;
        this.attributeType = attributeType;
    }

    @Override
    public String toString() {
        return attributeName + ": " + attributeType;
    }
}
