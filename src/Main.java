import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {

        ParametersBag bag = new ParametersBag(args);
        String folderPath = bag.getPath(); // путь получаем из соответствующего класса
        long sizeLimit = bag.getLimit();   // лимит из соответствующего класса

//      String folderPath = "D:/SAVCHUK";
//      long sizeLimit = 50 * 1024 * 1024; // лимит файлов для вывода в консоль

        File file = new File(folderPath);
        Node root = new Node(file, sizeLimit); // содержит все дерево всех папок и передаем в ноду лимит

        long start = System.currentTimeMillis();

        FolderSizeCalculator calculator = new FolderSizeCalculator(root);
        ForkJoinPool pool = new ForkJoinPool(); // управляет кол-вом одновременно работающих потоков
        pool.invoke(calculator);

        System.out.println(root); // возвращаем размер ноды

        long duration = System.currentTimeMillis() - start;
        System.out.println(duration + " ms");
    }
}
