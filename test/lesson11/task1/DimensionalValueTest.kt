package lesson11.task1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag

internal class DimensionalValueTest {

    private fun assertApproxEquals(expected: DimensionalValue, actual: DimensionalValue, eps: Double) {
        assertEquals(expected.dimension, actual.dimension)
        assertEquals(expected.value, actual.value, eps)
    }

    @Test
    @Tag("12")
    fun dvBase() {
        val first = DimensionalValue(1.0, "Kg")
        assertEquals(1000.0, first.value)
        assertEquals(Dimension.GRAM, first.dimension)
        val second = DimensionalValue("200 m")
        assertEquals(200.0, second.value)
        assertEquals(Dimension.METER, second.dimension)
        // TODO()
    }

    @Test
    @Tag("6")
    fun dvPlus() {
        assertApproxEquals(DimensionalValue("2 Km"), DimensionalValue("1 Km") + DimensionalValue("1000 m"), 1e-8)
        assertThrows(IllegalArgumentException::class.java) {
            DimensionalValue("1 g") + DimensionalValue("1 m")
        }
        // TODO()
    }

    @Test
    @Tag("4")
    fun dvUnaryMinus() {
        assertApproxEquals(DimensionalValue("-2 g"), -DimensionalValue("2 g"), 1e-12)
        // TODO()
    }

    @Test
    @Tag("6")
    fun dvMinus() {
        assertApproxEquals(DimensionalValue("0 m"), DimensionalValue("1 Km") - DimensionalValue("1000 m"), 1e-10)
        assertThrows(IllegalArgumentException::class.java) {
            DimensionalValue("1 g") - DimensionalValue("1 m")
        }
        // TODO()
    }

    @Test
    @Tag("4")
    fun dvTimes() {
        assertApproxEquals(DimensionalValue("2 Kg"), DimensionalValue("2 g") * 1000.0, 1e-8)
        // TODO()
    }

    @Test
    @Tag("6")
    fun divValue() {
        assertEquals(1.0, DimensionalValue("3 m") / DimensionalValue("3000 mm"), 1e-10)
        assertThrows(IllegalArgumentException::class.java) {
            DimensionalValue("1 g") / DimensionalValue("1 m")
        }
        // TODO()
    }

    @Test
    @Tag("4")
    fun divDouble() {
        assertApproxEquals(DimensionalValue("42 mm"), DimensionalValue("42 m") / 1000.0, 1e-11)
        // TODO()
    }

    @Test
    @Tag("4")
    fun dvEquals() {
        assertEquals(DimensionalValue("1 Kg"), DimensionalValue("1 Kg"))
        assertEquals(DimensionalValue("3 mm"), DimensionalValue("3 mm"))
        // TODO()
    }

    @Test
    @Tag("4")
    fun dvHashCode() {
        assertEquals(DimensionalValue("1 Kg").hashCode(), DimensionalValue("1 Kg").hashCode())
        // TODO()
    }

    @Test
    @Tag("4")
    fun dvCompareTo() {
        assertTrue(DimensionalValue("1 Kg") < DimensionalValue("1500 g"))
        assertTrue(DimensionalValue("1 m") > DimensionalValue("900 mm"))
        // TODO()
    }
}