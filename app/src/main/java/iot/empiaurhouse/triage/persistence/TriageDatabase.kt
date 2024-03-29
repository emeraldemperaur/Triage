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
import iot.empiaurhouse.triage.model.InsightModel
import iot.empiaurhouse.triage.utils.subscribeOnBackground

import java.time.LocalDate

@Database(entities = [DataPivot::class, InsightModel::class], version = 1, exportSchema = false)
@TypeConverters(ChronoConverter::class)
abstract class TriageDatabase: RoomDatabase() {

    abstract fun dataPivotDOA(): DataPivotDAO
    abstract fun insightModelDOA(): InsightModelDAO

    companion object{
        @Volatile private var dbInstance: TriageDatabase? = null
        private val CODEX = Any()

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
            val insightModelDAO = db.insightModelDOA()
            subscribeOnBackground {
                val initPivot = DataPivot(alias = "Prometheus", entityCode = 1, optionCode = 1,
                    endPointCode = 1, valueParamCode = 1, valueParameterA = "John", valueParameterB=" ",
                    valueParameterC = " ",
                    serverOfOrigin = "www.chiron.ca", createdOnTimeStamp = LocalDate.now().toString())
                dataPivotDAO.insertDataPivot(initPivot)
                val initPivotII = DataPivot(alias = "Lazuli  List", entityCode = 1, optionCode = 1,
                    endPointCode = 2, valueParamCode = 3,
                    valueParameterA = "Smith", valueParameterB="Doe", valueParameterC = "Coulson",
                    serverOfOrigin = "www.chiron.com", createdOnTimeStamp = LocalDate.now().toString())
                dataPivotDAO.insertDataPivot(initPivotII)

                val initInsight = InsightModel(alias = "Ultron", vistaCode = 1, entityCode = 2,
                    pointOfInterest = "Synopsis",rangeStartDate = LocalDate.now().toString(),
                    rangeEndDate = LocalDate.now().toString(), piThresholdValue = "Flu",
                    serverOfOrigin = "www.chiron.com", createdOnTimeStamp = LocalDate.now().toString())
                insightModelDAO.insertInsightModel(initInsight)
                println("Initialized Insight Model DB:\n\t $initInsight")
            }

        }


    }


}