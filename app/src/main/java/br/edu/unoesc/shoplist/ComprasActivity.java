package br.edu.unoesc.shoplist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import br.edu.unoesc.shoplist.adapter.ProdutosAdapter;
import br.edu.unoesc.shoplist.adapter.UsuarioAdapter;
import br.edu.unoesc.shoplist.helper.DatabaseHelper;
import br.edu.unoesc.shoplist.model.Produto;
import br.edu.unoesc.shoplist.model.Usuario;


public class ComprasActivity extends ActionBarActivity implements View.OnClickListener {
    private EditText edtDescricao;
    private EditText edtMarca;
    private EditText edtQuantidade;
    private Spinner spnUnidadeMedida;
    private Button btnSalvar;
    private Button btnLimpar;
    private ListView lstCompras;

    private DatabaseHelper dbHelper;
    private ProdutosAdapter produtosAdapter;
    private TabHost tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras);
        inicializarAbas();
        inicializarView();

        dbHelper = new DatabaseHelper(this);

        // criando o adaptador para unidades de medida
        ArrayAdapter<String>
                adaptadorUnidade =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.unidades));

        // vinculamos o adaptador ao spinner
        spnUnidadeMedida.setAdapter(adaptadorUnidade);

        try {
            atualizarListaCompras();
        } catch (SQLException ex) {
        }

        registerForContextMenu(lstCompras);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getMenuInflater().inflate(R.menu.menu_lista_compras, menu);

    }

    private void atualizarListaCompras() throws SQLException {
        produtosAdapter = new ProdutosAdapter(this, R.layout.lista_produtos, dbHelper.getDao(Produto.class).queryForAll());
        lstCompras.setAdapter(produtosAdapter);
        produtosAdapter.setNotifyOnChange(true);
        produtosAdapter.notifyDataSetChanged();
    }

    private void inicializarView() {
        // vincular os componentes com os objetos de tela
        spnUnidadeMedida = (Spinner) findViewById(R.id.spnUnidadeMedida);
        edtDescricao = (EditText) findViewById(R.id.edtDescricao);
        edtMarca = (EditText) findViewById(R.id.edtMarca);
        edtQuantidade = (EditText) findViewById(R.id.edtQuantidade);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(this);
        btnLimpar = (Button) findViewById(R.id.btnLimpar);
        btnLimpar.setOnClickListener(this);
        lstCompras = (ListView) findViewById(R.id.lstCompras);
    }

    private void inicializarAbas() {
        // lidando com as abas da tela
        tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();

        // vinculando a aba 1
        TabHost.TabSpec spec = tabs.newTabSpec("tagCompras");
        spec.setContent(R.id.tabCompras);
        // descricao e icone da aba
        //spec.setIndicator(getString(R.string.tabCompras),
        // TODO: Conferir getDrawable para abas
        //      getResources().getDrawable(R.mipmap.ic_launcher));
        spec.setIndicator(getString(R.string.tabCompras));
        tabs.addTab(spec);

        // vinculando a aba 2
        spec = tabs.newTabSpec("tagCadastro");
        spec.setContent(R.id.tabCadastro);
        // descricao e icone da aba
        spec.setIndicator(getString(R.string.tabCadastro));
        tabs.addTab(spec);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compras, menu);
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.mnRemover: {
                Produto p = (Produto) produtosAdapter.getItem(info.position);


                try {

                    dbHelper.getDao(Produto.class).delete(p);
                    Toast.makeText(this, "Produto " + p.getCodigo() + " - " + p.getDescricao() + " foi removido.", Toast.LENGTH_SHORT).show();
                    atualizarListaCompras();

                }catch (SQLException ex){}


                break;
            }

            case R.id.mnRemoverTodos: {

                try {

                    dbHelper.getDao(Produto.class).delete(dbHelper.getDao(Produto.class).queryForAll());
                    Toast.makeText(this, "Todos os produtos foram removidos.", Toast.LENGTH_SHORT).show();
                    atualizarListaCompras();

                }catch (SQLException ex){}


                break;
            }
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mnSair) {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setTitle(R.string.mnSair);
            dialogo.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sair();
                }
            });
            dialogo.setNegativeButton("Não", null);
            dialogo.show();

        }

        if (id == R.id.mnUsuario) {
            Intent it = new Intent(this, UsuarioActivity.class);
            startActivity(it);
        }

        return super.onOptionsItemSelected(item);
    }

    private void sair() {
        // finalizar a Activity
        finish();
        // finalizar a app
        System.exit(0);
    }

    private View getTabIndicatorView(Context context, String tag, int drawable) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.tab_widget_custom, null);
        TextView tv = (TextView) view.findViewById(R.id.tabIndicatorText);
        tv.setText(tag);
        ImageView tabIndicatorIcon = (ImageView) view
                .findViewById(R.id.tabIndicatorIcon);
        tabIndicatorIcon.setBackgroundResource(drawable);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnSalvar: {

                //
                String descricao = edtDescricao.getText().toString();
                String marca = edtMarca.getText().toString();
                Double quantidade = new Double(edtQuantidade.getText().toString());
                String unidadeMedida = spnUnidadeMedida.getSelectedItem().toString();


                Produto p = new Produto(descricao, marca, quantidade, unidadeMedida);
                try {

                    dbHelper.getDao(Produto.class).create(p);
                    atualizarListaCompras();
                    limpar();
                    tabs.setCurrentTab(0);
                    Toast.makeText(this, "Produto salvo com sucesso", Toast.LENGTH_SHORT).show();

                } catch (SQLException ex) {
                }

                break;

            }

            case R.id.btnLimpar: {
                limpar();
            }

        }
    }

    private void limpar() {
        edtDescricao.setText("");
        edtMarca.setText("");
        edtQuantidade.setText("");
        spnUnidadeMedida.setSelection(0);
        edtDescricao.requestFocus();
    }


}
