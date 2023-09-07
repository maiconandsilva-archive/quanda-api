package io.github.maiconandsilva.quanda.utils.sec

/**
 * Used together with {@link JsonView} to control which properties
 * will be rendered {@see https://tinyurl.com/2xrapzfn}
 */
class Views {
    // Generic views

    interface Private

    // No special authorization
    interface Preview
    interface Public : Preview

    interface Authenticated : Public
    interface Owner : Authenticated
    interface Admin : Owner

    // More specific views
    // May be used to render a relationship only when loaded by a specific side
    interface User {
        interface UpdatePassword
        interface UpdateEmail
        interface UpdateUsername
        interface Editable : UpdateEmail, UpdateUsername, UpdatePassword
    }

    interface Question
    interface Tag
    interface UserReaction
}
