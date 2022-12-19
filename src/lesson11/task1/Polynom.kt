@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import kotlin.math.pow

/**
 * Класс "полином с вещественными коэффициентами".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 16.
 * Объект класса -- полином от одной переменной (x) вида 7x^4+3x^3-6x^2+x-8.
 * Количество слагаемых неограничено.
 *
 * Полиномы можно складывать -- (x^2+3x+2) + (x^3-2x^2-x+4) = x^3-x^2+2x+6,
 * вычитать -- (x^3-2x^2-x+4) - (x^2+3x+2) = x^3-3x^2-4x+2,
 * умножать -- (x^2+3x+2) * (x^3-2x^2-x+4) = x^5+x^4-5x^3-3x^2+10x+8,
 * делить с остатком -- (x^3-2x^2-x+4) / (x^2+3x+2) = x-5, остаток 12x+16
 * вычислять значение при заданном x: при x=5 (x^2+3x+2) = 42.
 *
 * В конструктор полинома передаются его коэффициенты, начиная со старшего.
 * Нули в середине и в конце пропускаться не должны, например: x^3+2x+1 --> Polynom(1.0, 2.0, 0.0, 1.0)
 * Старшие коэффициенты, равные нулю, игнорировать, например Polynom(0.0, 0.0, 5.0, 3.0) соответствует 5x+3
 */
class Polynom(vararg coeffs: Double) {

    private val coefficients = coeffs
    private val coeffsLen = coefficients.size

    /**
     * Геттер: вернуть значение коэффициента при x^i
     */
    fun coeff(i: Int): Double = if (i < coeffsLen) coefficients.elementAt(coeffsLen - i - 1) else 0.0

    /**
     * Расчёт значения при заданном x
     */
    fun getValue(x: Double): Double = coefficients.foldIndexed(0.0)
    { i, previous, _ -> previous + coeff(i) * x.pow(i) }
    /*var ans = 0.0
    for (i in coefficients.indices) ans += coeff(i) * x.pow(i)
    return ans*/

    /**
     * Степень (максимальная степень x при ненулевом слагаемом, например 2 для x^2+x+1).
     *
     * Степень полинома с нулевыми коэффициентами считать равной 0.
     * Слагаемые с нулевыми коэффициентами игнорировать, т.е.
     * степень 0x^2+0x+2 также равна 0.
     */
    fun degree(): Int {
        for (i in coefficients.indices) if (coefficients[i] != 0.0) return coeffsLen - i - 1
        return 0
    }

    /**
     * Сложение
     */
    operator

    fun plus(other: Polynom): Polynom {
        val mx = maxOf(coeffsLen, other.coeffsLen)
        val ans = mutableListOf<Double>()
        for (i in mx - 1 downTo 0) ans.add(coeff(i) + other.coeff(i))
        return Polynom(*ans.toDoubleArray())
    }

    /**
     * Смена знака (при всех слагаемых)
     */
    operator fun unaryMinus(): Polynom = Polynom(*(coefficients.map { -it }.toDoubleArray()))

    /**
     * Вычитание
     */
    operator fun minus(other: Polynom): Polynom = this.plus(-other)

    /**
     * Умножение
     */
    operator fun times(other: Polynom): Polynom {
        // в чем сложность? в том, что при перемножении мы с degree(i) прыгаем на degree(i+j)
        val mx = maxOf(coeffsLen, other.coeffsLen)
        val ans = mutableListOf<Polynom>()
        // я могу взять первый полином, его degree -> i, возьмем значение другого полинома в degree j
        // очевидно, что результат перемножения каждого с каждым надо куда-то записать, а в записи полинома
        // надо сдвинуть на n (добавить n нулей в конец) n = j
        for (j in mx - 1 downTo 0) { // выбрали коэфф второго полинома (типо начинаем со старшего xD)
            val poly = mutableListOf<Double>()
            for (i in mx - 1 downTo 0) { // проходимся по коэффам первого полинома
                poly.add(coeff(i) * other.coeff(j))
                //coeff(i) * other.coeff(j))
            }
            for (i in 1..j) poly.add(0.0)
            ans.add(Polynom(*poly.toDoubleArray())) // возможно тут не стоит переводить в Polynom
        }
        var accumulate = Polynom(0.0)
        for (poly in ans) accumulate += poly // Однако, это позволяет реализовать accumulate в 2 строки
        return accumulate
    }

    /**
     * Деление
     *
     * Про операции деления и взятия остатка см. статью Википедии
     * "Деление многочленов столбиком". Основные свойства:
     *
     * Если A / B = C и A % B = D, то A = B * C + D и степень D меньше степени B
     */
    operator fun div(other: Polynom): Polynom = TODO()

    /**
     * Взятие остатка
     */
    operator fun rem(other: Polynom): Polynom = TODO()

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean = TODO()

    /**
     * Получение хеш-кода
     */
    override fun hashCode(): Int = TODO()
}
