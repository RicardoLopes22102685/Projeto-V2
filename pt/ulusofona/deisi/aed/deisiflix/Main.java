package pt.ulusofona.deisi.aed.deisiflix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static HashMap<Integer, Filme> Filmes;  //HashMap global para poder ser acedido globalmente pelo programa
    static ArrayList<Filme> FilmesValidos; //Array de Filmes válidos
    static ArrayList<String> linhasIgnoradasFilmes;
    static ArrayList<String> linhasIgnoradasVotes;
    static ArrayList<String> linhasIgnoradasPeople;
    static ArrayList<String> linhasIgnoradasGenre;

    public static void lerFicheiros() throws IOException {
        //TODO: Por IOExcpetion a funcionar
        Funcoes.readMovies(); // criar Dic com ID para guardar return de readMovies
        Funcoes.readMovieVotes(); //lê Votos e adiciona a Filmes
        Funcoes.readPeople();
        Funcoes.readGenre();
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
        long tInicial = System.currentTimeMillis();
        String[] dados = pergunta.split(" ", 2);
        String codigoPergunta = dados[0];
        String argumento = dados[1];
        switch (codigoPergunta) {
            case "COUNT_MOVIES_ACTOR":
                return FuncoesQuery.countMoviesActor(argumento);

            case "GET_MOVIES_ACTOR_YEAR":
                return FuncoesQuery.getMoviesActorYear(argumento);

            case "COUNT_MOVIES_WITH_ACTORS":
                return FuncoesQuery.countMoviesWithActors(argumento);

            case "COUNT_ACTORS_3_YEARS":
                return FuncoesQuery.countActors3Years(argumento);

            case "TOP_MOVIES_WITH_GENDER_BIAS":
                return FuncoesQuery.topMovieWithgenderBias(argumento);

            case "INSERT_ACTOR":
                return FuncoesQuery.insertActor(argumento);

            case "REMOVE_ACTOR":
                return FuncoesQuery.removeActor(argumento);


            default:
                QueryResult invalida = new QueryResult();
                invalida.valor = "Pergunta desconhecida. Tente novamente.";
                invalida.tempo = System.currentTimeMillis()-tInicial;
                return invalida;

        }


    }

    public static void main(String[] args) throws IOException {
        lerFicheiros();
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        while (input !=null && !input.equals("QUIT")){
            System.out.println(perguntar(input));
            input = in.nextLine();
        }

        // System.out.println(getLinhasIgnoradas("deisi_people.txt"));
       // System.out.println(perguntar("COUNT_MOVIES_ACTOR Davison Clark"));


        /* for (Integer i : Filmes.keySet()) { //itera o dicionário
            System.out.println("ID: " + i + "   Filme:" + Filmes.get(i));
        }*/

    }
}
