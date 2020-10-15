package com.features.test_app_favoriteimage.app.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.features.test_app_favoriteimage.app.ui.common.Executor
import com.features.test_app_favoriteimage.db.AppDatabase
import com.features.test_app_favoriteimage.db.models.DBUsers
import com.features.test_app_favoriteimage.sharedPref.StorageManager

class AuthViewModel(private val storageManager: StorageManager, private val db: AppDatabase) : ViewModel() {

    val error = MutableLiveData<String>()
    val successfulLogIn = MutableLiveData<Boolean>()
    val successfulSignUp = MutableLiveData<Boolean>()

    fun signup(phone: String?, email: String?, pass: String?) {
        validateSignUp(phone, email, pass){ er ->
            if(er != null) {
                error.value = er
            } else {
                Executor.IOThread(Runnable {
                    val isUserAlreadyExist = db.getDBUsersDao().getUserByEmail(email!!).isNotEmpty()
                    if (isUserAlreadyExist) {
                        error.postValue("User ${email} already exist")
                    } else {
                        val user = DBUsers(
                            phone = phone!!,
                            email = email,
                            password = pass!!
                        )
                        db.getDBUsersDao().insert(user)
                        storageManager.setActiveUser(user.email)
                        successfulSignUp.postValue(true)
                    }
                })
            }
        }
    }

    fun login(email: String?, pass: String?) {
        validateLogIn(email, pass){er ->
            if(er != null) {
                error.value = er
            } else {
                Executor.IOThread(Runnable {
                    val users: List<DBUsers> = db.getDBUsersDao().getUserByEmail(email!!)
                    if (users.isEmpty()) {
                        error.postValue("User ${email} is not exist")
                    } else {
                        val user = users.first()
                        val isPassMatches: Boolean = user.password == pass
                        if (!isPassMatches) {
                            error.postValue("Password is not correct")
                        } else {
                            storageManager.setActiveUser(user.email)
                            successfulLogIn.postValue(true)
                        }
                    }
                })
            }
        }
    }

    private fun validateSignUp(phone: String?, email: String?, pass: String?, error: (String?) -> Unit) {
        var erString: String? = null

        if(phone.isNullOrBlank()) erString = "Phone field can not be empty"
        else if(email.isNullOrBlank()) erString = "Email field can not be empty"
        else if(pass.isNullOrBlank()) erString = "Password field can not be empty"

        error.invoke(erString)
    }

    private fun validateLogIn(email: String?, pass: String?, error: (String?) -> Unit) {
        var erString: String? = null

        if(email.isNullOrBlank()) erString = "Email field can not be empty"
        else if(pass.isNullOrBlank()) erString = "Pass field can not be empty"

        error.invoke(erString)
    }



}