package pt.ulusofona.deisi.aed.deisiflix;

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
            QueryResult result = new QueryResult(String.valueOf(nMovies), System.currentTimeMillis() - tInicial);

            return new QueryResult(String.valueOf(nMovies), System.currentTimeMillis() - tInicial);
        }
        return new QueryResult("0", System.currentTimeMillis() - tInicial);
    }

    static QueryResult getMoviesActorYear(String pergunta) {
        QueryResult demo = new QueryResult();
        demo.valor = "";
        demo.tempo = 1000;
        return demo;
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
            filme.actores.remove(idARemover);
        }
        return new QueryResult("OK");

    }


}
