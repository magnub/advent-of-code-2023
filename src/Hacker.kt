interface Hacker {
    // Convert input string to hacker speak
    fun toHacker(input: String): String
}

class DefaultHacker(
    val mapping: (Char) -> Char
) : Hacker {
    override fun toHacker(input: String) =
        input.toCharArray().map {
            mapping(it)
        }.joinToString(separator = "")
}
