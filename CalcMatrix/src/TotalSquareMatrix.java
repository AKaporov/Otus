import java.util.Arrays;

/*
  Класс для результирующей матрицы (после расчета)
*/
public class TotalSquareMatrix {
    private String currentThreadNameCalcMatrix = "";  //Имя текущего потока, который сейчас производит расчет элемента матрицы

    private volatile int[][] totalMatrix;  //Итоговая матрица
    private int iIndexRowCalc;     // Номер строки для расчета
    private int iIndexColumnCalc;  // Номер колонки для расчета
    private static volatile TotalSquareMatrix totalSquareMatrix;
    private static Logger logger = Logger.getInstance();

    private TotalSquareMatrix(int matrixLength) {
        this.totalMatrix = new int[matrixLength][matrixLength];
    }

    public String getCurrentThreadNameCalcMatrix() {
        return currentThreadNameCalcMatrix;
    }

    /*
    Проверка занятости индекса матрицы для расчетаы
    */
    public Boolean isEmptyIndexMatrix() {
        Boolean bIsEmpty = Boolean.FALSE;
        iIndexColumnCalc = Integer.MIN_VALUE;
        iIndexRowCalc = Integer.MIN_VALUE;

        for (int i = 0; i < this.totalMatrix.length; i++) {
            for (int j = 0; j < this.totalMatrix.length; j++) {
                Integer index = this.totalMatrix[i][j];
                if (index.equals(Integer.MIN_VALUE)) {
                    bIsEmpty = Boolean.TRUE;
                    iIndexRowCalc = i;
                    iIndexColumnCalc = j;
                    break;
                }
            }
        }

        return bIsEmpty;
    }

    /*
    Расчет индекса матрицы переданным потоком
    */
    synchronized void calcIndexMatrix(String threadNameCalcMatrix) {

        try {
            Thread.sleep(1000);

            if ((iIndexRowCalc != Integer.MIN_VALUE) & (iIndexColumnCalc != Integer.MIN_VALUE)) {

                // Запомним текущий поток
                currentThreadNameCalcMatrix = threadNameCalcMatrix;

                totalMatrix[iIndexRowCalc][iIndexColumnCalc] = 0;
                int iLength = CalcTwoSquareMatrixInThread.getSquareMatrixA().matrix.length;
                for (int k = 0; k < iLength; k++) {
                    int a = CalcTwoSquareMatrixInThread.getSquareMatrixA().matrix[iIndexRowCalc][k];
                    int b = CalcTwoSquareMatrixInThread.getSquareMatrixB().matrix[k][iIndexColumnCalc];

                    totalMatrix[iIndexRowCalc][iIndexColumnCalc] += a * b;
                }

                String logInfo = "Поток (" + currentThreadNameCalcMatrix + ") расчитал элемент марицы [" + iIndexRowCalc + "][" + iIndexColumnCalc + "] = " + totalMatrix[iIndexRowCalc][iIndexColumnCalc];
                System.out.println(logInfo);
                logger.saveInfoToFile(logInfo);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
    Создание итоговой матрицы
    */
    public static TotalSquareMatrix getTotalSquareMatrix() {
        if (totalSquareMatrix == null) {
            synchronized (TotalSquareMatrix.class) {
                if (totalSquareMatrix == null) {
                    totalSquareMatrix = new TotalSquareMatrix(CalcTwoSquareMatrixInThread.getSquareMatrixA().matrix.length);
                    totalSquareMatrix.fillMatrixMinValue();

                    String logInfo = "Поток (" + Thread.currentThread().getName() + ") создал итоговую матрицы для расчета: " + totalSquareMatrix;
                    System.out.println(logInfo);
                }
            }
        }

        return totalSquareMatrix;
    }

    /*
    Проинициализируем итоговую матрицу минимальными значениями
    */
    private void fillMatrixMinValue() {
        for (int i = 0; i < this.totalMatrix.length; i++) {
            for (int j = 0; j < this.totalMatrix.length; j++) {
                this.totalMatrix[i][j] = Integer.MIN_VALUE;
            }
        }
    }

    @Override
    public String toString() {
        return "TotalSquareMatrix{" +
                "totalMatrix=" + Arrays.deepToString(this.totalMatrix) +
                '}';
    }

    public void saveMatrixToFile() {
        SaveMatrix inFile = new SaveMaxtrixInFileImpl("Otus_TotalSquareMatrix");
        inFile.saveMatrix(getTotalSquareMatrix().totalMatrix);
    }

    public void saveMatrixToConsole() {
        SaveMatrix inConsol = new SaveMatrixInConsolImpl();
        inConsol.saveMatrix(getTotalSquareMatrix().totalMatrix);
    }

    public void saveMatrixToAll() {
        saveMatrixToConsole();
        saveMatrixToFile();
    }
}
