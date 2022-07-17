package ru.sgrc.datasender;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.io.*;

public class GoToURL {
    private final String pathForTempFiles;
    private String DIR; // = "C:\\PDF\\";
    protected String nameFile, urlForPdf;
    protected HttpURLConnection connection;
    public boolean writeToFile = false;

    Logger logger = LoggerFactory.getLogger(GoToURL.class);

    public GoToURL(String urlForPdf, String nameFile, String pathForTempFiles) {
        this.pathForTempFiles = pathForTempFiles;
        logger.debug("Создается обьект/файл {} ссылка на ПД -> {}", nameFile, urlForPdf);
        this.nameFile = nameFile;
        this.urlForPdf = urlForPdf;

        try {
            URL url = new URL(urlForPdf);
            URLConnection urlConnection = url.openConnection();
            this.connection = null;
            if(urlConnection instanceof HttpURLConnection) {
                this.connection = (HttpURLConnection) urlConnection;
            }else {
                System.out.println(" HTTP URL не корректный ");
                return;
            }


            byte[] fileAsBytes = getArrayFromInputStream(this.connection.getInputStream());
            writeContent(fileAsBytes, pathForTempFiles + nameFile);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Создаем массив байтов потока с потока сервера, подготовка к записи в файл
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] getArrayFromInputStream(InputStream inputStream) throws IOException {
        byte[] bytes;
        byte[] buffer = new byte[1024];
        try(BufferedInputStream is = new BufferedInputStream(inputStream)){
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int length;
            while ((length = is.read(buffer)) > -1 ) {
                bos.write(buffer, 0, length);
            }
            bos.flush();
            bytes = bos.toByteArray();
        }
        return bytes;
    }

    /**
     * создание файла PDF платежного документа
     * @param content
     * @param fileToWriteTo
     * @throws IOException
     */
    public void writeContent(byte[] content, String fileToWriteTo) throws IOException {
        File file = new File(fileToWriteTo);
        try(BufferedOutputStream bufferedOutputStreamPdf = new BufferedOutputStream(new FileOutputStream(file))){
            bufferedOutputStreamPdf.write(content);
            bufferedOutputStreamPdf.flush();
            this.writeToFile = true;
            logger.debug("создание файла PDF платежного документа");
        }
    }
}
