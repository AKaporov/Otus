import java.util.Arrays;

/*
Класс для сохранения матрицы в консоль
*/
public class SaveMatrixInConsolImpl implements SaveMatrix {
    @Override
    public void saveMatrix(int[][] matrix) {
        System.out.println(Arrays.deepToString(matrix));
    }
}
