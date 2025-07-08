package com.unl.practica2.base.controller.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.unl.practica2.base.controller.dao.dato_models.DaoArtista;
import com.unl.practica2.base.controller.dao.dato_models.DaoArtistaBanda;
import com.unl.practica2.base.controller.dao.dato_models.DaoBanda;
import com.unl.practica2.base.domain.models.Artista_Banda;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

@BrowserCallable
@AnonymousAllowed


public class ArtistaBandaServices {
    private DaoArtistaBanda db;
    public ArtistaBandaServices(){
        db = new DaoArtistaBanda();
    }
    public List<HashMap> listAll(){
        List<HashMap> lista = new ArrayList<>();
        if(!db.listAll().isEmpty()) {
            Artista_Banda [] arreglo = db.listAll().toArray();
            DaoArtista da = new DaoArtista();
            DaoBanda db = new DaoBanda();
            for(int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("id", arreglo[i].getId().toString(i));
                aux.put("rol", arreglo[i].getRol().toString());
                aux.put("artista", da.listAll().get(arreglo[i].getId_artista() -1).getNombres());
                aux.put("banda", db.listAll().get(arreglo[i].getId_banda() -1).getNombre());
                lista.add(aux);
            }
        }
        return lista;
    }
}
