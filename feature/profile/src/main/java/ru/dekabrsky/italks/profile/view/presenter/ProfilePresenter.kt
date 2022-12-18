package ru.dekabrsky.italks.profile.view.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.profile.domain.interactor.ProfileInteractor
import ru.dekabrsky.italks.profile.domain.model.CodeEntity
import ru.dekabrsky.italks.profile.view.ProfileView
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
    router: FlowRouter,
    private val interactor: ProfileInteractor
) : BasicPresenter<ProfileView>(router) {

    fun generateParent() {

        interactor.generateParentCode()
            .observeOn(RxSchedulers.main())
            .subscribe(
                { dispatchParentCode(it) },
                { }
            )
            .addFullLifeCycle()
    }

    private fun dispatchParentCode(entity: CodeEntity) {
        viewState.setParentCode(entity.code)
    }
}