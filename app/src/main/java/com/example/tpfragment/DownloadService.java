package com.example.tpfragment;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String fileUrl = intent.getStringExtra("fileUrl");
        new DownloadTask().execute(fileUrl);
        return START_NOT_STICKY;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // Obtenir la longueur du fichier
                int fileLength = connection.getContentLength();

                // Créer un fichier de sortie
                File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "userdata.txt");

                // Créer les flux d'entrée et de sortie
                InputStream inputStream = new BufferedInputStream(url.openStream());
                FileOutputStream outputStream = new FileOutputStream(outputFile);

                byte[] buffer = new byte[1024];
                int bytesRead;
                long totalBytesRead = 0;

                // Lire le fichier et l'écrire dans la mémoire externe
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                    totalBytesRead += bytesRead;
                    // Envoyer une mise à jour de la progression si nécessaire
                    // (vous pouvez implémenter cela avec une interface si vous voulez mettre à jour l'interface utilisateur)
                    //int progress = (int) ((totalBytesRead * 100) / fileLength);
                    //publishProgress(progress);
                }

                // Fermer les flux
                outputStream.close();
                inputStream.close();

                result = outputFile.getAbsolutePath(); // Retourner le chemin absolu du fichier téléchargé
            } catch (IOException e) {
                Log.e("DownloadService", "Error downloading file: " + e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String filePath) {
            if (!filePath.isEmpty()) {
                // Le fichier a été téléchargé avec succès, vous pouvez effectuer d'autres opérations ici si nécessaire
                Log.d("DownloadService", "File downloaded: " + filePath);
            } else {
                Log.e("DownloadService", "File download failed");
            }
        }
    }
}
