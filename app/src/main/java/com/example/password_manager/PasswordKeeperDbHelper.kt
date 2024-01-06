import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PasswordKeeperDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "passwords.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
//        val createTableQuery = "CREATE TABLE ${PasswordKeeperContract.PasswordEntry.TABLE_NAME} (" +
//                "${BaseColumns.ID} INTEGER PRIMARY KEY," +
//                "${PasswordKeeperContract.PasswordEntry.COLUMN_NAME_RESOURCE} TEXT," +
//                "${PasswordKeeperContract.PasswordEntry.COLUMN_NAME_LOGIN} TEXT," +
//                "${PasswordKeeperContract.PasswordEntry.COLUMN_NAME_PASSWORD} TEXT," +
//                "${PasswordKeeperContract.PasswordEntry.COLUMN_NAME_NOTE} TEXT)"

        //db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Логика обновления базы данных, если необходимо
    }
}
