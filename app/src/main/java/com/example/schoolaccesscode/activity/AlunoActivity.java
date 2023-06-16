package com.example.schoolaccesscode.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolaccesscode.R;
import com.example.schoolaccesscode.entity.Aluno;
import com.example.schoolaccesscode.repository.AlunoRepository;

public class AlunoActivity extends AppCompatActivity {
    private AlunoRepository alunoRepository;
    private Aluno aluno;
    private TextView tvLabelMatricula;
    private TextView novoMatricula;
    private TextView tvLabelNome;
    private TextView novoNome;
    private TextView tvLabelAno;
    private TextView novoAno;
    private TextView tvLabelTurno;
    private TextView novoTurno;
    private TextView tvLabelNomeDoPai;
    private TextView novoNomeDoPai;
    private TextView tvLabelNomeDaMae;
    private TextView novoNomeDaMae;
    private Button bSalvar;

    private void inicializarVariaveis() {
        aluno = new Aluno();
        tvLabelMatricula = findViewById(R.id.tvNovoMatricula);
        novoMatricula = findViewById(R.id.novoMatricula);
        tvLabelNome = findViewById(R.id.tvNome);
        novoNome = findViewById(R.id.novoNome);
        tvLabelAno = findViewById(R.id.tvAno);
        novoAno = findViewById(R.id.novoAno);
        tvLabelTurno = findViewById(R.id.tvTurno);
        novoTurno = findViewById(R.id.novoTurno);
        tvLabelNomeDoPai = findViewById(R.id.tvNomeDoPai);
        novoNomeDoPai = findViewById(R.id.novoNomeDoPai);
        tvLabelNomeDaMae = findViewById(R.id.tvNomeDaMae);
        novoNomeDaMae = findViewById(R.id.novoNomeDaMae);
        bSalvar = findViewById(R.id.bSalvar);
        alunoRepository = new AlunoRepository(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);
        inicializarVariaveis();
        preencherLabels();
        Intent intent = getIntent();
        preencherCampos(intent);

        bSalvar.setOnClickListener(view -> {
            alunoRepository.deletarAluno(obterDadosPreenchidos().getMatricula());
            if (alunoRepository.inserirAluno(obterDadosPreenchidos()) == -1) {
                Toast.makeText(AlunoActivity.this, "Não foi possível salvar o aluno", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AlunoActivity.this, "Aluno salvo com sucesso", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }

    private void preencherLabels() {
        tvLabelMatricula.setText("Matrícula:");
        tvLabelNome.setText("Nome:");
        tvLabelAno.setText("Ano:");
        tvLabelTurno.setText("Turno:");
        tvLabelNomeDoPai.setText("Nome do Pai:");
        tvLabelNomeDaMae.setText("Nome da Mãe:");
        bSalvar.setVisibility(View.VISIBLE);
    }

    private void preencherCampos(Intent intent) {
        Aluno alunoParaEditar = intent.getParcelableExtra("aluno");
        if (alunoParaEditar == null) {
            limparCampos();
        } else {
            preencherComDadosDoAluno(alunoParaEditar);
        }
    }

    private void preencherComDadosDoAluno(Aluno aluno) {
        novoMatricula.setText(aluno.getMatricula());
        novoNome.setText(aluno.getNome());
        novoAno.setText(aluno.getAno());
        novoTurno.setText(aluno.getTurno());
        novoNomeDoPai.setText(aluno.getNomeDoPai());
        novoNomeDaMae.setText(aluno.getNomeDaMae());
    }

    private void limparCampos() {
        novoMatricula.setText("");
        novoNome.setText("");
        novoAno.setText("");
        novoTurno.setText("");
        novoNomeDoPai.setText("");
        novoNomeDaMae.setText("");
    }

    private void receberIntent(Intent intent) {
        aluno = intent.getParcelableExtra("aluno");
        preencherComDadosDoAluno(aluno);
    }

    private Aluno obterDadosPreenchidos() {
        aluno.setMatricula(novoMatricula.getText().toString());
        aluno.setNome(novoNome.getText().toString());
        aluno.setAno(novoAno.getText().toString());
        aluno.setTurno(novoTurno.getText().toString());
        aluno.setNomeDaMae(novoNomeDaMae.getText().toString());
        aluno.setNomeDoPai(novoNomeDoPai.getText().toString());
        aluno.setNumeroDeEntradas(0);
        return aluno;
    }

    public void cadastrarTodosAlunos() {
        Aluno aluno1 = new Aluno("5387948", "Eduarda Welter Braun", "3B", "Manhã", "Ademir Braun", "Rozane Welter", 0);
        Aluno aluno2 = new Aluno("5557234", "Estéfhany Regina Theisen Bernardi", "3B", "Manhã", "Mauro Bernardi", "Marisa Theisen", 0);
        Aluno aluno3 = new Aluno("5557235", "Gean Gustavo Kreuz", "3B", "Manhã", "Celso José Kreuz", "Marlise Henz Kreuz", 0);
        Aluno aluno4 = new Aluno("5557239", "Kaiane Thaís Steffens Albino da Rosa", "3B", "Manhã", "Não fornecido", "Andréia Ines Steffens", 0);
        Aluno aluno5 = new Aluno("5557243", "Luiza Eduarda Jung", "3A", "Manhã", "Luis Carlos Jung", "Vânia Maria Brummelhaus", 0);
        Aluno aluno6 = new Aluno("5557252", "Tauhan Arcanjo Babichuck", "3B", "Manhã", "Eduardo Babichuck", "Elisa Helena Mallmann", 0);
        Aluno aluno7 = new Aluno("3207620", "Vinicius Arenhart Kotz", "3A", "Manhã", "Pedro Dionísio Kotz", "Elveni Maria Arenhart", 0);
        Aluno aluno8 = new Aluno("5557261", "Eduardo Grun Deves", "3B", "Manhã", "Marino Antônio Deves", "Sueli Grün", 0);
        Aluno aluno9 = new Aluno("5557241", "Luan Carlos Gerlach", "3B", "Manhã", "Luis Carlos Gerlach", "Joseneia Maria Welter Gerlach", 0);

        alunoRepository.inserirAluno(aluno1);
        alunoRepository.inserirAluno(aluno2);
        alunoRepository.inserirAluno(aluno3);
        alunoRepository.inserirAluno(aluno4);
        alunoRepository.inserirAluno(aluno5);
        alunoRepository.inserirAluno(aluno6);
        alunoRepository.inserirAluno(aluno7);
        alunoRepository.inserirAluno(aluno8);
        alunoRepository.inserirAluno(aluno9);
    }
}
