package com.example.ezsupercombo

import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity



import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment

class DirSelect : DialogFragment(R.layout.fragment_dirselect)
{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var radioGroup = view.findViewById<RadioGroup>(R.id.rgOptions)
        val btnConfirm : Button = view.findViewById(R.id.btnConfirm)
        val main  : MainActivity = activity as MainActivity

        val rbOverview : RadioButton = view.findViewById(R.id.rbOverview)
        val rbMatchups : RadioButton = view.findViewById(R.id.rbMatchups)
        val rbResources : RadioButton = view.findViewById(R.id.rbResources)

        rbOverview.isChecked = true

        if(main.getGame() == "Street Fighter V")
        {
            rbMatchups.visibility = View.GONE
            rbResources.visibility = View.GONE
        }

        btnConfirm.setOnClickListener{
            val selected = radioGroup.checkedRadioButtonId

            var selectedButton : RadioButton = view.findViewById(selected)
            main.receiveDirectory(selectedButton.text.toString())
            dismiss()
        }

    }
}