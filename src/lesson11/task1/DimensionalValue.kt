@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import java.lang.IllegalArgumentException

/**
 * Класс "Величина с размерностью".
 *
 * Предназначен для представления величин вроде "6 метров" или "3 килограмма"
 * Общая сложность задания - средняя, общая ценность в баллах -- 18
 * Величины с размерностью можно складывать, вычитать, делить, менять им знак.
 * Их также можно умножать и делить на число.
 *
 * В конструктор передаётся вещественное значение и строковая размерность.
 * Строковая размерность может:
 * - либо строго соответствовать одной из abbreviation класса Dimension (m, g)
 * - либо соответствовать одной из приставок, к которой приписана сама размерность (Km, Kg, mm, mg)
 * - во всех остальных случаях следует бросить IllegalArgumentException
 */
class DimensionalValue(value: Double, dimension: String) : Comparable<DimensionalValue> {
    /**
     * Величина с БАЗОВОЙ размерностью (например для 1.0Kg следует вернуть результат в граммах -- 1000.0)
     */
    private val prVal = value
    private val prDim = dimension
    val value: Double
        get() {
            val prefix = if (prDim.length < 2) DimensionPrefix.EMPTY else when (prDim.first()) {

                'K' -> DimensionPrefix.KILO
                'm' -> DimensionPrefix.MILLI
                else -> DimensionPrefix.EMPTY

                // если dimension соответствует нашей базовой таблицы, то не трогаем
                // иначе переводим
            }
            return prVal * prefix.multiplier
        }

    /**
     * БАЗОВАЯ размерность (опять-таки для 1.0Kg следует вернуть GRAM)
     */
    val dimension: Dimension
        get() = if (prDim.last() == 'g') Dimension.GRAM else Dimension.METER
    // если префикс на самом деле равен единице измерения, то ничего не делаем
    // иначе dimension = какой-то из DimensionPrefix

    /**
     * Конструктор из строки. Формат строки: значение пробел размерность (1 Kg, 3 mm, 100 g и так далее).
     */
    constructor(s: String) : this(
        s.substring(0, s.indexOf(" ")).toDouble(),
        s.substring(s.indexOf(" ") + 1, s.length)
    )


    /**
     * Сложение с другой величиной. Если базовая размерность разная, бросить IllegalArgumentException
     * (нельзя складывать метры и килограммы)
     */
    operator fun plus(other: DimensionalValue): DimensionalValue =
        if (dimension != other.dimension) throw IllegalArgumentException() else {
            //println("${this.value + other.value} ${this.dimension.abbreviation}")
            DimensionalValue("${this.value + other.value} ${this.dimension.abbreviation}")
        }

    /**
     * Смена знака величины
     */
    operator fun unaryMinus(): DimensionalValue = DimensionalValue(-value, this.dimension.abbreviation)

    /**
     * Вычитание другой величины. Если базовая размерность разная, бросить IllegalArgumentException
     */
    operator fun minus(other: DimensionalValue): DimensionalValue = this + (-other)

    /**
     * Умножение на число
     */
    operator fun times(other: Double): DimensionalValue =
        DimensionalValue(value * other, this.dimension.abbreviation)

    /**
     * Деление на число
     */
    operator fun div(other: Double): DimensionalValue = this * (1 / other)

    /**
     * Деление на другую величину. Если базовая размерность разная, бросить IllegalArgumentException
     */
    operator fun div(other: DimensionalValue): Double =
        if (dimension != other.dimension) throw IllegalArgumentException() else {
            value / other.value
        }

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean =
        other is DimensionalValue && dimension == other.dimension && value == other.value

    /**
     * Сравнение на больше/меньше. Если базовая размерность разная, бросить IllegalArgumentException
     */
    override fun compareTo(other: DimensionalValue): Int {
        return if (dimension != other.dimension) throw IllegalArgumentException() else when {
            value == other.value -> 0
            value > other.value -> 1
            else -> -1
        }
    }

    override fun hashCode(): Int {
        var result = prVal.hashCode()
        result = 31 * result + prDim.hashCode()
        result = 31 * result + value.hashCode()
        result = 31 * result + dimension.hashCode()
        return result
    }
}

/**
 * Размерность. В этот класс можно добавлять новые варианты (секунды, амперы, прочие), но нельзя убирать
 */
enum class Dimension(val abbreviation: String) {
    METER("m"),
    GRAM("g");
}

/**
 * Приставка размерности. Опять-таки можно добавить новые варианты (деци-, санти-, мега-, ...), но нельзя убирать
 */
enum class DimensionPrefix(val abbreviation: String, val multiplier: Double) {
    KILO("K", 1000.0),
    MILLI("m", 0.001),
    EMPTY("", 1.0);
}