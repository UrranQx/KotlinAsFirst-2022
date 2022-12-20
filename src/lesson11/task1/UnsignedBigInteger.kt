package lesson11.task1

import java.lang.IllegalArgumentException

/**
 * Класс "беззнаковое большое целое число".
 *
 * Общая сложность задания -- очень сложная, общая ценность в баллах -- 32.
 * Объект класса содержит целое число без знака произвольного размера
 * и поддерживает основные операции над такими числами, а именно:
 * сложение, вычитание (при вычитании большего числа из меньшего бросается исключение),
 * умножение, деление, остаток от деления,
 * преобразование в строку/из строки, преобразование в целое/из целого,
 * сравнение на равенство и неравенство
 */
class UnsignedBigInteger : Comparable<UnsignedBigInteger> {
    private val len: Int
        get() = value.size

    // Пусть 0 индекс означает 0-й разряд, тогда число 1093 будет хранится как [3, 9, 0, 1]
    private val value = mutableListOf<Int>()

    /**
     * Конструктор из строки
     */
    constructor(s: String) {
        for (char in s.reversed()) value.add(char.digitToIntOrNull() ?: throw IllegalArgumentException())
    }

    /**
     * Конструктор из целого
     */
    constructor(i: Int) {
        var temp = i
        while (temp > 0) {
            value.add(temp % 10)
            temp /= 10
        }
        if (value.isEmpty()) value.add(0)
    }

    /**
     * Мой конструктор из списка целых чисел
     */
    constructor(list: List<Int>) {
        for (digit in list) {
            if (digit in 0..9) value.add(digit)
            else throw IllegalArgumentException()
        }
    }

    /**
     * Сложение
     */
    operator fun plus(other: UnsignedBigInteger): UnsignedBigInteger {
        // this is where all the "fun" begins
        // вспоминаем, как нас учили складывать столбиком,
        // что делать при переполнении разряда?
        // + 1 к новому
        var overShoot = 0 // будем постоянно прибавлять, в случае переполнения, то к след разряду прибавится + 1
        // разряд за разрядом. Какой длины будет итоговое число не известно.
        // однако не может быть такого, что будет длина больше чем mx + 1
        val ans = mutableListOf<Int>()
        val mxLen = maxOf(len, other.len) // эта штука нужна только для реализации фориком
        for (i in 0 until mxLen) { // тут можно идти вайлом, однако после прохождения this, надо допройти other
            val thatDigit = if (i < len) value[i] else 0
            val otherDigit = if (i < other.len) other.value[i] else 0
            val sm = thatDigit + otherDigit + overShoot
            ans.add(sm % 10)
            overShoot = if (sm > 9) 1 else 0 // после прибавки не забываем про приписку "1" над след digit (как в школе)
        }
        if (overShoot != 0) ans.add(1)
        return UnsignedBigInteger(ans)
    }

    /**
     * Вычитание (бросить ArithmeticException, если this < other)
     */
    operator fun minus(other: UnsignedBigInteger): UnsignedBigInteger {
        /**
         * как мы определим, что this < other?
         * чек 1) -> len < other.len
         * чек 2) на каждой итерации по числам, начиная сверять с самых больших, что просто, иначе
         * есть другой вариант, начиная с самых маленьких -> разобьем число на две части, правую - все что
         * до текущего числа, и левую - текущее число.
         * Если this.current_Digit > other.current_Digit, то nextRightPart = True
         * иначе если currentDigit == other.currentDigit nextRightPart = rightPart
         *        иначе this.rightPart >= other.rightPart, то next
         */
        // а как реализовать вычитание?
        // опять как в школе
        // начиная с нулевого разряда вычитаем нулевой разряд другого числа:
        //
        // 1) Но если мы получаем <0
        // то нам приходится занимать у следующего разряда -> Если конечно следущий разярд существует
        // иначе мы получаем отриц число, throw exception!!!
        //
        // 2) Разряд другого числа невозможно взять, тогда он равен 0
        // все храним в mutableList<Int>
        var debt = 0 // долг i-го рязряда, для нулевого нет долгов и быть не может у инта
        val ans = mutableListOf<Int>()
        if (len < other.len) throw ArithmeticException()
        // после этого, mxLen равен (либо len и other.len) || либо len -> mxLen == len
        for (i in 0 until len) {
            val thatDigit = value[i]
            val otherDigit = if (i < other.len) other.value[i] else 0
            val diff = thatDigit - otherDigit - debt // [ - debt] а не "+"
            ans.add((10 + diff) % 10) // 10 - base, основание СС// если полож, то +base ничего не меняет, иначе (n)%10
            //(-3) % 10 -> 7 -> ХАХА, так не работает, отрицательные остатки из Си на месте
            debt = if (diff < 0) 1 else 0
        }
        if (debt != 0) throw ArithmeticException()
        return UnsignedBigInteger(ans)
    }

    /**
     * Умножение
     */
    operator fun times(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Деление
     */
    operator fun div(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Взятие остатка
     */
    operator fun rem(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Сравнение на равенство (по контракту Any.equals)
     */
    override fun equals(other: Any?): Boolean =
        other is UnsignedBigInteger && len == other.len && (0 until len).all { value[it] == other.value[it] }
    // будем считать, что UnsignedBigInteger(2) != Int(2)

    /**
     * Сравнение на больше/меньше (по контракту Comparable.compareTo)
     */
    override fun compareTo(other: UnsignedBigInteger): Int = TODO()

    /**
     * Преобразование в строку
     */
    override fun toString(): String = value.reversed().joinToString("")

    /**
     * Преобразование в целое
     * Если число не влезает в диапазон Int, бросить ArithmeticException
     */
    fun toInt(): Int = TODO()

}