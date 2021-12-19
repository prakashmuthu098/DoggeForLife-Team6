package com.example.doggee

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PetDetaileActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_detaile)
        sessionManager = SessionManager(this)
        supportActionBar?.hide()


        var dogname = intent.getStringExtra("dname")
        var dogId = intent.getIntExtra("dId", 0)
        var dogImg = intent.getStringExtra("dImg")
        var dogVaccine = intent.getIntExtra("dVaccine", 0)
        var dogAge=intent.getIntExtra("dAge",0)

        val Pname=findViewById<TextView>(R.id.dname)
        var PImg=findViewById<ImageView>(R.id.dimg)
        val PVaccine=findViewById<TextView>(R.id.dvaccine)
        var Page=findViewById<TextView>(R.id.dage)
        val toolName=findViewById<TextView>(R.id.head)
        val interestBtn=findViewById<TextView>(R.id.interest)
        val items:MutableList<IntDetail> = mutableListOf<IntDetail>()
        var c:Boolean=true

        CoroutineScope(Dispatchers.IO).launch{
            var apiclient=application as ApiClient
            val service=apiclient.apiService
            var result=service.getMyInterest("Bearer ${sessionManager.fetchAuthToken()}")
            if(result.isSuccessful)
            {
                val pets=result.body()!!.petInterests
                if(pets.isNullOrEmpty())
                {
                    interestBtn.text="I'm Interested in "+dogname+"!"
                }
                else {
                    for (item in pets) {
                        if (item.petId == dogId) {
                             c=false
                            //findViewById<Button>(R.id.interest).isEnabled=false
                            findViewById<TextView>(R.id.interest).text = dogname+" Already added in your interest"
                            break
                        } else {
                            findViewById<TextView>(R.id.interest).text="I'm Interested in "+dogname+"!"
                        }
                    }
                }
            }else {
               // startActivity(intent)
            }

        }

        toolName.text=dogname

        Pname.text="Name : "+dogname
        if(dogVaccine==1)
        {
            PVaccine.text="Vaccinated : Yes"
        }
        else
            PVaccine.text="Vaccinated : No"
        if(dogAge==1)
        {
            Page.text="Age : "+dogAge.toString()+"year"
        }
        else
            Page.text="Age : "+dogAge.toString()+"years"

        Picasso.get().load(dogImg).into(PImg)

        val intId=DogId(dogId)
        interestBtn.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                val sampleApplication=application as ApiClient
                val service=sampleApplication.apiService
                service.addInterest(intId,"Bearer ${sessionManager.fetchAuthToken()}").enqueue(object :
                    Callback<Void?> {
                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                        if(response.isSuccessful)
                        {
                            Toast.makeText(applicationContext,"${dogname} added your interest", Toast.LENGTH_LONG).show()
                            val intent = Intent(this@PetDetaileActivity,PetListActivity::class.java)
                            startActivity(intent)

                        }
                        else
                        {
                            Toast.makeText(applicationContext,"Something went wrong", Toast.LENGTH_LONG).show()

                        }
                    }

                    override fun onFailure(call: Call<Void?>, t: Throwable) {
                        //  TODO("Not yet implemented")
                    }
                })
            }

        }

    }
}