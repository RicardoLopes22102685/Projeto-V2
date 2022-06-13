package pt.ulusofona.deisi.aed.deisiflix;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Funcoes {
    static void readMovies() {
        Main.FilmesValidos = new ArrayList<>();
        Main.linhasIgnoradasFilmes = new ArrayList<>();
        Main.Filmes = new HashMap<>();

        try {
            FileReader fr = new FileReader("deisi_movies.txt");
            BufferedReader reader = new BufferedReader(fr);

            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 5) {
                    Filme novoFilme = new Filme();
                    novoFilme.idFilme = Integer.parseInt(dados[0].replace(" ", ""));
                    novoFilme.titulo = dados[1];
                    novoFilme.orcamento = Integer.parseInt(dados[3].replace(" ", ""));
                    String[] dLancamento = dados[4].split("-");
                    novoFilme.dataLancamento = LocalDate.parse(dLancamento[2] + "-"
                            + dLancamento[1] + "-" + dLancamento[0]);
                    novoFilme.actores = new HashMap<>();
                    novoFilme.realizadores = new HashMap<>();
                    novoFilme.generoCinematografico = new HashSet<>();
                    Main.FilmesValidos.add(novoFilme);
                    Main.Filmes.put(novoFilme.idFilme, novoFilme);
                } else {
                    Main.linhasIgnoradasFilmes.add(linha);
                }

            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Erro: Não foi possível ler arquivo deisi_movies.txt");
        }
    }

    static void readMovieVotes() {
        Main.linhasIgnoradasVotes = new ArrayList<>();

        try {
            FileReader fr = new FileReader("deisi_movie_votes.txt");
            BufferedReader reader = new BufferedReader(fr);

            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 3) {
                    int filmeID = Integer.parseInt(dados[0].replace(" ", ""));
                    float mVotos = Float.parseFloat(dados[1].replace(" ", ""));
                    int nVotos = Integer.parseInt(dados[2].replace(" ", ""));
                    if (Main.Filmes.containsKey(filmeID)) {
                        Filme aModificar = Main.Filmes.get(filmeID);
                        aModificar.mediaVotos = mVotos;
                        aModificar.nVotos = nVotos;
                        Main.Filmes.put(filmeID, aModificar);
                    } else {
                        Main.linhasIgnoradasVotes.add(linha);
                    }
                } else {
                    Main.linhasIgnoradasVotes.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro: Não foi possível ler arquivo deisi_movie_votes.txt");
        }
    }

    static void readPeople() {
        //TODO Falta ignorar duplicados

        Main.linhasIgnoradasPeople = new ArrayList<>();
        try {
            FileReader fr = new FileReader("deisi_people.txt");
            BufferedReader reader = new BufferedReader(fr);

            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 5) {
                    String tipoPessoa = dados[0].replace(" ", "");
                    int idPessoa = Integer.parseInt(dados[1].replace(" ", ""));
                    String nome = dados[2];
                    char genero = dados[3].charAt(0);
                    int filmeID = Integer.parseInt(dados[4].replace(" ", ""));

                    if (Main.Filmes.containsKey(filmeID)) {
                        Pessoa aAdicionar = new Pessoa(idPessoa, nome, genero);
                        Filme aModificar = Main.Filmes.get(filmeID);
                        if (tipoPessoa.equals("ACTOR")) {
                            aModificar.actores.put(idPessoa, aAdicionar);
                            switch (genero) {
                                case 'M':
                                    aModificar.nActores++;
                                    break;
                                case 'F':
                                    aModificar.nActrizes++;
                                    break;
                            }
                        } else {
                            aModificar.realizadores.put(idPessoa, aAdicionar);
                            aModificar.nRealizadores++;
                        }

                        Main.Filmes.put(filmeID, aModificar);
                    } else {
                        Main.linhasIgnoradasPeople.add(linha); //Se filmeID não constar em Filmes, então linha igmorada
                    }
                } else {
                    Main.linhasIgnoradasPeople.add(linha); //Se linha tiver ± parametros então ignora.
                }
            }
        } catch (IOException e) {
            System.out.println("Erro: Não foi possível ler arquivo deisi_people.txt");
        }
    }

    static void readGenre() {
        Main.linhasIgnoradasGenre = new ArrayList<>();
        try {
            FileReader fr = new FileReader("deisi_genres.txt");
            BufferedReader reader = new BufferedReader(fr);

            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 2) {
                    String genero = dados[0].replace(" ", "");
                    int filmeID = Integer.parseInt(dados[1].replace(" ", ""));
                    if (Main.Filmes.containsKey(filmeID)) {
                        Filme aModificar = Main.Filmes.get(filmeID);
                        aModificar.generoCinematografico.add(genero);
                        Main.Filmes.get(filmeID).nGeneros = aModificar.generoCinematografico.size();
                    } else {
                        Main.linhasIgnoradasGenre.add(linha);
                    }
                } else {
                    Main.linhasIgnoradasGenre.add(linha);
                }

            }
        } catch (IOException e) {
            System.out.println("Erro: Não foi possível ler arquivo deisi_genres.txt");
        }
    }

  /*void contaGeneros() {
        // TODO Não está a contar bem
        short mGeneroActores;
        short fGeneroActores;
        short mGeneroRealizadores;
        short fGeneroRealizadores;
        for (Integer i : Main.Filmes.keySet()) {
            Filme aModificar = Main.Filmes.get(i);
            mGeneroActores = 0;
            fGeneroActores = 0;
            mGeneroRealizadores = 0;
            fGeneroRealizadores = 0;
            for (Pessoa k : aModificar.actores.values()) {
                switch (k.generoPessoa) {
                    case 'M':
                        mGeneroActores++;
                    case 'F':
                        fGeneroActores++;
                }

            }
            for (Pessoa j : aModificar.realizadores.values()) {
                switch (j.generoPessoa) {
                    case 'M':
                        mGeneroRealizadores++;
                    case 'F':
                        fGeneroRealizadores++;
                }
            }
            if (mGeneroActores + fGeneroActores == 2) {// Verifica os nGeneros correspondentes
                aModificar.nGeneros = 2;
            } else {
                aModificar.nGeneros = 1;
            }
            Main.Filmes.put(i, aModificar);
        }
    } */
}