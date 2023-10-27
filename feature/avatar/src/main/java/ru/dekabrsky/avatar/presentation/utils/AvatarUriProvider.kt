package ru.dekabrsky.avatar.presentation.utils

import android.net.Uri
import ru.dekabrsky.avatar.domain.AvatarType
import javax.inject.Inject

class AvatarUriProvider @Inject constructor() {
    fun provideUri(avatar: AvatarType) = Uri.parse("file:///android_asset/avatar/${avatar.name}.jpg")
}