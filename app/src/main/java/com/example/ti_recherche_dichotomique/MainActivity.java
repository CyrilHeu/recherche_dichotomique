package com.example.ti_recherche_dichotomique;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteTextView;

    // Nouvelle liste avec des noms à consonances proches
    private List<String> namesList = Arrays.asList(
            "Albert", "Alfred", "Bertrand", "Bernard",
            "Charles", "Charlie", "David", "Damien",
            "Edouard", "Eric", "François", "Frédéric",
            "Georges", "Gérard", "Henri", "Hervé",
            "Isabelle", "Isidore", "Jacques", "Julien"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        // Initialiser l'AutoCompleteTextView avec un adaptateur vide
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<>());
        autoCompleteTextView.setAdapter(adapter);

        // Ajouter un écouteur pour suivre les changements dans l'EditText
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Appeler la méthode pour mettre à jour les suggestions
                updateSuggestions(s.toString(), adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void updateSuggestions(String query, ArrayAdapter<String> adapter) {
        // Effectuer une recherche dichotomique
        List<String> filteredList = binarySearchMatches(query, namesList);
        // Mettre à jour les suggestions
        adapter.clear();
        adapter.addAll(filteredList);
        adapter.notifyDataSetChanged();
        autoCompleteTextView.showDropDown();
    }

    // Méthode pour trouver les correspondances avec recherche dichotomique
    private List<String> binarySearchMatches(String query, List<String> names) {
        List<String> result = new ArrayList<>();
        for (String name : names) {
            // Rechercher dès qu'il y a une correspondance, même avec une seule lettre
            if (name.toLowerCase().contains(query.toLowerCase())) {
                result.add(name);
            }
        }
        return result;
    }
}
