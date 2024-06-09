package ru.dekabrsky.easylife.game.avatarCustomization.domain.model

import androidx.annotation.DrawableRes
import ru.dekabrsky.easylife.game.R

interface Cloth {
    val drawableRes: Int?
    val rusName: String
}

enum class Glasses(
    @DrawableRes override val drawableRes: Int? = null,
    override val rusName: String
): Cloth {
    NONE(rusName = "Без очков"),
    CLASSIC(R.drawable.glasses_classic, "Круглые"),
    HEARTS(R.drawable.glasses_hearts, "Сердца"),
    GOOGLES(R.drawable.glasses_googles, "Лыжные");
}

enum class Hat(
    @DrawableRes override val drawableRes: Int? = null,
    override val rusName: String
): Cloth {
    NONE(rusName = "Без головного убора"),
    CLASSIC(R.drawable.hat_classic, "Шляпа"),
    CAP(R.drawable.hat_cap, "Кепка"),
    HOLIDAY(R.drawable.holiday_hat, "Праздничный колпак")
}

enum class Tie(
    @DrawableRes override val drawableRes: Int? = null,
    override val rusName: String
): Cloth {
    NONE(rusName = "Без галстука"),
    TIE(R.drawable.tie_tie, "Галстук"),
    BOW(R.drawable.tie_bow, "Бабочка")
}