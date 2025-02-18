@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant

import kotlin.math.pow
import kotlin.math.sqrt


// Урок 4: списки
// Максимальное количество баллов = 12
// Рекомендуемое количество баллов = 8
// Вместе с предыдущими уроками = 24/33

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.lowercase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая (2 балла)
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double = sqrt(v.sumOf { it * it })
/*{
val sqrV = v.map { it * it }
return sqrt(sqrV.fold(0.0) { previousEl, element -> previousEl + element })
}*/

/**
 * Простая (2 балла)
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    if (list.isEmpty()) return 0.0
    return list.sum() / list.size
}

/**
 * Средняя (3 балла)
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val delta = -mean(list)
    for (i in 0 until list.size) {
        list[i] += delta
    }
    return list
}

/**
 * Средняя (3 балла)
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    var ans = 0
    for (i in 0 until a.size) {
        ans += a[i] * b[i]
    }
    return ans
}

/**
 * Средняя (3 балла)
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    var ans = 0
    for (i in 0 until p.size) {
        ans += p[i] * x.toDouble().pow(i).toInt()
    }
    return ans
}

/**
 * Средняя (3 балла)
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    for (i in 1 until list.size) {
        list[i] += list[i - 1]
    }
    return list
}

/**
 * Средняя (3 балла)
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    fun isPrime(num: Int): Boolean {
        for (i in 2..sqrt(num.toDouble()).toInt()) if (num % i == 0) return false
        return true
    }

    fun solution1(n: Int): List<Int> {
        val factors = mutableListOf<Int>()
        var temp = n
        var i = 2
        if (isPrime(n)) return listOf(n)
        while (temp > 1) {
            var c = 0
            val boundary = temp
            while (i <= boundary && c < 1) {
                while (temp % i == 0) {
                    factors.add(i)
                    temp /= i
                }
                i += 1
                c += 1
            }
        }
        return factors
    }

    fun solution2(n: Int): List<Int> {
        for (i in 2..sqrt(n.toDouble()).toInt() + 1) {
            if (n % i == 0) {
                return listOf(i) + factorize(n / i)
            }
        }
        return if (n > 1) listOf(n) else emptyList()
    }
    //return solution2(n)
    return solution1(n)
}
/*for (i in 2..sqrt(n.toDouble()).toInt() + 1) {
    if (n % i == 0) {
        return listOf(i) + factorize(n / i)
        Решение интересное, но не самое эффективное. Получается что каждый раз когда нужно вернуть результат вы
        складываете два неизменяемых списка. А при их сложении каждый раз создается новый список в который
        копируются элементы из двух предыдущих. Лучше здесь использовать изменяемые (mutable) списки
        и решить задачу в цикле
    }
}
return if (n > 1) listOf(n) else emptyList()
*/
/**
 * Сложная (4 балла)
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")
/*{
var ans = ""
val divs = factorize(n)
if (divs.size == 1) return n.toString()
for (i in 0 until divs.size - 1) {
    ans += divs[i].toString() + "*"
}
return ans + divs[divs.size - 1]
}
*/

/**
 * Средняя (3 балла)
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    if (n == 0) return listOf(0)
    val ans = mutableListOf<Int>()
    var t = n
    while (t > 0) {
        ans.add(t % base)
        t /= base
    }
    return ans.reversed()

}

/**
 * Сложная (4 балла)
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String {
    val alph = "0123456789abcdefghijklmnopqrstuvwxyz"
//var ans = ""
    val ans2 = StringBuilder()
    val convTemp = convert(n, base)
    println(ans2)
    for (i in 0 until convTemp.size) ans2.append(alph[convTemp[i]])
//return ans
    return ans2.toString()
}

/**
 * Средняя (3 балла)
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var ans = 0
    for (i in 0 until digits.size) ans +=
        digits[i] * base.toDouble().pow(digits.size - i - 1).toInt()
    return ans
}

/**
 * Сложная (4 балла)
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int {
    var ans = 0
    for (i in 0 until str.length) {
        //val regulate = if (str[i] > '9') (str[i] - 49).code + 10 - 48 else str[i].code - 48
        val regulate = if (str[i].isDigit()) str[i].digitToInt() else (str[i] - 'a' + 10)
        ans += regulate * base.toDouble().pow(str.length - i - 1).toInt()
    }
    return ans
}

/**
 * Сложная (5 баллов)
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    val alph = "IVXLCDM"
//val desyph = listOf(1, 5, 10, 50, 100, 500, 1000)
    val ans = StringBuilder()
//val temp = mutableListOf(1)
    /*for (i in 1..6) temp += if (i % 2 == 0) temp[i - 1] * 5 else temp[i - 1] * 2*/
    val nums = convert(n, 10)
    for (i in 0 until nums.size) {
        val base = nums.size - i - 1
        if (nums[i] == 9) ans.append(alph[2 * base] + "${alph[base * 2 + 2]}")
        if (nums[i] == 4) ans.append(alph[2 * base] + "${alph[base * 2 + 1]}")
        if (nums[i] in 5..8) {
            ans.append(alph[2 * base + 1])
            for (j in 1..nums[i] - 5) ans.append(if (nums[i] == 5) 0 else alph[2 * base])
        }
        if (nums[i] < 4) for (j in 1..nums[i]) ans.append(alph[2 * base])

    }
//println(nums)
    return ans.toString()
//591

}


/**
 * Очень сложная (7 баллов)
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String = TODO()
// 32_235_000