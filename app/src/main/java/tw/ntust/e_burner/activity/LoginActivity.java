package tw.ntust.e_burner.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import tw.ntust.e_burner.R;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextView forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();


        final Dialog dialog = new Dialog(LoginActivity.this, R.style.MyDialog);//指定自定義樣式
        dialog.setContentView(R.layout.dialog_forget);//指定自定義layout

//可自由調整佈局內部元件的屬性
        LinearLayout ll = (LinearLayout)dialog.findViewById(R.id.lldialog);
        ll.getLayoutParams().width=600;

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//dialogWindow.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
    /*    lp.x = 500; // 新位置X坐標
        lp.y = 450; // 新位置Y坐標
        lp.width = 100; // 寬度
        lp.height = 100; // 高度
        lp.alpha = 0.7f; // 透明度

*/
//新增自定義按鈕點擊監聽
        TextView forget= (TextView)findViewById(R.id.forget);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();


            }
        });
        Button btn = (Button)dialog.findViewById(R.id.dialog_button_ok);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

//顯示dialog

    }


    private void initComponents() {
        btnLogin = (Button) findViewById(R.id.main_btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ChooseActivity.class));
                finish();
            }
        });
    }

}
