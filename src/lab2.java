import java.util.Scanner;

/**
 * Задача 2. Дана разреженная матрица CRS. Обойти её по варианту 2
 * и вывести все положительные элементы.
 */
public class lab2 {

    // Разреженная матрица в формате CRS.
    int rows;
    int cols;
    int[] values;      // значения ненулевых элементов
    int[] colIndex;    // номера столбцов для каждого значения
    int[] rowPointer;  // границы строк в values/colIndex

    // 
    lab2(int[][] matrix) {
        this.rows = matrix.length;
        this.cols = matrix[0].length;

        // Первый проход: nnz
        int nnz = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != 0) {
                    nnz++;
                }
            }
        }

        // Память
        values = new int[nnz];
        colIndex = new int[nnz];
        rowPointer = new int[rows + 1];

        // Второй проход: values, colIndex, rowPointer
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

    //Значение элемента (i, j)
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

    //Обход
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

        System.out.println("Sparse matrix (CRS)");
        System.out.print("Number of rows: ");
        int rows = sc.nextInt();
        System.out.print("Number of columns: ");
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