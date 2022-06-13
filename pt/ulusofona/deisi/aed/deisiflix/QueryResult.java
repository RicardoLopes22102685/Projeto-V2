package pt.ulusofona.deisi.aed.deisiflix;

public class QueryResult {
    String valor;
    long tempo;

    public QueryResult() {
    }

    public QueryResult(String valor) {
        this.valor = valor;
    }

    public QueryResult(String valor, long tempo) {
        this.valor = valor;
        this.tempo = tempo;
    }


}