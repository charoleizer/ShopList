package br.edu.unoesc.shoplist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import br.edu.unoesc.shoplist.R;
import br.edu.unoesc.shoplist.model.Usuario;

/**
 * Created by Microsoft on 03/08/2015.
 */
public class UsuarioAdapter extends ArrayAdapter<Usuario> {

    public UsuarioAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public UsuarioAdapter(Context context, int resource, List<Usuario> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_usuario, null);
        }

        Usuario u = getItem(position);

        if (u != null) {
            EditText edtNome = (EditText) v.findViewById(R.id.edtNome);
            EditText edtEmail = (EditText) v.findViewById(R.id.edtEmail);
            EditText edtLatitude = (EditText) v.findViewById(R.id.edtLatitude);
            EditText edtLongitude = (EditText) v.findViewById(R.id.edtLongitude);

            if (edtNome != null && u.getNome() != null) {
                edtNome.setText(u.getNome());
            }

            if (edtEmail != null && u.getEmail() != null) {
                edtEmail.setText(u.getEmail());
            }

            if (edtLatitude != null && u.getLatitude() != null) {
                edtLatitude.setText(u.getLatitude());
            }

            if (edtLongitude != null && u.getLongitude() != null) {
                edtLongitude.setText(u.getLongitude());
            }
        }

        return v;
    }

}
