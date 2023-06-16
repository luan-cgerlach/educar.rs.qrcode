package com.example.schoolaccesscode.activity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolaccesscode.R;
import com.example.schoolaccesscode.entity.Aluno;
import com.example.schoolaccesscode.entity.AlunoLog;
import com.example.schoolaccesscode.repository.AlunoRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LogAlunoActivity extends AppCompatActivity {

    private TextView tvLogAluno;
    private AlunoLog alunoLog;
    private StringBuilder stringBuilderLog;
    private AlunoRepository alunoRepository;
    private List<AlunoLog> listaDeLog;
    private LocalDateTime dateTime;
    private DateTimeFormatter formatoBrasileiro;
    private void inicializarVariaveis() {
        tvLogAluno = findViewById(R.id.tvLogAluno);
        tvLogAluno.setMovementMethod(new ScrollingMovementMethod());
        stringBuilderLog = new StringBuilder();
        alunoRepository = new AlunoRepository(this);
        listaDeLog = new ArrayList<>();
        alunoLog = new AlunoLog();
        dateTime = LocalDateTime.now();
        formatoBrasileiro = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_aluno);
        inicializarVariaveis();

        preencherRegistroDeEntradas();
    }

    private void preencherRegistroDeEntradas(){
        listaDeLog = alunoRepository.queryAllLog();

        for (AlunoLog log : listaDeLog) {
            stringBuilderLog.append(log.toString() + "\n\n");
        }
        tvLogAluno.append(stringBuilderLog);
    }
}
