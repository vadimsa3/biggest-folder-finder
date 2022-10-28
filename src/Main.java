import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {

        String folderPath = "C:/Users/savchuk/Desktop/";
        File file = new File(folderPath);

        long start = System.currentTimeMillis();

        FolderSizeCalculator calculator = new FolderSizeCalculator(file);
        ForkJoinPool pool = new ForkJoinPool(); // управляет кол-вом одновременно работающих потоков
        long size = pool.invoke(calculator);
        System.out.println(size); // возвращаем размер

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
}