package utils;

import javax.swing.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Utils {
    public <T> T[] listToArray(List<T> list) {
        T[] array = (T[]) new Object[list.size()];
        int i = 0;
        for (T iterator : list) {
            array[i++] = iterator;
        }
        return array;
    }

    public Object getOption(List<?> list, String message) {
        Object[] options = listToArray(list);
        return JOptionPane.showInputDialog(
                null,
                message,
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
    }

    public List<AttributeInfo> extractAttributesInfo(Class<?> clazz) {
        List<AttributeInfo> attributes = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            String name = field.getName();
            String type = field.getType().getSimpleName();
            attributes.add(new AttributeInfo(name, type));
        }
        return attributes;
    }

    public <T> T createObjectDynamically(Class<T> clazz, List<AttributeInfo> listAttributes) {
        for (AttributeInfo attributeInfo : listAttributes) {
            if (attributeInfo.getAttributeType().equalsIgnoreCase("int")) {
                int intValue = Integer.parseInt(JOptionPane.showInputDialog("Type " + attributeInfo.getAttributeName()));
                attributeInfo.setValue(intValue);
            }
            if (attributeInfo.getAttributeType().equalsIgnoreCase("string")) {
                String stringValue = JOptionPane.showInputDialog("Type " + attributeInfo.getAttributeName());
                attributeInfo.setValue(stringValue);
            }
            if (attributeInfo.getAttributeType().equalsIgnoreCase("double")) {
                double doubleValue = Double.parseDouble(JOptionPane.showInputDialog("Type " + attributeInfo.getAttributeName()));
                attributeInfo.setValue(doubleValue);
            }
            /* EN ESTE CASO NO NECESITO DATOS TIPO FECHA
            if (attributeInfo.getAttributeType().equalsIgnoreCase("date")) {
                String dateString = JOptionPane.showInputDialog("Type " + attributeInfo.getAttributeName() +"  dd-MM-yyyy");
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date dateValue = format.parse(dateString);
                    attributeInfo.setValue(dateValue);
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(null, "Format error");
                }
            }
            if (attributeInfo.getAttributeType().equalsIgnoreCase("time")) {
                String timeString = JOptionPane.showInputDialog("Type " + attributeInfo.getAttributeName()+"  HH:mm:ss");
                DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
                try {
                    LocalTime timeValue = LocalTime.parse(timeString, format);
                    attributeInfo.setValue(Time.valueOf(timeValue));
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(null, "Format error");
                }
            }*/
        }

        try {
            // Obtén el constructor de la clase correspondiente
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(false); // Si el constructor es privado

            // Crea una instancia de la clase
            T object = constructor.newInstance();

            // Configura los valores de los atributos utilizando reflexión
            for (AttributeInfo attributeInfo : listAttributes) {
                Field field = clazz.getDeclaredField(attributeInfo.getAttributeName());
                field.setAccessible(true); // Si el atributo es privado
                field.set(object, attributeInfo.getValue());
            }

            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
