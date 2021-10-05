package io.github.maiconandsilva.quanda.utils.sec

class Views {
    interface Public
    interface Authenticated : Public
    interface Owner : Authenticated
    interface Admin : Authenticated
    interface OwnerAdmin : Admin, Owner
}
