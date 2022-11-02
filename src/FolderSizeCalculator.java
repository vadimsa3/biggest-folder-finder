import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FolderSizeCalculator extends RecursiveTask<Long> {
    private Node node;
    private long sizeLimit;

    public FolderSizeCalculator(Node node) {
        this.node = node;
    }

    @Override
    protected Long compute() {
        File folder = node.getFolder();
        if (folder.isFile()) {
            long length = folder.length(); // если является файлом, то добавляем размер
            node.setSize(length);  // присваиваем текущей ноде
            return length;
        }
        long sum = 0;
        List<FolderSizeCalculator> subTasks = new LinkedList<>();
        File[] files = folder.listFiles();
        for (File file : files) {
            Node child = new Node(file, node.getLimit());
            FolderSizeCalculator task = new FolderSizeCalculator(child);
            task.fork(); // запускаем задачу асинхронно - отделяем в отдельный поток
            subTasks.add(task); // добавляем в список подзадач
            node.addChild(child); // добавляем в первоначальную ноду
        }
        for (FolderSizeCalculator task : subTasks) {
            sum += task.join(); // собираем подзадачи методом join (сработает после завершения соотв. потока)
        }
        node.setSize(sum);
        return sum;
    }
}
