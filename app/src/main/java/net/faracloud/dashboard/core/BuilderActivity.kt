package net.faracloud.dashboard.core

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

abstract class BuilderActivity<S: BuilderViewState, VM : BuilderViewModel<S>> : AppCompatActivity() {
    abstract val baseViewModel: BuilderViewModel<S>
    private var errorMassage: String? = null

    private var localType = LocalType.EN



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

    }


    override fun onResume() {
        super.onResume()
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun getLocal(): LocalType {
        return localType
    }

    private fun updateStatusbarColor(color: Int) {
        window.statusBarColor = color
    }

    fun setTranslucentNavigation() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        )
    }




    abstract fun onStateChange(state: S)

    companion object {
        private const val GENERIC_PERM_HANDLER = 100
        var funAfterSAFPermission: ((success: Boolean) -> Unit)? = null
    }
}