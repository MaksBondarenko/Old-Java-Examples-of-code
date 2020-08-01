package com.example.prm_02

import android.Manifest
import android.app.*
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.*
import android.location.Geocoder
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ThemedSpinnerAdapter
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    val PERMISSION_REQ_CODE = 12
    lateinit var photoFile: File
    private val STORAGE_PERMISSION_CODE = 101
    val geoc by lazy { Geocoder(this) }
    val locMan by lazy { LocationServices.getFusedLocationProviderClient(this) }
    lateinit var text : String
    var latitude = 0.0
    var longitude = 0.0
    val db by lazy { DbAccess.getInstance(applicationContext).db }
    var textColor = Color.GREEN
    var photoTextSize = 45f
    var notificationRadius = 1000f

    val geofences by lazy { LocationServices.getGeofencingClient(this) }
    val geofenceList: MutableList<Geofence> = ArrayList()





    private lateinit var adapter: MyAdapter
    lateinit var listView: ListView

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locRes: LocationResult?) {
            locRes?.locations?.get(0)?.let {
                val addr = geoc.getFromLocation(it.latitude, it.longitude, 1)[0]
                text = addr.countryName + ", " + addr.locality
                latitude = addr.latitude
                longitude = addr.longitude
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val channel = NotificationChannel("com.example.prm_02.channel.NOTI_CHANNEL", "My channel", NotificationManager.IMPORTANCE_DEFAULT)
        NotificationManagerCompat.from(this).createNotificationChannel(channel)
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 0
            )
        }
        checkPermissionReadWrite(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            STORAGE_PERMISSION_CODE
        )
        checkLocPerm()
        listView = photoList
        adapter = MyAdapter(this)
        listView.setAdapter(adapter)

        readToAdapter()

        listView.onItemClickListener =
            OnItemClickListener { arg, view, position, arg3 ->
                val photo: Photo = adapter.getItem(position) as Photo
                val dlgAlert: AlertDialog.Builder = AlertDialog.Builder(this)
                if(adapter.getItem(position).desc.equals(""))
                    dlgAlert.setMessage("Opis nie by dodany")
                else
                    dlgAlert.setMessage(adapter.getItem(position).desc)
                dlgAlert.setTitle("Opis")
                dlgAlert.setPositiveButton("OK", null)
                dlgAlert.setCancelable(true)
                dlgAlert.create().show()
            }
    }

    fun openCameraOnClick(view: View) {
        reqLocation()
        var timeStamp =  SimpleDateFormat("yyyyMMdd_HHmmss").format( Date())
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).let { takePictureIntent ->
            photoFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .resolve("img_"+timeStamp+".jpg").also { it.createNewFile() }
            val uri = FileProvider.getUriForFile(this,"com.example.prm_02.fileprovider",photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            var fileWriter = FileWriter(photoFile)
            fileWriter.flush()
            fileWriter.close()
            startActivityForResult(takePictureIntent, 0)
        }
    }

    fun openSettingsOnClick(view: View) {
        var intent = Intent(this, OptionsActivity::class.java)
        startActivityForResult(intent, 2)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            val intent = Intent(this, AddDescActivity::class.java)
            startActivityForResult(intent,1)
        }
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val date = sdf.format(Date())
            val bmp = addLoc(photoFile,date.toString())
            val desc = data?.getStringExtra("desc")
            val note = desc?.let { Note(0,photoFile.absolutePath,date.toString(), latitude,longitude,it) }
            thread {
                if (note != null) {
                    db.notes().insert(note)
                }
            }
            registerGeof(latitude,longitude, photoFile)
            adapter.addItem(Photo(latitude, longitude, desc, bmp))
            adapter.notifyDataSetChanged()
        }
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            textColor = data?.getIntExtra("color",Color.GREEN)!!
            photoTextSize = data?.getFloatExtra("size",45f)!!
            readToAdapter()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun reqLocation() {
        val req = LocationRequest.create().apply {
            interval = 1000
            numUpdates = 1
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        locMan.requestLocationUpdates(req, locationCallback, null)
    }
    fun checkPermissionReadWrite(permission: String,requestCode: Int) { // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(this@MainActivity,permission)
            == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat
                .requestPermissions(
                    this@MainActivity, arrayOf(permission),
                    requestCode
                )
        } else {
            Toast.makeText(
                    this@MainActivity,
                    "Permission already granted",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQ_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //reqLocation()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    fun addLoc(file : File, photoDate : String) : Bitmap{
        val image = BitmapFactory.decodeFile(file.absolutePath)
        val bmp = image.copy(Bitmap.Config.ARGB_8888, true)

        Canvas(bmp).apply {
            drawText(photoDate+"\n"+text, 100f, 1200f, Paint().apply {
                color = textColor
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                textSize = photoTextSize
            })
        }
        return bmp
    }
    fun readToAdapter(){
        geofenceList.clear()
        var initPhotos : MutableList<Photo> = ArrayList<Photo>()
        var directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        var files = directory.listFiles()
        thread {
            val notes = db.notes().getAll()
            runOnUiThread {
                for (note : Note in notes)
                {
                    for (photo : File in files)
                    {
                        if(photo.absolutePath.equals(note.photoId)){
                            var tmpPhoto = Photo()
                            val addr = geoc.getFromLocation(note.latitude, note.longitude, 1)[0]
                            text = addr.countryName + ", " + addr.locality
                            tmpPhoto.bitmap = addLoc(photo,note.photoDate)
                            tmpPhoto.desc = note.content
                            tmpPhoto.latitude = note.latitude
                            tmpPhoto.longitude = note.longitude
                            initPhotos.add(tmpPhoto)
                            registerGeof(note.latitude,note.longitude, photo)
                        }
                    }
                }
                adapter.setPhotoList(initPhotos)
                adapter.notifyDataSetChanged()
            }
        }
    }
    fun checkLocPerm() : Boolean{

        val permissionArray = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        else
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        val hasBgPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        this.checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
        else true

        val hasLocationFinePremission =
        this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
        return (hasBgPermission && hasLocationFinePremission).also {
            if (!it) {
                this.requestPermissions(
                    permissionArray,
                    PERMISSION_REQ_CODE
                )
            }
        }
    }
    private fun registerGeof(latitude : Double, longitude : Double, file : File){
        geofenceList.add(Geofence.Builder()
            .setRequestId(file.absolutePath)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setCircularRegion(
                latitude,
                longitude,
                notificationRadius
            )
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .build())


        val geoReq = GeofencingRequest.Builder()
            .addGeofences(geofenceList)
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .build()
        var intent = Intent(this, MyReceiver::class.java)
        intent.putExtra("radius", notificationRadius.toString())
        intent.putExtra("path", file.absolutePath)
        val pi = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        geofences.addGeofences(geoReq, pi)
    }
}
