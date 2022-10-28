import java.io.File;

public class Main {
    public static void main(String[] args) {
        String folderPath = "C:/Users/savchuk/Desktop/Новая папка";
        File file = new File(folderPath);
        System.out.println(getFolderSize(file));
    }

    // создаем метод для подсчета размера папки суммируя размеры файлов
    public static long getFolderSize(File folder) {
        if (folder.isFile()) {      // проверяем является-ли папка файлом
            return folder.length(); // если да, получаем размер файла
        }
        long sum = 0;
        File[] files = folder.listFiles();  // получаем список файлов в этой папке
        for (File file : files) {           // проходим по всем файлам
            sum += getFolderSize(file);     // суммируем размеры всех файлов всех папок
        }
        return sum;
    }
}