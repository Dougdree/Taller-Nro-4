package com.unl.practica2.base.controller.dao.dato_models;

import java.util.Date;

import com.unl.practica2.base.controller.dao.AdapterDao;
import com.unl.practica2.base.domain.models.Album;

public class DaoAlbum extends AdapterDao<Album> {
    private Album obj;

    public DaoAlbum() {
        super(Album.class);
    }

    public Album getObj() {
        if (obj == null)
            this.obj = new Album();
        return this.obj;
    }

    public void setObj(Album obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setId(listAll().getLength() + 1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        DaoAlbum da = new DaoAlbum();

        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Shawn");
        da.getObj().setFecha(new Date());
        da.getObj().setId_banda(1);
        if (da.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");

        da.setObj(null);
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Chase Atlantic");
        da.getObj().setFecha(new Date());
        da.getObj().setId_banda(2);
        if (da.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");

        da.setObj(null);
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Dangerous");
        da.getObj().setFecha(new Date());
        da.getObj().setId_banda(2);
        if (da.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");
    
        da.setObj(null);
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Lo Mejor de Vilma Palma");
        da.getObj().setFecha(new Date());
        da.getObj().setId_banda(2);
        if (da.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");       

        da.setObj(null);
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Tribalistas");
        da.getObj().setFecha(new Date());
        da.getObj().setId_banda(2);
        if (da.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");

    }
}
