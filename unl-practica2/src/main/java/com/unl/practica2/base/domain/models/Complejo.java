package com.unl.practica2.base.domain.models;

public class Complejo {
    private Float num;
    private Float ima;


    public Complejo(Float num, Float ima){
        this.num=num;
        this.ima=ima;
    }

    public Complejo(){
        this.num= 0f;
        this.ima=0f;
    }

    public Float getNum() {
        return this.num;
    }

    public Float getIma() {
        return this.ima;
    }

    public void setNum(Float num) {
        this.num=num;
    }

    public void setIma(Float ima) {
        this.ima=ima;
    }

}