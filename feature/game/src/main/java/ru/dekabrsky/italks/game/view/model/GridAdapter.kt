package ru.dekabrsky.italks.game.view.model

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import ru.dekabrsky.italks.game.R

class GridAdapter(private val mContext: Context, private val mCols: Int, private val mRows: Int) : BaseAdapter() {

    private val arrPict = ArrayList<String>()
    private val arrStatus = ArrayList<Status>()

    private var pictureCollection = "animal"
    private val mRes: Resources = mContext.resources

    init {
        makePictArray()
        closeAllCells()
    }

    private fun makePictArray() {
        arrPict.clear()
        val scale = mCols * mRows
        for (i in 0 until scale / 2) {
            arrPict.add("$pictureCollection$i")
            arrPict.add("$pictureCollection$i")
        }
        arrPict.shuffle()
    }

    private fun closeAllCells() {
        arrStatus.clear()
        repeat(mCols * mRows) {
            arrStatus.add(Status.CELL_CLOSE)
        }
    }

    override fun getCount(): Int {
        return mCols * mRows
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: ImageView = if (convertView == null) ImageView(mContext) else convertView as ImageView

        when (arrStatus[position]) {
            Status.CELL_OPEN -> {
                val drawableId = mRes.getIdentifier(arrPict[position], "drawable", mContext.packageName)
                view.setImageResource(drawableId)
            }
            Status.CELL_CLOSE -> view.setImageResource(R.drawable.close)
            else -> view.setImageResource(R.drawable.none)
        }

        return view
    }

    fun checkOpenCells() {
        val first = arrStatus.indexOf(Status.CELL_OPEN)
        val second = arrStatus.lastIndexOf(Status.CELL_OPEN)
        if (first == second) return
        if (arrPict[first] == arrPict[second]) {
            arrStatus[first] = Status.CELL_DELETE
            arrStatus[second] = Status.CELL_DELETE
        } else {
            arrStatus[first] = Status.CELL_CLOSE
            arrStatus[second] = Status.CELL_CLOSE
        }
    }

    fun openCell(position: Int): Boolean {
        if (arrStatus[position] == Status.CELL_DELETE || arrStatus[position] == Status.CELL_OPEN) {
            return false
        }

        if (arrStatus[position] != Status.CELL_DELETE) {
            arrStatus[position] = Status.CELL_OPEN
        }

        notifyDataSetChanged()
        return true
    }

    fun checkGameOver(): Boolean {
        return arrStatus.indexOf(Status.CELL_CLOSE) < 0
    }

    private enum class Status {
        CELL_OPEN, CELL_CLOSE, CELL_DELETE
    }
}