package com.maisonlacroix.projetfinaltehnique;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

public class AboutActivity extends AppCompatActivity{

    private String ID_USER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Intent intent = getIntent();
        ID_USER = intent.getStringExtra("key1");


        TextView textView = (TextView)findViewById(R.id.textView_link);
        textView.setClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='http://3.15.151.13/expressShop/'> Express Shop </a>";
        textView.setText(Html.fromHtml(text));
    }
    public void RedirectToMainMenu(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("key1", ID_USER);
        startActivity(intent);
    }
}
