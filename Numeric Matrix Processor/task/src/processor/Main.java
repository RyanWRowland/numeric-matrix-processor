package processor;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int input = -1;
        do {
            printMenu();
            try {
                System.out.print("Your choice: ");
                input = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input must be a number.");
                scanner.next();
            }

            switch (input) {
                case 1:
                    addMatrices(scanner);
                    break;
                case 2:
                    multiplyMatrix(scanner);
                    break;
                case 3:
                    multiplyMatrices(scanner);
                    break;
                case 4:
                    transposeMatrix(scanner);
                    break;
                case 5:
                    calculateDeterminant(scanner);
                    break;
                case 6:
                    inverseMatrix(scanner);
                    break;
                case 0:
                    System.out.println("Exiting.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (input != 0);
    }

    public static void printMenu() {
        System.out.print("1. Add matrices\n2. Multiply matrix by a constant\n3. Multiply matrices\n4. Transpose matrix\n5. Calculate a determinant\n6. Inverse matrix\n0. Exit\n");
    }

    public static void addMatrices(Scanner scanner) {
        System.out.println("First matrix");
        Matrix matrixA = getMatrix(scanner);
        System.out.println("Second matrix");
        Matrix matrixB = getMatrix(scanner);

        try {
            Matrix matrixC = matrixA.add(matrixB);
            System.out.println("The result is:");
            matrixC.printMatrix();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println();
    }

    public static void multiplyMatrix(Scanner scanner) {
        Matrix matrix = getMatrix(scanner);
        double constant;
        while (true) {
            try {
                System.out.print("Enter constant: ");
                constant = scanner.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Input must be a number.");
                scanner.next();
            }
        }

        Matrix matrixMultiplied = matrix.multiply(constant);
        System.out.println("The result is:");
        matrixMultiplied.printMatrix();

        System.out.println();
    }

    public static void multiplyMatrices(Scanner scanner) {
        System.out.println("First matrix");
        Matrix matrixA = getMatrix(scanner);
        System.out.println("Second matrix");
        Matrix matrixB = getMatrix(scanner);

        try {
            Matrix matrixC = matrixA.multiply(matrixB);
            System.out.println("The result is:");
            matrixC.printMatrix();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println();
    }

    public static void transposeMatrix(Scanner scanner) {
        int input = -1;
        while (true) {
            System.out.print("\n1. Main diagonal\n2. Side diagonal\n3. Vertical line\n4. Horizontal line\n");
            try {
                System.out.print("Your choice: ");
                input = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input must be a number.");
                scanner.next();
            }
            if (input < 1 || input > 4) {
                System.out.println("Invalid option.");
            } else {
                break;
            }
        }
        Matrix matrix = getMatrix(scanner);
        switch (input) {
            case 1:
                matrix.transposeMain().printMatrix();
                break;
            case 2:
                matrix.transposeSide().printMatrix();
                break;
            case 3:
                matrix.transposeVertical().printMatrix();
                break;
            case 4:
                matrix.transposeHorizontal().printMatrix();
                break;
        }

        System.out.println();
    }

    public static void calculateDeterminant(Scanner scanner) {
        Matrix matrix = getMatrix(scanner);
        try {
            double result = matrix.determinant();
            System.out.println("The result is: ");
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println();
    }

    public static void inverseMatrix(Scanner scanner) {
        Matrix matrix = getMatrix(scanner);

        try {
            matrix.inverse().printMatrix();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println();
    }

    public static Matrix getMatrix(Scanner scanner) {
        int matrixRows;
        int matrixCols;
        try {
            System.out.print("Enter size of matrix: ");
            matrixRows = scanner.nextInt();
            matrixCols = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Inputs must be numbers.");
            scanner.next();
            return getMatrix(scanner);
        }

        double[][] matrixArray = new double[matrixRows][matrixCols];
        System.out.println("Enter matrix: ");
        for (int i = 0; i < matrixRows; i++) {
            for (int j = 0; j < matrixCols; j++) {
                try {
                    matrixArray[i][j] = scanner.nextDouble();
                } catch (InputMismatchException e) {
                    System.out.println("Inputs must be numbers.");
                    scanner.next();
                    j--;
                }
            }
        }


        return new Matrix(matrixRows, matrixCols, matrixArray);
    }
}
