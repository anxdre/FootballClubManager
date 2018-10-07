package com.setiawan.anxdre.footballclubmanager.fragments

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class FragmentLastEventTest {

    @Test
    fun RequestDataFromServer() {
        val url = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328"
        val getLastEvent = mock(FragmentLastEvent::class.java)
        getLastEvent.loadFan(url)
        verify(getLastEvent).loadFan(url)
    }
}