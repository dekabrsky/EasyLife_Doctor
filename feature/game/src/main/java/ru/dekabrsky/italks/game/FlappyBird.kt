
package ru.dekabrsky.easylife.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.FitViewport
import java.util.Random


@Suppress("MagicNumber", "DEPRECATION")
class FlappyBird: Game() {

    private lateinit var stage: Stage
    private lateinit var batch: SpriteBatch
    private lateinit var background: Texture
    private lateinit var gameOver: Texture
    private lateinit var birds: Array<Texture>
    private lateinit var topTubeRectangles: Array<Rectangle?>
    private lateinit var bottomTubeRectangles: Array<Rectangle?>
    private lateinit var birdCircle: Circle
    private lateinit var font: BitmapFont
    private lateinit var topTube: Texture
    private lateinit var bottomTube: Texture
    private lateinit var random: Random
    private lateinit var myGameCallback: MyGameCallback

    private lateinit var exitButton: ImageButton

    private var flapState = 0
    private var birdY: Float = 0f
    private var velocity: Float = 0f
    private var score: Int = 0
    private var scoringTube: Int = 0
    private var gameState: Int = 0
    private val numberOfTubes: Int = 100
    private var gdxHeight: Int = 0
    private var gdxWidth: Int = 0
    private var topTubeWidth: Int = 0
    private var bottomTubeWidth: Int = 0
    private var topTubeHeight: Int = 0
    private var bottomTubeHeight: Int = 0

    private val tubeX = FloatArray(numberOfTubes)
    private val tubeOffset = FloatArray(numberOfTubes)
    private var distanceBetweenTubes: Float = 0.toFloat()

    interface MyGameCallback {
        fun exitGame()
        fun onLost(score: Int)
    }

    fun setMyGameCallback(callback: MyGameCallback) {
        myGameCallback = callback
    }

    override fun create() {
        stage = Stage()
        batch = SpriteBatch()
        background = Texture("bg.png")
        gameOver = Texture("gameover.png")
        birdCircle = Circle()
        font = BitmapFont()
        font.color = Color.WHITE
        font.data.setScale(6f)

        val exitTexture = Texture("exit.png")

        exitButton = ImageButton(TextureRegionDrawable(TextureRegion(exitTexture)))

        val scale = 4f
        exitButton.imageCell.size(scale * exitTexture.width, scale * exitTexture.height)

        stage = Stage(FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()))
        Gdx.input.inputProcessor = stage

        val margin = 40f
        val topMargin = 70f
        val buttonsY = Gdx.graphics.height.toFloat() - topMargin

        exitButton.setPosition(Gdx.graphics.width.toFloat() - exitButton.width - margin, buttonsY)

        stage.addActor(exitButton)

        birds = arrayOf(Texture("bird.png"), Texture("bird2.png"))

        gdxHeight = Gdx.graphics.height
        gdxWidth = Gdx.graphics.width

        topTube = Texture("toptube.png")
        bottomTube = Texture("bottomtube.png")
        random = Random()
        distanceBetweenTubes = gdxWidth * 3f / 4f
        topTubeRectangles = arrayOfNulls(numberOfTubes)
        bottomTubeRectangles = arrayOfNulls(numberOfTubes)

        Gdx.input.inputProcessor = stage

        topTubeWidth = topTube.width
        topTubeHeight = topTube.height
        bottomTubeWidth = bottomTube.width
        bottomTubeHeight = bottomTube.height

