package io.github.maiconandsilva.quanda.consts

sealed class Patterns {
    companion object {
       const val NO_ESPECIAL_CHARS = "^(([a-zA-Z0-9])(\\2|[-_])+)$"
       const val PASSWORD = "^(?=.*\\d)(?=.*[A-Z]).{8,64}\$"
    }
}
