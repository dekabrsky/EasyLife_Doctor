package ru.dekabrsky.callersbase.presentation.view

import android.os.Bundle
import android.view.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.callersbase.R
import ru.dekabrsky.callersbase.databinding.FragmentCallersBaseBinding
import ru.dekabrsky.callersbase.presentation.adapter.ChatsListAdapter
import ru.dekabrsky.callersbase.presentation.presenter.ChatsListPresenter
import ru.dekabrsky.callersbase_common.presentation.model.ChatUiModel
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes.SCOPE_FLOW_CHATS
import toothpick.Toothpick

class ChatsListFragment : BasicFragment(), ChatsListView {

    override val layoutRes: Int = R.layout.fragment_callers_base

    private val chatsAdapter by lazy { ChatsListAdapter(presenter::onChatClick) }

    private val binding by viewBinding(FragmentCallersBaseBinding::bind)

    private var sortByNameItem: MenuItem? = null
    private var sortByDateAscItem: MenuItem? = null
    private var sortByDateDescItem: MenuItem? = null

    @InjectPresenter
    lateinit var presenter: ChatsListPresenter

    @ProvidePresenter
    fun providePresenter(): ChatsListPresenter {
        return Toothpick.openScopes(SCOPE_FLOW_CHATS, scopeName)
            .getInstance(ChatsListPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.basesCardsList.adapter = chatsAdapter
        binding.toolbar.setTitle(R.string.callers_bases_title)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.callers_bases_menu, menu)

        sortByNameItem = menu.findItem(R.id.sort_name)
        sortByDateAscItem = menu.findItem(R.id.sort_date_asc)
        sortByDateDescItem = menu.findItem(R.id.sort_date_desc)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sorting -> {}
            R.id.search -> {}
            R.id.sort_date_asc -> {
                presenter.loadSortByDateAsc()
                sortByDateAscItem?.isChecked = true
                sortByDateDescItem?.isChecked = false
                sortByNameItem?.isChecked = false
            }
            R.id.sort_date_desc -> {
                presenter.loadSortByDateDesc()
                sortByDateAscItem?.isChecked = false
                sortByDateDescItem?.isChecked = true
                sortByNameItem?.isChecked = false
            }
            R.id.sort_name -> {
                presenter.loadSortByName()
                sortByDateAscItem?.isChecked = false
                sortByDateDescItem?.isChecked = false
                sortByNameItem?.isChecked = true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setChatsList(items: List<ChatUiModel>) {
        chatsAdapter.updateItems(items)
    }

    override fun showEmptyLayout() {
       binding.emptyLayout.visibility = View.VISIBLE
    }

    override fun onBackPressed() = presenter.onBackPressed()

    companion object {
        fun newInstance() = ChatsListFragment()
    }
}