package com.example.schoolaccesscode.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolaccesscode.R;
import com.example.schoolaccesscode.entity.Aluno;
import com.example.schoolaccesscode.repository.AlunoRepository;

import java.util.ArrayList;
import java.util.List;

public class ListaAlunosActivity extends AppCompatActivity {
    private TextView tvListaMatricula;
    private TextView tvListaNome;
    private TextView tvListaAno;
    private List<Aluno> listaAlunos;
    private AlunoRepository alunoRepository;
    private StringBuilder stringBuilderMatricula;
    private StringBuilder stringBuilderNome;
    private StringBuilder stringBuilderAno;

    private void inicializarVariaveis() {
        alunoRepository = new AlunoRepository(this);
        listaAlunos = new ArrayList<>();
        stringBuilderMatricula = new StringBuilder();
        stringBuilderNome = new StringBuilder();
        stringBuilderAno = new StringBuilder();
        tvListaMatricula = findViewById(R.id.tvListaMatricula);
        tvListaNome = findViewById(R.id.tvListaNome);
        tvListaAno = findViewById(R.id.tvListaAno);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        inicializarVariaveis();
        listaAlunos = alunoRepository.buscarTodosAlunos();

        criarCabecalhoDaTabela();
        for (Aluno aluno : listaAlunos) {
            stringBuilderMatricula.append(aluno.getMatricula() + "\n\n");
            stringBuilderNome.append(aluno.getNome() + "\n\n");
            stringBuilderAno.append(aluno.getAno() + "\n\n");
        }
        tvListaMatricula.append(stringBuilderMatricula);
        tvListaNome.append(stringBuilderNome);
        tvListaAno.append(stringBuilderAno);
    }

    private void criarCabecalhoDaTabela() {
        stringBuilderMatricula.append("Matrícula \n\n");
        stringBuilderNome.append("Nome \n\n");
        stringBuilderAno.append("Ano \n\n");
    }
}
