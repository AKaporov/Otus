/*
Класс запуска расчета матриц в потоках
 */
public class CalcMatrixThread implements Runnable {
    protected Thread thread;

    public CalcMatrixThread(String name) {
        // Создаем поток с именем name
        thread = new Thread(this, name);

        System.out.println("Создан поток с именем: " + thread.getName());
    }

    @Override
    public void run() {
        System.out.println("Запущен: " + Thread.currentThread().getName());

        //1. Получить матрицу в которую будем скаладывать рузультат расчета
        TotalSquareMatrix totalSquareMatrix = TotalSquareMatrix.getTotalSquareMatrix();

        //2. Проверить что есть элементы для расчета isEmptyIndexMatrix
        while (totalSquareMatrix.isEmptyIndexMatrix()) {
            //3. Запустить расчет одного элемента матрицы
            calcIndexMatrix(totalSquareMatrix);
        }
    }

    /*
     Расчет элемента матрицы
    */
    private void calcIndexMatrix(TotalSquareMatrix totalSquareMatrix) {
        // Если элемент матрицы не занят потоком, то расчитаем его
        if (!totalSquareMatrix.getCurrentThreadNameCalcMatrix().equals(Thread.currentThread().getName())) {
            totalSquareMatrix.calcIndexMatrix(Thread.currentThread().getName());
        }
    }
}
