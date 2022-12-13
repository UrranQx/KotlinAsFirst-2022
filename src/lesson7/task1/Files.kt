@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.BufferedWriter
import lesson3.task1.digitNumber as dNum
import java.io.File
import java.lang.StringBuilder

// Урок 7: работа с файлами
// Урок интегральный, поэтому его задачи имеют сильно увеличенную стоимость
// Максимальное количество баллов = 55
// Рекомендуемое количество баллов = 20
// Вместе с предыдущими уроками (пять лучших, 3-7) = 55/103

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    var currentLineLength = 0
    fun append(word: String) {
        if (currentLineLength > 0) {
            if (word.length + currentLineLength >= lineLength) {
                writer.newLine()
                currentLineLength = 0
            } else {
                writer.write(" ")
                currentLineLength++
            }
        }
        writer.write(word)
        currentLineLength += word.length
    }
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            writer.newLine()
            if (currentLineLength > 0) {
                writer.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(Regex("\\s+"))) {
            append(word)
        }
    }
    writer.close()
}

/**
 * Простая (8 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Некоторые его строки помечены на удаление первым символом _ (подчёркивание).
 * Перенести в выходной файл с именем outputName все строки входного файла, убрав при этом помеченные на удаление.
 * Все остальные строки должны быть перенесены без изменений, включая пустые строки.
 * Подчёркивание в середине и/или в конце строк значения не имеет.
 */
//File(outputName).bufferedWriter().use
fun deleteMarked(inputName: String, outputName: String) {
    File(outputName).bufferedWriter().use { writer ->
        for (line in File(inputName).readLines()) {
            if (line.startsWith("_")) continue
            writer.write(line)
            writer.newLine()
        }
//        writer.close()
    }
}

/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
// Добавить второй алгос
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val ans = mutableMapOf<String, Int>()
    val strings = substrings.toSet()
    // Алгос 1. Тупой перебор
    // Мы проходимся по каждому [Даже не слову, а надо пройтись по каждому чару]  в тексте, и
    val allStrings = File(inputName).readLines().joinToString("\n") { it.lowercase() }
    for (string in strings) { //m - кол-во подстрок
        val stringLowercased = string.lowercase()
        var startPos = allStrings.indexOf(stringLowercased, 0)
        while (-1 < startPos && startPos < allStrings.length - string.length + 1) {
            ans[string] = 1 + (ans[string] ?: 0) // 1
            startPos = allStrings.indexOf(stringLowercased, startPos + 1)
        }
        if (ans[string] == null) ans[string] = 0 //1
    }
    return ans// m * (n -l + const) * (l + const) как минимум
}
//другой алгос
// мы считываем чар, если с такого начинается any подстрока, то включается второй итератор,
// который бежит дальше по чарам и если (a: файл закончился или
// b: следующий чар ни с какой либо подстрокой не совпадает[не совсем те слова, имеется ввиду сравнение по индексам])
// то двигаем дальше первый бегунок, иначе продолжаем. И Если собранный лайн == any (подстрока),
// то "Собираем" подстроку в ответ, но делаем еще итерацию, ведь дальше может быть постфикс тоже заданной подстроки,
// типо "подстрока" = "собранная" + "постфикс", а мы бы такую строку бы не собрали, т.к. думаем, что нам интересно любое
// бла бла бла.
// тут будет цикл в цикле, и нередко в этом вложенном цикле мы еще по листу "strings" будем бегать и вытаскивать [j]- ю
// "букву"
// бла бла бла
// выводим ответ
// Этот алгоритм менее понятнее, хотя и уменьшает количество заходов и пробеганий по длине выданной построки.
//
// Предыдущий более прост, но он топорно бегает отрезками длиннами выданных подстрок так кол-во операций там уже m * n

