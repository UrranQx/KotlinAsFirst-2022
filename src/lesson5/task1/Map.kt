@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

import lesson4.task1.mean

// Урок 5: ассоциативные массивы и множества
// Максимальное количество баллов = 14
// Рекомендуемое количество баллов = 9
// Вместе с предыдущими уроками = 33/47

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
    shoppingList: List<String>,
    costs: Map<String, Double>
): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
    phoneBook: MutableMap<String, String>,
    countryCode: String
) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
    text: List<String>,
    vararg fillerWords: String
): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}


/**
 * Простая (2 балла)
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val table = mutableMapOf<Int, MutableList<String>>()
    for ((name, grade) in grades) {
        if (table[grade] != null) table[grade]!!.add(name) else table.put(grade, mutableListOf(name))
    }
    return table
}

/**
 * Простая (2 балла)
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    for ((key, value) in a) {
        if (value != b[key]) return false
    }
    return true
}

/**
 * Простая (2 балла)
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>) {
    for (i in b.keys) {
        if (a[i] == b[i])
            a.remove(i)
    }
}

/**
 * Простая (2 балла)
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяющихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> {
    return a.intersect(b.toSet()).toList()
    //val ans = mutableListOf<String>()
    //a.toSet()
    //b.toSet()
    //
    //    val boundary = minOf(a.size, b.size)
    //
    //    for (i in 0 until boundary) {
    //        if (a[i] in b) {
    //            ans.add(a[i])
    //        }
    //    }
    //return ans.toList()


}

/**
 * Средняя (3 балла)
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val ans = mutableMapOf<String, String>()
    val allKeys = mapA.keys.union(mapB.keys)
    val uniqueKeys = allKeys - (mapA.keys.intersect(mapB.keys))
    if (uniqueKeys.isNotEmpty()) {
        //print(uniqueKeys)
        for (key in uniqueKeys) ans[key] = (mapA[key] ?: mapB[key])!!
    }
    for (key in mapA.keys.intersect(mapB.keys)) {
        //print(mapA.keys.intersect(mapB.keys))
        if (mapA[key] != mapB[key]) {
            ans[key] = setOf(mapA[key], mapB[key]).joinToString(", ")
        } else ans[key] = (mapA[key] ?: mapB[key])!!
    }
    return ans
    //я хочу проходясь по общему сету из ключей (из А и Б) смотреть, Если есть это значение уникально
    // для какой-то таблицы - то добавить запись, иначе
    // Сравнивать А и Б так, что если есть в Б такой же ключ, как и в А, то записать любое из двух, иначе
    // если у него другое значение - добавить 
}

/**
 * Средняя (4 балла)
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val ans = mutableMapOf<String, MutableList<Double>>()
    for ((name, price) in stockPrices) {
        if (ans[name] == null) ans[name] = mutableListOf(price) else ans[name]!!.add(price)
    }
    return ans.mapValues { mean(it.value) }

}

/**
 * Средняя (4 балла)
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var cheapestStuff: String? = null
    var mn: Double = Double.MAX_VALUE
    for ((key, value) in stuff) {
        if (value.first == kind) {
            if (value.second <= mn) { //мы без понятия от нас требуют первый такой попавшийся товар или последний.
                // зато при <=mn тесты проходят, когда вкидывают минимум - Double.MAX_VALUE
                cheapestStuff = key
                mn = value.second
            }
        }
    }
    return cheapestStuff
}

/**
 * Средняя (3 балла)
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    val letters = mutableSetOf<Char>()
    for (i in 0 until word.length) letters.add(word[i].lowercaseChar())
//    println("${setOf(chars)}, $letters")
    return (chars.map { it.lowercaseChar() }.toSet()).containsAll(letters)
}

/**
 * Средняя (4 балла)
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    val table = mutableMapOf<String, Int>()
    for (element in list) {
        if (table[element] == null) table.put(element, 1)
        else table[element] = table[element]!! + 1
    }
    return table.filter { it.value > 1 }
}

/**
 * Средняя (3 балла)
 *
 * Для заданного списка слов определить, содержит ли он анаграммы.
 * Два слова здесь считаются анаграммами, если они имеют одинаковую длину
 * и одно можно составить из второго перестановкой его букв.
 * Скажем, тор и рот или роза и азор это анаграммы,
 * а поле и полено -- нет.
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
//    fun toSetOfChars(stringOfChars: String): Set<Char> {
//        val letters = mutableSetOf<Char>()
//        for (element in stringOfChars) letters.add(element)
//        return letters
//    }
//
//    fun toListOfChars(stringOfChars: String): List<String> {
//        val letters = mutableListOf<String>()
//        for (element in stringOfChars) letters.add(element.toString())
//        return letters
//    }

    val anagrams =
        words.map {
            listOf(it.map { char -> char.toString() }.toSet(), extractRepeats(it.map { char -> char.toString() }))
        }.toSet().size

    return words.size != anagrams
}

/**
 * Сложная (5 баллов)
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 *
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Оставлять пустой список знакомых для людей, которые их не имеют (см. EvilGnome ниже),
 * в том числе для случая, когда данного человека нет в ключах, но он есть в значениях
 * (см. GoodGnome ниже).
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta"),
 *       "Friend" to setOf("GoodGnome"),
 *       "EvilGnome" to setOf()
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat"),
 *          "Friend" to setOf("GoodGnome"),
 *          "EvilGnome" to setOf(),
 *          "GoodGnome" to setOf()
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val allKnots = friends.keys.toMutableSet()
    for (element in friends.values) allKnots.addAll(element)
    //print(allKnots)
    val ans = mutableMapOf<String, Set<String>>()
    fun askConnections(name: String, theirConnections: Set<String> = friends[name] ?: setOf("")): Set<String> {
        //спрашивает связи дальше идущего по цепи и возвращает их сетом
        val personsConnections = mutableSetOf<String>()
        fun askUsername(person: String, theirmates: MutableSet<String>) {
            //как работает asking -
            //у нас есть "глобальная" копилка - PersonsConnections
            //туда, по мере погружения в связи, будут записываться имена.
            //Какая проблема- ? => Надо определиться, когда мы заходим в цикл, и пропустить его.
            //Если нет связей, ничего не добавить и УСЁ
            //Если есть связи у спрашиваемого человека, то спрашиваем связи у его связей и залетаем в рекурсию
            //Мы конечно же пытаемся добавить в personsConnections новых людей и их связи, но если
            //Но если ничего не добавляется, следовательно, у него нет связей, либо, т.к у нас сет, они уже записаны

            if (theirmates.isEmpty()) return
            for (connection in theirmates) {
                if (personsConnections.containsAll(personsConnections.plus(connection))) continue //
                // т.е если добавление ни на что не повлияло
                if (connection != name) personsConnections.add(connection) // Т.к. шизофрении у людей пока что нет,
                // сами с собой они дружить не научились ¯\_(ツ)_/¯
                if (friends[connection] == null) continue
                askUsername(connection, friends[connection] as MutableSet<String>)
            }
        }
        askUsername(name, theirConnections.toMutableSet())


        return personsConnections


    }
    for (name in allKnots) {
        if (friends[name] != null) {//если есть связи. Если нет, то он пустышка
            //кого знает? - friends[name] - set<string>
            friends[name]?.let {
                ans.put(
                    name, it.plus(askConnections(name))
                )
            } //добавляем того, кого знает name и связи его знакомых


        } else {//пустышка
            ans.put(name, setOf())
        }
    }
    return ans
}

/**
 * Сложная (6 баллов)
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> = TODO()

/**
 * Очень сложная (8 баллов)
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Перед решением этой задачи лучше прочитать статью Википедии "Динамическое программирование".
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {// по веритикали- список предметов
    if (treasures.isEmpty()) return emptySet() // вырожденный случай
    val table = Array(treasures.size) {
        Array<Pair<Int, MutableSet<String>>>(capacity + 1) { Pair(0, mutableSetOf()) }
    } // по горизонтали - Вес
    // а внутри Пара - Мак. сумма, к этому весу, если взять все элементы от 0 до этого, + Множество названий пердметов,
    // которые мы взяли для такой суммы
    // - ? Почему size:capacity + 1 ? - потому что нумерация начинается с нуля, и мы будем считать возможным
    // что существует артефакт с нулевой массой, его кстати, надо брать в любом случае, как и все те с отрицательной
    // массой (хорошо, что хоть цена не отрицательная)

    var i = 0
    for ((artifact, stats) in treasures) {//stats - это пара из Веса и Цены
        val weight = stats.first
        val price = stats.second
        for (j in 0 until capacity + 1) {
            if (i == treasures.size) break
            //print(table[i][j])
            //print(" || ")
            // Ячейка [%artifact%][Вес], в ней хранится Пара значений - $$ Доллары за арт $$ и Множество собраных артов

            // У нас есть выбор: мы можем брать артефакт, или нет

            // Если вес артефакта превышает заданный вес j, то мы не берем артефакт. А значит максимальное значение
            // с таким же макс весом j и с набором артефактов, включающих i-ый** , берется из строчки выше
            //**будем считать, что элементы пронумерованы по порядку, в котором их достает итератор.
            if (weight > j) table[i][j] = if (i >= 1) table[i - 1][j] else Pair(0, mutableSetOf())
            // Иначе, если артефакт "подъемный", то мы стоим перед выбором: Брать или не брать? Вот в чем вопрос.
            // чтобы получить максимальное значение,мы"скормим maxOf"(на самом деле надо сравнить) значения с и без арта
            // казалось бы, надо всегда брать арт-к, но только мы сравниваем значение такое, чтобы вес арта поместился,
            // т.е по адресу [i-1][j- (Вес Артефакта) ]
            else {
                if (i < 1) {
                    table[i][j] = Pair(price, mutableSetOf(artifact))
                } else {

                    /*       if (table[i - 1][j].first == table[i][j - weight].first + price) { //Вырожденный случай
                               table[i][j] = Pair(
                                   table[i - 1][j].first + price,
                                   table[i - 1][j].second.plus(artifact).toMutableSet()
                               )}*/
                    if (table[i - 1][j].first <= table[i - 1][j - weight].first + price) {
                        table[i][j] = Pair(
                            table[i - 1][j - weight].first + price,
                            table[i - 1][j - weight].second.plus(artifact).toMutableSet()
                        )
                    } else table[i][j] = table[i - 1][j]
                }
            }
        }
        //println()
        i++
    }
    //ans = table[allElements][maxWeight].second //Ответ я вычислю, проходясь по таблице, основываясь на
    return table[treasures.size - 1][capacity].second
}
//Worst case scenario:
/*assertEquals(
    setOf("1","0"),
    lesson5.task1.bagPacking(
        mapOf("1" to (1 to 1), "0" to (1 to 1)),
        2
    )
)*/
