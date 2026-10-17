import java.util.Scanner;

/**
 * Задача 2. Дано бинарное дерево. Найти самый длинный от корня путь, 
 * проходящий только по вершинам с нечётными номерами.
 */
public class lab3 {

    //Узел бинарного дерева.
    static class Node {
        int number;
        Node left;
        Node right;

        Node(int number) {
            this.number = number;
            this.left = null;
            this.right = null;
        }
    }

    //Рекурсивная вставка в бинарное дерево поиска.
    //Меньшие - влево, большие - вправо. Равные игнорируются.
    static Node insert(Node root, int number) {
        if (root == null) {
            return new Node(number);
        }
        if (number < root.number) {
            root.left = insert(root.left, number);
        } else if (number > root.number) {
            root.right = insert(root.right, number);
        }
        return root;
    }

    /**
     * Рекурсивно ищет самый длинный путь от узла вниз,
     * состоящий только из нечётных вершин.
     * Возвращает количество вершин в этом пути.
     * Если узел null или чётный - возвращает 0.
     */
    static int longestOddPath(Node node) {
        if (node == null) {
            return 0;
        }
        if (node.number % 2 == 0) {
            return 0;
        }
        int left = longestOddPath(node.left);
        int right = longestOddPath(node.right);
        return 1 + Math.max(left, right);
    }

    //Печать дерева, корень слева
    static void printTree(Node node, int depth) {
        if (node == null) {
            return;
        }
        printTree(node.right, depth + 1);
        for (int i = 0; i < depth; i++) {
            System.out.print("    ");
        }
        System.out.println(node.number);
        printTree(node.left, depth + 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Binary tree");
        System.out.print("How many nodes? ");
        int n = sc.nextInt();

        Node root = null;

        if (n > 0) {
            System.out.println("Enter " + n + " numbers:");
            for (int i = 0; i < n; i++) {
                int value = sc.nextInt();
                root = insert(root, value);
            }
        }

        System.out.println("\nTree:");
        printTree(root, 0);

        if (root == null) {
            System.out.println("\n0");
        } else if (root.number % 2 == 0) {
            System.out.println("\nRoot is even (" + root.number + "), no odd-only path exists.");
            System.out.println("0");
        } else {
            int length = longestOddPath(root);
            System.out.println("\nPath: " + length + " vertices");
        }

        sc.close();
    }
}