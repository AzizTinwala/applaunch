package io.applaunch.applaunchmini.view.fragment

import android.os.Bundle
import android.os.SystemClock
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import io.applaunch.applaunchmini.R
import io.applaunch.applaunchmini.session.SessionManagement
import io.applaunch.applaunchmini.utils.CommonUtils

class LoginFragment : Fragment() {
    var email: EditText? = null
    var password: EditText? = null
    var login: Button? = null
    private var mLastClickTime: Long = 0
    var sessionManagement: SessionManagement? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController: NavController = Navigation.findNavController(view)
        sessionManagement = SessionManagement(view.context)
        if (sessionManagement!!.isLoggedIn()) {
            val navoption = NavOptions.Builder().setPopUpTo(R.id.mainFragment, true).build()
            navController.navigate(R.id.action_loginFragment_to_mainFragment,null,navoption)
        }
        email = view.findViewById(R.id.username)
        password = view.findViewById(R.id.password)
        login = view.findViewById(R.id.signin)

        login!!.setOnClickListener {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) { // 1000 = 1second
                return@setOnClickListener
            }
            mLastClickTime =
                SystemClock.elapsedRealtime()
            if (TextUtils.isEmpty(
                    email!!.text.toString().trim { it <= ' ' }) && TextUtils.isEmpty(
                    password!!.text.toString().trim { it <= ' ' })
            ) {
                Snackbar.make(
                    view.context,
                    view,
                    "UserName or Password cannot be empty",
                    Snackbar.LENGTH_LONG
                ).show()
            } else if (!CommonUtils.isValidEmail(email!!.text.toString())) {


                Snackbar.make(
                    view.context,
                    view,
                    "Invalid Username",
                    Snackbar.LENGTH_LONG
                ).show()
            } else if (email!!.text.toString() == "testapp@google.com" && password!!.text.toString() == "Test@123456") {
                sessionManagement!!.createLoginSession()
                val navoption = NavOptions.Builder().setPopUpTo(R.id.mainFragment, true).build()
                navController.navigate(R.id.action_loginFragment_to_mainFragment,null,navoption)

            } else {
                Snackbar.make(
                    view.context,
                    view,
                    "Invalid UserName or Password",
                    Snackbar.LENGTH_LONG
                ).show()

            }
        }

    }
}