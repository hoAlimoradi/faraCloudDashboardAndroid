package net.faracloud.dashboard.core

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import net.faracloud.dashboard.R

abstract class BuilderFragment<S: BuilderViewState, VM : BuilderViewModel<S>> : Fragment(){

    abstract val baseViewModel: BuilderViewModel<S>

    inline fun <reified VM : BuilderViewModel<S>> viewModelProvider(
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
        crossinline provider: () -> VM
    ) = lazy(mode) {
        ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T1 : ViewModel> create(aClass: Class<T1>) =
                provider() as T1
        }).get(VM::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserveViewModelState()
    }

    fun getFindViewController(): NavController? {
        return activity?.findNavController(R.id.nav_host_fragment)
    }

    private fun initObserveViewModelState() {

        baseViewModel.state.observe(this, Observer {
            onStateChange(it)
            baseViewModel.onStateUpdated()
        })
    }
    abstract fun onStateChange(state: S)

    fun getLocal(): LocalType {
        var local = LocalType.EN
        activity?.let {
            if (it is BuilderActivity<*, *>) {
                local = it.getLocal()
            }
        }
        return local
    }
}
