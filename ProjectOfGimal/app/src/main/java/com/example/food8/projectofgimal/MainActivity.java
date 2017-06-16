package com.example.food8.projectofgimal;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    MapFragment mapFragment;
    MainViewFragment mainViewFragment;
    CameraFragment cameraFragment;
    SingInFragment singInFragment;
    GaipFragment gaipFragment;
    WriteBoardFragment writeBoardFragment;
    NoticeBoardFragment noticeBoardFragment;
    BoardLookFragment boardLookFragment;
    ModifyBoardFragment modifyBoardFragment;
    Food_change_Fragment food_change_fragment;
    Text_View_Fragment textView_fragment;
    FoodFragment foodFragment;
    FragmentManager fragmentManager = getSupportFragmentManager();
    Food_info vison_food_info, arm_food_info, chang_food_info;
    private GoogleApiClient mGoogleApiClient;

    private String chang_URL = "http://www.gachon.ac.kr/etc/food.jsp?gubun=A";
    private String arm_URL = "http://www.gachon.ac.kr/etc/food.jsp?gubun=B";
    private String vision_URL = "http://www.gachon.ac.kr/etc/food.jsp?gubun=C";

    private static final String TAG = "SignInActivity";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private static final int RC_SIGN_IN = 9001;
    public static final String ANONYMOUS = "anonymous";
    public ProgressDialog progressDialog;
    private boolean islogin = false;
    private String mUsername;
    private String lUsername = "";
    private String fUsername = "";
    private Firebase_info finfo;
    String userName = "";
    String uNm = "";
    CallbackManager callbackManager;
    String name;
    DbHelper dbHelper;
    SQLiteDatabase sqldb;
    int check;
    String day;
    int num;
    Calendar c;
    SimpleAdapter simpleAdapter;


//////////////////////////////////////////////액티비티 생명주기////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionCheck();
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
        action.setHomeButtonEnabled(true);
        finfo = new Firebase_info();
        vison_food_info = new Food_info();
        arm_food_info = new Food_info();
        chang_food_info = new Food_info();

        c=Calendar.getInstance();
        num = c.get(Calendar.DAY_OF_WEEK);
        switch (num){
            case 1:
                day = "일";
                break;
            case 2:
                day = "월";
                break;
            case 3:
                day = "화";
                break;
            case 4:
                day = "수";
                break;
            case 5:
                day = "목";
                break;
            case 6:
                day = "금";
                break;
            case 7:
                day = "토";
                break;
        }

        if (!misLogin()) {
            mUsername = ANONYMOUS;
            Toast.makeText(MainActivity.this, "ANOY", Toast.LENGTH_SHORT).show();
        } else {
            mUsername = mFirebaseUser.getDisplayName().toString();
            Toast.makeText(MainActivity.this, "GOOGLE", Toast.LENGTH_SHORT).show();
        }


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        mainViewFragment = new MainViewFragment();
        fragmentManager.beginTransaction().replace(R.id.container, mainViewFragment).commit();
    }

    @Override
    protected void onStop() {
        if(islogin == true) {
            mFirebaseAuth.signOut();
            mFirebaseUser = null;
            LoginManager.getInstance().logOut();
            check = 0;
            islogin = false;
        }
        super.onStop();
    }

    @Override
    protected void onStart() {
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.d("onRestart", mUsername);
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d("onResume", mUsername);
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("onPause", mUsername);
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        if(islogin == true) {
            mFirebaseAuth.signOut();
            mFirebaseUser = null;
            LoginManager.getInstance().logOut();
            check = 0;
            islogin = false;
        }
        super.onDestroy();
    }


