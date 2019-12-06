package com.example.counterapplication.login

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.counterapplication.R
import com.spotify.mobius.Connectable
import com.spotify.mobius.Connection
import com.spotify.mobius.Mobius
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.functions.Consumer
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView, LoginViewActions,
    Connectable<LoginModel, LoginEvent> {

    companion object {
        private const val KEY_MODEL = "key_model"
    }

    private val loginApiStub = LoginApiStubImpl()
    private val repo = SaveTokenRepo()
    private val loop = Mobius.loop(
        LoginUpdate(),
        LoginEffectHandler(loginApiStub, this, repo)
    )
    private val controller = MobiusAndroid.controller(loop, LoginModel.INIT)
    private val viewRenderer: LoginViewRenderer by lazy {
        LoginViewRenderer(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState != null) {
            val model = savedInstanceState.getParcelable(KEY_MODEL) ?: LoginModel.INIT
            controller.replaceModel(model)
        }

        controller.connect(this)
    }

    override fun onResume() {
        super.onResume()
        controller.start()
    }

    override fun onPause() {
        super.onPause()
        controller.stop()
    }

    override fun onDestroy() {
        controller.disconnect()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putParcelable(KEY_MODEL, controller.model)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun connect(output: Consumer<LoginEvent>): Connection<LoginModel> {
        loginButton.setOnClickListener {
            output.accept(
                LoginButtonClicked(
                    usernameEditText?.text.toString(),
                    passwordEditText?.text.toString()
                )
            )
        }

        usernameEditText.addTextChangedListener {
            output.accept(UserNameEntered)
        }

        passwordEditText.addTextChangedListener {
            output.accept(PasswordEntered)
        }

        return object : Connection<LoginModel> {
            override fun accept(value: LoginModel) {
                viewRenderer.render(value)
            }

            override fun dispose() {
                loginButton.setOnClickListener(null)
                usernameEditText.addTextChangedListener(null)
                passwordEditText.addTextChangedListener(null)
            }
        }
    }

    override fun showProgress() {
        loginButton.isVisible = false
        loginProgressBar.isVisible = true
    }

    override fun hideProgress() {
        loginButton.isVisible = true
        loginProgressBar.isVisible = false
    }

    override fun showUsernameError() {
        usernameError.visibility = VISIBLE
    }

    override fun showPasswordError() {
        passwordError.visibility = VISIBLE
    }

    override fun showHomeScreen() {
        intent = Intent(applicationContext, LoginHomeScreen::class.java)
        startActivity(intent)
    }

    override fun showErrorMessageToast(error: NetworkErrorMessage) {
        Toast.makeText(applicationContext, "There was an error Logging in", Toast.LENGTH_SHORT).show()
    }

    override fun clearUsernameError() {
        usernameError.visibility = GONE
    }

    override fun clearPasswordError() {
        usernameError.visibility = GONE
    }
}
