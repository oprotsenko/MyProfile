package com.protsolo.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.protsolo.App
import com.protsolo.databinding.FragmentMainPageBinding
import com.protsolo.utils.Constants
import com.protsolo.utils.PreferenceStorage
import com.protsolo.utils.extensions.loadImageWithFresco


class MainPageFragment : Fragment() {

    val fragmentTag = Constants.MAIN_PAGE

    private val args: Bundle = Bundle()

    private lateinit var binding: FragmentMainPageBinding
    private lateinit var preferenceStorage: PreferenceStorage
    private var listener: INavigateToFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is INavigateToFragmentListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainPageBinding.inflate(layoutInflater)
        preferenceStorage = App().getStoragePreferences() // todo move to onCreatedView
        val photo = binding.imageViewMainProfilePhoto
        photo.loadImageWithFresco(Constants.DEFAULT_IMAGE)

        setListeners()
        setName()
        return binding.root
    }

    /**
     * Gets the email from the intent message, if it is nothing there,
     * gets the email from the app base, calls the method to
     * parse it for the name and soname.
     */
    private fun setName() {
        var name = arguments?.getString(Constants.PREFERENCE_EMAIL_KEY)
        if (name.isNullOrEmpty()) {
            name = preferenceStorage.getString(Constants.PREFERENCE_EMAIL_KEY)
        }
        val parsedUserName: String = parseName(name)
        binding.textViewMainUserName.text = parsedUserName
    }

    /**
     * Parse the obtained string to the user name.
     */
    private fun parseName(name: String?): String {
        val res = StringBuilder()
        if (name.isNullOrEmpty())
            return Constants.DEFAULT_NAME
        for (c in name.indices) {
            when (name[c]) {
                '.' -> res.append(" ")
                '@' -> break
                else -> if (c == 0 || res[c - 1] == ' ') res.append(name[c].uppercase()) else
                    res.append(name[c])
            }
        }
        return res.toString()
    }

    private fun setListeners() {
        binding.buttonMainViewContacts.setOnClickListener {
            startContactsListActivity()
        }
    }

    private fun startContactsListActivity() {
        listener?.onNavigateToFragment(Constants.CONTACTS_LIST, args)
    }

    companion object {
        @JvmStatic
        fun newInstance(args: Bundle) =
            MainPageFragment().apply {
                arguments = args
            }
    }
}
//package com.protsolo.ui
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.protsolo.R
//
//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//
///**
// * A simple [Fragment] subclass.
// * Use the [MainPageFragment.newInstance] factory method to
// * create an instance of this fragment.
// */
//class MainPageFragment : Fragment() {
//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main_page, container, false)
//    }
//

//}