/**
 * Средняя (12 баллов)
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
val prefixes = setOf("ж", "ч", "ш", "щ", "Ж", "Ч", "Ш", "Щ")
val postfixesSet = setOf("ы", "я", "ю", "Ы", "Я", "Ю")
val postfixesMap = mapOf("Ы" to "И", "Я" to "А", "Ю" to "У")
fun sibilants(inputName: String, outputName: String) {
    File(outputName).bufferedWriter().use { writer ->
        for (line in File(inputName).readLines()) {
            if (line.isEmpty()) continue
            val correctionLine = StringBuilder().append(line.first())
            for (i in 0 until line.length - 1) {
                if (line[i].toString() in prefixes && line[i + 1].toString() in postfixesSet) {
                    // значит мы наткнулись на ошибочное написание гласной после шипящей
                    val nextCase =
                        if (line[i + 1].isUpperCase()) postfixesMap[line[i + 1].uppercase()]
                        else postfixesMap[line[i + 1].uppercase()]!!.lowercase()
                    correctionLine.append(nextCase)
                } else correctionLine.append(line[i + 1])
            }
            writer.write(correctionLine.toString())
            writer.newLine()
        }
//        writer.close()
    }
}

/**
 * Средняя (15 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    File(outputName).bufferedWriter().use { writer ->
        var maxLength = 0
        File(inputName).forEachLine { maxLength = maxOf(maxLength, it.trim().length) }
        //val maxLength = strings.maxOfOrNull { it.length } ?: 0
        File(inputName).forEachLine {
            val str = it.trim()
            val centeredStr = StringBuilder()
            val k = maxLength - str.length
            val left = k / 2 //хитрая штука с целочисленным делением
            centeredStr.append(" ".repeat(left), str)
            writer.write(centeredStr.toString())
            writer.newLine()
        }
//        writer.close()

    }
}
// Найти самую длинную строку
// Пусть длина рандомной строки - x
// тогда надо добавить слево и справо по S пустых строк; k= (x-len(str)); S = k/2; left = S; right = S
// //Если k%2==1: left -- (Неверно, это не работает, как должно)
// final str = left + S + right
// Последний шаг - использовать StringBuilder
// если будут какие-то ошибки с Input Output, то придется использовать либо .use либо try {...} finally {writer.close())

/**
 * Сложная (20 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    File(outputName).bufferedWriter().use { writer ->
        //val lines = File(inputName).readLines().map { it.trim().split(Regex("""\s+""")).joinToString(" ") }
        //ресурсоёмкое по памяти убирание сдвоенных, строенных и т.д. пробелов
        var maxLength = 0
        File(inputName).forEachLine {
            maxLength = maxOf(maxLength, it.trim().split(Regex("""\s+""")).joinToString(" ").length)
        }
        File(inputName).forEachLine { fileLine ->
            val line = fileLine.trim().split(Regex("""\s+""")).joinToString(" ")
            val words = line.split(Regex("""\s""")).filter { it.isNotEmpty() }
            val wordsCount = words.size // слов n штук, тогда между ними (n - 1) пробелов
            var s = maxLength - words.sumOf { it.length } //всего места под пробелы = sum of gaps
            val fixedLine = StringBuilder()
            for (i in 0 until words.size - 1) {
                val k = (s / (wordsCount - 1 - i))
                val gap = if (s % (wordsCount - 1 - i) == 0) k else k + 1
                fixedLine.append(words[i], " ".repeat(gap))
                s -= gap
            }
            if (line.isNotEmpty()) fixedLine.append(words[wordsCount - 1])//ласт слово отдельно , т.к нет пробелов после
            writer.write(fixedLine.toString())
            writer.newLine()
        }
//        writer.close()
    }
}
// Каждую строчку сплитуем по пробелу, используя Regex("""\s+""").split(string)
// Найти самую длинную строку (делаем так, чтобы там не было сдвоенных пробелов) и она и задаст нужную длинну
// Дальше мы проходимся по строкам снова
// Мы считаем сколько пространства занимают слова, следовательно можем узнать, сколько надо на пробелы
// делим на (n-1);
// l - maxLength; wordsCount; wordsLength; k - amount of spaces in each gap;  s = l - wordsLength
//
// for each Gap:
//     k = (s / (wordsCount - 1 - i)).toInt()//Будем надеяться что .toInt() не округляет, а просто отбрасывает дробную часть
//     gap_j = if (s % (wordsCount - 1 - i) == 0) k else k + 1
//     // в line.StringBuilder() аппендим не только слово, но и пробелы после него.
//     // Или если у нас уже строка разбита на слова в листе, то придется идти от i = 1  с шагом 2
//     s = s - gap_j
//     //После ласт слова не должно быть пробелов.
// Так что добавляем это слово в line вручную
// А также
// соблюдаем правла выполнения задания
// Выводим
// Вауаля

/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 * Вернуть ассоциативный массив с числом слов больше 20, если 20-е, 21-е, ..., последнее слова
 * имеют одинаковое количество вхождений (см. также тест файла input/onegin.txt).
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
// string.lowercase()
// Используем regex для разделения по словам
// Заводим мапу типа mutableMap<String, Int>
// Заполняем согласно правилу, увидел - прибавил +1
// Sort???
// ???
// Если 20 + i имеет такое же число, как и 20, то добавляем его.
// Вывод
fun top20Words(inputName: String): Map<String, Int> = TODO()
// Самое сложное - достать слова из текста, дальше уже, как мы организуем мапу\массив для них -
// дело структурирования данных(где-то давно решенная задача)
/**
 * Средняя (14 баллов)
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    TODO()
}

/**
 * Средняя (12 баллов)
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    TODO()
}
// Все буквы разные, значит если str.length != str.toSet().size()// с учетом того, что все в одном регистре
/**
 * Сложная (22 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная (23 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body><p>...</p></body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<p>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>Или колбаса</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>Фрукты
<ol>
<li>Бананы</li>
<li>Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</p>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная (30 баллов)
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя (12 баллов)
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}
/**"""
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
"""
 */

