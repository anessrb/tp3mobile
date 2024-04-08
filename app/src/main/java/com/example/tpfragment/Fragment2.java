package com.example.tpfragment;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Fragment2 extends Fragment {

    private TextView nomTextView, prenomTextView, emailTextView, dateNaissanceTextView, telephoneTextView, centresInteretTextView, synchronisationTextView;
    private Button validerButton, retourButton, telechargerButton;
    private File fileToDownload;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        nomTextView = view.findViewById(R.id.nomTextView);
        prenomTextView = view.findViewById(R.id.prenomTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        dateNaissanceTextView = view.findViewById(R.id.dateNaissanceTextView);
        telephoneTextView = view.findViewById(R.id.telephoneTextView);
        centresInteretTextView = view.findViewById(R.id.centresInteretTextView);
        synchronisationTextView = view.findViewById(R.id.synchronisationTextView);
        validerButton = view.findViewById(R.id.validerButton);
        retourButton = view.findViewById(R.id.retourButton);
        telechargerButton = view.findViewById(R.id.telechargerButton);

        // Récupérer les données passées depuis le fragment de saisie
        Bundle bundle = getArguments();
        if (bundle != null) {
            nomTextView.setText("Nom: " + bundle.getString("nom"));
            prenomTextView.setText("Prénom: " + bundle.getString("prenom"));
            emailTextView.setText("Email: " + bundle.getString("email"));
            dateNaissanceTextView.setText("Date de naissance: " + bundle.getString("dateNaissance"));
            telephoneTextView.setText("Téléphone: " + bundle.getString("phone"));
            centresInteretTextView.setText("Centres d'intérêt: " + bundle.getString("hobbies"));
            synchronisationTextView.setText("Synchronisation: " + (bundle.getBoolean("synchronisation") ? "Oui" : "Non"));
        }

        telechargerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une Uri pour le fichier à télécharger
                Uri fileUri = Uri.fromFile(fileToDownload);

                // Créer une demande de téléchargement
                DownloadManager.Request request = new DownloadManager.Request(fileUri);

                // Configurer le titre et la description du téléchargement
                request.setTitle("Téléchargement de UserData");
                request.setDescription("Téléchargement en cours...");

                // Indiquer le répertoire de destination du fichier téléchargé
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "userdata.txt");

                // Obtenir le service de téléchargement
                DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);

                // Enregistrer le téléchargement et obtenir son ID
                long downloadId = downloadManager.enqueue(request);

                // Afficher un message pour informer l'utilisateur
                Toast.makeText(getContext(), "Téléchargement en cours...", Toast.LENGTH_SHORT).show();
            }
        });

        retourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getArguments();
                String nom = bundle.getString("nom");
                String prenom = bundle.getString("prenom");
                String dateNaissance = bundle.getString("dateNaissance");
                String email = bundle.getString("email");
                String phone = bundle.getString("phone");

                bundle.putString("nom", nom);
                bundle.putString("prenom", prenom);
                bundle.putString("dateNaissance", dateNaissance);
                bundle.putString("email", email);
                bundle.putString("phone", phone);

                Fragment fr1 = new Fragment1();
                fr1.setArguments(bundle);

                // Revenir au Fragment1 et déclencher son cycle de vie complet
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fr1)
                        .commit();
            }
        });

        validerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les données passées depuis le fragment de saisie
                Bundle bundle = getArguments();
                if (bundle != null) {
                    String nom = bundle.getString("nom");
                    String prenom = bundle.getString("prenom");
                    String email = bundle.getString("email");
                    String dateNaissance = bundle.getString("dateNaissance");
                    String phone = bundle.getString("phone");
                    String hobbies = bundle.getString("hobbies");
                    boolean synchronisation = bundle.getBoolean("synchronisation");

                    // Créer un objet JSON avec les données saisies
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("Nom", nom);
                        jsonObject.put("Prénom", prenom);
                        jsonObject.put("Email", email);
                        jsonObject.put("Date de naissance", dateNaissance);
                        jsonObject.put("Téléphone", phone);
                        jsonObject.put("Centres d'intérêt", hobbies);
                        jsonObject.put("Synchronisation", synchronisation);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Appeler la méthode pour écrire les données dans un fichier JSON
                    writeDataToJsonFile(jsonObject);
                }
            }
        });

        return view;
    }

    private void writeDataToJsonFile(JSONObject jsonObject) {
        try {
            fileToDownload = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "userdata.json");
            FileWriter writer = new FileWriter(fileToDownload);
            BufferedWriter fileWriter = new BufferedWriter(writer);

            // Écrire les données JSON dans le fichier
            fileWriter.write(jsonObject.toString());

            // Fermer le FileWriter
            fileWriter.close();

            // Afficher un message de succès
            Toast.makeText(getContext(), "Données sauvegardées avec succès", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            // Afficher un message d'erreur en cas d'échec de l'écriture
            Toast.makeText(getContext(), "Erreur lors de la sauvegarde des données", Toast.LENGTH_SHORT).show();
        }
    }
}
