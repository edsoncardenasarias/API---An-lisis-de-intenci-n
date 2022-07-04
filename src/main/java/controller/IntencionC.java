package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import modelo.model;
import service.Intencion;

@Named(value = "intencionC")
@SessionScoped
public class IntencionC implements Serializable {

    private model model;
    private Intencion service;

    public IntencionC() {
        model = new model();
        service = new Intencion();
    }

    public void generar(){
        try {
            Intencion.intento(model);
        } catch (Exception e) {
            System.out.println("error" + e.getMessage());
        }
 
    }
    public model getModel() {
        return model;
    }

    public void setModel(model model) {
        this.model = model;
    }

    public Intencion getService() {
        return service;
    }

    public void setService(Intencion service) {
        this.service = service;
    }

}