//////////////////////////////////내비게이션 관련////////////////////////////////////////
    @Override
    public boolean onSupportNavigateUp() {
    mainViewFragment = new MainViewFragment();
    fragmentManager.beginTransaction().replace(R.id.container, mainViewFragment).commit();
    return super.onSupportNavigateUp();
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!islogin){
            menu.getItem(0).setTitle("로그인");
        } else {
            menu.getItem(0).setTitle("로그아웃");
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_in_fragment:
                if(mUsername.equals(ANONYMOUS)&&!islogin) {
                    singInFragment = new SingInFragment();
                    fragmentManager.beginTransaction().replace(R.id.container, singInFragment).commit();
                }else{
                    if(!fUsername.equals("")){
                        LoginManager.getInstance().logOut();
                        fUsername = "";
                        islogin = false;
                        mainViewFragment = new MainViewFragment();
                        fragmentManager.beginTransaction().replace(R.id.container, mainViewFragment).commit();
                        Toast.makeText(getApplicationContext(),"Facebook 로그아웃 완료 " + fUsername, Toast.LENGTH_LONG).show();
                    }else if(check == 1){
                        check = 0;
                        lUsername = "";
                        islogin = false;
                        mainViewFragment = new MainViewFragment();
                        fragmentManager.beginTransaction().replace(R.id.container, mainViewFragment).commit();
                        Toast.makeText(getApplicationContext(),"APP 로그아웃 완료 " + fUsername, Toast.LENGTH_LONG).show();
                    }
                    else {
                        //item.setTitle("로그아웃");
                        mFirebaseAuth.signOut();
                        //Auth.GoogleSignInApi.signOut(mGoogleApiClient); //(로그인 아이디 매번 다시 선택하기);
                        mUsername = ANONYMOUS;
                        islogin = false;
                        //item.setTitle("로그인");
                        mainViewFragment = new MainViewFragment();
                        fragmentManager.beginTransaction().replace(R.id.container, mainViewFragment).commit();
                        Toast.makeText(getApplicationContext(), "Google 로그아웃 완료", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


////////////////////////////////////권한 설정////////////////////////////////////////////

    public void permissionCheck() {
        String[] permissions = {//import android.Manifest;
                Manifest.permission.ACCESS_FINE_LOCATION,   //GPS 이용권한
                Manifest.permission.ACCESS_COARSE_LOCATION, //네트워크/Wifi 이용 권한
                Manifest.permission.CAMERA
        };

        //권한을 가지고 있는지 체크
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한 승인 OK", Toast.LENGTH_LONG).show();// 권한 허가
// 해당 권한을 사용해서 작업을 진행할 수 있습니다
                } else {
                    Toast.makeText(this, "권한 거부", Toast.LENGTH_LONG).show();// 권한 거부
// 사용자가 해당권한을 거부했을때 해주어야 할 동작을 수행합니다
                }
                return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


///////////////////////////////////프래그먼트 이동///////////////////////////////////////

    public void goToMap() {
        if (islogin == false) {
            Toast.makeText(getApplicationContext(), "익명 사용자", Toast.LENGTH_LONG).show();
            uNm = "익명 사용자";
        } else {
            if(!fUsername.equals("")) {
                Toast.makeText(getApplicationContext(), "사용자 : " + fUsername, Toast.LENGTH_LONG).show();
                uNm = fUsername;
            }else if(check == 1){
                Toast.makeText(getApplicationContext(), "사용자 : " + lUsername, Toast.LENGTH_LONG).show();
                uNm = lUsername;
            }else{
                Toast.makeText(getApplicationContext(), "사용자 : " + mUsername, Toast.LENGTH_LONG).show();
                uNm = mUsername;
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("name",uNm);
        mapFragment = new MapFragment();
        mapFragment.setArguments(bundle);
        fragmentManager.beginTransaction().add(R.id.container, mapFragment).commit();
    }

    public void goBackMap(String name){
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        mapFragment = new MapFragment();
        mapFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container, mapFragment).commit();
    }

    public void goToCamera(String name){
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        cameraFragment = new CameraFragment();
        cameraFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container,cameraFragment).commit();
    }

    public void backHome(){
        mainViewFragment = new MainViewFragment();
        fragmentManager.beginTransaction().replace(R.id.container,mainViewFragment).commit();
    }

    public void gaipGoGo(){
        gaipFragment = new GaipFragment();
        fragmentManager.beginTransaction().replace(R.id.container,gaipFragment).commit();
    }

    public void goTologin(){
        singInFragment = new SingInFragment();
        fragmentManager.beginTransaction().replace(R.id.container,singInFragment).commit();
    }

    public void goFood(){
        Bundle bundle = new Bundle();
        bundle.putParcelable("chang", (Parcelable) chang_food_info);
        bundle.putParcelable("arm", (Parcelable) arm_food_info);
        bundle.putParcelable("vision", (Parcelable) vison_food_info);
        foodFragment = new FoodFragment();
        foodFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container,foodFragment).commit();
    }

    public void goBoard(){
        if(mUsername.equals(ANONYMOUS)&&lUsername.equals("")&&fUsername.equals("")){
            Toast.makeText(getApplicationContext(),"로그인 부터 해야 실행할 수 있습니다.",Toast.LENGTH_LONG).show();
        }else {
            if(!mUsername.equals(ANONYMOUS)){
                userName = mUsername;
            } else if(!lUsername.equals("")){
                userName = lUsername;
            } else if(!fUsername.equals("")){
                userName = fUsername;
            }
            Bundle bundle = new Bundle();
            bundle.putString("id",userName);
            noticeBoardFragment = new NoticeBoardFragment();
            noticeBoardFragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.container,noticeBoardFragment).commit();
        }
    }

    public void goModifyBoard(String id, String position){
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        bundle.putString("position",position);
        modifyBoardFragment = new ModifyBoardFragment();
        modifyBoardFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container,modifyBoardFragment).commit();
    }

    public void goBackBoard(String id){
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        noticeBoardFragment = new NoticeBoardFragment();
        noticeBoardFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container,noticeBoardFragment).commit();
    }

    public void goWirteBoard(String id){
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        writeBoardFragment = new WriteBoardFragment();
        writeBoardFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container,writeBoardFragment).commit();
    }

    public void goBoardLook(String id, String position){
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        bundle.putString("position",position);
        boardLookFragment = new BoardLookFragment();
        boardLookFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container,boardLookFragment).commit();
    }

    //프래그 먼트 내부 프래그먼트 이동
    public void changeFood(int gubun){
        Bundle bundle = new Bundle();
        bundle.putParcelable("chang", (Parcelable) chang_food_info);
        bundle.putParcelable("arm", (Parcelable) arm_food_info);
        bundle.putParcelable("vision", (Parcelable) vison_food_info);
        bundle.putInt("gubun",gubun);
        food_change_fragment = new Food_change_Fragment();
        food_change_fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.food_container, food_change_fragment).commit();
    }

    public void viewing(){
        Bundle bundle = new Bundle();
        bundle.putInt("gubun",3);
        bundle.putParcelable("chang", (Parcelable) chang_food_info);
        bundle.putParcelable("arm", (Parcelable) arm_food_info);
        bundle.putParcelable("vision", (Parcelable) vison_food_info);
        food_change_fragment = new Food_change_Fragment();
        food_change_fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.viewing,food_change_fragment).commit();
    }

    public void textViewing(){
        textView_fragment = new Text_View_Fragment();
        fragmentManager.beginTransaction().replace(R.id.viewing,textView_fragment).commit();
    }