/**
 * Сложная (25 баллов)
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun writeThenNewLine(str: String, writer: BufferedWriter) {
    writer.write(str)
    writer.newLine()
}

fun makeDivString(caret: Int, multLength: Int): String {
    val str = StringBuilder(" ".repeat(caret - multLength - 1))
    return str.append("-".repeat(multLength + 1)).toString()
}

fun makeSpaces(caret: Int, str: String): String = StringBuilder(" ".repeat(caret - 1)).append(str).toString()
fun zeroCaseScenario(lhv: Int, rhv: Int, writer: BufferedWriter) {
    // Какие приколы есть, когда у нас число при делении является представлением 0.(X)
    // 1 - Первая строка не начинается с пробела
    //println("$lhv $rhv ${lhv / rhv}")
    val firstLine = "$lhv | $rhv"
    val res = (lhv / rhv)
    val mult = res.toString().first().digitToInt() * rhv
    writeThenNewLine(firstLine, writer)
    val caret = dNum(lhv) - dNum(mult) + 1
    val secondLine = StringBuilder()
    writeThenNewLine(
        secondLine.append(makeSpaces(caret - 1, "-$mult"), " ".repeat(caret - dNum(lhv) + 3), res)
            .toString(),
        writer
    )
    writeThenNewLine(makeDivString(caret, dNum(lhv) - 1), writer)
    writer.write(lhv.toString())

}

fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    File(outputName).bufferedWriter().use { writer ->
        val res = (lhv / rhv).toString()
        var mult = res[0].digitToInt() * rhv
        if (rhv > lhv && dNum(lhv) > 1) return@use zeroCaseScenario(lhv, rhv, writer)
        // МЫ ставим пробел перед первой строкой, если первое вычитаемое число, больше чем уменьшаемое
        // из-за этого у нас есть два сценария в таком случае
        //сценарий 1, где количество разрядов совпадает
        var firstLine = " $lhv | $rhv"
        var caret = dNum(mult) + 1
        var spaceFixer1 = 0
        val secondLine = StringBuilder()
        var upperdigits = firstLine.substring(1, dNum(mult) + 1).toInt()
        if (upperdigits < mult) { //обработка сценария 2
            firstLine = "$lhv | $rhv"
            spaceFixer1 = -1// Оно не элегантно, зато работает.
            upperdigits = upperdigits * 10 + firstLine[dNum(mult)].digitToInt()
        }
        var leftOver = upperdigits - mult

        writeThenNewLine(firstLine, writer)

        writeThenNewLine(
            secondLine.append("-${mult}", " ".repeat(dNum(lhv) - dNum(mult) + 3 + spaceFixer1), res).toString(),
            writer
        )
        writeThenNewLine(makeDivString(caret, dNum(mult)), writer)
        writer.write(makeSpaces(caret + 1 - dNum(leftOver), leftOver.toString()))
        // newRes = leftOver * 10 + firstLine[caret].digitToInt()
        if (firstLine[caret].isDigit()) writer.write(firstLine[caret].toString())
        caret += 1
        writer.newLine()
        for (i in 1 until res.length) {
            val newRes = leftOver * 10 + firstLine[caret - 1].digitToInt()
            //println(res[i])
            //println(firstLine[caret])

            mult = res[i].digitToInt() * rhv
            leftOver = newRes - mult
            writeThenNewLine(makeSpaces(caret - dNum(mult), "-$mult"),writer)
            writeThenNewLine(makeDivString(caret, maxOf(dNum(mult), dNum(newRes) - 1)),writer)
            //
            writer.write(makeSpaces(caret + 1 - dNum(leftOver), leftOver.toString()))
            if (firstLine[caret].isDigit()) writer.write(firstLine[caret].toString())
            caret += 1
            writer.newLine()
        }
    }
}