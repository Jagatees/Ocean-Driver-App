package com.example.mgpa1;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookButtonBase;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import static androidx.media2.MediaUtils.TAG;

// Code Written By : Yanson , 180500N


public class MainActivity extends Activity {

    private LoginButton loginButton;
    private TextView txtName;
    private CallbackManager callbackManager;

    Button button, btnSetting, btnLeaderBoard, btnCustomiza, btnLogout, btnShare, btnInstructions;

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    DatabaseReference MusicmyRef = FirebaseDatabase.getInstance().getReference(currentFirebaseUser.getUid()).child("Settings");

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(currentFirebaseUser.getUid()).child("GameOver");

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("xd");

        printKeyHash();
        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        txtName = findViewById(R.id.profile_name);
        loginButton.setPermissions(Arrays.asList("public_profile"));
        checkLoginStatus();

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        myRef.child("GameOver").setValue("false");
        MusicmyRef.child("Music").setValue("ON");

        //Music player done by Yanson
        MusicManager musicplayer = null;
        if (musicplayer.getCurrentMusic() != 0 && musicplayer.getCurrentMusic() != -1){
            musicplayer.reset(this, 0, false);
        }
        else
        {
            musicplayer.start(this, 0);
        }
        //Music player done by Yanson



        MusicmyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Music").getValue().equals("ON")){
                    onResume();
                }else if (dataSnapshot.child("Music").getValue().equals("OFF")){
                    onPause();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        MusicmyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("Accelerometer").exists()){
                    if (dataSnapshot.child("Accelerometer").getValue().equals("ON")){
                        MusicmyRef.child("Accelerometer").setValue("ON");

                    }else if (dataSnapshot.child("Accelerometer").getValue().equals("OFF")){
                        MusicmyRef.child("Accelerometer").setValue("OFF");

                    }
                }else  {
                    MusicmyRef.child("Accelerometer").setValue("OFF");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;

        btnLeaderBoard = findViewById(R.id.btnLeader);
        btnLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                Intent intent = new Intent(MainActivity.this, LeaderBoard.class);
                startActivity(intent);
            }
        });
        buttonEffect(btnLeaderBoard);


        button = findViewById(R.id.startGame);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                Intent intent = new Intent(MainActivity.this, LevelPicker.class);
                startActivity(intent);

            }
        });
        buttonEffect(button);

        btnSetting = findViewById(R.id.btnOptions);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                Intent intent = new Intent(MainActivity.this, SettingPage.class);
                startActivity(intent);
            }
        });
        buttonEffect(btnSetting);

        btnCustomiza = findViewById(R.id.btnCustomiose);
        btnCustomiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Intent intent = new Intent(MainActivity.this, Customization.class);
                startActivity(intent);
            }
        });
        buttonEffect(btnCustomiza);

        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });
        buttonEffect(btnLogout);

        btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(MainActivity.this, Share2.class);
                startActivity(intent);
            }
        });
        buttonEffect(btnShare);

        btnInstructions = findViewById(R.id.btnInstructions);
        btnInstructions.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mp.start();
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(MainActivity.this, InstructionPage.class);
                startActivity(intent);

            }
        });
        buttonEffect(btnInstructions);

        btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(MainActivity.this, Share2.class);
                startActivity(intent);
            }
        });
        buttonEffect(btnShare);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,requestCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken)
        {
            if (currentAccessToken==null)
            {
                txtName.setText("");
                System.out.println("lol");
                Toast.makeText(MainActivity.this,"User logged out", Toast.LENGTH_LONG).show();
            }
            else
            {
                loaduserProfile(currentAccessToken);
                System.out.println("lmao");
            }
        }
    };

    private void loaduserProfile(AccessToken newAccessToken)
    {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");

                    txtName.setText(first_name + " "+last_name);
                    System.out.println(txtName.getText());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void checkLoginStatus()
    {
        if (AccessToken.getCurrentAccessToken()!=null)
        {
            loaduserProfile(AccessToken.getCurrentAccessToken());
        }
    }

    //Music player done by Yanson
    @Override
    protected void onPause() {
        super.onPause();
        MusicManager musicplayer = null;
        musicplayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MusicManager musicplayer = null;
        musicplayer.start(this, 0);
    }

    @Override
    protected void onStart(){
        super.onStart();
        MusicManager musicplayer = null;
        musicplayer.start(this,0);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MusicManager musicplayer = null;
        musicplayer.start(this, 0);
    }

    //Music player done by Yanson

    private void printKeyHash()
    {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.mgpa1",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    private void buttonEffect(Button button)
    {
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xe0a9a9a9, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }

}
