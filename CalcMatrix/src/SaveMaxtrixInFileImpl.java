import java.io.*;
import java.util.Arrays;

/*

Класс для сохранения матрицы в файл.
Если имя файла не передано, то сохраняется по пути указаном в TEMP/TMP с префиксом Otus_FileName
(напрмиер, C:\Windows\Temp\Otus_FileName17321303727237893112.tmp)

    //по умолчанию файл создается во временной директрории
    //C:\Windows\Temp\Task2001.txt9756643539832909062.tmp
//            File your_file_name = File.createTempFile("your_file_name", null);

*/

public class SaveMaxtrixInFileImpl implements SaveMatrix {
    String fileName;

    public SaveMaxtrixInFileImpl(String fileName) {
        if (fileName.isEmpty()) {
            fileName = "Otus_File_Name";
        }
        this.fileName = fileName;
    }

    @Override
    public void saveMatrix(int[][] matrix) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(createTempFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PrintWriter printWriter = new PrintWriter(out);
        printWriter.println(Arrays.deepToString(matrix));

        try {
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printWriter.close();
    }

    private File createTempFile() {
        try {
            return File.createTempFile(this.fileName, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
