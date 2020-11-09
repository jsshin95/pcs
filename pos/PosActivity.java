package org.techtown.pos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


public class PosActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_11 = 11;
    public static final int REQUEST_CODE_12 = 12;
    public static final int REQUEST_CODE_13 = 13;
    public static final int REQUEST_CODE_14 = 14;

    private Map<String,String> map;
    TextView textView;
    String status1;
    String status2;
    String status3;
    String status4;
    Button button,button1,button2,button3,button4;
    TextView textView7;
    TextView textView8;
    String bestmenu;
    int avs;

    static RequestQueue requestQueue;

    public static final String KEY_store="store";
    public static final String KEY_table="table";
    public static final String KEY_status1="status1";
    public static final String KEY_status2="status2";
    public static final String KEY_status3="status3";
    public static final String KEY_status4="status4";
    public static final String KEY_best="bestmenu";
    public static final String KEY_avs="avs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos);


        textView=findViewById(R.id.textView);
        textView7=findViewById(R.id.textView7);
        textView8=findViewById(R.id.textView8);

        Intent intent = getIntent();
        processIntent(intent);
        String data = intent.getStringExtra(KEY_store);
        textView.setText(data);
        textView7.setText("예약가능 "+avs+"석");
        textView8.setText(bestmenu);


        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                makeRequest((String)textView.getText());
            }
        });


        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }

        button1 = findViewById(R.id.button1);
        button1.setText(status1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent11 = new Intent(getApplicationContext(), MenuActivity.class);
                String data1 = (String) textView.getText();
                intent11.putExtra(KEY_store,data1);
                String data2 = "테이블1";
                intent11.putExtra(KEY_table,data2);
                startActivityForResult(intent11, REQUEST_CODE_11);
            }
        });

        button2 = findViewById(R.id.button2);
        button2.setText(status2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent12 = new Intent(getApplicationContext(), MenuActivity.class);
                String data1 = (String) textView.getText();
                intent12.putExtra(KEY_store,data1);
                String data2 = "테이블2";
                intent12.putExtra(KEY_table,data2);
                startActivityForResult(intent12, REQUEST_CODE_12);
            }
        });

        button3 = findViewById(R.id.button3);
        button3.setText(status3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent13 = new Intent(getApplicationContext(), MenuActivity.class);
                String data1 = (String) textView.getText();
                intent13.putExtra(KEY_store,data1);
                String data2 = "테이블3";
                intent13.putExtra(KEY_table,data2);
                startActivityForResult(intent13, REQUEST_CODE_13);
            }
        });

        button4 = findViewById(R.id.button4);
        button4.setText(status4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent14 = new Intent(getApplicationContext(), MenuActivity.class);
                String data1 = (String) textView.getText();
                intent14.putExtra(KEY_store,data1);
                String data2 = "테이블4";
                intent14.putExtra(KEY_table,data2);
                startActivityForResult(intent14, REQUEST_CODE_14);
            }
        });


    }


    private void processIntent(Intent intent){
        if(intent!=null){

            status1 = intent.getStringExtra(KEY_status1);
            status2 = intent.getStringExtra(KEY_status2);
            status3 = intent.getStringExtra(KEY_status3);
            status4 = intent.getStringExtra(KEY_status4);
            bestmenu = intent.getStringExtra(KEY_best);
            avs = intent.getIntExtra(KEY_avs,0);

        }
    }

    public void makeRequest(String store){
        String url = "http://postest.dothome.co.kr/connect/PosActivity.php";

        map = new HashMap<>();
        map.put("store_name", store);


        StringRequest request=new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status1=jsonObject.getString("status1");
                    status2=jsonObject.getString("status2");
                    status3=jsonObject.getString("status3");
                    status4=jsonObject.getString("status4");
                    bestmenu=jsonObject.getString("best");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                textView7.setText("예약가능 "+avs(status1,status2,status3,status4)+"석");
                textView8.setText(bestmenu);

                button1.setText(status1);
                button2.setText(status2);
                button3.setText(status3);
                button4.setText(status4);
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

    public int avs(String st1, String st2, String st3, String st4){
        int i=0;
        if(st1.equals("예약가능")) i=i+1;
        if(st2.equals("예약가능")) i=i+1;
        if(st3.equals("예약가능")) i=i+1;
        if(st4.equals("예약가능")) i=i+1;

        return i;
    }

}