package net.faracloud.dashboard.core


import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import net.faracloud.dashboard.core.api.Resource

const val REMAINING_TIME = 120
abstract class BuilderViewModel<S: BuilderViewState>(
    private val defaultState: S
) : ViewModel() {

    val state = MutableLiveData<S>(defaultState).apply { value = defaultState }

    open fun onStateUpdated() = Unit
    override fun onCleared() {
        super.onCleared()
    }

}

fun <T> LifecycleOwner.observe(liveData: LiveData<T>?, action: (t: T) -> Unit) {
    liveData?.observe(this, Observer { t -> action(t) })
}


fun <T,S: BuilderViewState> callResource(viewModel: BuilderViewModel<S>, resource: Resource<T>, isShowError: Boolean = true) : Resource<T> {
    /*when {
        resource.code == 401 -> {
            viewModel.unAuthorized(resource.message!!) {  }
        }
        resource.code == 1033 -> {
            viewModel.existProfileUser()
        }
        resource.code == 1009 -> {
            viewModel.connectAuth()
        }
        resource.code == 1051 -> {
            viewModel.showError(resource.message ?: "عملیات شما با خطا مواجه شد"," ",isShowError)
        }
        resource.status == BaseStatus.ERROR -> {
            viewModel.showError(resource.message ?: "عملیات شما با خطا مواجه شد",null,isShowError)
        }
    }*/
    return resource
}