        startGame()
    }

    @Suppress("LongMethod", "CyclomaticComplexMethod")
    override fun render() {
        batch.projectionMatrix = stage.camera.combined
        batch.begin()
        batch.draw(background, 0f, 0f, gdxWidth.toFloat(), gdxHeight.toFloat())

        if (gameState == 1) {

            if (tubeX[scoringTube] < gdxWidth / 2) {
                score++
                if (scoringTube < numberOfTubes - 1) {
                    scoringTube++
                } else {
                    scoringTube = 0
                }
            }

            if (Gdx.input.justTouched()) {
                velocity = -30f
            }

            for (i in 0 until numberOfTubes) {

                if (tubeX[i] < -topTubeWidth) {
                    tubeX[i] += numberOfTubes * distanceBetweenTubes
                    tubeOffset[i] = (random.nextFloat() - 0.5f) * (gdxHeight.toFloat() - GAP - 200f)
                } else {
                    tubeX[i] = tubeX[i] - TUBE_VELOCITY
                }

                batch.draw(topTube, tubeX[i], gdxHeight / 2f + GAP / 2 + tubeOffset[i])
                batch.draw(bottomTube,
                        tubeX[i],
                        gdxHeight / 2f - GAP / 2 - bottomTubeHeight.toFloat() + tubeOffset[i])

                topTubeRectangles[i] = Rectangle(tubeX[i],
                        gdxHeight / 2f + GAP / 2 + tubeOffset[i],
                        topTubeWidth.toFloat(),
                        topTubeHeight.toFloat())

                bottomTubeRectangles[i] = Rectangle(tubeX[i],
                        gdxHeight / 2f - GAP / 2 - bottomTubeHeight.toFloat() + tubeOffset[i],
                        bottomTubeWidth.toFloat(),
                        bottomTubeHeight.toFloat())
            }

            if (birdY > 0) {
                velocity += GRAVITY
                birdY -= velocity
            } else {
                gameState = 2
            }

        } else if (gameState == 0) {
            if (Gdx.input.justTouched()) {
                gameState = 1
            }
        } else if (gameState == 2) {
            batch.draw(gameOver,
                    gdxWidth / 2f - gameOver.width / 2f,
                    gdxHeight / 2f - gameOver.height / 2f)

            myGameCallback.onLost(score)

            if (Gdx.input.justTouched()) {
                gameState = 1
                startGame()
                score = 0
                scoringTube = 0
                velocity = 0f
            }
        }

        flapState = if (flapState == 0) 1 else 0

        batch.draw(birds[flapState], gdxWidth / 2f - birds[flapState].width / 2f, birdY)
        val scoreString = score.toString()
        val layout = GlyphLayout(font, scoreString)
        val scoreX = (Gdx.graphics.width - layout.width) / 2f
        val scoreY = Gdx.graphics.height - 50f
        font.draw(batch, scoreString, scoreX, scoreY)
        birdCircle.set(gdxWidth / 2f,
                birdY + birds[flapState].height / 2f,
                birds[flapState].width / 2f)

        for (i in 0 until numberOfTubes) {
            if (Intersector.overlaps(birdCircle, topTubeRectangles[i])
                    || Intersector.overlaps(birdCircle, bottomTubeRectangles[i])) gameState = 2
        }

        exitButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                myGameCallback.exitGame()
            }
        })

        batch.end()

        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
    }

    override fun dispose() {
        batch.dispose()
        stage.dispose()
        background.dispose()
        gameOver.dispose()
        birds.forEach { it.dispose() }
        topTube.dispose()
        bottomTube.dispose()
        font.dispose()
    }

    private fun startGame() {
        birdY = gdxHeight / 2f - birds[0].height / 2f

        for (i in 0 until numberOfTubes) {
            tubeOffset[i] = (random.nextFloat() - 0.5f) * (gdxHeight.toFloat() - GAP - 200f)
            tubeX[i] = gdxWidth / 2f - topTubeWidth / 2f + gdxWidth.toFloat() + i * distanceBetweenTubes
            topTubeRectangles[i] = Rectangle()
            bottomTubeRectangles[i] = Rectangle()
        }
    }

    fun score(): Int{
        return score
    }

    companion object {
        private const val GRAVITY = 2f
        private const val TUBE_VELOCITY = 5f
        private const val GAP = 800f
    }
}
