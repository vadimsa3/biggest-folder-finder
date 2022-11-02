import java.io.File;
import java.util.Objects;

public class ParametersBag {

    private long limit;
    private String path;

    public ParametersBag(String args[]) {  // конструктор из аргументов
        // проверяем длину аргументов
        if (args.length != 4) {
            throw new IllegalArgumentException("Укажите два параметра: " +
                    "-l (лимит по объему) и -d (путь к папке");
        }
        limit = 0;
        path = "";
        // движемся по аргументам парами
        for (int i = 0; i < 4; i = i + 2) {
            if (Objects.equals(args[i], "-l")) {
                limit = SizeCalculator.getSizeFromHumanReadable(args[i + 1]); // устанавливаем лимит
            } else if (Objects.equals(args[i], "-d")) {
                path = args[i + 1];  // устанавливаем путь
            }
        }

        if (limit <= 0) {   // проверяем лимит
            throw new IllegalArgumentException("Лимит не указан или указан неверно");
        }
        File folder = new File(path);
        // если путь неверный или папка не существует или не является папкой
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("Путь к папке не указан или указан неверно");
        }
    }

    public long getLimit() {
        return limit;
    }

    public String getPath() {
        return path;
    }
}
