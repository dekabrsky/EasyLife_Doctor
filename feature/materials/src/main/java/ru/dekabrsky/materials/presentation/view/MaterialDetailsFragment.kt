package ru.dekabrsky.materials.presentation.view

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.easylife.basic.di.module
import ru.dekabrsky.easylife.basic.fragments.BasicFragment
import ru.dekabrsky.easylife.basic.viewBinding.viewBinding
import ru.dekabrsky.easylife.scopes.Scopes
import ru.dekabrsky.materials.R
import ru.dekabrsky.materials.databinding.FragmentMaterialDetailsBinding
import ru.dekabrsky.materials.presentation.model.MaterialDetailsUiModel
import ru.dekabrsky.materials.presentation.presenter.MaterialDetailsPresenter
import toothpick.Toothpick

class MaterialDetailsFragment: BasicFragment(), MaterialDetailsView {

    override val layoutRes = R.layout.fragment_material_details

    private lateinit var material : MaterialDetailsUiModel

    @InjectPresenter
    lateinit var presenter: MaterialDetailsPresenter

    private val binding by viewBinding(FragmentMaterialDetailsBinding::bind)

    @ProvidePresenter
    fun providePresenter(): MaterialDetailsPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_MATERIALS, scopeName)
            .module { bind(MaterialDetailsUiModel::class.java).toInstance(material) }
            .getInstance(MaterialDetailsPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        (parentFragment as MaterialsFlowFragment).setNavBarVisibility(false)
    }

    override fun showMaterial(material: MaterialDetailsUiModel) {
        binding.articleTitle.text = material.title
        binding.text.text = material.fullText
        Glide
            .with(requireContext())
            .load(material.imageLink)
            .centerCrop()
            .into(binding.image)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    companion object {
        fun newInstance(material: MaterialDetailsUiModel) = MaterialDetailsFragment().apply {
            this.material = material
        }
    }
}