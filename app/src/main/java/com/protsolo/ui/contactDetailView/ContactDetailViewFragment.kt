package com.protsolo.ui.contactDetailView

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.protsolo.databinding.FragmentContactDetailViewBinding
import com.protsolo.itemModel.UserModel
import com.protsolo.ui.INavigateToFragmentListener
import com.protsolo.utils.Constants
import com.protsolo.utils.extensions.loadImageWithFresco

class ContactDetailViewFragment : Fragment() {

    private lateinit var binding: FragmentContactDetailViewBinding
    private var listener: INavigateToFragmentListener? = null

    val fragmentTag = Constants.DETAIL_VIEW

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
    ): View {
        binding = FragmentContactDetailViewBinding.inflate(layoutInflater)
        val args = arguments?.get(Constants.BUNDLE_KEY)
        if (args != null) {
            extractArguments(args)
        }

        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.buttonContactDetailViewBack.setOnClickListener {
            listener?.onBackButtonPressed()
        }
    }

    private fun extractArguments(args: Any) {
        val user = args as UserModel
        with(binding) {
            imageViewContactDetailViewProfilePhoto.loadImageWithFresco(user.image)
            textViewContactDetailViewUserName.text = user.name
            textViewContactDetailViewCareer.text = user.career
            textViewContactDetailViewHomeAddress.text = user.address
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(args: Bundle) =
            ContactDetailViewFragment().apply {
                arguments = args
            }
    }
}


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
// * Use the [ContactDetailViewFragment.newInstance] factory method to
// * create an instance of this fragment.
// */
//class ContactDetailViewFragment : Fragment() {
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
//        return inflater.inflate(R.layout.fragment_contact_detail_view, container, false)
//    }
//

//}