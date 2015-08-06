package br.edu.unoesc.shoplist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.EditTextPreference;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;

import br.edu.unoesc.shoplist.adapter.UsuarioAdapter;
import br.edu.unoesc.shoplist.helper.DatabaseHelper;
import br.edu.unoesc.shoplist.model.Usuario;

public class UsuarioActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtLatitude;
    private EditText edtLongitude;
    private ImageView imgFoto;
    private Button btnFoto;
    private Button btnMapa;
    private Button btnSalvar;
    private DatabaseHelper dbHelper;
    private UsuarioAdapter usuarioAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtLatitude = (EditText) findViewById(R.id.edtLatitude);
        edtLongitude = (EditText) findViewById(R.id.edtLongitude);
        imgFoto = (ImageView) findViewById(R.id.imgFoto);
        btnFoto = (Button) findViewById(R.id.btnFoto);
        btnMapa = (Button) findViewById(R.id.btnMapa);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);

        btnFoto.setOnClickListener(this);
        btnMapa.setOnClickListener(this);
        btnSalvar.setOnClickListener(this);

        dbHelper = new DatabaseHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_usuario, menu);
        getUserImage();

        try {
            usuarioAdapter = new UsuarioAdapter(this, R.layout.activity_usuario, dbHelper.getDao(Usuario.class).queryForAll());
            usuarioAdapter.setNotifyOnChange(true);
            usuarioAdapter.notifyDataSetChanged();
        } catch (SQLException ex) {
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnFoto: {
                Intent itCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (itCamera.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(itCamera, 100);
                }
                break;
            }

            case R.id.btnMapa: {
                Intent itMapa = new Intent(this, MapaActivity.class);
                startActivityForResult(itMapa, 110);
                getUserImage();
                break;
            }

            case R.id.btnSalvar: {

                String nome = edtNome.getText().toString();
                String email = edtEmail.getText().toString();
                String latitude = edtLatitude.getText().toString();
                String longitude = edtLongitude.getText().toString();

                Usuario u = new Usuario(nome, email, latitude, longitude);
                try {

                    dbHelper.getDao(Usuario.class).create(u);
                    Toast.makeText(this, "Registro salvo com sucesso", Toast.LENGTH_SHORT).show();

                } catch (SQLException ex) {
                }

                break;
            }

        }
    }

    private void getUserImage() {
        File arquivoFoto = new File(Environment.getExternalStorageDirectory() + File.separator + "foto.jpg");
        if (arquivoFoto.exists()) {
            Bitmap foto = (Bitmap) BitmapFactory.decodeFile(arquivoFoto.getAbsolutePath());
            imgFoto.setImageBitmap(foto);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case 100: {
                    Bitmap foto = (Bitmap) data.getExtras().get("data");

                    imgFoto.setImageBitmap(foto);

                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    foto.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
                    File arquivoFoto = new File(Environment.getExternalStorageDirectory() + File.separator + "foto.jpg");
                    try {
                        arquivoFoto.createNewFile();
                        FileOutputStream fo = new FileOutputStream(arquivoFoto.getAbsolutePath());
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (Exception ex) {
                    }
                    break;

                }

                case 110: {
                    Double latitude = data.getDoubleExtra("latitude", 0.0);
                    Double longitude = data.getDoubleExtra("longitude", 0.0);

                    edtLatitude.setText(latitude.toString());
                    edtLongitude.setText(longitude.toString());

                    break;

                }
            }
        }

    }
}
