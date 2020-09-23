/*
Класс для расчета квадратной матрицы
*/

public class CalcTwoSquareMatrixInThread implements CalcMatrix {
    // Нужен конструктор, внутри которого создается объект типа Thread
    private CalcMatrixThread tOne = new CalcMatrixThread("Первый поток");
    private CalcMatrixThread tTwo = new CalcMatrixThread("Второй поток");

    private static SquareMatrix squareMatrixA;
    private static SquareMatrix squareMatrixB;


    public CalcTwoSquareMatrixInThread(int iMatrixLength, Boolean fillMatrixRandom) {
        // 1. Создание квадратные матрицы размером iMatrixLength
        squareMatrixA = new SquareMatrix(iMatrixLength);
        squareMatrixB = new SquareMatrix(iMatrixLength);

        if (fillMatrixRandom) {
            // Заполнение матрицы случайными числами
            squareMatrixA.fillMatrixRandom();
            squareMatrixB.fillMatrixRandom();
        }

        System.out.println("Создана матрица А = " + squareMatrixA);
        System.out.println("Создана матрица В = " + squareMatrixB);
        System.out.println("==============================");
    }

    @Override
    public void calcTwoMatrixInThread() throws InterruptedException {
        // Запуск потоков
        tOne.thread.start();
        tTwo.thread.start();

        System.out.println("Ждим завершения потоков");
        Thread.sleep(20000);

        // Завершение работы потоков
        tOne.thread.join();
        tTwo.thread.join();

        System.out.println("Потоки завершили работу");
        // Сохранение результатов
        saveSquareMatrix();
    }


    public static SquareMatrix getSquareMatrixA() {
        return squareMatrixA;
    }

    public static SquareMatrix getSquareMatrixB() {
        return squareMatrixB;
    }

    /*
    Сохранение расчиатанной матрицы
    */
    public void saveSquareMatrix() {
        TotalSquareMatrix totalSquareMatrix = TotalSquareMatrix.getTotalSquareMatrix();

        System.out.println("==============================");
        System.out.println("Расчитанная матрицы:");
        totalSquareMatrix.saveMatrixToAll();
    }
}