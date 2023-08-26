package com.alexzh.moodtracker.common.ext

inline fun <E> Iterable<E>.indexesOf(predicate: (E) -> Boolean) =
    mapIndexedNotNull { index, elem -> index.takeIf { predicate(elem) } }