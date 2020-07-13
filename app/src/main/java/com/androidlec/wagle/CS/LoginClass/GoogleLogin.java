package com.androidlec.wagle.CS.LoginClass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.androidlec.wagle.CS.Network.CSNetworkTask;
import com.androidlec.wagle.HomeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

public class GoogleLogin {

    public static final int RC_SIGN_IN = 100;

    private Context mContext;

    @SuppressLint("StaticFieldLeak")
    public static GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;

    public GoogleLogin(Context mContext) {
        this.mContext = mContext;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(mContext, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        account = GoogleSignIn.getLastSignedInAccount(mContext);
    }

    public void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);

            String userId = getUserId();
            if(!findUserFromDB(userId)) {
                InputUserDataToDB();
            }

            mContext.startActivity(new Intent(mContext, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getMessage());
        }
    }

    private String getUserId() {
        return account.getId();
    }

    private boolean findUserFromDB(String userId) {
        boolean result = false;

        String urlAddr = "http://192.168.0.79:8080/wagle/csFindUserWAGLE.jsp?";

        urlAddr = urlAddr + "userId=" + userId;

        try {
            CSNetworkTask csNetworkTask = new CSNetworkTask(mContext, urlAddr);
            result = csNetworkTask.execute().get(); // doInBackground 의 리턴값
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void InputUserDataToDB() {
        String urlAddr = "http://192.168.0.79:8080/wagle/csInputUserWAGLE.jsp?";

        urlAddr = urlAddr + "uId=" + account.getId() + "&uEmail=" + account.getEmail() + "&uName=" + account.getFamilyName()+account.getGivenName() + "&uImageName=" + account.getPhotoUrl() + "&uBirthDate=" + "" + "&uLoginType=GOOGLE";

        try {
            CSNetworkTask csNetworkTask = new CSNetworkTask(mContext, urlAddr);
            csNetworkTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        ((Activity) mContext).finish();
                    }
                });
    }

    public void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        ((Activity) mContext).finish();
                    }
                });
    }

}
