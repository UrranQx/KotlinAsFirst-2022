@file:Suppress("UNUSED_PARAMETER")

package lesson8.task2

import java.lang.IllegalArgumentException
import kotlin.math.*
import lesson8.task1.*

/**
 * Клетка шахматной доски. Шахматная доска квадратная и имеет 8 х 8 клеток.
 * Поэтому, обе координаты клетки (горизонталь row, вертикаль column) могут находиться в пределах от 1 до 8.
 * Горизонтали нумеруются снизу вверх, вертикали слева направо.
 */
data class Square(val column: Int, val row: Int) {
    /**
     * Пример
     *
     * Возвращает true, если клетка находится в пределах доски
     */
    fun inside(): Boolean = column in 1..8 && row in 1..8

    /**
     * Простая (2 балла)
     *
     * Возвращает строковую нотацию для клетки.
     * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
     * Для клетки не в пределах доски вернуть пустую строку
     */
    fun notation(): String = if (this.inside()) "${'a' + this.column - 1}${this.row}" else ""
}

/**
 * Простая (2 балла)
 *
 * Создаёт клетку по строковой нотации.
 * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
 * Если нотация некорректна, бросить IllegalArgumentException
 */
fun square(notation: String): Square {
    if (!notation.matches(Regex("""[a-h][1-8]"""))) throw IllegalArgumentException()
    return Square(notation[0] - 'a' + 1, notation[1].digitToInt())
}

/**
 * Простая (2 балла)
 *
 * Определить число ходов, за которое шахматная ладья пройдёт из клетки start в клетку end.
 * Шахматная ладья может за один ход переместиться на любую другую клетку
 * по вертикали или горизонтали.
 * Ниже точками выделены возможные ходы ладьи, а крестиками -- невозможные:
 *
 * xx.xxххх
 * xх.хxххх
 * ..Л.....
 * xх.хxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: rookMoveNumber(Square(3, 1), Square(6, 3)) = 2
 * Ладья может пройти через клетку (3, 3) или через клетку (6, 1) к клетке (6, 3).
 */
fun rookMoveNumber(start: Square, end: Square): Int {
    if (!start.inside() || !end.inside()) throw IllegalArgumentException()
    return when {
        start == end -> 0
        start.column == end.column || start.row == end.row -> 1
        else -> 2
    }
}

/**
 * Средняя (3 балла)
 *
 * Вернуть список из клеток, по которым шахматная ладья может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов ладьи см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: rookTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможен ещё один вариант)
 *          rookTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(3, 3), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          rookTrajectory(Square(3, 5), Square(8, 5)) = listOf(Square(3, 5), Square(8, 5))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun rookTrajectory(start: Square, end: Square): List<Square> {
    return when (rookMoveNumber(start, end)) {
        0 -> listOf(start)
        1 -> listOf(start, end)
        else -> listOf(start, Square(end.column, start.row), end)
    }
}

/**
 * Простая (2 балла)
 *
 * Определить число ходов, за которое шахматный слон пройдёт из клетки start в клетку end.
 * Шахматный слон может за один ход переместиться на любую другую клетку по диагонали.
 * Ниже точками выделены возможные ходы слона, а крестиками -- невозможные:
 *
 * .xxx.ххх
 * x.x.xххх
 * xxСxxxxx
 * x.x.xххх
 * .xxx.ххх
 * xxxxx.хх
 * xxxxxх.х
 * xxxxxхх.
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если клетка end недостижима для слона, вернуть -1.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Примеры: bishopMoveNumber(Square(3, 1), Square(6, 3)) = -1; bishopMoveNumber(Square(3, 1), Square(3, 7)) = 2.
 * Слон может пройти через клетку (6, 4) к клетке (3, 7).
 */
fun bishopMoveNumber(start: Square, end: Square): Int {
    // введем понятие четности клетки
    // клетка четна, если сумма ее координат % 2 = 0 иначе -> нечетна.
    // несложно заметить, что это в нужной степени характерезует шахматное поле
    // И так, если слон на белых клетках, то он никогда не попадет на черную (следует из правил)
    // И наоборот
    return when {
        (!start.inside() || !end.inside()) -> throw IllegalArgumentException()
        start == end -> 0
        (start.column + start.row + end.column + end.row) % 2 != 0 -> -1 // четн + четн = четн, нечетн + нечетн = четн
        abs(start.column - end.column) == abs(start.row - end.row) -> 1
        else -> 2

    }
}

