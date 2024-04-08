package com.example.tpfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

public class Fragment1 extends Fragment {

    private TextInputEditText nomEditText, prenomEditText, dateNaissanceEditText, emailEditText, phoneEditText;
    private RadioGroup hobbiesRadioGroup;
    private RadioButton readingRadio, sportRadio, musicRadio;
    private RadioButton synchronizeRadio;
    private Button submitButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        nomEditText = view.findViewById(R.id.nom);
        prenomEditText = view.findViewById(R.id.prenom);
        dateNaissanceEditText = view.findViewById(R.id.datenaissance);
        emailEditText = view.findViewById(R.id.email);
        phoneEditText = view.findViewById(R.id.phone);
        hobbiesRadioGroup = view.findViewById(R.id.hobbiesRadioGroup);
        readingRadio = view.findViewById(R.id.readingRadio);
        sportRadio = view.findViewById(R.id.sportRadio);
        musicRadio = view.findViewById(R.id.musicRadio);
        synchronizeRadio = view.findViewById(R.id.synchronizeRadio);
        submitButton = view.findViewById(R.id.button);

        Bundle bundle = getArguments();
        if(bundle != null){
        nomEditText.setText(bundle.getString("nom"));
        prenomEditText.setText(bundle.getString("prenom"));
        dateNaissanceEditText.setText(bundle.getString("dateNaissance"));
        phoneEditText.setText(bundle.getString("phone"));}

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les données saisies
                String nom = nomEditText.getText().toString();
                String prenom = prenomEditText.getText().toString();
                String dateNaissance = dateNaissanceEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String hobbies = "";

                // Vérifier les centres d'intérêt sélectionnés
                if (readingRadio.isChecked()) {
                    hobbies += "Reading, ";
                }
                if (sportRadio.isChecked()) {
                    hobbies += "Sport, ";
                }
                if (musicRadio.isChecked()) {
                    hobbies += "Music, ";
                }

                // Vérifier la synchronisation des données
                boolean synchronisation = synchronizeRadio.isChecked();

                // Passer les données au fragment d'affichage
                Fragment2 fragment2 = new Fragment2();
                Bundle bundle = new Bundle();
                bundle.putString("nom", nom);
                bundle.putString("prenom", prenom);
                bundle.putString("dateNaissance", dateNaissance);
                bundle.putString("email", email);
                bundle.putString("phone", phone);
                bundle.putString("hobbies", hobbies);
                bundle.putBoolean("synchronisation", synchronisation);
                fragment2.setArguments(bundle);

                // Remplacer le fragment actuel par le fragment d'affichage
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment2)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }


}
