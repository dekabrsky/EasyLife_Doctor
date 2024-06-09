package ru.dekabrsky.simpleBottomsheet.view.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import main.utils.visible
import ru.dekabrsky.easylife.basic.viewBinding.viewBinding
import ru.dekabrsky.simpleBottomsheet.R
import ru.dekabrsky.simpleBottomsheet.databinding.SimpleInfobottomSheetFragmentBinding
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetScreenArgs


open class SimpleInfoBottomSheet : BottomSheetDialogFragment() {
    private val viewBinding by viewBinding(SimpleInfobottomSheetFragmentBinding::bind)

    private val bottomSheet get() = dialog?.findViewById<View>(R.id.design_bottom_sheet)
    protected open val initState = BottomSheetBehavior.STATE_EXPANDED
    protected open val skipCollapsed = false
    private val animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.bounce_animation) }

    override fun getTheme(): Int = R.style.Dialog_Bottom

    private val closeListener = { dismissAllowingStateLoss() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.closeButton.setOnClickListener { closeListener() }

        viewBinding.title.text = args?.title
        viewBinding.description.text = args?.subtitle?.let { Html.fromHtml(it) }

        args?.mode?.let { mode ->
            viewBinding.root.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), mode.bgColor))
            viewBinding.description.setTextColor(ContextCompat.getColor(requireContext(), mode.textColor))
            viewBinding.title.setTextColor(ContextCompat.getColor(requireContext(), mode.textColor))
        }

        args?.icon?.let { drawableRes ->
            viewBinding.image.visible()
            viewBinding.image.setImageResource(drawableRes)
            viewBinding.image.startAnimation(animation)
        }

        args?.buttonState?.let { buttonState ->
            viewBinding.customButton.visible()
            viewBinding.customButton.text = buttonState.text
            viewBinding.customButton.setOnClickListener {
                dismissAllowingStateLoss()
                buttonState.action.invoke()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.simple_infobottom_sheet_fragment, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : BottomSheetDialog(requireActivity(), theme) {
            override fun cancel() {
                super.cancel()
            }
        }
            .apply { setCancelable(false) }
            .apply { setOnShowListener { initOnShowListeners() } }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        closeListener()
    }

    protected open fun initOnShowListeners() {
        bottomSheet?.let {
            val behavior = bottomSheet?.let { BottomSheetBehavior.from(it) }
            behavior?.state = initState
            behavior?.skipCollapsed = skipCollapsed
        }
    }

    private var args: BottomSheetScreenArgs? = null

    companion object {
        fun newInstance(args: BottomSheetScreenArgs) =
            SimpleInfoBottomSheet().apply { this.args = args }
    }
}