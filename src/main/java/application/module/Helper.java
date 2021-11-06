package application.module;

import org.springframework.stereotype.Component;

@Component
public class Helper {

    public static String bytesToString(byte[] bytes) {
        StringBuilder builder = new StringBuilder("[");

        for (byte b : bytes) {
            builder.append(b);
            builder.append(',');
        }

        builder.setCharAt(builder.lastIndexOf(","), ']');

        return builder.toString();
    }

    public static byte[] stringToBytes(String str) {
        str = str.substring(1, str.length()-1);

        String[] strings = str.split(",");

        byte[] result = new byte[strings.length];

        for (int i = 0; i < result.length; i++)
            result[i] = Byte.parseByte(strings[i].strip());

        return result;
    }

}
