package ro.pub.cs.systems.eim.practicaltest01;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var06SecondaryActivity extends AppCompatActivity {

    Button okButton;
    TextView resultTextView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_second);

        okButton = findViewById(R.id.ok_button);
        resultTextView = findViewById(R.id.textView);
        String v1, v2, v3;
        int scor = 0;
        v1 = getIntent().getStringExtra("Left");
        v2 = getIntent().getStringExtra("Right");
        v3 = getIntent().getStringExtra("Center");
        int count = getIntent().getIntExtra("nr", 0);
        if ((v1 == "*" && v2 == v3) || (v1 == v2 && v2 == v3) || (v2 == "*" && v1 == v3) ||
                (v3 == "*" && v1 == v2) || (v1 == "*" && v2 == "*") || (v2 == "*" && v3 == "*") ||
                (v1 == "*" && v3 == "*")) {
            if (count == 0){
                resultTextView.setText("Gained 100");
                scor = 100;
            }
            else if (count == 1)
            {
                resultTextView.setText("Gained 50");
                scor = 50;
            }
            else if (count == 2) {
                resultTextView.setText("Gained 10");
                scor = 10;
            }
        }
        //getIntent().getStringExtra("name") + " " +
        //        getIntent().getStringExtra("group")

        int finalScor = scor;
        okButton.setOnClickListener(view -> {
            setResult(finalScor, null);
            finish();
        });

    }
}