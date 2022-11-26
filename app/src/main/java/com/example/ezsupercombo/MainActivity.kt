package com.example.ezsupercombo

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*


class MainActivity : AppCompatActivity() {

    private var game : String = "Street Fighter V"; //Default game
    //game rosters
    private val rosterSFV = arrayOf("Abigail","Akira","Akuma","Alex","Balrog","Birdie","Blanka","Cammy","Nash","Chun-Li","Cody","Dan","Dhalsim","E.Honda","Ed","F.A.N.G","Falke","G","Gill","Guile","Ibuki","Juri","Kage","Karin","Ken","Kolin","Laura","Lucia","Luke","M.Bison","Menat","Necalli","Oro","Poison","R.Mika","Rashid","Rose","Ryu","Sagat","Sakura","Seth","Urien","Vega","Zangief","Zeku")
    private val rosterUSFIV = arrayOf("Abel","Adon","Akuma","Balrog","Blanka","C. Viper","Cammy","Chun-Li","Cody","Dan","Decapre","Deejay","Dhalsim","Dudley","E. Honda","El Fuerte","Elena","Evil Ryu","Fei Long","Gen","Gouken","Guile","Guy","Hakan","Hugo","Ibuki","Juri","Ken","M. Bison","Makoto","Oni","Poison","Rolento","Rose","Rufus","Ryu","Sagat","Sakura","Seth","T. Hawk","Vega","Yang","Yun","Zangief")
    private val rosterSFIII = arrayOf("Akuma","Alex","Chun-Li","Dudley","Elena","Hugo","Ibuki","Ken","Makoto","Necro","Oro","Q","Remy","Ryu","Sean","Twelve","Urien","Yang","Yun")
    //Pattern for easier array handling in adapters
    private var selGameRoster : Array<String> = rosterSFV

    private val baseURL = "https://wiki.supercombo.gg/w/" //All URLs start with this
    private var link : String = ""

  //  private var lock : Boolean = false //prevents DialogDragment popup on startup

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var selectedChar : String = ""
        val URLIntent : Intent = Intent(Intent.ACTION_VIEW)

        //Widget handlers
        val btnGo : Button = findViewById(R.id.btnGo)
        val btnChangeDir : Button = findViewById(R.id.btnChangeDir)
        val spSel : Spinner = findViewById(R.id.spSelectChar)
        val tvLink : TextView = findViewById(R.id.tvLinkPreview)

        spSel.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,rosterSFV)

        spSel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                selectedChar = selGameRoster[p2]

                link = """$baseURL$game/${selectedChar}""".replace(" ","_")
                if(game == "Street Fighter 3: 3rd Strike")
                    link += "/2021"
                tvLink.text = link
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                return
            }


        }
        btnChangeDir.setOnClickListener{
            link = """$baseURL$game/${selectedChar}""".replace(" ","_")
            DirSelect().show(supportFragmentManager,"Directory Select")
        }

        btnGo.setOnClickListener {
            URLIntent.data = Uri.parse(link)
            startActivity(URLIntent)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_gameselect, menu)
        return super.onCreateOptionsMenu(menu)

    }

    fun getGame(): String {
        return game
    }
    fun receiveDirectory(dir : String?)
    {
        if((dir == "Resources" || dir == "Matchups") && game != "Street Fighter 3: 3rd Strike") {
            link = link
            Toast.makeText(applicationContext,"$dir page not available for this game." +
                    "Try the discord!",
            Toast.LENGTH_SHORT).show()
        }

        else if(dir != "Overview")
            link += "/$dir"

        if(dir == "Overview" && game == "Street Fighter 3: 3rd Strike")
            link += "/2021"


        val tvLink : TextView = findViewById(R.id.tvLinkPreview)
        tvLink.text = link
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val sp : Spinner = findViewById(R.id.spSelectChar)
        val btnSubpage : Button = findViewById(R.id.btnChangeDir);

        when(item.itemId){

            R.id.SFV -> {
                game = "Street Fighter V"
                selGameRoster = rosterSFV
                btnSubpage.visibility = View.VISIBLE
            }

            R.id.USFIV -> {
                game = "Ultra Street Fighter IV"
                sp.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,rosterUSFIV)
                selGameRoster = rosterUSFIV
                btnSubpage.visibility = View.GONE
            }

            R.id.SFIII -> {
                game = "Street Fighter 3: 3rd Strike"
                selGameRoster = rosterSFIII
                btnSubpage.visibility = View.VISIBLE
            }

            R.id.exit ->
            {
                System.exit(0)
            }
        }

        sp.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,selGameRoster)
        link = "https://wiki.supercombo.gg/w/"
        return true;
    }
}