/**
 * Сложная (5 баллов)
 *
 * Вернуть список из клеток, по которым шахматный слон может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов слона см. предыдущую задачу.
 *
 * Если клетка end недостижима для слона, вернуть пустой список.
 *
 * Если клетка достижима:
 * - список всегда включает в себя клетку start
 * - клетка end включается, если она не совпадает со start.
 * - между ними должны находиться промежуточные клетки, по порядку от start до end.
 *
 * Примеры: bishopTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          bishopTrajectory(Square(3, 1), Square(3, 7)) = listOf(Square(3, 1), Square(6, 4), Square(3, 7))
 *          bishopTrajectory(Square(1, 3), Square(6, 8)) = listOf(Square(1, 3), Square(6, 8))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun bishopTrajectory(start: Square, end: Square): List<Square> {
    //val delta = (start.column + start.row - end.column - end.row) / 2
    return when (bishopMoveNumber(start, end)) {
        -1 -> listOf()
        0 -> listOf(start)
        1 -> listOf(start, end)
        // если два хода, то надо переместиться на линию, откуда по диагонали останется один ход
        //
        else -> {
            val st = Point(start.row.toDouble(), start.column.toDouble())
            val fn = Point(end.row.toDouble(), end.column.toDouble())
            val p1 = Line(st, PI / 4).crossPoint(Line(fn, 3 * PI / 4))// Точка пересечения 1
            val p2 = Line(st, 3 * PI / 4).crossPoint(Line(fn, PI / 4))// Точка перечечения 2
            //println("p1 = $p1; p2 = $p2")
            val sq1 = Square(round(p1.y).toInt(), round(p1.x).toInt()) // трансформация точки в клетку
            val sq2 = Square(round(p2.y).toInt(), round(p2.x).toInt()) // трансформация точки в клетку
            listOf(
                start,
                if (sq1.inside()) sq1 else sq2, // Если ходов 2, и какая-то одна из точек не на доске-> другая 100 будет
                end
            )
        }
        // Геметрический вариант хорошо масштабируется для огромных полей, однако при n = 8 выгодней альтернатива
        // альтернатива -> перебор ходов на диагоналях
    }
}

/**
 * Средняя (3 балла)
 *
 * Определить число ходов, за которое шахматный король пройдёт из клетки start в клетку end.
 * Шахматный король одним ходом может переместиться из клетки, в которой стоит,
 * на любую соседнюю по вертикали, горизонтали или диагонали.
 * Ниже точками выделены возможные ходы короля, а крестиками -- невозможные:
 *
 * xxxxx
 * x...x
 * x.K.x
 * x...x
 * xxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: kingMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Король может последовательно пройти через клетки (4, 2) и (5, 2) к клетке (6, 3).
 */
fun kingMoveNumber(start: Square, end: Square): Int =
    if (!(start.inside() && end.inside())) throw IllegalArgumentException()
    else max(abs(start.column - end.column), abs(start.row - end.row))
// если просто доступен путь по диагонали или по прямой, то такой вывод очевиден
// если же траектория - ломаная -> то мы идем вначале по диагонали, пока не достигнем равенства с одной из координат
// потом надо дойти по прямой. Отсюда первый шаг занимает min(a,b) второй, max(a,b) - min(a,b) -> весь путь max(a,b) + 0

/**
 * Сложная (5 баллов)
 *
 * Вернуть список из клеток, по которым шахматный король может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов короля см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: kingTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможны другие варианты)
 *          kingTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(4, 2), Square(5, 2), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          kingTrajectory(Square(3, 5), Square(6, 2)) = listOf(Square(3, 5), Square(4, 4), Square(5, 3), Square(6, 2))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun kingTrajectory(start: Square, end: Square): List<Square> {
    val ans = mutableListOf(start)
    var y = start.column
    var x = start.row
    for (iter in 0 until kingMoveNumber(start, end)) {
        val deltaY = (y - end.column).sign
        val deltaX = (x - end.row).sign
        ans.add(Square(y - deltaY, x - deltaX))
        y -= deltaY
        x -= deltaX

    }
    return ans
}

/**
 * Сложная (6 баллов)
 *
 * Определить число ходов, за которое шахматный конь пройдёт из клетки start в клетку end.
 * Шахматный конь одним ходом вначале передвигается ровно на 2 клетки по горизонтали или вертикали,
 * а затем ещё на 1 клетку под прямым углом, образуя букву "Г".
 * Ниже точками выделены возможные ходы коня, а крестиками -- невозможные:
 *
 * .xxx.xxx
 * xxKxxxxx
 * .xxx.xxx
 * x.x.xxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: knightMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Конь может последовательно пройти через клетки (5, 2) и (4, 4) к клетке (6, 3).
 */
