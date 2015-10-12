package com.narola.kpa.scientificeditor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.narola.kpa.scientificeditor.view.ScientificEditor;

public class ScientificEditorActivity extends AppCompatActivity {

    private static final String TAG = ScientificEditor.class.getSimpleName();

    private ScientificEditor mScientificEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scientific_editor);

        initGlobal();

    }

    private void initGlobal() {
        mScientificEditor = (ScientificEditor) findViewById(R.id.scietific_editor_question);

        mScientificEditor.setQuestion("Example question for scientific editor. Show some text here. For example of question.");
        mScientificEditor.getQuestionImageView().setImageResource(R.drawable.img_question);
        mScientificEditor.setFormula(getResources().getStringArray(R.array.tex_examples)[3]);

        mScientificEditor.getQuestionImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScientificEditor.setFormula(getResources().getStringArray(R.array.tex_examples)[3]);
            }
        });

    }

}
