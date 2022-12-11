@file:Suppress("UNUSED_PARAMETER")

package lesson8.task1

import lesson1.task1.sqr
import java.lang.IllegalArgumentException
import kotlin.math.*

// Урок 8: простые классы
// Максимальное количество баллов = 40 (без очень трудных задач = 11)

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
@Suppress("MemberVisibilityCanBePrivate")
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая (2 балла)
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double {
        val cdm = center.distance(other.center) - (radius + other.radius)
        return if (cdm > 0) cdm else 0.0
    }

    /**
     * Тривиальная (1 балл)
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = center.distance(p) <= radius
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
        other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
        begin.hashCode() + end.hashCode()

    val length: Double = begin.distance(end)
    //val middlePoint: Point = Point((begin.x + end.x) / 2, (begin.y + end.y) / 2)
}

/**
 * Средняя (3 балла)
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */

fun diameter(vararg points: Point): Segment {
    if (points.size < 2) throw IllegalArgumentException()
    // перебор n^2
    var mx = -1.0
    var longestSegment = Segment(Point(0.0, 0.0), Point(0.0, 0.0))
    for (i in points.indices) {
        for (j in i + 1 until points.size) {
            val temp = points[i].distance(points[j])
            if (temp >= mx) {
                mx = temp
                longestSegment = Segment(points[i], points[j])
            }

        }
    }
    return longestSegment
}

/**
 * Простая (2 балла)
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle {
    val center = Point((diameter.begin.x + diameter.end.x) / 2, (diameter.begin.y + diameter.end.y) / 2)
    return Circle(center, diameter.length / 2)
}

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        require(angle >= 0 && angle < PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * cos(angle) - point.x * sin(angle), angle)

    /**
     * Средняя (3 балла)
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        //точка пересечения - это особая точка, где x1 = x2, y1 = y2
        val x = (other.b / cos(other.angle) - b / cos(angle)) / (tan(angle) - tan(other.angle))
        val y = tan(other.angle) * x + other.b / cos(other.angle)
        return Point(x, y)
    }

    //fun perpendicularLineByPoint(point: Point): Line = Line(point, (this.angle + PI / 2) % PI)

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${cos(angle)} * y = ${sin(angle)} * x + $b)"
}

/**
 * Средняя (3 балла)
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line = lineByPoints(s.begin, s.end)

/**
 * Средняя (3 балла)
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line {
    val tmp = ((b.y - a.y) / (b.x - a.x))
    //println("tmp = $tmp; Points = $a ; $b")
    //println(ans.angle)
    return if (tmp == Double.POSITIVE_INFINITY || tmp == Double.NEGATIVE_INFINITY) Line(a, abs(atan(tmp)))
    else Line(a, (PI + (atan(tmp))) % PI)
}

/**
 * Сложная (5 баллов)
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val equation = lineByPoints(a, b)
    val middlePoint = Point((a.x + b.x) / 2, (a.y + b.y) / 2)

    /**return equation.perpendicularLineByPoint(middlePoint)*/
    //у нас представление такое: точка - ..... | ..... - точка
    //соответственно, эту линию надо строить посередине координат этих точек
    //а угол наклона вычисляется из угла наклона прямой по точкам: k - угол наклона представления y = k * x + b
    //тогда ур-е прямой ей перп-й -> y = (-1 / k) * x + b


    // другой алгос - построить две окружности из этих точек, соединить точки пересечения в линию
    // но что есть circle.intersections ?
    // это должно быть выводиться из уравнения окружности для каждой из точек, а затем
    // приравнивания каждого. Квадратное уравнение с двумя решениями** из которых мы найдем коорды для прямой
    // ** - начальные точки не совпадают
    //
    // Другой алгос
    // Находим delta X и delta Y
    // из любой точки строим вправо dy
    // потом в зависимости от угла ( <= PI / 2 ) -> вверх (коэфф - полож) ( > PI / 2) -> вниз (коэфф - отриц)
    // нашли эти две точки, строим по ним линию, берем от нее угол наклона
    val dx = abs(a.x - b.x)
    val dy = abs(a.y - b.y)
    // let's take a point "a"
    val xPoint = Point(a.x + dy, a.y)
    val yPoint = Point(a.x, a.y + sign(PI / 2 - equation.angle) * dx)
    val perpendicularLine = lineByPoints(xPoint, yPoint)
    return Line(middlePoint, perpendicularLine.angle)
}

/**
 * Средняя (3 балла)
 *
 * Задан список из n окружностей на плоскости.
 * Найти пару наименее удалённых из них; расстояние между окружностями
 * рассчитывать так, как указано в Circle.distance.
 *
 * При наличии нескольких наименее удалённых пар,
 * вернуть первую из них по порядку в списке circles.
 *
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    if (circles.size < 2) throw IllegalArgumentException()
    var mn = Double.POSITIVE_INFINITY
    var ans = Pair(circles[0], circles[1])
    for (i in 0 until circles.size - 1) {
        for (j in i + 1 until circles.size) {
            val len = circles[i].distance(circles[j])
            if (mn > len) {
                mn = len
                ans = Pair(circles[i], circles[j])
            }
        }
    }
    return ans
}
// по сути это граф, в котором ребра заданны расстоянием между кругами и надо найти наименьшее ребро и его основания
// алгоритм Дейкстры тут был бы оверкиллом
// Недолго думая, после этих слов в голове остался только лишь перебор
/**
 * Сложная (5 баллов)
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    //val center = bisectorByPoints(a, c).crossPoint(bisectorByPoints(a, b))
    // Порядок точек, почему то влияет на значение center
    val points = listOf(a, b, c)
    val centers = mutableListOf<Point>()
    for (i in points.indices) {
        for (j in points.indices) {
            if (i == j) continue
            val k = 3 - (i + j)
            val t = bisectorByPoints(points[i], points[j]).crossPoint(bisectorByPoints(points[k], points[i]))
            centers.add(t)
        }
    }
//    println(centers)
//    println()
    val n = centers.size
    val cMid = Point(centers.map { it.x }.sum() / n, centers.map { it.y }.sum() / n)
    // Методом научного ТЫКА было исследованно, что почему то только точка пересечения таких перпендикуляров, как
    // пр-р к CB и пр-р к AC - дают нужный рез-т. (хотя есть пары, которые дают другой рез-т)
    // полученные точки надо как-то фильтровать. Надо разделить их на "команды"
    // делим каждую коорду на 10, чтобы потом нормально округлить
    // потом проходимся по листу
    // если встречалось -> бац и в сет +1, нет - записываем в сет
    // на выходе берем ключ сета с максимальным значением
    val ans = mutableMapOf<Point, Int>()
    for ((x, y) in centers) {
        val temp = Point(ceil(x * 10.0.pow(12)), ceil(y * 10.0.pow(12)))
        if (ans[temp] == null) ans[temp] = 1
        else ans[temp] = ans[temp]!! + 1
    }
    var popularOpinion: Point = cMid
    var mx = 0
    for ((key, element) in ans) {
        if (mx <= element) {
            mx = element
            popularOpinion = Point(key.x / 10.0.pow(12), key.y / 10.0.pow(12))
        }
    }
    return Circle(popularOpinion, a.distance(popularOpinion))
}

/**
 *
 */

/**
 * Очень сложная (10 баллов)
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle = TODO()

