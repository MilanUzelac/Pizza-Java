import java.io.Serializable;

public class Pica implements Serializable {
    private String velicina;
    private String vrsta;
    private String dodaci;
    private String nacinPlacanja;
    private String adresa;
    private String brojTelefona;
    private String napomena;


    public Pica(String velicina, String vrsta, String dodaci, String nacinPlacanja,String adresa,String brojTelefona,String napomena) {
        this.velicina = velicina;
        this.vrsta = vrsta;
        this.dodaci = dodaci;
        this.nacinPlacanja = nacinPlacanja;
        this.adresa=adresa;
        this.brojTelefona = brojTelefona;
        this.napomena = napomena;
    }

    public String getVelicina() {
        return velicina;
    }

    public void setVelicina(String velicina) {
        this.velicina = velicina;
    }

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public String  getDodaci() {
        return dodaci;
    }

    public void setDodaci(String dodaci) {
        this.dodaci = dodaci;
    }

    public String getNacinPlacanja() {
        return nacinPlacanja;
    }

    public void setNacinPlacanja(String nacinPlacanja) {
        this.nacinPlacanja = nacinPlacanja;
    }

    @Override
    public String toString() {
        return "Porudzbina: -> ( "+ velicina + ", " +  vrsta + ", " + dodaci + ", " + nacinPlacanja + ", " + adresa + ", " + brojTelefona + ", " + napomena + " )";
    }

}