///////////////////////////////////////로그인 기능///////////////////////////////////////
///////페이스북/////
    public void facebookLogIn(){
    if(AccessToken.getCurrentAccessToken()!=null){
        islogin = false;
        fUsername = "";
    }else{
        islogin = true;
    }
    FacebookSdk.sdkInitialize(getApplicationContext());
    callbackManager = CallbackManager.Factory.create();
    LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"));
    LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    Log.d("TAG","페이스북 로그인 결과" + response.toString());
                    try{
                        String email = object.getString("email");
                        name = object.getString("name");
                        String gender = object.getString("gender");
                        fUsername = name;
                        Log.d("TAG","이메일 -> " + email);
                        Log.d("TAG","이름 -> " + name);
                        Log.d("TAG","성별 -> " + gender);
                        Toast.makeText(getApplicationContext(),"Facebook 로그인 완료 : " + fUsername,Toast.LENGTH_LONG).show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields","id,name,email,gender");
            request.setParameters(parameters);
            request.executeAsync();
            Log.d("TAG","페이스북 토큰 -> " +loginResult.getAccessToken().getToken());
            Log.d("TAG","페이스북 USER ID -> " +loginResult.getAccessToken().getUserId());
        }

        @Override
        public void onCancel() {
            Log.d("TAG","취소됨");
        }

        @Override
        public void onError(FacebookException e) {
            e.printStackTrace();
        }
    });
    mainViewFragment = new MainViewFragment();
    fragmentManager.beginTransaction().replace(R.id.container,mainViewFragment).commit();

}
///파이어베이스/////
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
    Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
    AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
    mFirebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "signInWithCredential", task.getException());
                        Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else {
                        mUsername = mFirebaseAuth.getCurrentUser().getDisplayName().toString();
                        Toast.makeText(getApplicationContext(), "Authentication Success. : " + mUsername, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });

}
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("TAG", "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    private void handleFirebaseAuthResult(AuthResult authResult) {
        if (authResult != null) {
            // Welcome the user
            FirebaseUser user = authResult.getUser();
            Toast.makeText(this, "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();

        }
    }

    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signInGoogle() {
        Configuration newConfig = new Configuration();
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText(getApplicationContext(),"카메라 모드에서는 로그인 불가능",Toast.LENGTH_LONG).show();
            mainViewFragment = new MainViewFragment();
            fragmentManager.beginTransaction().replace(R.id.container,mainViewFragment).commit();
        }
        if (mUsername.equals(ANONYMOUS)) {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setTitle("구글 로그인 요청");
            progressDialog.setMessage("로그인하는 중입니다.");

            progressDialog.show();

            final Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    signIn();
                    islogin = true;
                }
            });
            t.start();
            mainViewFragment = new MainViewFragment();
            fragmentManager.beginTransaction().replace(R.id.container,mainViewFragment).commit();
        }
    }
