package pt.ulusofona.deisi.aed.deisiflix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static HashMap<Integer, Filme> Filmes;  //HashMap global para poder ser acedido globalmente pelo programa
    static ArrayList<Filme> FilmesValidos; //Array de Filmes válidos
    static ArrayList<Integer> idActores;
    static ArrayList<String> linhasIgnoradasFilmes;
    static ArrayList<String> linhasIgnoradasVotes;
    static ArrayList<String> linhasIgnoradasPeople;
    static ArrayList<String> linhasIgnoradasGenre;

    public static void lerFicheiros() throws IOException {
        //TODO: Por IOExcpetion a funcionar
        LerFicheirosFunctions.readMovies(); // criar Dic com ID para guardar return de readMovies
        LerFicheirosFunctions.readMovieVotes(); //lê Votos e adiciona a Filmes
        LerFicheirosFunctions.readPeople();
        LerFicheirosFunctions.readGenre();
    }

    public static ArrayList<Filme> getFilmes() {
        return FilmesValidos;
    }

    public static ArrayList<String> getLinhasIgnoradas(String filename) {
        switch (filename) {
            case "deisi_movies.txt":
                return linhasIgnoradasFilmes;
            case "deisi_movie_votes.txt":
                return linhasIgnoradasVotes;
            case "deisi_people.txt":
                return linhasIgnoradasPeople;
            case "deisi_genres.txt":
                return linhasIgnoradasGenre;
            default:
                return null;
        }
    }

    public static String getVideoURL() {
        return null;
    }

    public static String getCreativeQuery() {
        return null;
    }

    public static QueryResult perguntar(String pergunta) {
        String[] dados = pergunta.split(" ", 2);
        String codigoPergunta = dados[0];
        String argumento = dados[1];
        switch (codigoPergunta) {
            case "COUNT_MOVIES_ACTOR":
                return QueryFunctions.countMoviesActor(argumento);

            case "GET_MOVIES_ACTOR_YEAR":
                return QueryFunctions.getMoviesActorYear(argumento);

            case "COUNT_MOVIES_WITH_ACTORS":
                return QueryFunctions.countMoviesWithActors(argumento);

            case "COUNT_ACTORS_3_YEARS":
                return QueryFunctions.countActors3Years(argumento);

            case "TOP_MOVIES_WITH_GENDER_BIAS":
                return QueryFunctions.topMovieWithgenderBias(argumento);

            case "INSERT_ACTOR":
                return QueryFunctions.insertActor(argumento);

            case "REMOVE_ACTOR":
                return QueryFunctions.removeActor(argumento);

            default:
                QueryResult invalida = new QueryResult();
                invalida.valor = "Pergunta desconhecida. Tente novamente.";
                return invalida;

        }


    }

    public static void main(String[] args) throws IOException {
        lerFicheiros();
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        while (input != null && !input.equals("QUIT")) {
            QueryResult result = perguntar(input);
            if (!result.valor.equals("")) {
                System.out.println(result.valor);
            }
            String[] query = input.split(" ");
            if (!(query[0].equals("INSERT_ACTOR") || query[0].equals("REMOVE_ACTOR"))) {
                System.out.println(" (demorou " + result.tempo + "ms)");
            }
            input = in.nextLine();
        }

        // System.out.println(getLinhasIgnoradas("deisi_people.txt"));
        // System.out.println(perguntar("COUNT_MOVIES_ACTOR Davison Clark"));


        /* for (Integer i : Filmes.keySet()) { //itera o dicionário
            System.out.println("ID: " + i + "   Filme:" + Filmes.get(i));
        }*/

    }
}
