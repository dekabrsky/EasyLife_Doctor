package ru.dekabrsky.easylife.game.avatarCustomization.view.model

import ru.dekabrsky.easylife.game.avatarCustomization.domain.model.Glasses
import ru.dekabrsky.easylife.game.avatarCustomization.domain.model.Hat
import ru.dekabrsky.easylife.game.avatarCustomization.domain.model.Tie

class CatWidgetSettings(
    val hat: Hat = Hat.NONE,
    val glasses: Glasses = Glasses.NONE,
    val tie: Tie = Tie.NONE
)