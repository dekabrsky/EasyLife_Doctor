package ru.dekabrsky.stats.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import ru.dekabrsky.common.domain.model.ContactEntity
import ru.dekabrsky.easylife.basic.fragments.BasicView
import ru.dekabrsky.feature.loginCommon.domain.model.UserInfoEntity

@AddToEndSingle
interface AdultProfileView: BasicView {
    fun showMyInfo(infoEntity: UserInfoEntity?)
    fun showChildInfo(children: List<ContactEntity>)
    @OneExecution
    fun showLogoutDialog()
}