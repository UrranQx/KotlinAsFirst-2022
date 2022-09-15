@file:Suppress("UNUSED_PARAMETER")

package lesson2.task2

import lesson1.task1.sqr

/**
 * Пример
 *
 * Лежит ли точка (x, y) внутри окружности с центром в (x0, y0) и радиусом r?
 */
fun pointInsideCircle(x: Double, y: Double, x0: Double, y0: Double, r: Double) =
    sqr(x - x0) + sqr(y - y0) <= sqr(r)

/**
 * Простая (2 балла)
 *
 * Четырехзначное число назовем счастливым, если сумма первых двух ее цифр равна сумме двух последних.
 * Определить, счастливое ли заданное число, вернуть true, если это так.
 */
fun isNumberHappy(number: Int): Boolean {
    val sum34: Int = number % 10 + (number / 10) % 10
    val sum12: Int = number / 1000 + (number / 100) % 10
    return sum34 == sum12
}

/**
 * Простая (2 балла)
 *
 * На шахматной доске стоят два ферзя (ферзь бьет по вертикали, горизонтали и диагоналям).
 * Определить, угрожают ли они друг другу. Вернуть true, если угрожают.
 * Считать, что ферзи не могут загораживать друг друга.
 */
fun queenThreatens(x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
    val diagonal: Boolean = ((x2 - x1) * (x2 - x1)) == ((y2 - y1) * (y2 - y1))
    val axis: Boolean = (x1 == x2 || y1 == y2)
    return (diagonal || axis)
}


/**
 * Простая (2 балла)
 *
 * Дан номер месяца (от 1 до 12 включительно) и год (положительный).
 * Вернуть число дней в этом месяце этого года по григорианскому календарю.
 */
fun daysInMonth(month: Int, year: Int): Int {
    var ans = 30
    fun LeapYearFix(number: Int): Int {
        if ((number % 4 == 0 && number % 100 != 0) || number % 400 == 0) {
            return 1
        }
        return 0
    }
    if (month == 2) return 28 + LeapYearFix(year)
    if (month in 1..7) ans += month % 2
    else ans += 1 - month % 2
    return ans
}

/**
 * Простая (2 балла)
 *
 * Проверить, лежит ли окружность с центром в (x1, y1) и радиусом r1 целиком внутри
 * окружности с центром в (x2, y2) и радиусом r2.
 * Вернуть true, если утверждение верно
 */
fun circleInside(
    x1: Double, y1: Double, r1: Double,
    x2: Double, y2: Double, r2: Double
): Boolean {
    fun vectorLen(vx: Double, vy: Double): Double {
        return kotlin.math.sqrt(vx * vx + vy * vy)
    }
    // max len from x2 y2 is equal r2
    // len from x2 y2 is equal vectorLen(x1 - x2, y1 - y2)
    // plus r1
    return r2 >= vectorLen(x1 - x2, y1 - y2) + r1
}

/**
 * Средняя (3 балла)
 *
 * Определить, пройдет ли кирпич со сторонами а, b, c сквозь прямоугольное отверстие в стене со сторонами r и s.
 * Стороны отверстия должны быть параллельны граням кирпича.
 * Считать, что совпадения длин сторон достаточно для прохождения кирпича, т.е., например,
 * кирпич 4 х 4 х 4 пройдёт через отверстие 4 х 4.
 * Вернуть true, если кирпич пройдёт
 */
fun brickPasses(a: Int, b: Int, c: Int, r: Int, s: Int): Boolean =
    // Solution // ((min(a,b,c) <= min(r,s)) && mean(a,b,c) <= max(r,s)
    (minOf(a, b, c) <= minOf(r, s)) &&
            ((a + b + c - maxOf(a, b, c) - minOf(a, b, c) <= maxOf(r, s)))

