package ru.mirea.shumikhin.cryptoloader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.example.cryptoloader.databinding.ActivityMainBinding
import java.security.InvalidParameterException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<String?> {
    val TAG = this.javaClass.simpleName
    private val LoaderID = 1234
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.btnSend.setOnClickListener {
            val bundle = Bundle()
            val text = binding.etText.text.toString()
            val key = generateKey()
            bundle.putByteArray(MyLoader.ARG_WORD, encryptMsg(text, key))
            bundle.putByteArray("key", key.encoded)
            LoaderManager.getInstance(this).initLoader(LoaderID, bundle, this)
        }
        setContentView(binding.root)
    }

    override fun onLoaderReset(loader: Loader<String?>) {
        Log.d(TAG, "onLoaderReset")
    }

    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<String?> {
        if (i == LoaderID) {
            Toast.makeText(this, "onCreateLoader:$i", Toast.LENGTH_SHORT).show()
            return MyLoader(this, bundle)
        }
        throw InvalidParameterException("Invalid loader id")
    }

    override fun onLoadFinished(loader: Loader<String?>, s: String?) {
        if (loader.getId() === LoaderID) {
            Log.d(TAG, "onLoadFinished: $s")
            Toast.makeText(this, "onLoadFinished: $s", Toast.LENGTH_SHORT).show()
        }
    }
    private fun generateKey(): SecretKey {
        return try {
            val sr: SecureRandom = SecureRandom.getInstance("SHA1PRNG")
            sr.setSeed("any data used as random seed".toByteArray())
            val kg: KeyGenerator = KeyGenerator.getInstance("AES")
            kg.init(256, sr)
            SecretKeySpec(kg.generateKey().encoded, "AES")
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
    }
    private fun encryptMsg(message: String, secret: SecretKey): ByteArray? {
        var cipher: Cipher? = null
        cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secret)
        return cipher.doFinal(message.toByteArray())
    }
}