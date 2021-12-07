package com.protsolo.ui.main.authorization

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.protsolo.R
import com.protsolo.app.base.BaseFragment
import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.extensions.hideKeyboard
import com.protsolo.databinding.FragmentAuthorizationBinding
import com.protsolo.ui.main.authorization.viewPager.ViewPagerFragment
import com.protsolo.ui.main.authorization.viewPager.ViewPagerFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthorizationFragment :
    BaseFragment<FragmentAuthorizationBinding>(FragmentAuthorizationBinding::inflate) {

    private val viewModel: AuthorizationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.isLoginPage(arguments?.getBoolean(Constants.IS_LOGIN_PAGE) ?: true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObservers()
        setListeners()
    }

    private fun setObservers() {
        viewModel.apply {
            isAutologin.observe(viewLifecycleOwner, {
                navigator.navigate(
                    ViewPagerFragmentDirections.actionViewPagerFragmentNavToProfileFragmentNav()
                )
            })

            isLoginPage.observe(viewLifecycleOwner, { isLoginPage ->
                if (isLoginPage) {
                    setUpViews()
                }
            })

            enteredEmail.observe(viewLifecycleOwner, { isValidEmail() })

            enteredPass.observe(viewLifecycleOwner, { isValidPass() })

            isValidEmail.observe(viewLifecycleOwner, { isCorrectEmail ->
                binding.apply {
                    if (!isCorrectEmail) {
                        textInputLayoutAuthEmail.error = getString(R.string.authMessageEmailError)
                    } else {
                        textInputLayoutAuthEmail.isErrorEnabled = false
                    }
                }
            })

            isValidPass.observe(viewLifecycleOwner, { isCorrectPass ->
                binding.apply {
                    if (!isCorrectPass) {
                        textInputLayoutAuthPassword.error =
                            getString(R.string.authMessagePasswordError)
                    } else {
                        textInputLayoutAuthPassword.isErrorEnabled = false
                    }
                }
                isValidData()
            })

            isValidData.observe(viewLifecycleOwner, { isValidData ->
                if (isValidData) {
                    writeToPreferenceStorage(binding.checkBoxAuthRememberMe.isChecked)
                    navigator.navigate(
                        ViewPagerFragmentDirections.actionViewPagerFragmentNavToProfileFragmentNav()
                    )
                }
            })
        }
    }

    private fun setUpViews() {
        binding.apply {
            textViewAuthTitle.text = resources.getText(R.string.login_hello)
            textViewAuthFillOutDescription.text = resources.getText(R.string.login_description)
            textViewAuthForgotPass.visibility = View.VISIBLE
            customButtonGoogle.visibility = View.GONE
            textViewAuthOr.visibility = View.GONE
            buttonAuthRegister.text = resources.getText(R.string.login_login)
            textViewAuthTermsAndConditions.visibility = View.GONE
            textViewAuthHaveAccount.text = resources.getText(R.string.login_do_not_have_account)
            textViewAuthSignInUp.text = resources.getText(R.string.login_sign_up)
        }
    }

    private fun setListeners() {
        binding.apply {

            root.setOnClickListener { root.hideKeyboard() }

            customButtonGoogle.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/")))
            }
            textViewAuthSignInUp.setOnClickListener {
                val onPagerClickListener = parentFragment as ViewPagerFragment
                onPagerClickListener.onClick()
            }
            buttonAuthRegister.setOnClickListener {
                viewModel.setEnteredEmail(editTextAuthEmailAddressField.text.toString())
                viewModel.setEnteredPass(editTextAuthPasswordField.text.toString())
            }
        }
    }

    companion object {
        fun createFragment(isLoginPage: Boolean) =
            AuthorizationFragment().apply {
                arguments = Bundle()
                arguments?.putBoolean(Constants.IS_LOGIN_PAGE, isLoginPage)
            }
    }
}