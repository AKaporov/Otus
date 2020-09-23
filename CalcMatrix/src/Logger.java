import java.io.*;

public class Logger {
    private static volatile String fileName;
    private static volatile Logger instance;
    private static volatile File file;

    public Logger() {
    }

    public static Logger getInstance() {
        Logger localInstance = instance;

        if (localInstance == null) {
            synchronized (Logger.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Logger();
                }
            }
        }

        return localInstance;
    }

    private void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void saveInfoToFile(String info) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(getTempFile(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter printWriter = new PrintWriter(out, true);
        printWriter.println(info);

        try {
            //out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printWriter.close();
    }

    private File getTempFile() {
        setFileName("Otus_LoggerFile_Singleton");
        File localFile = file;

        try {
            if (localFile == null) {
                synchronized (Logger.class) {
                    localFile = file;
                    if (localFile == null) {
                        file = localFile = File.createTempFile(this.fileName, null);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return localFile;
    }
}
