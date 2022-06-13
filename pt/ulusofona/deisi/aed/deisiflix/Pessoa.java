package pt.ulusofona.deisi.aed.deisiflix;

public class Pessoa {
    int idPessoa;
    String nome;
    char generoPessoa;


    public Pessoa(int idPessoa, String nome, char generoPessoa) {
        this.idPessoa = idPessoa;
        this.nome = nome;
        this.generoPessoa = generoPessoa;
    }

    @Override
    public String toString() {
        return idPessoa + " | " + nome + " | " + generoPessoa;
    }
}
