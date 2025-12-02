package com.mod6.ae3_abpro1_loginapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mod6.ae3_abpro1_loginapp.R
import com.mod6.ae3_abpro1_loginapp.presentation.LoginActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityUITest {

    /**
     * Verifica que los elementos esenciales del login sean visibles
     */
    @Test
    fun elementosSonVisibles() {
        ActivityScenario.launch<LoginActivity>(LoginActivity::class.java).use { scenario ->
            // Forzar focus del decorView para evitar RootViewWithoutFocusException
            scenario.onActivity { activity ->
                activity.window.decorView.requestFocus()
            }

            // Comprobar que los elementos están visibles
            onView(withId(R.id.editTextUsername)).check(matches(isDisplayed()))
            onView(withId(R.id.editTextPassword)).check(matches(isDisplayed()))
            onView(withId(R.id.buttonLogin)).check(matches(isDisplayed()))
        }
    }

    /**
     * Prueba que al hacer login con campos vacíos se muestre el mensaje de error
     */
    @Test
    fun login_conCamposVacios_muestraError() {
        ActivityScenario.launch<LoginActivity>(LoginActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                activity.window.decorView.requestFocus()
            }

            // Simular click en login
            onView(withId(R.id.buttonLogin)).perform(click())

            // Verificar mensaje de error
            onView(withId(R.id.textViewMessage))
                .check(matches(withText("Usuario y contraseña son obligatorios")))
        }
    }
}