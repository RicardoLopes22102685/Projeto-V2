package pt.ulusofona.deisi.aed.deisiflix;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

public class Filme {
    int idFilme;
    String titulo;
    HashMap<Integer, Pessoa> actores;
    HashMap<Integer,Pessoa> realizadores;
    HashSet<String> generoCinematografico;
    LocalDate dataLancamento;
    int orcamento;
    float mediaVotos;
    int nVotos;
    int nGeneros;
    short nRealizadores;
    short nActores;
    short nActrizes;


    @Override
    public String toString() {
        return idFilme + " | " + titulo + " | "
                + dataLancamento + " | "
                + nVotos + " | "
                + mediaVotos + " | " + nGeneros
                + " | " + nRealizadores + " | "
                + nActores + " | " + nActrizes;
    }
}