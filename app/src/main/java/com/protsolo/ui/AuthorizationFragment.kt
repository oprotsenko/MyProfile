package com.protsolo.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.protsolo.App
import com.protsolo.R
import com.protsolo.databinding.FragmentAuthorizationBinding
import com.protsolo.utils.Constants
import com.protsolo.utils.extensions.hideKeyboard


class AuthorizationFragment : Fragment() {

    val fragmentTag = Constants.AUTHORIZATION
    private val preferenceStorage = App().getStoragePreferences()
    private var listener: INavigateToFragmentListener? = null

    private val args: Bundle = Bundle()

    private lateinit var binding: FragmentAuthorizationBinding

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
        binding = FragmentAuthorizationBinding.inflate(layoutInflater)

        if (preferenceStorage.getBoolean(Constants.AUTOLOGIN)) {
            autologin()
        }

        binding.root.hideKeyboard() // todo not working
        setListeners()
        return binding.root
    }

    private fun autologin() {
        args.putString(
            Constants.PREFERENCE_EMAIL_KEY,
            preferenceStorage.getString(Constants.PREFERENCE_EMAIL_KEY)
        )
        listener?.onNavigateToFragment(Constants.MAIN_PAGE, args)
    }

    private fun setListeners() {
        binding.apply {
            editTextAuthEmailAddressField.post {
                editTextAuthEmailAddressField.doOnTextChanged { text, _, _, _ ->
                    if (isValidMail(text)) {
                        textInputLayoutAuthEmail.isErrorEnabled = false
                    } else {
                        textInputLayoutAuthEmail.error = getString(R.string.authMessageEmailError)
                        textInputLayoutAuthEmail.isErrorEnabled = true
                    }
                    setButtonStatus(binding)
                }
            }
            editTextAuthPasswordField.post {
                editTextAuthPasswordField.doOnTextChanged { text, _, _, _ ->
                    if (isValidPassword(text)) {
                        textInputLayoutAuthPassword.isErrorEnabled = false
                    } else {
                        textInputLayoutAuthPassword.error =
                            getString(R.string.authMessagePasswordError)
                        textInputLayoutAuthPassword.isErrorEnabled = true
                    }
                    setButtonStatus(binding)
                }
            }
            buttonAuthRegister.setOnClickListener {
                buttonAuthRegister.isEnabled = false // to prevent double click
                register()
            }
            customButtonGoogle.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/")))
            }
        }
    }

    private fun setButtonStatus(binding: FragmentAuthorizationBinding) {
        binding.apply {
            buttonAuthRegister.isEnabled =
                isValidMail(textInputLayoutAuthEmail.editText?.text)
                        && isValidPassword(editTextAuthPasswordField.text)
        }
    }

    private fun isValidPassword(text: CharSequence?) =
        Regex(Constants.PASSWORD_PATTERN).matches(text.toString())

    private fun isValidMail(text: CharSequence?) =
        Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()

    private fun register() {
        with(Constants) {
            preferenceStorage.save(
                PREFERENCE_EMAIL_KEY,
                binding.editTextAuthEmailAddressField.text.toString()
            )
            preferenceStorage.save(
                PREFERENCE_PASSWORD_KEY,
                binding.editTextAuthPasswordField.text.toString()
            )
            preferenceStorage.save(
                AUTOLOGIN, binding.checkBoxAuthRememberMe.isChecked
            )
            args.putString(
                PREFERENCE_EMAIL_KEY,
                binding.editTextAuthEmailAddressField.text.toString()
            )
        }
        listener?.onNavigateToFragment(Constants.MAIN_PAGE, args)
    }

    companion object {

        @JvmStatic
        fun newInstance(args: Bundle) =
            AuthorizationFragment().apply {
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
// * Use the [AuthorizationFragment.newInstance] factory method to
// * create an instance of this fragment.
// */
//class AuthorizationFragment : Fragment() {
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
//        return inflater.inflate(R.layout.fragment_authorization, container, false)
//    }
//

//}