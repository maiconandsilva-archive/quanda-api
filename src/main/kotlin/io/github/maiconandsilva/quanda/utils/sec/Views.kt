package io.github.maiconandsilva.quanda.utils.sec

class Views {
    // Generic views
    interface Private
    interface Public
    interface Authenticated : Public
    interface Owner : Authenticated
    interface Admin : Authenticated
    interface OwnerAdmin : Admin, Owner

    // More specific views
    class User {
        interface UpdatePassword
        interface UpdateEmail
        interface UpdateUsername
    }
}
