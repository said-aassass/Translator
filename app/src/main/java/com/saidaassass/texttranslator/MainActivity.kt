package com.saidaassass.texttranslator

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.saidaassass.texttranslator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var items= arrayOf("English","French","Italian","Spanish","German","Arabic")
    private var conditions = DownloadConditions.Builder()
        .requireWifi()
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)

        val itemsAdapter:ArrayAdapter<String> =ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line, items)

        binding.languageFrom.setAdapter(itemsAdapter)

        binding.languageTo.setAdapter(itemsAdapter)

        binding.translate.setOnClickListener {

            val options = TranslatorOptions.Builder()
                .setSourceLanguage(selectFrom())
                .setTargetLanguage(selectTo())
                .build()

            val englishGermanTranslator = Translation.getClient(options)

            englishGermanTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener {

                    englishGermanTranslator.translate(binding.input.text.toString())
                        .addOnSuccessListener { translatedText ->

                            binding.output.text=translatedText

                        }
                        .addOnFailureListener { exception ->

                            Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()

                        }


                }
                .addOnFailureListener { exception ->

                    Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()

                }


        }




    }

    private fun selectFrom(): String {

        return when(binding.languageFrom.text.toString()){

            ""-> TranslateLanguage.ENGLISH

            "English"->TranslateLanguage.ENGLISH

            "French"->TranslateLanguage.FRENCH

            "Italian"->TranslateLanguage.ITALIAN

            "Spanish"->TranslateLanguage.SPANISH

            "German"->TranslateLanguage.GERMAN

            "Arabic"->TranslateLanguage.ARABIC

            else->TranslateLanguage.ENGLISH

        }


    }

    private fun selectTo(): String {

        return when(binding.languageTo.text.toString()){

            ""-> TranslateLanguage.FRENCH

            "English"->TranslateLanguage.ENGLISH

            "French"->TranslateLanguage.FRENCH

            "Italian"->TranslateLanguage.ITALIAN

            "Spanish"->TranslateLanguage.SPANISH

            "German"->TranslateLanguage.GERMAN

            "Arabic"->TranslateLanguage.ARABIC

            else->TranslateLanguage.FRENCH

        }


    }

}

