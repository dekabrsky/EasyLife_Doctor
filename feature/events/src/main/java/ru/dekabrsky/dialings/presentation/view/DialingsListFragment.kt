package ru.dekabrsky.dialings.presentation.view

import android.app.AlertDialog
import android.content.res.AssetManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.dialings.R
import ru.dekabrsky.dialings.databinding.FragmentDialingListBinding
import ru.dekabrsky.dialings.presentation.adapter.DialingListAdapter
import ru.dekabrsky.dialings.domain.model.PlainProduct
import ru.dekabrsky.dialings.presentation.presenter.DialingsListPresenter
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class DialingsListFragment : BasicFragment(), DialingsListView {

    override val layoutRes: Int = R.layout.fragment_dialing_list

    private val adapter by lazy {
        DialingListAdapter(
            onItemClick = presenter::onToDetailsClick,
            onRunClick = presenter::onRunClick
        )
    }

    private val binding by viewBinding(FragmentDialingListBinding::bind)

    private var sortByNameItem: MenuItem? = null
    private var sortByDateAscItem: MenuItem? = null
    private var sortByDateDescItem: MenuItem? = null

    @InjectPresenter
    lateinit var presenter: DialingsListPresenter

    @ProvidePresenter
    fun providePresenter(): DialingsListPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_DIALINGS, scopeName)
            .getInstance(DialingsListPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onResume() {
        super.onResume()
        (parentFragment as DialingsFlowFragment).setNavBarVisibility(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.basesCardsList.adapter = adapter
        binding.toolbar.setTitle(R.string.dialings_title)

        val assets = activity?.assets

        presenter.setJson(assets?.let { getDataFromJson(it) })

        binding.cityLayout.setOnClickListener { presenter.onCityFilterClick() }
        binding.typeLayout.setOnClickListener { presenter.onTypeFilterClick() }
    }

    private fun getDataFromJson(it: AssetManager): String? {
        var json: String? = null
        json = try {
            val stream: InputStream = it.open("res_data.json")
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            String(buffer, Charset.defaultCharset())
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.dialings_menu, menu)

        sortByNameItem = menu.findItem(R.id.sort_name)
        sortByDateAscItem = menu.findItem(R.id.sort_date_asc)
        sortByDateDescItem = menu.findItem(R.id.sort_date_desc)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sorting -> {
            }
            R.id.search -> {
            }
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

    override fun setItems(items: List<PlainProduct>) {
        adapter.updateItems(items)
    }

    override fun showEmptyLayout() {
        binding.emptyLayout.visibility = View.VISIBLE
        binding.basesCardsList.visibility = View.GONE
    }

    override fun hideEmptyLayout() {
        binding.emptyLayout.visibility = View.GONE
        binding.basesCardsList.visibility = View.VISIBLE
    }

    override fun showNoConnectionDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Нет подключения")
        builder.setMessage(
            "Приложение не может подключиться к серверу. Убедитесь, что он доступен из вашей сети"
        )
        builder.show()
    }

    override fun setFilter(chipId: Int) {
//        binding.filters.check(chipId)
    }

    override fun showRunNowDialog(id: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Запустить сейчас?")
        builder.setPositiveButton("Да") { _, _ -> presenter.runNow(id) }
        builder.setNegativeButton("Нет") { _, _ -> }
        builder.show()
    }

    override fun setCityMarkerVisibility(notEmpty: Boolean) {
        binding.cityMarker.visibility = if (notEmpty) View.VISIBLE else View.GONE
    }

    override fun setTypeMarkerVisibility(notEmpty: Boolean) {
        binding.typeMarker.visibility = if (notEmpty) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() = presenter.onBackPressed()

    companion object {
        fun newInstance() = DialingsListFragment()
    }
}