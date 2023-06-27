package ru.sogya.projects.smartrevolutionapp.screens.ticket.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.domain.usecases.firebase.ticket.CreateTicketUseCase
import com.sogya.domain.utils.MyCallBack
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.sogya.projects.smartrevolutionapp.utils.VisibilityStates
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CreateTicketVM @Inject constructor(
    private val createTicketUseCase: CreateTicketUseCase
) : ViewModel() {
    private val loadingLiveData = MutableLiveData<Int>()

    fun createTicket(
        device: String,
        zone: String,
        description: String?,
        myCallBack: MyCallBack<Boolean>
    ) {
        val time = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = dateFormat.format(time)
        loadingLiveData.postValue(VisibilityStates.VISIBLE.visibility)
        createTicketUseCase.invoke(
            ticketDevice = device,
            ticketZone = zone,
            ticketDesc = description,
            ticketDate = date,
            object : MyCallBack<Boolean> {
                override fun data(t: Boolean) {
                    super.data(t)
                    loadingLiveData.postValue(VisibilityStates.GONE.visibility)
                    myCallBack.data(true)
                }

                override fun error(error: String) {
                    super.error(error)
                    myCallBack.error(error)
                }
            })
    }

    fun getLoadingLiveData(): LiveData<Int> = loadingLiveData
}