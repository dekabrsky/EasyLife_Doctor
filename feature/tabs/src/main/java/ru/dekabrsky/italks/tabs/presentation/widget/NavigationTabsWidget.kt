package ru.dekabrsky.easylife.tabs.presentation.widget

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.bottomnavigation.BottomNavigationView
import moxy.MvpDelegate
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.easylife.scopes.Scopes
import ru.dekabrsky.easylife.tabs.presentation.presenter.TabsWidgetPresenter
import ru.dekabrsky.easylife.tabs.presentation.view.BottomTabsView
import toothpick.Toothpick

class NavigationTabsWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr), BottomTabsView {

    @InjectPresenter
    lateinit var presenter: TabsWidgetPresenter
    private var parentDelegate: MvpDelegate<*>? = null
    private val delegate by lazy {
        MvpDelegate(this).also {
            it.setParentDelegate(
                parentDelegate,
                this::class.java.simpleName + hashCode().toString()
            )
        }
    }
    private val scopeName = this::class.java.simpleName


    @ProvidePresenter
    fun providePresenter(): TabsWidgetPresenter {
        return Toothpick.openScope(Scopes.SCOPE_APP)
            .getInstance(TabsWidgetPresenter::class.java)
    }

    fun initWidget(parentDelegate: MvpDelegate<*>) {
        this.parentDelegate = parentDelegate
        delegate.onCreate()
        delegate.onAttach()
    }

}