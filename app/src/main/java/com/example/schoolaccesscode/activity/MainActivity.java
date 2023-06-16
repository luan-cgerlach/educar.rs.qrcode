package com.example.schoolaccesscode.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.schoolaccesscode.R;
import com.example.schoolaccesscode.entity.Aluno;
import com.example.schoolaccesscode.entity.RegistroAluno;
import com.example.schoolaccesscode.repository.AlunoRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {
    private static final int QR_CODE_REQUEST_CODE = 1;
    private Aluno aluno;
    private AlunoRepository alunoRepository;
    private TextView tvMatricula;
    private TextView matricula;
    private TextView tvNome;
    private TextView nome;
    private TextView tvAno;
    private TextView ano;
    private TextView tvTurno;
    private TextView turno;
    private TextView tvNomeDoPai;
    private TextView nomeDoPai;
    private TextView tvNomeDaMae;
    private TextView nomeDaMae;
    private TextView tvEditarAluno;
    private TextView tvExcluirAluno;
    private Button bScanner;
    private Button bAcesso;
    DateTimeFormatter formatoBrasileiro;

    private void inicializarVariaveis() {
        bScanner = findViewById(R.id.bScanner);
        tvMatricula = findViewById(R.id.tvMatricula);
        matricula = findViewById(R.id.matricula);
        tvNome = findViewById(R.id.tvNome);
        nome = findViewById(R.id.nome);
        tvAno = findViewById(R.id.tvAno);
        ano = findViewById(R.id.ano);
        tvTurno = findViewById(R.id.tvTurno);
        turno = findViewById(R.id.turno);
        tvNomeDoPai = findViewById(R.id.tvNomeDoPai);
        nomeDoPai = findViewById(R.id.nomeDoPai);
        tvNomeDaMae = findViewById(R.id.tvNomeDaMae);
        nomeDaMae = findViewById(R.id.nomeDaMae);
        bAcesso = findViewById(R.id.bSalvar);
        tvEditarAluno = findViewById(R.id.tvEditarAluno);
        tvExcluirAluno = findViewById(R.id.tvExcluirAluno);
        aluno = new Aluno();
        formatoBrasileiro = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        alunoRepository = new AlunoRepository(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarVariaveis();
        bAcesso.setVisibility(View.INVISIBLE);
        tvEditarAluno.setVisibility(View.INVISIBLE);
        tvExcluirAluno.setVisibility(View.INVISIBLE);
        bScanner.setOnClickListener(view -> {
            limparTela();
            Intent qrScannerIntent = new Intent(this, QRScannerActivity.class);
            startActivityForResult(qrScannerIntent, QR_CODE_REQUEST_CODE);
        });

        bAcesso.setOnClickListener(view -> {
            limparTela();
        });

        tvEditarAluno.setOnClickListener(View -> {
            editarAluno(buscarAlunoPorMatricula(matricula.getText().toString()));
        });

        tvExcluirAluno.setOnClickListener(View -> {
            alunoRepository.deletarAluno(matricula.getText().toString());
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == QR_CODE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String dadosQrCode = data.getStringExtra("qr_code_result");
            String numeroMatricula = dadosQrCode.substring(dadosQrCode.length() - 7, dadosQrCode.length());

            Aluno alunoPorId = alunoRepository.buscarAlunoPorId(numeroMatricula);

            if (alunoPorId == null) {
                Toast.makeText(MainActivity.this, "Aluno não cadastrado!!!", Toast.LENGTH_LONG).show();
            } else {
                preencherDadosDoAluno(alunoPorId);
                criarRegistroDeEntrada();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void criarRegistroDeEntrada() {
        aluno = buscarAlunoPorMatricula(matricula.getText().toString());
        Integer numeroDeEntradas = verificarSeAlunoEntrouOuSaiu(aluno);

        RegistroAluno alunoLog = new RegistroAluno(aluno.getNome(), LocalDateTime.now().withNano(0).format(formatoBrasileiro), numeroDeEntradas);

        alunoRepository.inserirRegistroDeEntradaDoAluno(alunoLog);
    }

    private Integer verificarSeAlunoEntrouOuSaiu(Aluno aluno) {
        aluno.setNumeroDeEntradas(aluno.getNumeroDeEntradas() + 1);
        alunoRepository.atualizarAluno(aluno);
        return aluno.getNumeroDeEntradas() % 2 == 0 ? 1 : 0; // retorna 1 se verdadeiro e 0 se falso
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_aluno) {
            Intent intent = new Intent(this, AlunoActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.edit_aluno) {
            receberMatriculaParaExcluirOuEditar("Editar");
            return true;
        } else if (id == R.id.remov_aluno) {
            receberMatriculaParaExcluirOuEditar("Excluir");
            return true;
        } else if (id == R.id.list_aluno) {
            Intent intent = new Intent(this, ListaAlunosActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.log_aluno) {
            Intent intent = new Intent(this, RegEntradasAlunoActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void receberMatriculaParaExcluirOuEditar(String excluirOuEditar) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Digite a matricula do aluno");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String matricula = input.getText().toString();

                // Usa a string `excluirOuEditar` para ver se deve excluir ou editar
                if (excluirOuEditar.equals("Excluir")) {
                    excluirAluno(buscarAlunoPorMatricula(matricula));
                } else {
                    editarAluno(buscarAlunoPorMatricula(matricula));
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void editarAluno(Aluno aluno) {
        Intent intent = new Intent(this, AlunoActivity.class);
        intent.putExtra("aluno", aluno);
        startActivity(intent);
    }

    public void excluirAluno(Aluno aluno) {
        if (aluno != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmação de exclusão");
            builder.setMessage("Deseja excluir o aluno " + aluno.getNome() + "?");
            builder.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        alunoRepository.deletarAluno(aluno.getMatricula());
                        Toast.makeText(getApplicationContext(), "Aluno excluído com sucesso", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Erro ao excluir aluno", Toast.LENGTH_SHORT).show();
                        Log.e("Exclusão de Aluno", e.getMessage());
                    }
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Toast.makeText(getApplicationContext(), "Aluno não encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private Aluno buscarAlunoPorMatricula(String matricula) {
        if (alunoRepository.buscarAlunoPorId(matricula) == null) {
            return null;
        } else {
            return alunoRepository.buscarAlunoPorId(matricula);
        }
    }

    private void preencherDadosDoAluno(Aluno aluno) {
        tvMatricula.setText("Matrícula:");
        matricula.setText(aluno.getMatricula());
        tvNome.setText("Nome:");
        nome.setText(aluno.getNome());
        tvAno.setText("Ano:");
        ano.setText(aluno.getAno());
        tvTurno.setText("Turno:");
        turno.setText(aluno.getTurno());
        tvNomeDoPai.setText("Nome do Pai:");
        nomeDoPai.setText(aluno.getNomeDoPai());
        tvNomeDaMae.setText("Nome da Mãe:");
        nomeDaMae.setText(aluno.getNomeDaMae());
        bAcesso.setVisibility(View.VISIBLE);
        tvEditarAluno.setVisibility(View.VISIBLE);
        tvExcluirAluno.setVisibility(View.VISIBLE);
    }

    private void limparTela() {
        tvMatricula.setText("");
        matricula.setText("");
        tvNome.setText("");
        nome.setText("");
        tvAno.setText("");
        ano.setText("");
        tvTurno.setText("");
        turno.setText("");
        tvNomeDoPai.setText("");
        nomeDoPai.setText("");
        tvNomeDaMae.setText("");
        nomeDaMae.setText("");
        bAcesso.setVisibility(View.INVISIBLE);
        tvEditarAluno.setVisibility(View.INVISIBLE);
        tvExcluirAluno.setVisibility(View.INVISIBLE);
    }
}

