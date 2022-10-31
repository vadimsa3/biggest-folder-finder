import java.io.File;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;


public class Main {
    public static void main(String[] args) {

        String folderPath = "C:/Users/savchuk/Desktop/";
        File file = new File(folderPath);

        long start = System.currentTimeMillis();

        FolderSizeCalculator calculator = new FolderSizeCalculator(file);
        ForkJoinPool pool = new ForkJoinPool(); // управляет кол-вом одновременно работающих потоков
        long size = pool.invoke(calculator);
        System.out.println(size); // возвращаем размер

        System.out.println(getHumanReadableSize(size));
        System.out.println(getSizeFromHumanReadable("1T"));

//      System.out.println(getFolderSize(file));

        long duration = System.currentTimeMillis() - start;
        System.out.println(duration);
    }

    // создаем рекурсивный метод (вызывает сам себя) для подсчета размера папки суммируя размеры файлов
    public static long getFolderSize(File folder) {
        if (folder.isFile()) {      // проверяем является-ли папка файлом
            return folder.length(); // если да, получаем размер файла
        }
        long sum = 0;
        File[] files = folder.listFiles();  // получаем список файлов и папок в этой папке
        for (File file : files) {           // проходим по всем файлам
            sum += getFolderSize(file);     // для них метод вызывает себя и суммирует размеры всех файлов всех папок
        }
        return sum;
    }

    // TODO: метод правильной печати размеров: 24B, 234Kb, 36Mb, 34Gb, 42Tb
    public static String getHumanReadableSize(long size) {
        Map<String, Long> volume = new HashMap<>();
        volume.put("B", size);
        volume.put("Kb", Math.round(size / Math.pow(2, 10)));
        volume.put("Mb", Math.round(size / Math.pow(2, 20)));
        volume.put("Gb", Math.round(size / Math.pow(2, 30)));
        volume.put("Tb", Math.round(size / Math.pow(2, 40)));
        Map<String, Long> sorted = volume
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        return sorted.toString();
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
}