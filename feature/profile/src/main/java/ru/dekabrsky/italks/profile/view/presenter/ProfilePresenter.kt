package ru.dekabrsky.italks.profile.view.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.profile.domain.interactor.ProfileInteractor
import ru.dekabrsky.italks.profile.domain.model.CodeEntity
import ru.dekabrsky.italks.profile.view.ProfileView
import ru.dekabrsky.sharedpreferences.SharedPreferencesProvider
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
    val router: FlowRouter,
    private val interactor: ProfileInteractor,
    private val sharedPreferencesProvider: SharedPreferencesProvider
) : BasicPresenter<ProfileView>(router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        checkAvatars()
        viewState.setupAvatars(router)
    }

    fun generateParent() {
        interactor.generateParentCode()
            .observeOn(RxSchedulers.main())
            .subscribe(
                { dispatchParentCode(it) },
                { }
            )
            .addFullLifeCycle()
    }

    private fun checkAvatars() {
        if (sharedPreferencesProvider.gameAvatar.get().isEmpty()) {
            router.startFlow(Flows.Avatar.name)
        }
    }

    private fun dispatchParentCode(entity: CodeEntity) {
        viewState.setParentCode(entity.code)
    }

    fun onLogoutClick() {
        router.newRootFlow(Flows.Login.name)
    }
}