package lesson11.task1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import java.lang.ArithmeticException

internal class UnsignedBigIntegerTest {

    @Test
    @Tag("8")
    fun ubiPlus() {
        assertEquals(UnsignedBigInteger(4), UnsignedBigInteger(2) + UnsignedBigInteger(2))
        assertEquals(UnsignedBigInteger("9087654330"), UnsignedBigInteger("9087654329") + UnsignedBigInteger(1))
    }

    @Test
    @Tag("8")
    fun ubiMinus() {
        assertEquals(UnsignedBigInteger(2), UnsignedBigInteger(4) - UnsignedBigInteger(2))
        assertEquals(UnsignedBigInteger("9087654329"), UnsignedBigInteger("9087654330") - UnsignedBigInteger(1))
        assertThrows(ArithmeticException::class.java) {
            UnsignedBigInteger(2) - UnsignedBigInteger(4)
        }
    }
    /*   @Test
       fun timesDigit() {
           assertEquals(
               UnsignedBigInteger("25769803776"),
               UnsignedBigInteger("4294967296") * 6
           )
       }*/

    @Test
    @Tag("12")
    fun ubiTimes() {
        assertEquals(
            UnsignedBigInteger("18446744073709551616"),
            UnsignedBigInteger("4294967296") * UnsignedBigInteger("4294967296")
        )
        assertEquals(
            UnsignedBigInteger("18810003804461274319515696"),
            UnsignedBigInteger("1153077420648") * UnsignedBigInteger("16312871510302")
            // 1153077420648 * 16312871510302 = 18810003804461274319515696
        )
        assertEquals(
            UnsignedBigInteger("32258878843930306530811650"),
            UnsignedBigInteger("3813485310405") * UnsignedBigInteger("8459159067930")
            //3813485310405 * 8459159067930 = 32258878843930306530811650
        )

        assertEquals(
            UnsignedBigInteger("24657096209079299511598623115275267540752014491723381506392009942772444005875"),
            UnsignedBigInteger("94041043251190174529930108247654427625") *
                    UnsignedBigInteger("262195051826663371676715460800146122107")
            //94041043251190174529930108247654427625 * 262195051826663371676715460800146122107 = 24657096209079299511598623115275267540752014491723381506392009942772444005875
        )
        assertEquals(
            UnsignedBigInteger("3633520143875024262454096726920769997975021790820941923314731087989300039220"),
            UnsignedBigInteger("215984006970303919751387823465344404222") *
                    UnsignedBigInteger("16823098130476875237188351146704513510")
            //215984006970303919751387823465344404222 * 16823098130476875237188351146704513510 = 3633520143875024262454096726920769997975021790820941923314731087989300039220
        )
        assertEquals(
            UnsignedBigInteger("14399310222834831547522639435533762749771803213513954647291007775036497335658654796007966855467342730404665057410535701275595469023500606471508653591036164"),
            UnsignedBigInteger("119997125894059777466250769560237028237860748876407391685772952750733028574142") *
                    UnsignedBigInteger("119997125894059777466250769560237028237860748876407391685772952750733028574142")
        )
        //14399310222834831547522639435533762749771803213513954647291007775036497335658654796007966855467342730404665057410535701275595469023500606471508653591036164
    }

    @Test
    @Tag("16")
    fun ubiDiv() {
        assertEquals(
            UnsignedBigInteger("4294967296‬"),
            UnsignedBigInteger("18446744073709551616") / UnsignedBigInteger("4294967296‬")
        )
    }

    @Test
    @Tag("16")
    fun ubiRem() {
        assertEquals(UnsignedBigInteger(5), UnsignedBigInteger(19) % UnsignedBigInteger(7))
        assertEquals(
            UnsignedBigInteger(0),
            UnsignedBigInteger("18446744073709551616") % UnsignedBigInteger("4294967296‬")
        )
    }

    @Test
    @Tag("8")
    fun ubiEquals() {
        assertEquals(UnsignedBigInteger(123456789), UnsignedBigInteger("123456789"))
    }

    @Test
    @Tag("8")
    fun ubiCompareTo() {
        assertTrue(UnsignedBigInteger(123456789) < UnsignedBigInteger("9876543210"))
        assertTrue(UnsignedBigInteger("9876543210") > UnsignedBigInteger(123456789))
    }

    @Test
    @Tag("8")
    fun ubiToInt() {
        assertEquals(123456789, UnsignedBigInteger("123456789").toInt())
    }
}