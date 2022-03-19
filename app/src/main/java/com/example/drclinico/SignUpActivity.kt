package com.example.drclinico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_sign_up.*
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.speciality_bottom_sheet.*


class SignUpActivity : AppCompatActivity() {

    lateinit var rootNode: FirebaseDatabase
    lateinit var database: DatabaseReference
    var UserData: UserHelperClass? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        rb_patient.isChecked = true

        rb_doctor.setOnClickListener {
            rb_patient.isChecked = false
            sl_country.visibility = View.VISIBLE
            country.visibility = View.VISIBLE
            qualification.visibility = View.VISIBLE
            pmdc_number.visibility = View.VISIBLE
            speciality.visibility = View.VISIBLE
            sl_gender.visibility = View.VISIBLE
            select_gender.visibility = View.VISIBLE
        }
        rb_patient.setOnClickListener {
            rb_doctor.isChecked = false
            sl_country.visibility = View.GONE
            country.visibility = View.GONE
            qualification.visibility = View.GONE
            pmdc_number.visibility = View.GONE
            speciality.visibility = View.GONE
            sl_gender.visibility = View.GONE
            select_gender.visibility = View.GONE
        }

        speciality.setOnClickListener {

            val bottomView = layoutInflater.inflate(R.layout.speciality_bottom_sheet, null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(bottomView)
            dialog.show()
            dialog.done_btn.setOnClickListener {
                dialog.dismiss()
            }
        }
        register_btn.setOnClickListener {
            val name = user_name.text.toString()
            val phoneNumber = phone_number.text.toString().toDouble()
            val country = country.text.toString()
            val qualification = qualification.text.toString()
            val pmdcNumber = pmdc_number.text.toString()
            val gender = select_gender.text.toString()



            database = FirebaseDatabase.getInstance().getReference("User")
            val user = UserHelperClass(name,phoneNumber,country,qualification,qualification,pmdcNumber,gender)
            database.child(name).setValue(user).addOnSuccessListener {
                Toast.makeText(this, "Submit", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        val suggestions = arrayOf("Pakistan", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, suggestions)
        country.setAdapter(adapter)
        val gender = arrayOf("Male", "Female")
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, gender)
        select_gender.setAdapter(genderAdapter)
    }
}