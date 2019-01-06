package com.nsnik.nrs.jaron

import com.nsnik.nrs.jaron.util.ApplicationUtility
import junit.framework.Assert.assertEquals
import org.junit.Test

class ApplicationUtilityTest {

    @Test
    fun testReturnCurrentMonth(){
        assertEquals(ApplicationUtility.getCurrentMonth(),"January")
    }

}