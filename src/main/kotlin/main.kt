package caesar

import kotlin.math.absoluteValue

fun main(args : Array<String>) {
    val originalPlainText = "Ten years ago, researchers into formal methods (and I was the most mistaken among them) predicted that the programming world would embrace with gratitude every assistance promised by formalisation to solve the problems of reliability that arise when programs get large and more safety-critical. Programs have now got very large and very critical – well beyond the scale which can be comfortably tackled by formal methods. There have been many problems and failures, but these have nearly always been attributable to inadequate analysis of requirements or inadequate management control. It has turned out that the world just does not suffer significantly from the kind of problem that our research was originally intended to solve."
    val shift = 5
    val c = Caesar(originalPlainText, shift)
    val cipherText = c.encipher()
    val plainText = c.decipher(cipherText)
    println(plainText)
}

class Caesar (val plainText: String, shift: Int) {
    val key = shift % 26
    var result = ""
    var i = 0
    // Encipher a string by rotating all characters by k spaces
    fun encipher() : String {
        if(i == plainText.length) {
            return result
        }
        val c = plainText.get(i)
        var char: Char
        // If the character is alphabetical, shift it by key amount, otherwise do not change it
        if (c in 'a'..'z' || c in 'A'..'Z') {
            char = c + key
            if ((c.isUpperCase() && char > 'Z') || (c.isLowerCase() && char > 'z')) char -= 26
        }
        else char = c
        result += "$char"
        i ++
        return encipher()
    }

    fun decipher(s: String) : String{
        var key = 0
        var lowest = -1.0
        var sum = 0.0

        // Hash map of the frequencies of characters in the English language, via wikipedia
        val targetFrequency: HashMap<String,Double> = hashMapOf("a" to 8.167, "b" to 1.492, "c" to 2.782, "d" to 4.253, "e" to 12.702, "f" to 2.228, "g" to 2.015, "h" to 6.094, "i" to 6.966, "j" to 0.153, "k" to 0.772
                , "l" to 4.025, "m" to 2.406, "n" to 6.749, "o" to 7.507, "p" to 1.929, "q" to 0.095, "r" to 5.987, "s" to 6.327, "t" to 9.056, "u" to 2.758, "v" to 0.978, "w" to 2.360, "x" to 0.150, "y" to 1.974, "z" to 0.074)

        // Compare the frequency of characters in each possible deciphering of the text
        // to the expected frequency in the English language and return the closest match
        for (i in 1..26){
            val c = Caesar(s, i)
            val frequency = c.encipher()
                    .toLowerCase()
                    .filter { it in 'a'..'z' }

            for (letter in frequency) {
                sum -= targetFrequency[letter.toString()]!!
            }

            if(lowest == -1.0 || sum < lowest){
                lowest = sum
                key = i
            }

            sum = 0.0
        }
        return Caesar(s, key).encipher()
    }
}