////////sqlLite////////
    public void myLogIn(String id, String pw){
    dbHelper = new DbHelper(getApplicationContext());
    sqldb = dbHelper.getReadableDatabase();
    Cursor cursor;
    cursor = sqldb.rawQuery("SELECT * FROM userTBL", null);
    String checkid;
    String checkpw;
    check=0;
    while (cursor.moveToNext()) {
        checkid = cursor.getString(0);
        checkpw = cursor.getString(1);
        if (checkid.equals(id)&&checkpw.equals(pw)) {
            Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_LONG).show();
            check=1;
            islogin = true;
            lUsername = cursor.getString(3);
            mainViewFragment = new MainViewFragment();
            fragmentManager.beginTransaction().replace(R.id.container,mainViewFragment).commit();
        }
    }
    if(check==0)
        Toast.makeText(getApplicationContext(),"일치하는 정보가 없습니다.",Toast.LENGTH_LONG).show();
}

    public boolean misLogin() {
    mFirebaseAuth = FirebaseAuth.getInstance();
    mFirebaseUser = mFirebaseAuth.getCurrentUser();

    if (mFirebaseUser == null) {
        return false;
    } else {
        return true;
    }
}
//////결과//////////
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed
                Log.e(TAG, "Google Sign In failed.");
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    ///////////////////////////////////////식단 기능/////////////////////////////////////////
    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(chang_URL).get();
                Document doc2 = Jsoup.connect(arm_URL).get();
                Document doc3 = Jsoup.connect(vision_URL).get();
                Elements aaa = doc.select("tr");
                Elements bbb = doc2.select("tr");
                Elements ccc = doc3.select("tr");

                for(Element link : aaa){
                    if(link.select("th").html().contains(day)){
                        Elements abc = link.select(".tl");
                        chang_food_info.setGyojikone(abc.first().html());
                        chang_food_info.setIlpoom(abc.get(1).html());
                        chang_food_info.setSpepoom(abc.get(2).html());
                    }
                }

                for(Element link1 : bbb){
                    if(link1.select("th").html().contains(day)){
                        Elements abc = link1.select(".tl");
                        arm_food_info.setGyojikone(abc.first().html());
                        arm_food_info.setIlpoom(abc.get(1).html());
                        arm_food_info.setSpepoom(abc.get(2).html());
                    }
                }

                for(Element link2 : ccc){
                    if(link2.select("th").html().contains(day)){
                        Elements abc = link2.select(".tl");
                        vison_food_info.setGyojikone(abc.first().html());
                        vison_food_info.setIlpoom(abc.get(1).html());
                        vison_food_info.setSpepoom(abc.get(2).html());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        }
    }

///////////////////////////////////////기 타/////////////////////////////////////////////

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putAll(outState);
        super.onSaveInstanceState(outState);
        // 데이터 저장
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

}