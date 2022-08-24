package io.applaunch.applaunchmini.view.fragment

import android.os.Bundle
import android.os.SystemClock
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import io.applaunch.applaunchmini.R
import io.applaunch.applaunchmini.adapter.UserAdapter
import io.applaunch.applaunchmini.repository.databaseRoom.entity.User
import io.applaunch.applaunchmini.utils.CommonUtils
import io.applaunch.applaunchmini.viewmodel.UserListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddUserFragment : Fragment() {
    private lateinit var userViewModel: UserListViewModel

    var firstName: EditText? = null
    var lastName: EditText? = null
    var email: EditText? = null
    var save: Button? = null
    var cancel: Button? = null
    private var mLastClickTime: Long = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController: NavController = Navigation.findNavController(view)

        firstName = view.findViewById(R.id.edt_first_name)
        lastName = view.findViewById(R.id.edt_last_name)
        email = view.findViewById(R.id.edt_email)
        save = view.findViewById(R.id.save)
        cancel = view.findViewById(R.id.cancel)

        save!!.setOnClickListener {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) { // 1000 = 1second
                return@setOnClickListener
            }
            mLastClickTime =
                SystemClock.elapsedRealtime()
            if (TextUtils.isEmpty(
                    email!!.text.toString().trim { it <= ' ' }) && TextUtils.isEmpty(
                    firstName!!.text.toString().trim { it <= ' ' }) && TextUtils.isEmpty(
                    lastName!!.text.toString().trim { it <= ' ' })
            ) {
                Snackbar.make(
                    view.context,
                    view,
                    "All Fields as Mandatory",
                    Snackbar.LENGTH_LONG
                ).show()
            } else if (!CommonUtils.isValidEmail(email!!.text.toString())) {


                Snackbar.make(
                    view.context,
                    view,
                    "Invalid Email",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                val user = User(
                    null,
                    firstName!!.text.toString(),
                    lastName!!.text.toString(),
                    email!!.text.toString()
                )

                CoroutineScope(Dispatchers.IO).launch {
                    insertUser(user)
                }


                val navoption = NavOptions.Builder().setPopUpTo(R.id.mainFragment, true).build()
                navController.navigate(
                    R.id.action_addUserFragment2_to_mainFragment,
                    null,
                    navoption
                )

            }
        }


        cancel!!.setOnClickListener {
            val navoption = NavOptions.Builder().setPopUpTo(R.id.mainFragment, true).build()
            navController.navigate(R.id.action_addUserFragment2_to_mainFragment, null, navoption)
        }
    }

    private suspend fun insertUser(user: User) {

        userViewModel = ViewModelProvider(this)[UserListViewModel::class.java]
        userViewModel.insertUser(user)
    }
}