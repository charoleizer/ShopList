package br.edu.unoesc.shoplist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.unoesc.shoplist.R;
import br.edu.unoesc.shoplist.model.Produto;

/**
 * Created by roberson.alves on 30/07/2015.
 */
public class ProdutosAdapter extends ArrayAdapter<Produto> {
    public ProdutosAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ProdutosAdapter(Context context, int resource, List<Produto> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.lista_produtos, null);
        }

        Produto p = getItem(position);

        if (p != null) {
            TextView txtCodigo = (TextView) v.findViewById(R.id.txtCodigo);
            TextView txtDescricao = (TextView) v.findViewById(R.id.txtDescricao);
            TextView txtQuantidade = (TextView) v.findViewById(R.id.txtQuantidade);

            if (txtCodigo != null) {
                txtCodigo.setText(p.getCodigo() + "");
            }

            if (txtDescricao != null && p.getQuantidade() != null) {
                txtDescricao.setText(p.getDescricao());
            }

            if (txtQuantidade != null) {
                txtQuantidade.setText(p.getQuantidade().toString());
            }
        }

        return v;
    }
}
