@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

import java.lang.IllegalArgumentException
import java.lang.StringBuilder

// Урок 9: проектирование классов
// Максимальное количество баллов = 40 (без очень трудных задач = 15)

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Простая (2 балла)
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */

fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> { // т.е. я тут должен вернуть интерфейс а может класс?
    if (height <= 0 || width <= 0) throw IllegalArgumentException()
    return MatrixImpl(height, width, e)

}

/**
 * Средняя сложность (считается двумя задачами в 3 балла каждая)
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> { // и тут тоже?
    private val list = mutableListOf<E>()

    init { //Я сделаю не список списков, а что-то типо такого же списка списков, только вместо\n будет просто ;
        for (i in 0 until height * width) list.add(e)
    }

    override fun get(row: Int, column: Int): E = list[row * width + column]

    override fun get(cell: Cell): E = list[cell.row * width + cell.column]

    override fun set(row: Int, column: Int, value: E) {
        list[row * width + column] = value
    }

    override fun set(cell: Cell, value: E) {
        list[cell.row * width + cell.column] = value
    }

    override fun equals(other: Any?): Boolean {
        if (other is Matrix<*> && height == other.height && width == other.width) {
            for (i in 0 until height)
                for (j in 0 until width) {
                    if (this[i, j] != other[i, j]) return false
                }
            return true
        } else return false
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        result = 31 * result + list.hashCode()
        return result
    }

    /** Пример для height = 5, width = 6:
     *  1  1  1  1  1  1
     *  1  2  2  2  2  1
     *  1  2  3  3  2  1
     *  1  2  2  2  2  1
     *  1  1  1  1  1  1
     */
    override fun toString(): String {
        val sb = StringBuilder()
        for (i in 0 until height)
            sb.append(list.slice(i * width until i * width + width)).append("\n")
        return sb.toString()
    }

}

