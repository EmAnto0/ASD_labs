import java.util.Scanner;

/**
 * Лабораторная работа №2. Разреженные матрицы.
 * Вариант 2.
 *
 * Дана разреженная матрица CRS. Обойти её по варианту 2
 * (столбцы справа налево, внутри каждого - снизу вверх)
 * и вывести все положительные элементы.
 */
public class lab2 {

    // Разреженная матрица в формате CRS.
    int rows;
    int cols;
    int[] values;      // значения ненулевых элементов
    int[] colIndex;    // номера столбцов для каждого значения
    int[] rowPointer;  // границы строк в values/colIndex

    /**
     * Конструктор: принимает матрицу как int[][] и превращает её в CRS.
     * Попутно считает число ненулевых (nnz).
     */
    lab2(int[][] matrix) {
        this.rows = matrix.length;
        this.cols = matrix[0].length;

        // Первый проход: считаем nnz
        int nnz = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != 0) {
                    nnz++;
                }
            }
        }

        // Выделяем память
        values = new int[nnz];
        colIndex = new int[nnz];
        rowPointer = new int[rows + 1];

        // Второй проход: заполняем values, colIndex, rowPointer
        int k = 0;
        for (int i = 0; i < rows; i++) {
            rowPointer[i] = k;
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != 0) {
                    values[k] = matrix[i][j];
                    colIndex[k] = j;
                    k++;
                }
            }
        }
        rowPointer[rows] = k; // конец последней строки
    }

    /**
     * Возвращает значение элемента (i, j).
     * Если в этой позиции ноль - возвращает 0.
     */
    int get(int i, int j) {
        int start = rowPointer[i];
        int end = rowPointer[i + 1];
        for (int k = start; k < end; k++) {
            if (colIndex[k] == j) {
                return values[k];
            }
        }
        return 0;
    }

    /**
     * Обход по варианту 2: столбцы справа налево, внутри - снизу вверх.
     * Выводит все положительные элементы.
     */
    void traverseAndPrintPositive() {
        System.out.print("Positive elements in traversal order: ");
        for (int j = cols - 1; j >= 0; j--) {          // столбцы: справа налево
            for (int i = rows - 1; i >= 0; i--) {      // строки: снизу вверх
                int value = get(i, j);
                if (value > 0) {
                    System.out.print(value + " ");
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Sparse matrix (CRS), variant 2 ===");
        System.out.print("Enter number of rows: ");
        int rows = sc.nextInt();
        System.out.print("Enter number of columns: ");
        int cols = sc.nextInt();

        int[][] matrix = new int[rows][cols];
        System.out.println("Enter matrix (" + rows + "x" + cols + "):");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }

        lab2 sparse = new lab2(matrix);
        sparse.traverseAndPrintPositive();

        sc.close();
    }
}