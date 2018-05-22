package myfire.bong.com.logintest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText login_id, login_pw;
    Button button;
    DatabaseReference mReference;
    String input_id, input_pw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        mReference = FirebaseDatabase.getInstance().getReference();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_id = login_id.getText().toString();
                input_pw = login_pw.getText().toString();

                isValidData();
            }
        });

    }

    private void initView(){
        login_id = (EditText)findViewById(R.id.login_id);
        login_pw = (EditText)findViewById(R.id.login_pw);
        button = (Button)findViewById(R.id.button3);
    }

    private void isValidData(){

        mReference.child("members").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isValid = false;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Member member = snapshot.getValue(Member.class);

                    // db 데이터 중 로그인 아이디, 비밀번호가 모두 동일한 정보가 있는지 확인
                    if(member.getId().toString().equals(input_id)){
                        if(member.getPw().toString().equals(input_pw)) {
                            isValid = true;
                            // 로그인 정보가 일치하는지 확인하고 화면에 띄워줌
                            checkLogin(isValid, member.getName());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void checkLogin(boolean is_valid, String user_name){
        if(is_valid) {
            Toast.makeText(getApplicationContext(), "로그인 되었습니다.", Toast.LENGTH_LONG).show();
            login_id.setText("");
            login_pw.setText("");

            // 로그인이 되었을 때에 bool값 넘겨주기
            Intent intent = getIntent();
            intent.putExtra("isLogin",true);
            intent.putExtra("user_name",user_name);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "정보가 일치하지 않습니다. 다시 로그인 해주세요.", Toast.LENGTH_LONG).show();
            login_id.setText("");
            login_pw.setText("");
        }
    }
}

