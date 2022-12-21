package lesson11.task1

import java.lang.IllegalArgumentException
import kotlin.math.pow
import kotlin.math.sign

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
        // как реализовать вычитание?
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
        var lastElementInd = ans.lastIndex
        while (ans[lastElementInd] == 0 && lastElementInd != 0) {
            ans.removeAt(lastElementInd)
            lastElementInd -= 1
        }
        return UnsignedBigInteger(ans)
    }

    /**
     * Умножение на digit
     */
    private operator fun times(otherInt: Int): UnsignedBigInteger {
        if (otherInt !in 0..9) throw IllegalArgumentException()
        val itemsList = mutableListOf<UnsignedBigInteger>()
        var degree = 0
        //println("baza = $this $otherInt")
        for (num in value) {
            val res = UnsignedBigInteger(num * otherInt).value
            for (i in 0 until degree) res.add(0, 0)
            degree += 1
            itemsList.add(UnsignedBigInteger(res))
        }
        var s = UnsignedBigInteger(0)
        for (item in itemsList) s += item
        //
        return s
    }

    /**
     * аналогично битовому сдвигу, только для десетичной системы счисления. Сдвиг влево, т.е умножение на 10
     */

    //private fun timesBase() = value.add(0, 0)

    /**
     * аналогично битовому сдвигу, только для десетичной системы счисления. Сдвиг вправо, т.е деление на 10
     */

    //private fun divBase() = value.removeAt(0)
    // Только ли хочу ли я постоянно создавать новый список, или все же сделать фунцкию побочной?// TODO()
    /**
     * Умножение
     */
    operator fun times(other: UnsignedBigInteger): UnsignedBigInteger {
        val itemsList = mutableListOf<UnsignedBigInteger>()
        var degree = 0
        for (otherDigit in other.value) {
            val middleRes = this.times(otherDigit).value
            //println("${otherDigit} * ${this} = ${middleRes.reversed()}")
            for (i in 0 until degree) middleRes.add(0, 0)
            //println("${middleRes.reversed()} $degree\n")
            itemsList.add(UnsignedBigInteger(middleRes))
            degree += 1
        }
        var s = UnsignedBigInteger(0)
        for (item in itemsList) s += item
        //
        return s
    }

    /**
     * Деление
     * как бы я делил целые числа нацело, если бы умел умножать:
     * нам как минимум надо умножать на 10^(len - other.len - 1)
     * умножили, посчитали, получили разность // сколько еще делить? // нет овершута
     * повторяем деление.... так будет продолжаться до тех пор,
     * пока мы не попадем на момент YXZZZZZZZ / XZZZZZZZ или на момент YZZZZZZZ / XZZZZZZZ
     * в таком случае, Пока "Результат умножения меньше или равен initialNumber" -> { ... ; i+1 }
     * тогда если мы овершутнем, то выйдем из цикла while и вернем i-1 в добавок, тогда остаток -> этот овершут - denom
     */
    operator fun div(other: UnsignedBigInteger): UnsignedBigInteger {
        if (other == UnsignedBigInteger(0)) throw ArithmeticException("Division By Zero")
        val ans: UnsignedBigInteger
        if (len - other.len - 1 <= 0) {
            var i = 0
            var tempFactor = UnsignedBigInteger(0)
            while (tempFactor <= this) { // начальная итерация в холостую
                tempFactor = other * UnsignedBigInteger(i)
                i++
            }
            ans = UnsignedBigInteger(i - 2) // поэтому тут -1 и -1
        } else {
            val tenInDegreeX = mutableListOf(1) // min degree = 0// На столько надо делить как минимум
            for (j in 0 until len - other.len - 1) { // тут хитрый until с +-1, прошу внимательней
                tenInDegreeX.add(0, 0)
            }
            val wholeTens = UnsignedBigInteger(tenInDegreeX)
            if (this >= wholeTens * other * UnsignedBigInteger(10)) wholeTens.value.add(0, 0) // умножение на 10
//            println("this: $this whole tens = $wholeTens\nWholeTents * other = ${wholeTens * other}")
//            println("${this - wholeTens * other}")
            ans = wholeTens + ((this - wholeTens * other).div(other))
            // вся проблема алгоса тут
        }
        return ans
    }


    /**
     * Взятие остатка
     */
    operator fun rem(other: UnsignedBigInteger): UnsignedBigInteger = this - (this / other) * other

    /**
     * Быстрое возведение в степень (значение степени по модулю)
     */
    fun quickPowAbs(degree: UnsignedBigInteger): UnsignedBigInteger {
        return when (degree) {
            UnsignedBigInteger(0) -> UnsignedBigInteger(1)
            UnsignedBigInteger(1) -> this
            else -> {
                val t = quickPowAbs(degree / UnsignedBigInteger(2))
                if (degree % UnsignedBigInteger(2) == UnsignedBigInteger(0)) t * t
                else this * t * t //this * quickPowAbs(degree - 1)
            }
        }

    }

    /**
     * Сравнение на равенство (по контракту Any.equals)
     */
    override fun equals(other: Any?): Boolean =
        other is UnsignedBigInteger && len == other.len && (0 until len).all { value[it] == other.value[it] }

    // будем считать, что UnsignedBigInteger(2) != Int(2)
    override fun hashCode(): Int {
        return value.hashCode()
    }

    /**
     * Сравнение на больше/меньше (по контракту Comparable.compareTo)
     */
    override fun compareTo(other: UnsignedBigInteger): Int {
        /**
         * как мы определим, что this < other ???
         * чек 1) -> len < other.len
         * чек 2) на каждой итерации по числам, начиная сверять с самых больших, что просто, иначе
         * есть другой вариант, начиная с самых маленьких -> разобьем число на две части, правую - все что
         * до текущего числа, и левую - текущее число.
         * Если this.current_Digit > other.current_Digit, то nextRightPart = True
         * иначе если currentDigit == other.currentDigit nextRightPart = rightPart
         *        иначе this.rightPart >= other.rightPart, то next
         */
        if (len != other.len) return (len - other.len).sign
        // иначе они равны по знакам. можно спокойно проходить реверсом
        for (i in 0 until len) {
            val thisDigit = value[len - 1 - i]
            val otherDigit = other.value[len - 1 - i]
            if (thisDigit != otherDigit) return (thisDigit - otherDigit).sign
        }
        return 0
    }

    /**
     * Преобразование в строку
     */
    override fun toString(): String = value.reversed().joinToString("")

    /**
     * Преобразование в целое
     * Если число не влезает в диапазон Int, бросить ArithmeticException
     */
    fun toInt(): Int {
        if (this > UnsignedBigInteger(Int.MAX_VALUE)) throw ArithmeticException()
        var ans = 0
        for (i in 0 until len) ans += value[i] * (10.0.pow(i)).toInt()
        return ans
    }

}