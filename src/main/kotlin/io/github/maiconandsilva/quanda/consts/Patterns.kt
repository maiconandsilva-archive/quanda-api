package io.github.maiconandsilva.quanda.consts

sealed class Patterns {
    companion object {
       const val NO_ESPECIAL_CHARS = "^(([a-zA-Z0-9])(([a-zA-Z0-9])|[-_])+)$"
       const val PASSWORD = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$"
    }
}
