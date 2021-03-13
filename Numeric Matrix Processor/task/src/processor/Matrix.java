package processor;

public class Matrix {
    private final int rows;
    private final int cols;
    private final double[][] matrix;

    public Matrix(int rows, int cols, double[][] matrix) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = matrix;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void printMatrix() {
        if (matrix == null) {
            return;
        }

        for (int i = 0; i < this.rows; i ++) {
            for (int j = 0; j < this.cols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // add 2 matrices together, returns new matrix that is the sum
    public Matrix add(Matrix matrix) throws Exception {
        // to sum, dimensions have to be the same
        if (this.rows != matrix.getRows() || this.cols != matrix.getCols()) {
            throw new Exception("The operation cannot be performed.");
        }

        int sumRows = this.rows;
        int sumCols = this.cols;
        double[][] sumMatrix = new double[sumRows][sumCols];
        for (int i = 0; i < sumRows; i ++) {
            for (int j = 0; j < sumCols; j++) {
                sumMatrix[i][j] = this.matrix[i][j] + matrix.getMatrix()[i][j];
            }
        }

        return new Matrix(this.rows, this.cols, sumMatrix);
    }

    // multiplies matrix by a constant and returns new matrix with result
    public Matrix multiply(double constant) {
        double[][] productMatrix = new double[this.rows][this.cols];
        for (int i = 0; i < this.rows; i ++) {
            for (int j = 0; j < this.cols; j++) {
                productMatrix[i][j] = this.matrix[i][j] * constant;
            }
        }

        return new Matrix(this.rows, this.cols, productMatrix);
    }

    // multiply matrix by a matrix
    public Matrix multiply(Matrix matrix) throws Exception {
        if (this.cols != matrix.getRows()) {
            throw new Exception("This operation cannot be performed.");
        }

        int productRows = this.rows;
        int productCol = matrix.getCols();
        double[][] productMatrix = new double[productRows][productCol];

        for (int i = 0; i < productRows; i++) {
            for (int j = 0; j < productCol; j++) {
                double dot = 0;
                for (int k = 0; k < matrix.getRows(); k++) {
                    dot += this.matrix[i][k] * matrix.getMatrix()[k][j];
                }
                productMatrix[i][j] = dot;
            }
        }

        return new Matrix(productRows, productCol, productMatrix);
    }

    // transpose along main diagonal
    public Matrix transposeMain() {
        double[][] transposed = new double[this.rows][this.cols];

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                transposed[j][i] = this.matrix[i][j];
            }
        }

        return new Matrix(this.rows, this.cols, transposed);
    }

    // transpose along side diagonal
    public Matrix transposeSide() {
        double[][] transposed = new double[this.rows][this.cols];

        for (int i = this.rows - 1; i >= 0; i--) {
            for (int j = this.cols - 1; j >= 0; j--) {
                transposed[this.cols - 1 - j][this.rows - 1 - i] = this.matrix[i][j];
            }
        }

        return new Matrix(this.rows, this.cols, transposed);
    }

    // transpose vertically
    public Matrix transposeVertical() {
        double[][] transposed = new double[this.rows][this.cols];

        for (int i = 0; i < this.rows; i++) {
            for (int j = this.cols - 1; j >= 0; j--) {
                transposed[i][this.cols - 1 - j] = this.matrix[i][j];
            }
        }

        return new Matrix(this.rows, this.cols, transposed);
    }

    // transpose horizontally
    public Matrix transposeHorizontal() {
        double[][] transposed = new double[this.rows][this.cols];

        for (int i = this.rows - 1; i >= 0; i--) {
            for (int j = 0; j < this.cols; j++) {
                transposed[this.rows - 1 - i][j] = this.matrix[i][j];
            }
        }

        return new Matrix(this.rows, this.cols, transposed);
    }

    // calculate determinant of matrix
    // recursive function
    public double determinant() throws Exception {
        // if matrix is not a square an exception is thrown
        if (rows != cols) {
            throw new Exception("This operation cannot be performed.");
        }

        if (cols == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        double total = 0;
        for (int i = 0; i < cols; i++) {
            double a = matrix[0][i] * subMatrix(0, i).determinant();
            if (i % 2 == 0) {
                total += a;
            } else {
                total -= a;
            }
        }

        return total;
    }

    // returns a sub-matrix excluding the providing row and column
    // used by other functions
    private Matrix subMatrix(int excludedRow, int excludedCol) {
        double[][] subMatrixArray = new double[rows - 1][cols - 1];
        for (int i = 0; i < this.rows; i ++) {
            if (i == excludedRow) {
                continue;
            }
            for (int j = 0; j < this.cols; j++) {
                if (j == excludedCol) {
                    continue;
                }

                subMatrixArray[i >= excludedRow ? i - 1 : i][j >= excludedCol ? j - 1 : j] = matrix[i][j];
            }
        }

        return new Matrix(rows - 1, cols - 1, subMatrixArray);
    }

    // returns an the inverse of the matrix
    // throws exceptions if determinant is 0 or determinant cannot be found (not square)
    public Matrix inverse() throws Exception {
        double[][] determinantArray = new double[this.rows][this.cols];
        double determinant = 0;

        try {
            // store determinant for later
            determinant = this.determinant();

            // exception if determinant is 0
            if (determinant == 0) {
                throw new Exception("Inverse cannot be found, determinant of matrix is 0");
            }

            // matrix of minors
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.cols; j++) {
                    determinantArray[i][j] = this.subMatrix(i, j).determinant();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // matrix of cofactors
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (i % 2 ==0 && j % 2 != 0 || i % 2 != 0 && j % 2 ==0) {
                    determinantArray[i][j] *= -1;
                }
            }
        }

        // return Adjugate multiplied by 1 / determinant
        return new Matrix(this.rows, this.cols, determinantArray).transposeMain().multiply(1 / determinant);
    }
}
