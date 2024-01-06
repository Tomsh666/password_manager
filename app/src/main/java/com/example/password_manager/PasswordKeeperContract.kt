import android.provider.BaseColumns

object PasswordKeeperContract {
    object PasswordEntry : BaseColumns {
        const val ID = "id"
        const val COLUMN_NAME_RESOURCE = "resource"
        const val COLUMN_NAME_LOGIN = "login"
        const val COLUMN_NAME_PASSWORD = "password"
        const val COLUMN_NAME_NOTE = "note"
    }
}
