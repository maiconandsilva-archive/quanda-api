package io.github.maiconandsilva.quanda.schemas

enum class ErrorType(val message: String) {
    VALIDATION("Validation error"),
    SERVER_ERROR("Unknown error")
}

data class ErrorResponse(
    val type: ErrorType,
    val errors: Map<String, List<String>>,
    val message: String = type.message,
)
