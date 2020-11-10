package com.example.linkshare;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.linkshare.Models.WebModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.entity.mime.Header;

public class ReceiveActivity extends AppCompatActivity {

    private TextView txtRecTitulo;
    private TextView txtRecDesc;
    private Button btnRecCrear;
    private ImageView imgRecImg;
    private String imageURL;
    private DatabaseReference tDatabase;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_layout);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        txtRecTitulo = findViewById(R.id.txtRecTitulo);
        txtRecDesc = findViewById(R.id.txtRecDesc);
        btnRecCrear = findViewById(R.id.btnRecCrear);
        imgRecImg = findViewById(R.id.imgRecImg);


        tDatabase = FirebaseDatabase.getInstance().getReference();

        btnRecCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> linkMap = new HashMap<>();
                linkMap.put("titulo", txtRecTitulo.getText().toString());
                linkMap.put("descripcion", txtRecDesc.getText().toString());
                linkMap.put("img_url", imageURL);
                linkMap.put("url", url);
                tDatabase.child("enlace").push().setValue(linkMap);
            }
        });

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);

                if (sharedText != null) {
                    handleSendText(sharedText); // Handle text being sent
                }
            }
        }


        if(intent !=null)
        {
            String strdata = intent.getExtras().getString("Uniqid");
            String txtLink = intent.getExtras().getString("text");
            if(strdata.equals("From_Activity_A")){
                if (!txtLink.isEmpty()) {
                    handleSendText(txtLink); // Handle text being sent
                }
            }
        }

    }


    void handleSendText(String url) {
        getWebAsync(url);
    }


    static String  TAG = "WebRequest";
    void getWebAsync(String urlString) {
        AsyncHttpClient androidClient = new AsyncHttpClient();

        androidClient.get(urlString, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, "responseString: " + responseString);
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e(TAG, "Client token: " + responseString);
                try {
                    handleHtml(responseString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void handleHtml(String htmlCode) throws IOException {
        WebModel webModel = extractAllText(htmlCode);

        //Log.e("PARSED_MODEL", "Model: " + webModel.toString());

        if (webModel.getTilte() != null) {
           // titleTV.setText(webModel.getTilte());
            Log.i("TAG", "____TT___");
            Log.i("TAG", webModel.getTilte());
            txtRecTitulo.setText(webModel.getTilte());
        }

        if (webModel.getDescription() != null) {
            // titleTV.setText(webModel.getTilte());
            Log.i("TAG", "____DD___");
            Log.i("TAG", webModel.getDescription());
            txtRecDesc.setText(webModel.getDescription());

        }

        if (webModel.getMainIamgeURL() != null) {
            Picasso.with(this)
                    .load(webModel.getMainIamgeURL())
                    .error(R.mipmap.ic_launcher)
                    .into(imgRecImg);
            imageURL = webModel.getMainIamgeURL();
            Log.i("TAG", "____II___");
            Log.i("TAG", webModel.getMainIamgeURL());
        } else {
            imgRecImg.setImageResource(R.mipmap.ic_launcher);
            Log.i("TAG", "____II___");
            Log.i("TAG", "NOIMAGE");

            imageURL = null;
        }

    }

    public WebModel extractAllText(String htmlText){
        Source source = new Source(htmlText);
        String strData = "";

        WebModel webModel = new WebModel();

        List<Element> elements;
        elements = source.getAllElements("title");

        String title = elements.get(0).getContent().toString();
        webModel.setTilte(title);

        elements = source.getAllElements("meta");
        boolean img = false, desc = false;
        for(Element element : elements )
        {
            final String id = element.getAttributeValue("property"); // Get Attribute 'id'
            if( id != null && id.equals("og:image")){
                strData = element.getAttributeValue("content");
                webModel.setMainIamgeURL(strData);
                img = true;
            }
            if( id != null && id.equals("og:description")){
                strData = element.getAttributeValue("content");
                webModel.setDescription(strData);
                desc = true;
            }
            if(img && desc){
                break;
            }
        }
        return webModel;
    }



}
