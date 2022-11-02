import java.io.File;
import java.util.ArrayList;

public class Node {                     // дерево для хранения папок с размерами для последующей печати
    private File folder;
    private ArrayList<Node> children;  // узлы потомки
    private long size;
    private int level = 0; // уровень текущей ноды по умолчанию
    private long limit;

    public Node(File folder, long limit) {
        this.folder = folder;
        this.limit = limit;
        children = new ArrayList<>();
    }

    public long getLimit() {
        return limit;
    }

    public File getFolder() {
        return folder;
    }

    public void addChild(Node node) {
        node.setLevel(level + 1);     // устанавливаем переданной ноде новый уровень
        children.add(node);           // добавляем в список переданную ноду
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String toString() {  // реализация печати ноды со всеми потомками
        StringBuilder builder = new StringBuilder(); // класс для добавления в него любых строк и его возврата
        String size = SizeCalculator.getHumanReadableSize(getSize());
        // добавляем имя текущей папки и размер
        builder.append(folder.getName() + "-" + size + "\n");
        for (Node child : children) {   // проходим по всем подпапкам
            if (child.getSize() < limit) {
                continue;
            }
            builder.append("  ".repeat(level + 1) + child.toString()); // выводим с отступом, с repeat деревом
        }
        return builder.toString();
    }

    private void setLevel(int level) {
        this.level = level;
    }
}
