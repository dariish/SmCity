package ipvc.estg.smcity

import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ipvc.estg.smcity.api.EndPoints
import ipvc.estg.smcity.api.Ocorrencia
import ipvc.estg.smcity.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var ocorrencias: List<Ocorrencia>

    //Last Known Location
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Iniciar o fusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getOcorrencias()
        var posicao : LatLng

        call.enqueue(object : Callback<List<Ocorrencia>> {
            override fun onResponse(call : Call<List<Ocorrencia>>, response: Response<List<Ocorrencia>>) {
                if (response.isSuccessful) {
                    // Guardo o response body do OutputLogin da variável c
                    ocorrencias = response.body()!!
                    for(ocorrencia in ocorrencias){
                        posicao = LatLng(ocorrencia.latitude.toString().toDouble(), ocorrencia.longitude.toString().toDouble())
                        mMap.addMarker(MarkerOptions().position(posicao).title(ocorrencia.descricao))
                    }
                }
            }

            override fun onFailure(call: Call<List<Ocorrencia>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        setUpMap()
        // Add a marker in Sydney and move the camera
      //  val sydney = LatLng(-34.0, 151.0)
       // mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    fun setUpMap(){
        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 11)

            return
        }else{
            mMap.isMyLocationEnabled = true

            fusedLocationClient.lastLocation.addOnSuccessListener(this) {location ->
                if(location != null){
                    lastLocation = location
                    Toast.makeText(this@MapsActivity, lastLocation.toString(), Toast.LENGTH_SHORT).show()
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                }
            }
        }
    }
}