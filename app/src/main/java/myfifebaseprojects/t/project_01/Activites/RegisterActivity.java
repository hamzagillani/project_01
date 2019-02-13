package myfifebaseprojects.t.project_01.Activites;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import myfifebaseprojects.t.project_01.R;

public class RegisterActivity extends AppCompatActivity {

    private ImageView profile_photo;
     static int pReqCode=1;
    static int REQUESTCODE=1;
    Uri pickedImgUri;
    private FirebaseAuth mAuth;
    private EditText user_name,user_email,user_password1,user_password2;
    private ProgressBar loadingProgress;
    private Button reg_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        profile_photo=findViewById(R.id.add_a_photo_id);
        user_name=findViewById(R.id.username_id);
        user_email=findViewById(R.id.email_id);
        user_password1=findViewById(R.id.pass1_id);
        user_password2=findViewById(R.id.pass2_id);
        reg_btn=findViewById(R.id.register_id);
        loadingProgress=findViewById(R.id.reg_progressbar_id);

        mAuth = FirebaseAuth.getInstance();




 loadingProgress.setVisibility(View.INVISIBLE);
reg_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        reg_btn.setVisibility(View.INVISIBLE);
        loadingProgress.setVisibility(View.INVISIBLE);
        final String email=user_email.getText().toString();
        final String password1=user_password1.getText().toString();
        final String password2=user_password2.getText().toString();
        final String name=user_name.getText().toString();
        if (email.isEmpty()||name.isEmpty()||password1.isEmpty()||password2.isEmpty()||password1.equals(password2)){
            showMessage("Please Verify all Fields");
            reg_btn.setVisibility(View.VISIBLE);
            loadingProgress.setVisibility(View.INVISIBLE);
        }else {
            createUserAccount(email,name,password1);
        }
    }
});
        profile_photo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

 if(Build.VERSION.SDK_INT>=22){

     checkAndRequestFromPermission();
 }else{
     openGallery();
 }
    }
});
    }



    private void openGallery() {
        Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESTCODE);
    }
    private void checkAndRequestFromPermission() {
    if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
    != PackageManager.PERMISSION_GRANTED){

        if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(RegisterActivity.this,"Please accept requried permission",Toast.LENGTH_SHORT).show();
        }else {
            ActivityCompat.requestPermissions(RegisterActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},pReqCode);
        }
    }else {
        openGallery();
    }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

if (resultCode==RESULT_OK && requestCode==REQUESTCODE&&data!=null){
    //user has successfully picked an image
    //save file reference to Uri variable
pickedImgUri=data.getData();
profile_photo.setImageURI(pickedImgUri);


}

    }
    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
    private void createUserAccount(String email, String name, String password1) {


    }


}
