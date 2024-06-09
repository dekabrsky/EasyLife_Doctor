package ru.dekabrsky.avatar.presentation.presenter

import android.net.Uri
import ru.dekabrsky.avatar.domain.AvatarType
import ru.dekabrsky.avatar.presentation.utils.AvatarUriProvider
import ru.dekabrsky.avatar.presentation.view.AvatarSelectionView
import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.presenter.BasicPresenter
import ru.dekabrsky.easylife.flows.Flows
import ru.dekabrsky.sharedpreferences.SharedPreferencesProvider
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetMode
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetScreenArgs
import ru.dekabrsky.simpleBottomsheet.view.model.ButtonState
import javax.inject.Inject

class AvatarSelectionPresenter @Inject constructor(
    private val router: FlowRouter,
    private val uriProvider: AvatarUriProvider,
    private val sharedPreferencesProvider: SharedPreferencesProvider
): BasicPresenter<AvatarSelectionView>(router) {

    private val initialAvatar = AvatarType.getByValueOrNull(sharedPreferencesProvider.gameAvatar.get())
    private var selectedAvatar = initialAvatar

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setAvatars(AvatarType.values().toList())
        checkHasAvatar()
    }

    private fun checkHasAvatar() {
        if (selectedAvatar != null) return

        router.navigateTo(
            Flows.Common.SCREEN_BOTTOM_INFO,
            BottomSheetScreenArgs(
                title = "Время выбрать аватар",
                subtitle = "Прежде чем начать, выбери своего героя! Потом его можно будет поменять",
                mode = BottomSheetMode.GAME,
                buttonState = ButtonState("Хорошо", {})
            )
        )
    }

    fun onAvatarClick(avatarType: AvatarType) {
        selectedAvatar = avatarType
        viewState.refreshAvatars()
        viewState.setButtonVisibility(initialAvatar != selectedAvatar)
    }

    fun isAvatarSelected(avatarType: AvatarType) = avatarType == selectedAvatar

    fun provideUri(avatarType: AvatarType): Uri = uriProvider.provideUri(avatarType)

    fun onSaveBtnClicked() {
        selectedAvatar?.name?.let { sharedPreferencesProvider.gameAvatar.set(it) }
        router.finishFlow()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}