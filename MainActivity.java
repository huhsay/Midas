package myfire.bong.com.logintest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button btn_login, btn_singup;
    TextView textView;
    boolean isLogin;
    String user_name;
    final int LOGINPAGE_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        isLogin = false;
        user_name = "";

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivityForResult(intent, LOGINPAGE_CODE);
            }
        });
        btn_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == LOGINPAGE_CODE){
            isLogin = data.getBooleanExtra("isLogin", false);
            user_name = data.getStringExtra("user_name");
            if(isLogin)
                textView.setText(user_name + "님 환영합니다.");
        }
    }

    private void initView(){
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_singup = (Button)findViewById(R.id.btn_singup);
        textView = (TextView)findViewById(R.id.textView);
    }
}
