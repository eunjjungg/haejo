package com.itstime.haejo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.itstime.haejo.databinding.ActivityBeginBinding
import com.itstime.haejo.util.AppSetting


class BeginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBeginBinding
    private lateinit var auth : FirebaseAuth
    private var RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityBeginBinding.inflate(layoutInflater)


        auth = FirebaseAuth.getInstance()


        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("100929782179-jlf51ivuju3ie3uvl42nkgr8lq9ofgid.apps.googleusercontent.com")
            .requestEmail()
            .build()
        var googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnLogin.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        setContentView(binding.root)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException){

            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    setSharedPrfs(user!!.email.toString(), user!!.displayName.toString())
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, task.result.toString(), Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun setSharedPrfs(email: String, name: String) {
        AppSetting.prefs.setAutoLogin("Yes")
        AppSetting.prefs.setEmail(email)
        AppSetting.prefs.setName(name)
    }
}