package a77777_888.me.t.shoptestapp.ui.entities

class Validator {
    companion object {
        private val namePattern = "[А-Яа-я]+".toRegex()

        fun validateName(name: String): Boolean {
            return namePattern.matches(name)
        }
    }
}