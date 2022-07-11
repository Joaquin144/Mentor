package com.joaquin.hackathon.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.joaquin.hackathon.R;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URLEncoder;

import static android.widget.Toast.LENGTH_SHORT;

public class WhatsappActivity extends AppCompatActivity {
    private AppCompatButton apiSubmitBtn;
    private TextView t1,t3;
    private LinearLayout layoutFields;
    private EditText phoneEt;
    private int option=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsapp);
        //initialisation
        apiSubmitBtn=findViewById(R.id.apiSubmitBtn);
        t1=findViewById(R.id.textView3);//--> Academic
        t3=findViewById(R.id.textView5);// --> financial
        layoutFields=findViewById(R.id.linear_fields);
        phoneEt=findViewById(R.id.mera_phoneET);
        //end
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //forcing light theme

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option = 1;
                layoutFields.setVisibility(View.VISIBLE);
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option = 2;
                layoutFields.setVisibility(View.VISIBLE);
            }
        });


        apiSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usr_phn= phoneEt.getText().toString().trim();
                usr_phn="+91"+usr_phn;

                OkHttpClient client = new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/json");
                String s1="Hi We came to know you need academic help. We are always ready to help you. Tell us more about it.";
                String s2="Hi We came to know you need financial help. Money matters a lot in one's life. We suggest you to take our free scholarship test on geteasyscholarship.edu";
                RequestBody body=RequestBody.create(mediaType, "{\"phone\":\""+usr_phn+"\",\"text\":\""+(option==1?s1:s2)+"\"}");
                String BASE_URL = "https://rapidapi.rmlconnect.net/wbm/v1/message";
                Request request = new Request.Builder().url(BASE_URL).method("POST", body).addHeader("Content-Type", "application/json").addHeader("Authorization", "618fc5f20fcc5f0012911573").build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(Response response) throws IOException {
                        if(response.isSuccessful()){
                            Log.d("####","response was a success");
                            onWhatsApp();
                        }
                        else{
                            Toast.makeText(WhatsappActivity.this, "Request cannot be initiated", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Request request, IOException e) {
                        e.printStackTrace();
                        Toast.makeText(WhatsappActivity.this, "Request cannot be initiated"+e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                });
            }
        });

    }
    public void onWhatsApp() {

        PackageManager packageManager =getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);

        try {
            String url = "https://api.whatsapp.com/send?phone="+"+918928894215"+"&text=" + URLEncoder.encode("", "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager)!= null) {
                startActivity(i);
            }
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Whatsapp Not Found", LENGTH_SHORT).show();
        }

    }
}