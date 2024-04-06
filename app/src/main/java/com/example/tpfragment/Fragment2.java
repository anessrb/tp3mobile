package com.example.tpfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
                // Mettez ici le code pour valider les données
                // par exemple : mListener.onValidationClicked(data);
            }
        });

        return view;
    }
}
