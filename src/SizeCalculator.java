import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class SizeCalculator {

    public static char[] sizeMultipliers = {'B', 'K', 'M', 'G', 'T'};
//    private static HashMap<Character, Integer> char2multiplier = getMultipliers();

    // TODO: метод правильной печати размеров: 24B, 234Kb, 36Mb, 34Gb, 42Tb
//    public static String getHumanReadableSize(long size) {
//        Map<String, Long> volume = new HashMap<>();
//        volume.put("B", size);
//        volume.put("Kb", Math.round((double) size / Math.pow(2, 10)*100)/100);
//        volume.put("Mb", Math.round((double) size / Math.pow(2, 20)*100)/100);
//        volume.put("Gb", Math.round(size / Math.pow(2, 30)));
//        volume.put("Tb", Math.round(size / Math.pow(2, 40)));
//        Map<String, Long> sorted = volume
//                .entrySet()
//                .stream()
//                .sorted(comparingByValue())
//                .collect(
//                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
//                                LinkedHashMap::new));
//        return sorted.toString();
//    }

    public static String getHumanReadableSize(long size) {
        for (int i = 0; i < sizeMultipliers.length; i++) {
            double value = ((double) size) / Math.pow(1024, i);
            if (value < 1024) {
                return Math.round(value * 100)/100. + " " + sizeMultipliers[i] +  // "" добавили для преобразования в строку
                        (i > 0 ? "b" : "");
            }
        }
        return "Very big!";
    }

    // TODO: метод правильной печати размеров: 24B, 234K, 36M, 34G, 42T в байтах
    // 235K => 240640
    public static long getSizeFromHumanReadable(String size) {

        long value = 0;
        if (size.contains("B")) {
            value = Integer.parseInt(size.replaceAll("[^0-9]", ""));
        }
        if (size.contains("K")) {
            int k = Integer.parseInt(size.replaceAll("[^0-9]", ""));
            value = (long) (k * Math.pow(2,10));
        }
        if (size.contains("M")) {
            int m = Integer.parseInt(size.replaceAll("[^0-9]", ""));
            value = (long) (m * Math.pow(2,20));
        }
        if (size.contains("G")) {
            int g = Integer.parseInt(size.replaceAll("[^0-9]", ""));
            value = (long) (g * Math.pow(2,30));
        }
        if (size.contains("T")) {
            int t = Integer.parseInt(size.replaceAll("[^0-9]", ""));
            value = (long) (t * Math.pow(2,40));
        }
        return value;
    }

//    public static long getSizeFromHumanReadable(String size) {
//       char sizeFactor = size
//                .replaceAll("[^0-9\\s+]+", "")
//                .charAt(0);  // возвращает значение char по указанному индексу
//        int multiplier = char2multiplier.get(sizeFactor); // получаем множитель
//        return multiplier * Long.valueOf(size.replaceAll("[^0-9]", ""));
//    }
//
//    private static HashMap<Character, Integer> getMultipliers() {
//
//        HashMap<Character, Integer> char2multiplier = new HashMap<>();
//        for (int i = 0; i < sizeMultipliers.length; i++) {
//            char2multiplier.put(
//                    sizeMultipliers[i],
//                    (int) Math.pow(1024, i)
//            );
//        }
//        return char2multiplier;
//    }
}

