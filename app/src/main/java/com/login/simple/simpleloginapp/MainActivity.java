package com.login.simple.simpleloginapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.andexert.library.RippleView;
import com.rengwuxian.materialedittext.MaterialEditText;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    static final int REQUEST_IMAGE_CAPTURE = 1;


    private RippleView mLogInButton;
    private RippleView mRegisterButton;

    private CircleImageView mImageView;

    private MaterialEditText mUserField;
    private MaterialEditText mPassField;
    private MaterialEditText mConfirmField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initializeViews();
        applyListener();
    }

    private void initializeViews() {
        mLogInButton    = (RippleView)      findViewById(R.id.log_in);
        mRegisterButton = (RippleView)      findViewById(R.id.register);

        mUserField      = (MaterialEditText) findViewById(R.id.user_name);
        mPassField      = (MaterialEditText) findViewById(R.id.pass);
        mConfirmField   = (MaterialEditText) findViewById(R.id.confirm);

        mImageView      = (CircleImageView)  findViewById(R.id.image);
    }

    private void applyListener() {
        mRegisterButton.setOnClickListener(this);
        mLogInButton.setOnClickListener(this);
        mImageView.setOnClickListener(this);
    }

    public boolean checkField(){

        boolean check = false;

        String userName     = mUserField.getText().toString();
        String passWord     = mPassField.getText().toString();
        String confirmPass  = mConfirmField.getText().toString();
        if (!userName.isEmpty() && !passWord.isEmpty() ){
            if (mConfirmField.getVisibility() == View.VISIBLE){
                if (!confirmPass.isEmpty()){
                    check = true;
                }else {
                    check = false;
                }
            }else {
                check = true;
            }
        }else {
            check = false;
        }

        return check;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.log_in:
                if (checkField()){
                    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Good job!")
                            .setContentText(getResources().getString(R.string.success_message))
                            .show();
                }else {
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText(getResources().getString(R.string.warning_message))
                            .show();
                }

                break;

            case R.id.register:
                if (mLogInButton.getVisibility() == View.VISIBLE){
                    mLogInButton.setVisibility(View.INVISIBLE);
                    mConfirmField.setVisibility(View.VISIBLE);
                    mImageView.setVisibility(View.VISIBLE);
                }else {
                    if (checkField()){
                        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Good job!")
                                .setContentText(getResources().getString(R.string.success_message))
                                .show();
                    }else{
                        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(getResources().getString(R.string.warning_message))
                                .show();
                    }
                }
                break;

            case R.id.image:
                dispatchTakePictureIntent();
                break;
        }
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }
}
