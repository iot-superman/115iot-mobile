package com.example.listview5

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.GLUtils
import android.opengl.Matrix
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.util.Locale
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.cos
import kotlin.math.sin

class AccActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var accSensor: Sensor? = null
    
    private lateinit var textViewAccData: TextView
    private lateinit var imageViewAccPic: ImageView
    private lateinit var glView: GLSurfaceView
    private lateinit var renderer: EarthRenderer
    
    private lateinit var ivUp: ImageView
    private lateinit var ivDown: ImageView
    private lateinit var ivLeft: ImageView
    private lateinit var ivRight: ImageView
    private lateinit var ivZin: ImageView
    private lateinit var ivZout: ImageView
    
    private val showThreshold = 4.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_acc)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        title = "ACC sensor"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        textViewAccData = findViewById(R.id.textView_accData)
        imageViewAccPic = findViewById(R.id.imageView_accPic)
        glView = findViewById(R.id.gl_earth_view)
        
        ivUp = findViewById(R.id.iv_up)
        ivDown = findViewById(R.id.iv_down)
        ivLeft = findViewById(R.id.iv_left)
        ivRight = findViewById(R.id.iv_right)
        ivZin = findViewById(R.id.iv_z_in)
        ivZout = findViewById(R.id.iv_z_out)

        // 設定 OpenGL ES 2.0
        glView.setEGLContextClientVersion(2)
        // 為了去背，設定透明背景支援
        glView.setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        renderer = EarthRenderer(this)
        glView.setRenderer(renderer)
        glView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
        glView.holder.setFormat(android.graphics.PixelFormat.TRANSLUCENT)
        glView.setZOrderOnTop(true)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()
        glView.onResume()
        accSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        glView.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            
            textViewAccData.text = String.format(Locale.US, "X: %7.2f\nY: %7.2f\nZ: %7.2f", x, y, z)
            
            imageViewAccPic.rotation = -x * 5
            var scaleFactor = (z / 9.8f) + 0.5f
            if (scaleFactor < 0.3f) scaleFactor = 0.3f
            if (scaleFactor > 2.0f) scaleFactor = 2.0f
            imageViewAccPic.scaleX = scaleFactor
            imageViewAccPic.scaleY = scaleFactor

            // 更新 3D 地球的旋轉角度
            renderer.updateRotation(x, y)
            
            updateArrowVisibility(x, y, z)
        }
    }

    private fun updateArrowVisibility(x: Float, y: Float, z: Float) {
        ivLeft.alpha = if (x > showThreshold) 1.0f else 0.0f
        ivRight.alpha = if (x < -showThreshold) 1.0f else 0.0f
        ivUp.alpha = if (y > showThreshold) 1.0f else 0.0f
        ivDown.alpha = if (y < -showThreshold) 1.0f else 0.0f
        val zDiff = z - 9.8f
        ivZout.alpha = if (zDiff > showThreshold) 1.0f else 0.0f
        ivZin.alpha = if (zDiff < -showThreshold) 1.0f else 0.0f
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    override fun onSupportNavigateUp(): Boolean { finish(); return true }

    // ========================================================
    // OpenGL ES 2.0 真 3D 貼圖渲染器
    // ========================================================
    class EarthRenderer(private val context: Context) : GLSurfaceView.Renderer {
        private var program = 0
        private var textureId = 0
        private var vertexBuffer: FloatBuffer? = null
        private var indexBuffer: java.nio.ShortBuffer? = null
        private var numIndices = 0
        
        private val mvpMatrix = FloatArray(16)
        private val projectionMatrix = FloatArray(16)
        private val viewMatrix = FloatArray(16)
        private val modelMatrix = FloatArray(16)
        
        // 初始旋轉角度：調整至正對亞洲 (經度偏移約 -90 到 -110 度)
        private var rotationX = 0f
        private var rotationY = -100f

        fun updateRotation(x: Float, y: Float) {
            rotationY -= x * 0.5f // 累積旋轉，產生轉動感
            rotationX -= y * 0.5f
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES20.glClearColor(0f, 0f, 0f, 0f) // 改為全透明 (0,0,0,0) 達成完全去背
            GLES20.glEnable(GLES20.GL_DEPTH_TEST)
            
            val vertexShaderCode = """
                uniform mat4 uMVPMatrix;
                attribute vec4 vPosition;
                attribute vec2 vTexCoord;
                varying vec2 vVaryingTexCoord;
                void main() {
                    gl_Position = uMVPMatrix * vPosition;
                    vVaryingTexCoord = vTexCoord;
                }
            """.trimIndent()

            val fragmentShaderCode = """
                precision mediump float;
                uniform sampler2D uTexture;
                varying vec2 vVaryingTexCoord;
                void main() {
                    gl_FragColor = texture2D(uTexture, vVaryingTexCoord);
                }
            """.trimIndent()

            program = loadProgram(vertexShaderCode, fragmentShaderCode)
            
            // 建立球體模型
            createSphere(1.2f, 30, 30)
            
            // 載入貼圖
            textureId = loadTexture(context, R.drawable.earth_map) // 已修正為您的 earth_map 圖片
        }

        private fun createSphere(radius: Float, rings: Int, sectors: Int) {
            val vertices = mutableListOf<Float>()
            val indices = mutableListOf<Short>()

            for (r in 0..rings) {
                val phi = (Math.PI * r / rings).toFloat()
                for (s in 0..sectors) {
                    val theta = (2.0 * Math.PI * s / sectors).toFloat()
                    val x = (radius * sin(phi) * cos(theta))
                    val y = (radius * cos(phi))
                    val z = (radius * sin(phi) * sin(theta))
                    
                    vertices.add(x); vertices.add(y); vertices.add(z)
                    // UV 座標 (貼圖對齊)
                    vertices.add(s.toFloat() / sectors)
                    vertices.add(r.toFloat() / rings)
                }
            }

            for (r in 0 until rings) {
                for (s in 0 until sectors) {
                    val first = (r * (sectors + 1) + s).toShort()
                    val second = (first + sectors + 1).toShort()
                    
                    indices.add(first); indices.add(second); indices.add((first + 1).toShort())
                    indices.add(second); indices.add((second + 1).toShort()); indices.add((first + 1).toShort())
                }
            }
            
            numIndices = indices.size
            vertexBuffer = ByteBuffer.allocateDirect(vertices.size * 4).run {
                order(ByteOrder.nativeOrder()); asFloatBuffer().apply { put(vertices.toFloatArray()); position(0) }
            }
            indexBuffer = ByteBuffer.allocateDirect(indices.size * 2).run {
                order(ByteOrder.nativeOrder()); asShortBuffer().apply { put(indices.toShortArray()); position(0) }
            }
        }

        override fun onDrawFrame(gl: GL10?) {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
            GLES20.glUseProgram(program)

            Matrix.setIdentityM(modelMatrix, 0)
            Matrix.rotateM(modelMatrix, 0, rotationX, 1f, 0f, 0f)
            Matrix.rotateM(modelMatrix, 0, rotationY, 0f, 1f, 0f)
            
            Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, 5f, 0f, 0f, 0f, 0f, 1f, 0f)
            Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
            Matrix.multiplyMM(mvpMatrix, 0, mvpMatrix, 0, modelMatrix, 0)

            val matrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix")
            GLES20.glUniformMatrix4fv(matrixHandle, 1, false, mvpMatrix, 0)

            val posHandle = GLES20.glGetAttribLocation(program, "vPosition")
            GLES20.glEnableVertexAttribArray(posHandle)
            vertexBuffer?.position(0)
            GLES20.glVertexAttribPointer(posHandle, 3, GLES20.GL_FLOAT, false, 5 * 4, vertexBuffer)

            val texHandle = GLES20.glGetAttribLocation(program, "vTexCoord")
            GLES20.glEnableVertexAttribArray(texHandle)
            vertexBuffer?.position(3)
            GLES20.glVertexAttribPointer(texHandle, 2, GLES20.GL_FLOAT, false, 5 * 4, vertexBuffer)

            GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
            GLES20.glUniform1i(GLES20.glGetUniformLocation(program, "uTexture"), 0)

            GLES20.glDrawElements(GLES20.GL_TRIANGLES, numIndices, GLES20.GL_UNSIGNED_SHORT, indexBuffer)
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES20.glViewport(0, 0, width, height)
            val ratio = width.toFloat() / height
            Matrix.perspectiveM(projectionMatrix, 0, 45f, ratio, 0.1f, 100f)
        }

        private fun loadTexture(context: Context, resId: Int): Int {
            val textureHandle = IntArray(1)
            GLES20.glGenTextures(1, textureHandle, 0)
            if (textureHandle[0] != 0) {
                val options = BitmapFactory.Options().apply { inScaled = false }
                val bitmap = BitmapFactory.decodeResource(context.resources, resId, options)
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0])
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST)
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST)
                GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
                bitmap.recycle()
            }
            return textureHandle[0]
        }

        private fun loadProgram(vSource: String, fSource: String): Int {
            val vs = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER).also { GLES20.glShaderSource(it, vSource); GLES20.glCompileShader(it) }
            val fs = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER).also { GLES20.glShaderSource(it, fSource); GLES20.glCompileShader(it) }
            return GLES20.glCreateProgram().also { GLES20.glAttachShader(it, vs); GLES20.glAttachShader(it, fs); GLES20.glLinkProgram(it) }
        }
    }
}
