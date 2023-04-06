package ro.pub.cs.systems.eim.practicaltest01;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class PracticalTest01Var06MainActivity extends AppCompatActivity {

    private Button navigateToSecondaryActivityButton;
    private int scor = 0;
    private int last;
    private String[] values = {"3", "1", "2", "*"};
    private Button playButton;

    private EditText rightEditText, leftEditText, centerEditText;
    private IntentFilter intentFilter = new IntentFilter();
    Random rand = new Random();
    CheckBox checkBoxLeft, checkBoxRight, checkBoxCenter;
    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();

    private class MessageBroadcastReceiver extends android.content.BroadcastReceiver {
        @Override
        public void onReceive(android.content.Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_EXTRA, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    private void startPracticalTestService() {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06Service.class);
            intent.putExtra("scor", scor);
            getApplicationContext().startService(intent);
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.play_me_button:
                    Integer[] valori = new Integer[3];
                        for (int i = 0; i < 3; i++)
                            valori[i] = rand.nextInt(4);
                        Log.i("numere", values[valori[0]] + " " + values[valori[1]] + " " + values[valori[2]]);
                        if (checkBoxLeft.isChecked())
                            leftEditText.setText(values[valori[0]]);
                        if (checkBoxCenter.isChecked())
                            centerEditText.setText(values[valori[1]]);
                        if (checkBoxRight.isChecked())
                            rightEditText.setText(values[valori[2]]);
                    break;
                case R.id.navigate_to_secondary_activity_button:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06SecondaryActivity.class);
                    intent.putExtra("Left", leftEditText.getText());
                    intent.putExtra("Center", centerEditText.getText());
                    intent.putExtra("Right", rightEditText.getText());
                    int count = 0;
                    if (checkBoxLeft.isChecked())
                        count++;
                    if (checkBoxCenter.isChecked())
                        count++;
                    if (checkBoxRight.isChecked())
                        count++;
                    intent.putExtra("nr", count);
                    startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
                    if (scor > 300) {
                        startPracticalTestService();
                    }
                    break;

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_main);

        leftEditText = (EditText)findViewById(R.id.left_edit_text);
        rightEditText = (EditText)findViewById(R.id.right_edit_text);
        centerEditText = (EditText)findViewById(R.id.center_edit_text);

        playButton = (Button) findViewById(R.id.play_me_button);
        playButton.setOnClickListener(buttonClickListener);

        checkBoxCenter = findViewById(R.id.checkBoxCenter);
        checkBoxLeft = findViewById(R.id.checkBoxLeft);
        checkBoxRight = findViewById(R.id.checkBoxRight);

        navigateToSecondaryActivityButton = (Button)findViewById(R.id.navigate_to_secondary_activity_button);
        navigateToSecondaryActivityButton.setOnClickListener(buttonClickListener);

        for (String action : Constants.actionTypes) {
            intentFilter.addAction(action);
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06Service.class);
        getApplicationContext().stopService(intent);
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            last = scor;
            scor += resultCode;
            Toast.makeText(this, "Total " + scor, Toast.LENGTH_LONG).show();
        }
    }

    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("scor", scor);
    }

    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("scor")) {
            scor = savedInstanceState.getInt("scor");
        }
    }
}