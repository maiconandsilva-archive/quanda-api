package io.github.maiconandsilva.quanda.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(code = HttpStatus.BAD_REQUEST)
open class BadRequestException : RuntimeException()
 

class ResourceNotFoundException : BadRequestException()
