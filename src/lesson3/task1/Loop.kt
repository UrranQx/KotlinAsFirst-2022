@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import java.util.DoubleSummaryStatistics
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

// Урок 3: циклы
// Максимальное количество баллов = 9
// Рекомендуемое количество баллов = 7
// Вместе с предыдущими уроками = 16/21

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
    when {
        n == m -> 1
        n < 10 -> 0
        else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
    }

/**
 * Простая (2 балла)
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    var ans = 0
    var temp = abs(n)
    while (temp > 0) {
        ans += 1
        temp /= 10
    }
    return ans + if (n == 0) 1 else 0
}

/**
 * Простая (2 балла)
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {

    //base
    var prev1 = 1
    var prev2 = 1
    var ans = 1
    if (n <= 2) return ans
    for (i in 3..n) {
        ans = prev1 + prev2
        prev2 = prev1
        prev1 = ans
    }
    return ans
}

/**
 * Простая (2 балла)
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    for (i in 2..sqrt(n.toDouble()).toInt()) {
        if (n % i == 0) return i
    }
    return n
}


/**
 * Простая (2 балла)
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int = if (minDivisor(n) == n) 1 else n / minDivisor(n)

/**
 * Простая (2 балла)
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var ans = 0
    var temp = x
    if (temp < 0) return 0
    while (temp != 1) {
        if (temp % 2 == 0) temp /= 2
        else temp = 3 * temp + 1
        ans += 1
    }
    return ans
}

/**
 * Средняя (3 балла)
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    fun gcdLoop(num1: Int, num2: Int): Int {
        var a = num1
        var b = num2
        while (a != 0 && b != 0) {
            if (a > b) a -= b
            else if (b == a) return a
            else b -= a
        }
        return maxOf(a, b)
    }
    return m * n / (gcdLoop(m, n))
}

/**
 * Средняя (3 балла)
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean = lcm(m, n) == m * n //Вот код, где матан помог
/* А ниже код - написанный в лоб
val minim = minOf(m, n)
for (i in 2..minim) {
    if (n % i == 0 && m % i == 0) return false
}
return true
 */

/**
 * Средняя (3 балла)
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var ans = 0.0
    var temp = n
    val lenX = digitNumber(n)
    for (i in 1..lenX) {
        ans += (temp % 10) * (10.0.pow(lenX - i))
        temp /= 10
    }

    return ans.toInt()
}


/**
 * Средняя (3 балла)
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean {
    fun revertL(n: Int): Long { //revert переписанная под Long
        var ans = 0L
        var temp = n.toLong()
        val lenX = digitNumber(n)
        for (i in 1..lenX) {
            ans += (temp % 10) * (10.0.pow(lenX - i)).toInt()
            temp /= 10
        }
        return ans
    }


    return revertL(n) == n.toLong()
}

/**
 * Средняя (3 балла)
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    fun digitNumberLong(n: Long): Long {
        var ans = 0L
        var temp = abs(n)
        while (temp > 0) {
            ans += 1
            temp /= 10
        }
        return ans + if (n == 0L) 1 else 0
    }

    val lenX = digitNumberLong(n.toLong())
    var t = n
    var sumX = 0
    for (i in 1..lenX) {
        sumX += t % 10
        t /= 10
    }
    return (sumX.toDouble() / lenX) != (n % 10).toDouble()
}

/**
 * Средняя (4 балла)
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю.
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.sin и другие стандартные реализации функции синуса в этой задаче запрещается.
 */
fun sin(x: Double, eps: Double): Double {
    var ans = 0.0
    val t = x % (PI * 2)
    var nextVal = t
    var k = 1
    var i = 1
    while (abs(nextVal) >= eps) {
        ans += nextVal
        i += 2
        k *= -1
        nextVal = k * (t.pow(i) / factorial(i))
    }
    return ans
}

/**
 * Средняя (4 балла)
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.cos и другие стандартные реализации функции косинуса в этой задаче запрещается.
 */
fun cos(x: Double, eps: Double): Double {//= sin(x + PI / 2, eps)
    var ans = 0.0
    val t = x % (PI * 2)
    var nextVal = 1.0
    var k = 1
    var i = 0
    while (abs(nextVal) >= eps) {
        ans += nextVal
        i += 2
        k *= -1
        nextVal = k * (t.pow(i) / factorial(i))
    }
    return ans
}

/**
 * Сложная (4 балла)
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var ans = 0
    var x = 0
    var count = 0//sumOfDigits
    while (count < n) {
        x += 1
        count += digitNumber(x * x)

    }
    val lx = count - n
    ans = x * x
    for (i in 1..lx) ans /= 10
    return ans % 10

}

/**
 * Сложная (5 баллов)
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var ans = 0
    var x = 0
    var count = 0//sumOfDigits
    while (count < n) {
        x += 1
        count += digitNumber(fib(x))

    }
    val lx = count - n
    ans = fib(x)
    for (i in 1..lx) ans /= 10
    return ans % 10

}
