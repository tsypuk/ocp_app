package com.tiv.ocpapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.tiv.ocpapp.R;
import com.tiv.ocpapp.model.Answer;
import com.tiv.ocpapp.model.Question;
import com.tiv.ocpapp.mvp.MainPresenter;
import com.tiv.ocpapp.mvp.MainPresenterImpl;
import com.tiv.ocpapp.mvp.MainView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView question, description;
    private EditText questionNumber;
    private View answerBtn, descBtn;
    private CheckBox answer1, answer2, answer3, answer4;
    private View descBlind;
    private MainPresenter presenter;
    private List<CheckBox> checkBoxes;
    private int descriptionHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        presenter = new MainPresenterImpl(this);
        loadData(11);
    }

    private void initUI() {
        question = (TextView) findViewById(R.id.question_text);
        description = (TextView) findViewById(R.id.description);
        descBtn = findViewById(R.id.desc_btn);
        descBtn.setOnClickListener(v -> {
            descBlind.animate().translationY(descriptionHeight).start();
        });
        descBlind = findViewById(R.id.desc_blind);
        answerBtn = findViewById(R.id.answer_btn);
        questionNumber = (EditText) findViewById(R.id.number);
        answer1 = (CheckBox) findViewById(R.id.cb_id_1);
        answer2 = (CheckBox) findViewById(R.id.cb_id_2);
        answer3 = (CheckBox) findViewById(R.id.cb_id_3);
        answer4 = (CheckBox) findViewById(R.id.cb_id_4);
        checkBoxes = new ArrayList<>(4);
        checkBoxes.add(answer1);
        checkBoxes.add(answer2);
        checkBoxes.add(answer3);
        checkBoxes.add(answer4);

    }

    @Override
    public void loadData(int id) {
        presenter.loadData(id);
    }

    @Override
    public void onDataLoaded(Question question) {
        updateUI(question);
    }

    private void updateUI(Question data) {
        question.setText(data.getText());
        description.setText(data.getDescription());
        Log.d(TAG, "updateUI: Description Height " + description.getHeight());
        descriptionHeight = description.getHeight();
        questionNumber.setText(String.valueOf(data.getId()));
        Log.d(TAG, "updateUI: " + checkBoxes.size());
        for (int i = 0; i < checkBoxes.size(); i++) {
            Log.d(TAG, "updateUI: " + data.getAnswers().get(i).getText());
            checkBoxes.get(i).setText(data.getAnswers().get(i).getText());
        }
    }
}
