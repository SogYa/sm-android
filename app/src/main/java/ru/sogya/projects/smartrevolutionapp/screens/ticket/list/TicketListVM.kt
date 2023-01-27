package ru.sogya.projects.smartrevolutionapp.screens.ticket.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.domain.models.TicketDomain
import com.sogya.domain.usecases.firebase.ticket.ReadAllTicketUseCase
import com.sogya.domain.utils.MyCallBack
import ru.sogya.projects.smartrevolutionapp.app.App

class TicketListVM : ViewModel() {
    private val repository = App.getFirebase()
    private val getAllTicketUseCase = ReadAllTicketUseCase(repository)
    private val ticketsLiveData = MutableLiveData<List<TicketDomain>>()
    private val errorLiveData = MutableLiveData<String>()

    init {
        val list = arrayListOf<TicketDomain>()
        getAllTicketUseCase.invoke(object : MyCallBack<TicketDomain> {
            override fun data(t: TicketDomain) {
                super.data(t)
                list.add(t)
                ticketsLiveData.postValue(list)
            }

            override fun error(error: String) {
                super.error(error)
                errorLiveData.postValue(error)
            }
        })
    }

    fun getTicketsLiveData() = ticketsLiveData
    fun getErrorLiveData() = errorLiveData
}