package io.applaunch.applaunchmini.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.applaunch.applaunchmini.R
import io.applaunch.applaunchmini.adapter.UserAdapter
import io.applaunch.applaunchmini.repository.databaseRoom.entity.User
import io.applaunch.applaunchmini.viewmodel.UserListViewModel

class MainFragment : Fragment(), UserAdapter.OnClickListener {
    var userView: RecyclerView? = null
    var addUser: Button? = null
    private lateinit var userObserver: Observer<MutableList<User>>
    private lateinit var userViewModel: UserListViewModel
    private lateinit var userList: MutableList<User>
    private lateinit var userAdapter: UserAdapter
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        addUser = view.findViewById(R.id.add_user)
        userView = view.findViewById(R.id.user_view)
        addUser!!.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_addUserFragment2)
        }
        with(userView!!) {
            layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)

            val dividerItemDecoration =
                DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)

        }
        getUser()
    }

    private fun getUser() {

        userViewModel = ViewModelProvider(this)[UserListViewModel::class.java]
        userObserver = Observer<MutableList<User>> {
            userList = it
            userAdapter = UserAdapter(it, this@MainFragment)
            userView!!.adapter = userAdapter
        }
        userViewModel.user.observe(
            viewLifecycleOwner, userObserver
        )
    }

    override fun onItemClick(position: Int) {
        navController.navigate(R.id.action_mainFragment_to_weatherFragment)
    }

}
