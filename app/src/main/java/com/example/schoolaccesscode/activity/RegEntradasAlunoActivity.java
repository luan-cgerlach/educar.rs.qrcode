package com.example.schoolaccesscode.activity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolaccesscode.R;
import com.example.schoolaccesscode.entity.RegistroAluno;
import com.example.schoolaccesscode.repository.AlunoRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RegEntradasAlunoActivity extends AppCompatActivity {

    private TextView tvRegistroEntradasAluno;
    private RegistroAluno alunoLog;
    private StringBuilder stringBuilderRegistro;
    private AlunoRepository alunoRepository;
    private List<RegistroAluno> listaDeRegistros;
    private LocalDateTime dataHoraAtual;
    private DateTimeFormatter formatoBrasileiro;

    private void inicializarVariaveis() {
        tvRegistroEntradasAluno = findViewById(R.id.tvRegistroEntradasAluno);
        tvRegistroEntradasAluno.setMovementMethod(new ScrollingMovementMethod());
        stringBuilderRegistro = new StringBuilder();
        alunoRepository = new AlunoRepository(this);
        listaDeRegistros = new ArrayList<>();
        alunoLog = new RegistroAluno();
        dataHoraAtual = LocalDateTime.now();
        formatoBrasileiro = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_entradas_aluno);
        inicializarVariaveis();

        preencherRegistroDeEntradas();
    }

    private void preencherRegistroDeEntradas() {
        listaDeRegistros = alunoRepository.buscarTodosRegistrosDeEntrada();

        for (RegistroAluno registro : listaDeRegistros) {
            stringBuilderRegistro.append("\n" + registro.toString() + "\n");
        }
        tvRegistroEntradasAluno.append(stringBuilderRegistro);
    }
}
