package ru.dekabrsky.italks.game.view.model

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Rect
import android.os.CountDownTimer
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import ru.dekabrsky.italks.game.R
import java.util.concurrent.CopyOnWriteArrayList

@Suppress("MagicNumber")
class Leaves(context: Context) : SurfaceView(context), Runnable {

    private var isMovingBox = false
    private var lastTouchX = 0

    private val surfaceHolder: SurfaceHolder = holder
    private val paint: Paint = Paint()
    private val screenWidth = context.resources.displayMetrics.widthPixels
    private val screenHeight = context.resources.displayMetrics.heightPixels

    private val treeBitmap = BitmapFactory.decodeResource(resources, R.drawable.tree2)
    private val boxBitmap = BitmapFactory.decodeResource(resources, R.drawable.box)
    private val leafBitmap = BitmapFactory.decodeResource(resources, R.drawable.leaf)

    private val treeRect = Rect(0, 0, screenWidth, screenHeight)
    private val boxRect = Rect(screenWidth / 2 - 150, screenHeight - 400, screenWidth / 2 + 150, screenHeight - 150)

    private val fallLeaves = CopyOnWriteArrayList<Leaf>()
    private var score = 0

    private var isPlaying = false
    private var gameThread: Thread? = null

    private var timer: CountDownTimer? = null
    private var timeLeft = 60000L

    init {
        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                startGame()
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                stopGame()
            }
        })

        paint.isAntiAlias = true
        paint.textSize = 64f
    }

    override fun run() {
        while (isPlaying) {
            update()
            draw()
            control()
        }
    }

    private fun update() {
        for (leaf in fallLeaves) {
            leaf.update()
            if (leaf.isOutOfBounds()) {
                fallLeaves.remove(leaf)
            } else if (leaf.isCaught(boxRect)) {
                fallLeaves.remove(leaf)
                score++
            }
        }
    }

    private fun draw() {
        val canvas = surfaceHolder.lockCanvas() ?: return
        try {
            canvas.drawBitmap(treeBitmap, null, treeRect, null)
            canvas.drawBitmap(boxBitmap, null, boxRect, null)

            for (leaf in fallLeaves) {
                canvas.drawBitmap(leafBitmap, null, leaf.rect, null)
            }

            canvas.drawText("Счет: $score", 50f, 100f, paint)
            canvas.drawText("Время: ${timeLeft / 1000}", screenWidth - 400f, 100f, paint)
        } finally {
            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }

    private fun control() {
        try {
            Thread.sleep(16)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun startGame() {
        isPlaying = true
        gameThread = Thread(this)
        gameThread?.start()

        createLeaves()
        startTimer()
    }

    fun stopGame() {
        isPlaying = false
        gameThread?.interrupt()
        timer?.cancel()
    }

    @SuppressLint("ShowToast")
    fun saveScore(): Int{
        snackBarView(this)
        return score
    }

    private fun snackBarView(view: View){
        val snackBar = Snackbar.make(view, "  Ты заработал $score коинов", Snackbar.LENGTH_SHORT)
        val snackBarLayout: View = snackBar.view
        val textView: TextView =
            snackBarLayout.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.coin_mini, 0, 0, 0)
        snackBar.show()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            val touchX = event.x.toInt()
            val touchY = event.y.toInt()

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (boxRect.contains(touchX, touchY)) {
                        isMovingBox = true
                        lastTouchX = touchX
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    if (isMovingBox) {
                        val deltaX = touchX - lastTouchX
                        boxRect.offset(deltaX, 0)
                        lastTouchX = touchX
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    isMovingBox = false
                }
            }
        }
        return true
    }

    private fun createLeaves() {
        Thread {
            while (isPlaying) {
                val leaf = Leaf(screenWidth, screenHeight)
                fallLeaves.add(leaf)
                Thread.sleep((200..800).toRandomLong())
            }
        }.start()
    }

    private fun startTimer() {
        timer = object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
            }

            override fun onFinish() {
                timeLeft = 0
                stopGame()
            }
        }
        timer?.start()
    }

    private fun ClosedRange<Int>.toRandomLong() = (Math.random() * (endInclusive - start) + start).toLong()
}