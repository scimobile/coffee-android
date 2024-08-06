package com.sci.coffeeandroid.util

import android.text.InputFilter
import android.text.Spanned

class PhoneNumberInputFilter : InputFilter {
    // Regex pattern to allow digits, spaces, dashes, plus sign, and parentheses
    private val regex = Regex("[0-9+\\-() ]*")

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        if (source == null) return null

        val input = dest?.subSequence(0, dstart).toString() +
                source.subSequence(start, end).toString() +
                dest?.subSequence(dend, dest.length).toString()

        return if (regex.matches(input)) {
            null // Accept the input
        } else {
            ""   // Reject the input
        }
    }
}


