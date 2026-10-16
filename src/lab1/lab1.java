import java.util.Scanner;

/**
 * Лабораторная работа №1. Списки.
 * Вариант 2.
 *
 * В круге стоят N человек, пронумерованных 1..N.
 * Считающий ведет счет до M, человек на котором остановился счет - выходит.
 * Счет продолжается со следующего.
 */
public class lab1 {

    /** Узел кольцевого односвязного списка. */
    static class Node {
        int number;
        Node next;

        Node(int number) {
            this.number = number;
            this.next = null;
        }
    }

    /**
     * Строит кольцевой список из n узлов с номерами 1..n.
     * Возвращает ссылку на узел с номером 1.
     */
    static Node buildCircle(int n) {
        Node first = new Node(1);
        Node last = first;
        for (int i = 2; i <= n; i++) {
            last.next = new Node(i);
            last = last.next;
        }
        last.next = first;
        return first;
    }

    /**
     * Выполняет выбывание по правилам задачи.
     * start - узел, с которого начинается счет (он же считается первым в счете).
     * m - шаг счета.
     * Возвращает узел, который остался последним.
     */
    static Node countOut(Node start, int m) {
        Node current = start;
        Node previous = start;

        // Ищем узел, стоящий перед start
        while (previous.next != start) {
            previous = previous.next;
        }

        // Пока в круге больше одного узла
        while (current.next != current) {
            // Отсчитываем m-1 шагов (current считается первым)
            for (int i = 1; i < m; i++) {
                previous = current;
                current = current.next;
            }
            // Удаляем current из круга
            previous.next = current.next;
            // Счет продолжается со следующего
            current = current.next;
        }
        return current;
    }

    /**
     * Пункт а): счет начинался с человека №1.
     */
    static int findRemainingFromFirst(int n, int m) {
        Node first = buildCircle(n);
        Node remaining = countOut(first, m);
        return remaining.number;
    }

    /**
     * Пункт б): известен M и номер оставшегося L.
     * Найти номер человека, с которого начинался счет.
     */
    static int findStartByRemaining(int n, int m, int L) {
        for (int start = 1; start <= n; start++) {
            Node first = buildCircle(n);
            Node startNode = first;
            while (startNode.number != start) {
                startNode = startNode.next;
            }
            Node remaining = countOut(startNode, m);
            if (remaining.number == L) {
                return start;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("start");
        System.out.print("N: ");
        int n = sc.nextInt();
        System.out.print("M: ");
        int m = sc.nextInt();

        // Пункт а)
        int remaining = findRemainingFromFirst(n, m);
        System.out.println("\na) if begin from person №1, ending №" + remaining);

        // Пункт б)
        System.out.print("\nL: ");
        int L = sc.nextInt();
        int startNumber = findStartByRemaining(n, m, L);
        if (startNumber == -1) {
            System.out.println("error");
        } else {
            System.out.println("Count begin from person №" + startNumber);
        }

        sc.close();
    }
}