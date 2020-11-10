package com.example.speechtotext;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int RESULT_BANGLA = 1;
    private static final int RESULT_ENGLISH = 2;
    TextView textView;
    Button startBangla, startEnglish;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inti();
        textView.setOnClickListener(v -> {//for copy the text
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("PhoneNumber", textView.getText());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Copy", Toast.LENGTH_SHORT).show();
        });
        startBangla.setOnClickListener(v -> {//for bangla speech to bangla text


            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "bn-BD");
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "I am Listening...");
            try {
                startActivityForResult(intent, RESULT_BANGLA);
            } catch (ActivityNotFoundException ignored) {

            }
        });

        startEnglish.setOnClickListener(v -> {//for english speech to english text
            Intent intent = new Intent(
                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "bn-BD");

            try {
                startActivityForResult(intent, RESULT_ENGLISH);
                textView.setText("");
            } catch (ActivityNotFoundException a) {
                Toast t = Toast.makeText(getApplicationContext(),
                        "Opps! Your device doesn't support Speech to Text",
                        Toast.LENGTH_SHORT);
                t.show();
            }
    });
}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_BANGLA) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (result != null) {
                    String text = result.get(0);
                    textView.setText(text);
                }
            }
        }
        if (requestCode==RESULT_ENGLISH){
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> text = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                textView.setText(text.get(0));
            }
        }
    }

    private void inti() {
        textView = findViewById(R.id.textCa);
        startBangla = findViewById(R.id.startBangla);
        startEnglish = findViewById(R.id.startEnglish);
    }

}