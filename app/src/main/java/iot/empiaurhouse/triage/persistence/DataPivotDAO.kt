package iot.empiaurhouse.triage.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Single
import iot.empiaurhouse.triage.model.DataPivot

@Dao
interface DataPivotDAO {

    @Insert
    fun insertDataPivot(dataPivot: DataPivot)

    @Update
    fun updateDataPivot(dataPivot: DataPivot)

    @Delete
    fun deleteDataPivot(dataPivot: DataPivot)

    @Query("delete from pivot_db")
    fun deleteDataPivotsDB()

    @Query("select * from pivot_db")
    fun bulkFetchDataPivots(): LiveData<List<DataPivot>>

    @Query("select * from pivot_db where id==:id")
    fun fetchDataPivotByID(id: Int): LiveData<DataPivot>

    @Query("select * from pivot_db where alias==:pivotAlias")
    fun fetchDataPivotByAlias(pivotAlias: String): LiveData<DataPivot>

    @Query("select * from pivot_db order by id desc")
    fun fetchDataPivotsByID(): Single<List<DataPivot>>

    @Query("select * from pivot_db where serverOfOrigin==:serverOfOrigin order by id desc")
    fun fetchDataPivotsByIDClean(serverOfOrigin: String): Single<List<DataPivot>>

    @Query("select * from pivot_db order by createdOnTimeStamp desc")
    fun fetchDataPivotsByTimeStamp(): Single<List<DataPivot>>

    @Query("select * from pivot_db where serverOfOrigin==:serverOfOrigin")
    fun fetchDataPivotsByOriginServer(serverOfOrigin: String): LiveData<List<DataPivot>>

    @Query("select * from pivot_db order by alias desc")
    fun fetchDataPivotsByDESCAlias(): LiveData<List<DataPivot>>

    @Query("select * from pivot_db order by alias asc")
    fun fetchDataPivotsByASCAlias(): LiveData<List<DataPivot>>

    @Query("select * from pivot_db where entityCode==:entityCodeID")
    fun fetchDataPivotsByEntityCode(entityCodeID: Int): LiveData<List<DataPivot>>

    @Query("select * from pivot_db where practitionerCode==:practitionerCode")
    fun fetchDataPivotsByPractitionerCode(practitionerCode: Int): LiveData<List<DataPivot>>

    @Query("select * from pivot_db where serverOfOrigin==:serverUrl")
    fun fetchDataPivotsByServerUrl(serverUrl: String): LiveData<List<DataPivot>>

    @Query("select * from pivot_db where valueParameterA==:valueParameterA")
    fun fetchPivotsByAlphaValueParameter(valueParameterA: String): LiveData<List<DataPivot>>

    @Query("select * from pivot_db where valueParameterB==:valueParameterB")
    fun fetchPivotsByBetaValueParameter(valueParameterB: String): LiveData<List<DataPivot>>

    @Query("select * from pivot_db where valueParameterC==:valueParameterC")
    fun fetchPivotsByEpsilonValueParameter(valueParameterC: String): LiveData<List<DataPivot>>


}