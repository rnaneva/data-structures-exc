package implementations;

public class TheMatrix {
    private char[][] matrix;
    private char fillChar;
    private char toBeReplaced;
    private int startRow;
    private int startCol;

    public TheMatrix(char[][] matrix, char fillChar, int startRow, int startCol) {
        this.matrix = matrix;
        this.fillChar = fillChar;
        this.startRow = startRow;
        this.startCol = startCol;
        this.toBeReplaced = this.matrix[this.startRow][this.startCol];
    }

    public void solve() {

        replaceSymbols(startRow, startCol);

    }


    private void replaceSymbols(int row, int col) {

        if (isOutOfBounds(row, col) || matrix[row][col] != toBeReplaced) {
            return;
        }

        matrix[row][col] = fillChar;

        replaceSymbols(row + 1, col);
        replaceSymbols(row, col + 1);
        replaceSymbols(row - 1, col);
        replaceSymbols(row, col - 1);

    }

    private boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= matrix.length || col < 0 || col >= matrix[row].length;
    }


    public String toOutputString() {
        StringBuilder sb = new StringBuilder();
        for (char[] chars : matrix) {
            sb.append(String.valueOf(chars));
            sb.append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
