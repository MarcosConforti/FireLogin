package com.marcosconforti.firelogin.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.resume

class AuthService @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    //forma basica
    suspend fun login(user: String, password: String): FirebaseUser? {
        return firebaseAuth.signInWithEmailAndPassword(user, password).await().user
    }

    //esta es otra forma, aqui manejamos exepciones
    suspend fun register(user: String, password: String): FirebaseUser? {
        return suspendCancellableCoroutine { cancellableContinuation ->
            firebaseAuth.createUserWithEmailAndPassword(user, password)
                .addOnSuccessListener {
                    val user = it.user
                    cancellableContinuation.resume(user)
                }
                .addOnFailureListener {
                    cancellableContinuation.resumeWithException(it)
                }
        }
    }

    fun isUserLogged(): Boolean {
        return getCurrentUser() != null
    }

    fun logout() {
        firebaseAuth.signOut()
    }

    private fun getCurrentUser() = firebaseAuth.currentUser

}