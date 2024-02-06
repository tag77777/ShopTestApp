package a77777_888.me.t.shoptestapp

import a77777_888.me.t.shoptestapp.data.remote.MockSource
import a77777_888.me.t.shoptestapp.data.remote.entities.MockResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun `data download is correct`() {
        assertEquals(4, 2 + 2)

        runBlocking {
            val response = MockSource().getData()

            println("RESPONSE")
            println(response.body())

            assertNotNull(response)
        }
    }

    @Test
    fun `validation is correct`() {
        val pattern = "[а-яА-Я]+".toRegex()

        assertTrue(pattern.matches("Абрикос"))
        assertTrue("ываыаЫАПЫфыаф".matches(pattern))
        assertFalse("рпароzz".matches(pattern))
        assertFalse("ждло====".matches(pattern))
        assertFalse("ждлоЯЯZZ".matches(pattern))
    }
}