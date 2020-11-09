package org.techtown.pos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {
    Button Button0;
    Button Button1;
    Button Button2;
    Button Button3;
    Button Button4;
    String data;
    String data2;
    TextView textView20;


    public static final String KEY_store="store";
    public static final String KEY_table="table";

    static RequestQueue requestQueue;
    private Map<String,String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button0=findViewById(R.id.button20);
        Button1=findViewById(R.id.button21);
        Button2=findViewById(R.id.button22);
        Button3=findViewById(R.id.button23);
        Button4=findViewById(R.id.button5);


        textView20=findViewById(R.id.textView20);


        Intent intent = getIntent();
        processIntent(intent);

        Button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                makeRequestMenu("book");

            }
        });
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                makeRequestMenu((String) Button1.getText());
                menu1order();
            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                makeRequestMenu((String) Button2.getText());
                menu2order();
            }
        });
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                makeRequestMenu((String) Button3.getText());
                menu3order();
            }
        });
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                makeRequestMenu("clear");
                clear();
            }
        });

        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }

    }


    public void menu1order(){
        Toast.makeText(this, Button1.getText()+" 주문 완료", Toast.LENGTH_LONG).show();

    }

    public void menu2order(){
        Toast.makeText(this, Button2.getText()+" 주문 완료", Toast.LENGTH_LONG).show();

    }

    public void menu3order(){
        Toast.makeText(this, Button3.getText()+" 주문 완료", Toast.LENGTH_LONG).show();

    }

    public void clear(){
        Toast.makeText(this,"Clear",Toast.LENGTH_LONG).show();

    }



    private void processIntent(Intent intent){
        if(intent!=null){
            data = intent.getStringExtra(KEY_store);
            data2 = intent.getStringExtra(KEY_table);


            if (intent != null){
                if(data.equals("동아치킨")){
                    Button1.setText("후라이드치킨");
                    Button2.setText("양념치킨");
                    Button3.setText("간장치킨");
                }
                else if(data.equals("청도반점")){
                    Button1.setText("짜장면");
                    Button2.setText("짬뽕");
                    Button3.setText("탕수육");
                }
                else if(data.equals("하루식당")){
                    Button1.setText("김치찌개");
                    Button2.setText("된장찌개");
                    Button3.setText("제육볶음");
                }
            }
        }
    }

    public void makeRequestMenu(String M){
        String url = "http://postest.dothome.co.kr/connect/MenuActivity.php";

        map = new HashMap<>();
        map.put("store_name", data);
        map.put("table_number", data2);
        map.put("menu", M);

        StringRequest request=new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){

            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                return map;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }

}