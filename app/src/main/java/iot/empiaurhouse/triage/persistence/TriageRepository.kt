package iot.empiaurhouse.triage.persistence

import android.app.Application
import androidx.lifecycle.LiveData
import io.reactivex.Single
import iot.empiaurhouse.triage.model.DataPivot
import kotlinx.coroutines.InternalCoroutinesApi


class TriageRepository {

    private lateinit var dataPivotDAO: DataPivotDAO
    private lateinit var insightModelDAO: InsightModelDAO

    @InternalCoroutinesApi
    fun TriageRepository(application: Application) {
        val triageDatabase = TriageDatabase(application.baseContext)
        dataPivotDAO = triageDatabase.dataPivotDOA()
        insightModelDAO = triageDatabase.insightModelDOA()

    }

    fun fetchDataPivots(): LiveData<List<DataPivot>>{
        return dataPivotDAO.bulkFetchDataPivots()
    }

    fun fetchDataPivotsByID(): Single<List<DataPivot>>{
        return dataPivotDAO.fetchDataPivotsByID()
    }

    fun fetchDataPivotsByIDClean(serverOfOrigin: String): Single<List<DataPivot>>{
        return dataPivotDAO.fetchDataPivotsByIDClean(serverOfOrigin)
    }

    fun fetchDataPivotsByChronology(): Single<List<DataPivot>>{
        return dataPivotDAO.fetchDataPivotsByTimeStamp()
    }

    fun fetchDataPivotsByDESCAlias(): LiveData<List<DataPivot>>{
        return dataPivotDAO.fetchDataPivotsByDESCAlias()
    }


    fun fetchDataPivotsByASCAlias(): LiveData<List<DataPivot>>{
        return dataPivotDAO.fetchDataPivotsByASCAlias()
    }

    fun fetchDataPivotsByOriginServer(originServer: String): LiveData<List<DataPivot>>{
        return dataPivotDAO.fetchDataPivotsByOriginServer(originServer)
    }

    fun fetchDataPivotsByEntityCode(entityCode: Int): LiveData<List<DataPivot>>{
        return dataPivotDAO.fetchDataPivotsByEntityCode(entityCode)
    }

    fun fetchDataPivotsByPractitionerCode(practitionerCode: Int): LiveData<List<DataPivot>>{
        return dataPivotDAO.fetchDataPivotsByPractitionerCode(practitionerCode)
    }

    fun fetchDataPivotsByServerUrl(serverUrl: String): LiveData<List<DataPivot>>{
        return dataPivotDAO.fetchDataPivotsByServerUrl(serverUrl)
    }

    fun fetchPivotsByAlphaValueParameter(alphaValueParameter: String): LiveData<List<DataPivot>>{
        return dataPivotDAO.fetchPivotsByAlphaValueParameter(alphaValueParameter)
    }

    fun fetchPivotsByBetaValueParameter(betaValueParameter: String = "null"): LiveData<List<DataPivot>>{
        return dataPivotDAO.fetchPivotsByBetaValueParameter(betaValueParameter)
    }

    fun fetchPivotsByEpsilonValueParameter(epsilonValueParameter: String = "null"): LiveData<List<DataPivot>>{
        return dataPivotDAO.fetchPivotsByEpsilonValueParameter(epsilonValueParameter)
    }


    fun getDataPivotByID(id: Int): LiveData<DataPivot>{
        return dataPivotDAO.fetchDataPivotByID(id)
    }

    fun getDataPivotByAlias(pivotAlias: String): LiveData<DataPivot>{
        return dataPivotDAO.fetchDataPivotByAlias(pivotAlias)
    }

    fun insertDataPivot(dataPivot: DataPivot): DataPivot{
        dataPivotDAO.insertDataPivot(dataPivot)
        return dataPivot
    }

    fun editDataPivot(dataPivot: DataPivot): DataPivot{
        dataPivotDAO.updateDataPivot(dataPivot)
        return dataPivot
    }

    fun deleteDataPivots(){
        dataPivotDAO.deleteDataPivotsDB()
    }

    fun deleteDataPivot(dataPivot: DataPivot) {
         dataPivotDAO.deleteDataPivot(dataPivot)
    }

}