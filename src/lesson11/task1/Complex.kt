@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import java.lang.IllegalArgumentException
import kotlin.math.pow

/**
 * Фабричный метод для создания комплексного числа из строки вида x+yi
 */
fun Complex(s: String): Complex {
    //if(!s.matches(Regex("""(-?\d+)([-\+]\d+i)"""))) throw IllegalArgumentException()
    val parts = Regex("""(-?\d+(.\d+)?)([-+]\d+(.\d+)?)""").find(s)
    return if (parts != null) {
        Complex(parts.groupValues[1].toDouble(), parts.groupValues[3].toDouble())
    } else throw IllegalArgumentException()
}

/**
 * Класс "комплексное число".
 *
 * Общая сложность задания -- лёгкая, общая ценность в баллах -- 8.
 * Объект класса -- комплексное число вида x+yi.
 * Про принципы работы с комплексными числами см. статью Википедии "Комплексное число".
 *
 * Аргументы конструктора -- вещественная и мнимая часть числа.
 */
class Complex(val re: Double, val im: Double) {

    /**
     * Конструктор из вещественного числа
     */
    constructor(x: Double) : this(x, 0.0)

    /**
     * Сложение.
     */
    operator fun plus(other: Complex): Complex = Complex(re + other.re, im + other.im)

    /**
     * Смена знака (у обеих частей числа)
     */
    operator fun unaryMinus(): Complex = Complex(-re, -im)

    /**
     * Вычитание
     */
    operator fun minus(other: Complex): Complex = this + (-other)

    /**
     * Умножение
     */
    operator fun times(other: Complex): Complex =
        Complex(re * other.re - im * other.im, im * other.re + re * other.im)

    /**
     * Деление
     */
    operator fun div(other: Complex): Complex {

        /*
        Через определение 1 / z
        Однако "Для каждого комплексного числа a + b i , {a+bi} кроме нуля, можно найти обратное к нему"
        val denominator = other.re.pow(2) + other.im.pow(2)
        val oneOverOther = Complex(other.re / denominator, -other.im / denominator)
        return this * oneOverOther*/
        return Complex(
            (re * other.re + im * other.im) / (other.re.pow(2) + other.im.pow(2)),
            (im * other.re - re * other.im) / (other.re.pow(2) + other.im.pow(2))
        )
    }

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean = other is Complex && re == other.re && im == other.im

    /**
     * Преобразование в строку
     */
    override fun toString(): String = when (im < 0) { // В тестах ввод осуществляется в Int, однако вывод в Double
        true -> "${re}${im}i"
        else -> "${re}+${im}i"
    }

    override fun hashCode(): Int {
        var result = re.hashCode()
        result = 31 * result + im.hashCode()
        return result
    }
}
/*
println(Complex(1.0, 2.0))
println(Complex(-1.0, -23.0))
*/
