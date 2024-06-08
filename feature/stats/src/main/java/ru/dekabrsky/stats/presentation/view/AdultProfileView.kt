package ru.dekabrsky.stats.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import ru.dekabrsky.common.domain.model.ContactEntity
import ru.dekabrsky.feature.loginCommon.domain.model.UserInfoEntity
import ru.dekabrsky.italks.basic.fragments.BasicView

@AddToEndSingle
interface AdultProfileView: BasicView {
    fun showMyInfo(infoEntity: UserInfoEntity?)
    fun showChildInfo(children: List<ContactEntity>)
    @OneExecution
    fun showLogoutDialog()
}