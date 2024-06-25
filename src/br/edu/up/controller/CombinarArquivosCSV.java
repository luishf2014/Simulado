package br.edu.up.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CombinarArquivosCSV {

    public static void main(String[] args) {
        String arquivoPessoa = "src/br/edu/up/DAO/Pessoa.CSV";
        String arquivoEndereco = "src/br/edu/up/DAO/Endereco.CSV";
        String arquivoPessoaComEndereco = "src/br/edu/up/DAO/PessoaComEndereco.CSV";

        try {
            BufferedReader leitorPessoa = new BufferedReader(new FileReader(arquivoPessoa));
            BufferedReader leitorEndereco = new BufferedReader(new FileReader(arquivoEndereco));
            FileWriter escritor = new FileWriter(arquivoPessoaComEndereco);

            String linhaPessoa;
            String linhaEndereco;

            // Lê os cabeçalhos de ambos os arquivos
            String cabecalhoPessoa = leitorPessoa.readLine();
            String cabecalhoEndereco = leitorEndereco.readLine();

            // Escreve o cabeçalho combinado no novo arquivo
            escritor.write("codigo;nome;rua;cidade;\n");

            // Cria um mapa para associar nomes aos códigos
            Map<String, String> mapaCodigoParaNome = new HashMap<>();

            // Lê as linhas de dados do arquivo Pessoa.CSV e preenche o mapa
            while ((linhaPessoa = leitorPessoa.readLine()) != null) {
                String[] dadosPessoa = linhaPessoa.split(";");
                String codigo = dadosPessoa[0];
                String nome = dadosPessoa[1];
                mapaCodigoParaNome.put(codigo, nome);
            }

            // Lê as linhas de dados do arquivo Endereco.CSV e combina-os
            while ((linhaEndereco = leitorEndereco.readLine()) != null) {
                String[] dadosEndereco = linhaEndereco.split(";");
                String codigo = dadosEndereco[2]; // Assumindo que a coluna "codigo" está no índice 2
                String rua = dadosEndereco[0];
                String cidade = dadosEndereco[1];
                String nome = mapaCodigoParaNome.get(codigo);

                // Escreve os dados combinados no novo arquivo
                escritor.write(codigo + ";" + nome + ";" + rua + ";" + cidade + "\n");
            }

            leitorPessoa.close();
            leitorEndereco.close();
            escritor.close();

            System.out.println("Arquivo PessoaComEndereco.CSV criado com sucesso!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}