package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemLongClickListener {

    LinkedList<Pet> animais;
    RadioGroup botoes;
    EditText edRaca, edNome, edIdade;
    Switch swVacinado;
    ListView listaAnimais;
    ArrayAdapter adapter;
    Pet edicao = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animais = new LinkedList<>();
        botoes = (RadioGroup) findViewById(R.id.grupo_tipo);
        edRaca = (EditText) findViewById(R.id.ed_raca);
        edNome = (EditText) findViewById(R.id.ed_nome);
        edIdade = (EditText) findViewById(R.id.ed_idade);
        swVacinado = (Switch) findViewById(R.id.sw_vacinado);
        listaAnimais = (ListView) findViewById(R.id.lista_animais);
        adapter = new ArrayAdapter( this,
                android.R.layout.simple_list_item_multiple_choice,
                animais);
        listaAnimais.setAdapter( adapter );
        listaAnimais.setChoiceMode( AbsListView.CHOICE_MODE_MULTIPLE );
        listaAnimais.setOnItemLongClickListener( this );
    }

    public void confirmar( View v) {
        Pet pet = (edicao != null) ? edicao : new Pet();
        switch( botoes.getCheckedRadioButtonId() ) {
            case R.id.rb_gato: pet.setTipo('G'); break;
            case R.id.rb_cao:  pet.setTipo('C'); break;
            case R.id.rb_passaro: pet.setTipo('P'); break;
        }
        pet.setRaca( edRaca.getText().toString() );
        pet.setNome( edNome.getText().toString() );
        pet.setIdade( Integer.parseInt(edIdade.getText().toString()) );
        pet.setVacinado( swVacinado.isChecked() );
        if (edicao == null) {
            animais.add(pet);
        }
        adapter.notifyDataSetChanged();
        edRaca.setText("");
        edNome.setText("");
        edIdade.setText("");
        swVacinado.setChecked(false);
        edicao = null; // terminando a edicao
    }

    public void remover(View v) {
        if (listaAnimais.getChoiceMode() == AbsListView.CHOICE_MODE_SINGLE) {
            int pos = listaAnimais.getCheckedItemPosition();
            if (pos >= 0) {
                animais.remove(pos);
                adapter.notifyDataSetChanged();
                listaAnimais.clearChoices();
            } else {
                Toast.makeText(this, R.string.msg_sem_selecao_unica,
                        Toast.LENGTH_LONG).show();
            }
        } else {
            if ( listaAnimais.getCheckedItemCount() > 0) {
                SparseBooleanArray sels = listaAnimais.getCheckedItemPositions();
                LinkedList<Pet> selecionados = new LinkedList<>();
                for (int i = 0; i < animais.size(); i++) {
                    if (sels.get(i)) {
                        selecionados.add( animais.get(i) );
                    }
                }
                animais.removeAll( selecionados );
                adapter.notifyDataSetChanged();
                listaAnimais.clearChoices();
            } else {
                Toast.makeText(this, R.string.msg_sem_selecao_multipla,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView,
                                   View view, int pos, long id) {
        edicao = animais.get( pos );
        switch( edicao.getTipo() ) {
            case 'G': botoes.check( R.id.rb_gato); break;
            case 'C': botoes.check( R.id.rb_cao); break;
            case 'P': botoes.check( R.id.rb_passaro); break;
        }
        edNome.setText( edicao.getNome() );

        edRaca.setText( edicao.getRaca() );
        swVacinado.setChecked( edicao.isVacinado() );
        edIdade.setText( String.valueOf( edicao.getIdade() ) );
        return true;
    }
}