// чтобы определить, сколько ходов надо коню-> можно было бы создать граф, где каждое ребро соединяет возможных ход коня
// из данной позиции. Запускаем Дейкстру, находим ответ

// Другой вариант, попроще, но по концепции чем-то напоминает проход по графам, но больше 18 задачу из КЕГЭ
// там для поиска минимального маршрута, мы заполнили бы все клетки, кроме стартовой максимальным числом (миллиордами),
// а клетка start - 0
//
// Мы начнем с того, что мы смотрим на конец. Если там стоит 0 - мы прибили, иначе ->
// Как мы могли попасть в эту клетку?(минимальным ходом) - смотрим возможные ходы от данной клетки,
// берем то, что наименьшее и т.к. оттуда надо сделать ход в endPoint, то надо прибавить 1
// Однако, откуда мы знаем а) Наименьшее значение из этих клеток? б) Значения в этих клетках?
// a) - minBy{} или что-то такое на поиск минимума
// б) - Ситуация напоминает начальную, так что тут весьма очевидно образуется рекурсия. База уже есть
// Можно ли перевернуть в рекурсию в динамику?
// Подумаем... Мы тогда начинаем из startPoint, и двигаемся по листу возможных ходов из него...
/*
fun knightMoves(start: Square): List<Square> {
    val moves = mutableListOf<Square>()
    val col = start.column
    val row = start.row
    moves.add(Square(col - 2, row - 1))
    moves.add(Square(col - 1, row - 2))
    moves.add(Square(col + 1, row - 2))
    moves.add(Square(col + 2, row - 1))
    moves.add(Square(col + 2, row + 1))
    moves.add(Square(col + 1, row + 2))
    moves.add(Square(col - 1, row + 2))
    moves.add(Square(col - 2, row + 1))
    // Вообще всего 8 возможных перестановок, можно было и циклом создать
    // Типо set(-2, -1, 1, 2) со всеми возможными перестановками, за вычетом (2, -2) (1, -1) и обратные пары
    return moves.filter { it.inside() }
}
*/

/*
fun calculateMvNumb(start: Square, field: MutableList<MutableList<Int>>, hop: Int) {
    // функция (subprogramm) рекурсивного заполнения таблицы. Как эксель практически
    // значение в клетке - кол-во прыжков до нее от самого начала
    //if (field[start.column - 1][start.row - 1] == 0) return - не то
    if (hop + 1 > field[start.column - 1][start.row - 1]) return
    // если у нас значение в клетке меньше, чем количество прыжков до нее + 1,
    // то значит к ней где-то есть путь получше, игнорим/выпригиваем из этого мува
    // иначе этот путь потенциально хорош, значит значение этой клетки надо исправить
    field[start.column - 1][start.row - 1] = hop + 1
    // тогда придется перезаполнить таблицу, сначала заменив значения доступные из этой клетки, если
    for (mv in knightMoves(start)) {
        calculateMvNumb(mv, field, field[start.column - 1][start.row - 1])
    }


    // if any{mv} вернет
}
*/

fun knightMoveNumber(start: Square, end: Square): Int = TODO()
    /*
        if (!(start.inside() && end.inside())) throw IllegalArgumentException()
        if (start == end) return 0
        val field = MutableList(8) { MutableList(8) { Int.MAX_VALUE } }
        //var ans = 0
        */
    /* for (i in 1..8) {
             for (j in 1..8) {
                 field[i][j] = Int.MAX_VALUE
             }
         }*//*

    //field[start.column - 1][start.row - 1] = 0

    calculateMvNumb(end, field, 0)
    return field[end.column - 1][end.row - 1]

*/


/**
 * Очень сложная (10 баллов)
 *
 * Вернуть список из клеток, по которым шахматный конь может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов коня см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры:
 *
 * knightTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 * здесь возможны другие варианты)
 * knightTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(5, 2), Square(4, 4), Square(6, 3))
 * (здесь возможен единственный вариант)
 * knightTrajectory(Square(3, 5), Square(5, 6)) = listOf(Square(3, 5), Square(5, 6))
 * (здесь опять возможны другие варианты)
 * knightTrajectory(Square(7, 7), Square(8, 8)) =
 *     listOf(Square(7, 7), Square(5, 8), Square(4, 6), Square(6, 7), Square(8, 8))
 *
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun knightTrajectory(start: Square, end: Square): List<Square> = TODO()
