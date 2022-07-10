package pt.ulusofona.deisi.aed.deisiflix;

import java.util.ArrayList;
import java.util.Comparator;

public class QueryFunctions {
    static QueryResult countMoviesActor(String argumento) {
        long tInicial = System.currentTimeMillis();
        short nMovies = 0;
        for (Filme filme : Main.Filmes.values()) {
            for (Pessoa actor : filme.actores.values()) {
                if (actor.nome.equals(argumento)) {
                    nMovies++;
                }
            }
        }
        if (nMovies > 0) {
            return new QueryResult(String.valueOf(nMovies), System.currentTimeMillis() - tInicial);
        }
        return new QueryResult("0", System.currentTimeMillis() - tInicial);
    }

    static QueryResult getMoviesActorYear(String pergunta) {
        long tInicial = System.currentTimeMillis();
        ArrayList<Filme> filmesDesseAno = new ArrayList<>();
        ArrayList<Filme> filmesComActorAno = new ArrayList<>();
        String[] dados = pergunta.split(" ");
        int ano = Integer.parseInt(dados[dados.length - 1]);
        StringBuilder actor = new StringBuilder();
        for (int i = 0; i < dados.length - 1; i++) {
            actor.append(dados[i]).append(" ");
        }
        actor = new StringBuilder(actor.toString().trim());
        int idActor = 0;
        for (Filme filme : Main.Filmes.values()) {
            for (Pessoa pessoa : filme.actores.values()) {
                if (pessoa.nome.equals(actor.toString())) {
                    idActor = pessoa.idPessoa;
                    break; //Se é encontrado sai do for
                }
            }
            if (idActor != 0) {
                break; //Se idActor foi atribuido actor ja foi encontrado
            }
        }
        for (Filme filme : Main.Filmes.values()) {
            if (filme.dataLancamento.getYear() == ano) {
                filmesDesseAno.add(filme);
            }
        }
        for (Filme filme : filmesDesseAno) {
            if (filme.actores.containsKey(idActor)) {
                filmesComActorAno.add(filme);
            }
        }
        filmesComActorAno.sort(Comparator.comparing((Filme filme) -> filme.dataLancamento).reversed());
        StringBuilder concatenacao = new StringBuilder();
        for (int i = 0; i < filmesComActorAno.size(); i++) {
            if (i < filmesComActorAno.size() - 1) {
                concatenacao.append(filmesComActorAno.get(i).titulo).append(" ").append("(")
                        .append(filmesComActorAno.get(i).dataLancamento)
                        .append(")").append("\n");
            } else {
                concatenacao.append(filmesComActorAno.get(i).titulo)
                        .append(" ").append("(").append(filmesComActorAno.get(i).dataLancamento).append(")");
            }
        }
        String resultado = concatenacao.toString();
        return new QueryResult(resultado, System.currentTimeMillis() - tInicial);
    }

    static QueryResult countMoviesWithActors(String pergunta) {
        QueryResult demo = new QueryResult();
        demo.valor = "";
        demo.tempo = 1000;
        return demo;
    }

    static QueryResult countActors3Years(String pergunta) {
        QueryResult demo = new QueryResult();
        demo.valor = "";
        demo.tempo = 1000;
        return demo;
    }

    static QueryResult topMovieWithgenderBias(String pergunta) {
        QueryResult demo = new QueryResult();
        demo.valor = "";
        demo.tempo = 1000;
        return demo;
    }

    static QueryResult insertActor(String argumento) {
        String[] parcelas = argumento.split(";", 5);
        int idActor = Integer.parseInt(parcelas[0]);
        String nome = parcelas[1];
        char generoActor = parcelas[2].charAt(0);
        int idFilme = Integer.parseInt(parcelas[3]);
        if (!Main.Filmes.containsKey(idFilme)) {
            return new QueryResult("Erro");
        }
        if (!Main.idActores.contains(idActor)) {
            Main.idActores.add(idActor);
        }
        for (Filme filme : Main.Filmes.values()) {
            if (!filme.actores.containsKey(idActor)) {
                filme.actores.put(idActor, new Pessoa(idActor, nome, generoActor));
                if (generoActor == 'M') {
                    filme.nActores++;
                } else if (generoActor == 'F') {
                    filme.nActrizes++;
                }
                return new QueryResult("OK");
            }
        }
        return new QueryResult("Erro");
    }


    static QueryResult removeActor(String remover) {
        int idARemover = Integer.parseInt(remover);
        if (!Main.idActores.contains(idARemover)) {
            return new QueryResult("Erro");
        }
        for (int i = 0; i < Main.idActores.size(); i++) {
            //  Main.idActores.removeIf(id -> (id == idARemover));
            if (Main.idActores.get(i) == idARemover) {
                Main.idActores.remove(i);
                i--;
                //WORKAROUND de iterator, sem i-- next element is skipped
            }
        }
        for (Filme filme : Main.Filmes.values()) {
            if (filme.actores.containsKey(idARemover)) {
                char genero = filme.actores.get(idARemover).generoPessoa;
                if (genero == 'M') {
                    filme.nActores--;
                } else if (genero == 'F') {
                    filme.nActrizes--;
                }
                filme.actores.remove(idARemover);
            }
        }
        return new QueryResult("OK");

    }
}
