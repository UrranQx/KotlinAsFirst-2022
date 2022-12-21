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
        assertEquals(
            UnsignedBigInteger("9".repeat(1000)),
            UnsignedBigInteger("1" + "0".repeat(1000)) - UnsignedBigInteger(1)
        )
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
            UnsignedBigInteger(4),
            UnsignedBigInteger(24) / UnsignedBigInteger(6)
        )
        assertEquals(
            UnsignedBigInteger("4294967296"),
            UnsignedBigInteger("18446744073709551616") / UnsignedBigInteger("4294967296")
        )
        UnsignedBigInteger("94041043251190174529930108247654427625")
        UnsignedBigInteger("24657096209079299511598623115275267540752014491723381506392009942772444005875") /
                UnsignedBigInteger("262195051826663371676715460800146122107")
    }

    @Test
    fun ubiPow() {
        /*println(
            UnsignedBigInteger(2).quickPow(
                UnsignedBigInteger(2).quickPow(
                    UnsignedBigInteger(12)
                )
            )
        )*/
        assertEquals(
            UnsignedBigInteger("215443603695053959568491434297407572853129769129479492603780631350401380974467032676539087874293028927093092995091382473665313475961476104769288282122090994827050053307056695649733208616053512929502758017060013049505627727257265571040872174418643670928149798177828131558406438313043404001"),
            UnsignedBigInteger(7).quickPow(UnsignedBigInteger(340))
        )
        assertEquals(
            UnsignedBigInteger("1"),
            UnsignedBigInteger(7).quickPow(UnsignedBigInteger(0))
        )
        assertEquals(
            UnsignedBigInteger("316227914292292593635741230782135133880359578608581867130909484360672139143998570978286204833991482968970636466044658228409623346562937144979432552340278065564457976768445210523320961929954388752035177598518906901943"),
            UnsignedBigInteger(7).quickPow(UnsignedBigInteger(255))
        )
        assertEquals(
            UnsignedBigInteger(
                "1044388881413152506691752710716624382579964249047383780384233483283953907971557456848826811934997558340890106714439262837987573438185793607263236087851365277945956976543709998340361590134383718314428070011855946226376318839397712745672334684344586617496807908705803704071284048740118609114467977783598029006686938976881787785946905630190260940599579453432823469303026696443059025015972399867714215541693835559885291486318237914434496734087811872639496475100189041349008417061675093668333850551032972088269550769983616369411933015213796825837188091833656751221318492846368125550225998300412344784862595674492194617023806505913245610825731835380087608622102834270197698202313169017678006675195485079921636419370285375124784014907159135459982790513399611551794271106831134090584272884279791554849782954323534517065223269061394905987693002122963395687782878948440616007412945674919823050571642377154816321380631045902916136926708342856440730447899971901781465763473223850267253059899795996090799469201774624817718449867455659250178329070473119433165550807568221846571746373296884912819520317457002440926616910874148385078411929804522981857338977648103126085903001302413467189726673216491511131602920781738033436090243804708340403154190336"
            ),
            UnsignedBigInteger(2).quickPow(UnsignedBigInteger(4096))
        )
    }

    @Test
    @Tag("16")
    fun ubiRem() {
        assertEquals(UnsignedBigInteger(5), UnsignedBigInteger(19) % UnsignedBigInteger(7))
        assertEquals(
            UnsignedBigInteger(0),
            UnsignedBigInteger("18446744073709551616") % UnsignedBigInteger("4294967296")
        )
        assertEquals(
            UnsignedBigInteger(20),
            UnsignedBigInteger(3).quickPow(54) % UnsignedBigInteger(101)
        )
    }

    @Test
    @Tag("8")
    fun ubiEquals() {
        assertEquals(UnsignedBigInteger(123456789), UnsignedBigInteger("123456789"))
        assertEquals(UnsignedBigInteger(409), UnsignedBigInteger("0000000000000000000000409"))
    }

    @Test
    fun ubiHashCode() {
        assertEquals(UnsignedBigInteger(123456789).hashCode(), UnsignedBigInteger("123456789").hashCode())
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