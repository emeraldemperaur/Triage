package iot.empiaurhouse.triage.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Single
import iot.empiaurhouse.triage.model.InsightModel


@Dao
interface InsightModelDAO {

    @Insert
    fun insertInsightModel(insight: InsightModel)

    @Update
    fun updateInsightModel(insight: InsightModel)

    @Delete
    fun deleteInsightModel(insight: InsightModel)

    @Query("delete from insight_db")
    fun deleteInsightModelsDB()

    @Query("select * from insight_db")
    fun bulkFetchInsightModels(): LiveData<List<InsightModel>>

    @Query("select * from insight_db where id==:id")
    fun fetchInsightModelByID(id: Int): LiveData<InsightModel>

    @Query("select * from insight_db where alias==:insightAlias")
    fun fetchInsightModelByAlias(insightAlias: String): LiveData<InsightModel>

    @Query("select * from insight_db order by id desc")
    fun fetchInsightModelsByID(): Single<List<InsightModel>>

    @Query("select * from insight_db where serverOfOrigin==:serverOfOrigin order by id desc")
    fun fetchInsightModelsByIDClean(serverOfOrigin: String): Single<List<InsightModel>>

    @Query("select * from insight_db order by createdOnTimeStamp desc")
    fun fetchInsightModelsByTimeStamp(): Single<List<InsightModel>>

    @Query("select * from insight_db where serverOfOrigin==:serverOfOrigin")
    fun fetchInsightModelsByOriginServer(serverOfOrigin: String): LiveData<List<InsightModel>>

    @Query("select * from insight_db order by alias desc")
    fun fetchInsightModelsByDESCAlias(): LiveData<List<InsightModel>>

    @Query("select * from insight_db order by alias asc")
    fun fetchInsightModelsByASCAlias(): LiveData<List<InsightModel>>

}