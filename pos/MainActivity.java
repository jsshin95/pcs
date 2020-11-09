package org.techtown.pos;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_1 = 1;
    public static final int REQUEST_CODE_2 = 2;
    public static final int REQUEST_CODE_3 = 3;
    public static final String KEY_store="store";
    public static final String KEY_status1="status1";
    public static final String KEY_status2="status2";
    public static final String KEY_status3="status3";
    public static final String KEY_status4="status4";
    public static final String KEY_best="bestmenu";
    public static final String KEY_avs="avs";
    TextView textView;
    private Map<String,String> map;
    String SN;
    int RQ;

    String status1;
    String status2;
    String status3;
    String status4;
    String bestmenu;
    int avs;

    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.textView10);

        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }

        Button button10 = findViewById(R.id.button10);
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SN = "동아치킨";
                RQ=1;
                makeRequest("동아치킨");

            }
        });

        Button button11 = findViewById(R.id.button11);
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SN = "청도반점";
                RQ=2;
                makeRequest("청도반점");

            }
        });

        Button button12 = findViewById(R.id.button12);
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SN = "하루식당";
                RQ=3;
                makeRequest("하루식당");


            }
        });
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
                avs=avs(status1,status2,status3,status4);

                Intent intent1 = new Intent(getApplicationContext(), PosActivity.class);
                intent1.putExtra(KEY_store, SN);
                intent1.putExtra(KEY_status1,status1);
                intent1.putExtra(KEY_status2,status2);
                intent1.putExtra(KEY_status3,status3);
                intent1.putExtra(KEY_status4,status4);
                intent1.putExtra(KEY_best,bestmenu);
                intent1.putExtra(KEY_avs,avs);
                startActivityForResult(intent1, RQ);

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