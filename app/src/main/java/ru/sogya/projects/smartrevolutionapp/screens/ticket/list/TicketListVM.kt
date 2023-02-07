package ru.sogya.projects.smartrevolutionapp.screens.ticket.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.domain.models.TicketDomain
import com.sogya.domain.usecases.firebase.ticket.ReadAllTicketUseCase
import com.sogya.domain.utils.MyCallBack
import ru.sogya.projects.smartrevolutionapp.app.App

class TicketListVM : ViewModel() {
    private val repository = App.getFirebase()
    private val getAllTicketUseCase = ReadAllTicketUseCase(repository)
    private val ticketsLiveData = MutableLiveData<Map<String, TicketDomain>>()
    private val errorLiveData = MutableLiveData<String>()

    init {
        val list = mutableMapOf<String, TicketDomain>()
        getAllTicketUseCase.invoke(object : MyCallBack<TicketDomain> {
            override fun data(t: TicketDomain) {
                super.data(t)
                list[t.ticketId] = t
                ticketsLiveData.postValue(list)
            }

            override fun error(error: String) {
                super.error(error)
                errorLiveData.postValue(error)
            }
        })
    }

    fun getTicketsLiveData(): LiveData<Map<String, TicketDomain>> = ticketsLiveData
    fun getErrorLiveData(): LiveData<String> = errorLiveData
}