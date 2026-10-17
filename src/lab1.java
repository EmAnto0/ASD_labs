import java.util.Scanner;

/**
 * Задача 2. Вокруг считающего стоит N человек, из которых выделен первый, 
 * а остальные занумерованы по часовой стрелке числами от 2 до N. 
 * Считающий, начиная с кого-то, ведет счет до M. 
 * Человек, на котором остановился счет, выходит из круга. 
 * Счет продолжается со следующего человека и так до тех пор, 
 * пока не останется один человек. 
 * Определить
 * a) номер оставшегося человека, если известно M и то, 
 *    что счет начинался с первого человека;
 * b) номер человека c которого начинался счет, 
 *    если известно M и номер оставшегося человека L.
 */
public class lab1 {

    //Узел кольцевого односвязного списка
    static class Node {
        int number;
        Node next;

        Node(int number) {
            this.number = number;
            this.next = null;
        }
    }

    //Кольцевой список из n узлов с номерами 1..n.
    //Возвращает ссылку на узел с номером 1.
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

    // Выход из круга
    static Node countOut(Node start, int m) {
        Node current = start;
        Node previous = start;

        // Узел перед start
        while (previous.next != start) {
            previous = previous.next;
        }

        // Пока больше одного узла
        while (current.next != current) {
            // Шаги
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

    //а): Счет начинался с №1
    static int findRemainingFromFirst(int n, int m) {
        Node first = buildCircle(n);
        Node remaining = countOut(first, m);
        return remaining.number;
    }

    //б): Найти номер, с которого начинался счет
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

        //а)
        int remaining = findRemainingFromFirst(n, m);
        System.out.println("\na) if begin from person №1, ending №" + remaining);

        //б)
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