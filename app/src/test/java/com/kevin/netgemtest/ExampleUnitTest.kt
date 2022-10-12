package com.kevin.netgemtest

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.giphy.sdk.ui.Giphy
import com.kevin.netgemtest.Activity.MainActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest {


    @Rule
    var scenarioActivity  = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init(){
        Giphy.configure(ApplicationProvider.getApplicationContext(), "25QLCq6dnp5trFPXDXAclf6AvkSHKgmP", true)
    }
    
    @Test
    public fun DisplayRandomGif() {
        
    }

    @Test
    public fun showGifButton() {

    }

    @Test
    public fun startDisplayIntent() {

    }

    @Test
    public fun bsckFromDisplayIntent() {

    }
    
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}