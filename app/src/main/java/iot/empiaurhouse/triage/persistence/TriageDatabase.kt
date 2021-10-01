package iot.empiaurhouse.triage.persistence

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import iot.empiaurhouse.triage.model.DataPivot
import iot.empiaurhouse.triage.utils.subscribeOnBackground
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import java.time.LocalDate

@Database(entities = [DataPivot::class], version = 1)
@TypeConverters(ChronoConverter::class)
abstract class TriageDatabase: RoomDatabase() {

    abstract fun dataPivotDOA(): DataPivotDAO
    abstract fun insightModelDOA(): InsightModelDAO

    companion object{
        @Volatile private var dbInstance: TriageDatabase? = null
        private val CODEX = Any()

        @InternalCoroutinesApi
        operator fun invoke(context: Context)= dbInstance ?: synchronized(CODEX){
            dbInstance ?: buildTriageDatabase(context).also { dbInstance = it}
        }

        private fun buildTriageDatabase(context: Context) = Room.databaseBuilder(context,
        TriageDatabase::class.java, "triage.db")
            .fallbackToDestructiveMigration()
            .addCallback(bootyCall)
            .build()


        private val bootyCall = object : Callback(){
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                initDB(dbInstance!!)
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun initDB(db: TriageDatabase){
            val dataPivotDAO = db.dataPivotDOA()
            subscribeOnBackground {
                val initPivot = DataPivot(alias = "Prometheus", entityCode = 1, optionCode = 1,
                    endPointCode = 1, valueParamCode = 1, valueParameterA = "John",
                    serverOfOrigin = "www.chiron.ca", createdOnTimeStamp = LocalDate.now().toString())
                dataPivotDAO.insertDataPivot(initPivot)
                val initPivotII = DataPivot(alias = "Lazuli  List", entityCode = 1, optionCode = 1,
                    endPointCode = 2, valueParamCode = 3,
                    valueParameterA = "Smith", valueParameterB="Doe", valueParameterC = "Coulson",
                    serverOfOrigin = "www.chiron.com", createdOnTimeStamp = LocalDate.now().toString())
                dataPivotDAO.insertDataPivot(initPivot)
                dataPivotDAO.insertDataPivot(initPivotII)
            }

        }


    }


}