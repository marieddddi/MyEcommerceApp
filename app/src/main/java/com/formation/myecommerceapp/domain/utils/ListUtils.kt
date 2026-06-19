package com.formation.myecommerceapp.domain.utils

/**
 * Remplace [originalItem] dans la liste par [newItem].
 * Si l'élément n'est pas trouvé, la liste n'est pas modifiée.
 */
fun <T> MutableList<T>.replaceBy(originalItem: T, newItem: T): MutableList<T> {
    if (originalItem != -1) {
        remove(originalItem)
        add(newItem)
    }
    return this
}