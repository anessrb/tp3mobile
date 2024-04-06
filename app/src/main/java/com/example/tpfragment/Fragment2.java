package com.example.tpfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Fragment2 extends Fragment {

    private TextView nomTextView, prenomTextView, emailTextView, dateNaissanceTextView, telephoneTextView, centresInteretTextView, synchronisationTextView;
    private Button validerButton;

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

                    // Créer une chaîne de texte avec les données saisies
                    String data = "Nom: " + nom + "\n" +
                            "Prénom: " + prenom + "\n" +
                            "Email: " + email + "\n" +
                            "Date de naissance: " + dateNaissance + "\n" +
                            "Téléphone: " + phone + "\n" +
                            "Centres d'intérêt: " + hobbies + "\n" +
                            "Synchronisation: " + (synchronisation ? "Oui" : "Non");

                    // Appeler la méthode pour écrire les données dans un fichier texte
                    writeDataToFile(data);
                }
            }
        });

        return view;
    }

    private void writeDataToFile(String data) {
        try {
            // Créer un objet FileWriter pour écrire dans le fichier texte
            FileWriter writer = new FileWriter(new File(getContext().getFilesDir(), "userdata.txt"));

            // Écrire les données dans le fichier texte
            writer.write(data);

            // Fermer le FileWriter
            writer.close();

            // Afficher un message de succès
            Toast.makeText(getContext(), "Données sauvegardées avec succès", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            // Afficher un message d'erreur en cas d'échec de l'écriture
            Toast.makeText(getContext(), "Erreur lors de la sauvegarde des données", Toast.LENGTH_SHORT).show();
        }
    }
}
