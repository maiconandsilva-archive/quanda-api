package io.github.maiconandsilva.quanda.entities

sealed class Patterns {
    companion object {
       const val NO_ESPECIAL_CHARS = "^(([a-zA-Z0-9])(\\2|[-_])+)$"
    }
}
