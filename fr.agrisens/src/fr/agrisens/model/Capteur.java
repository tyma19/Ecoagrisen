package fr.agrisens.model;

import java.time.LocalDate;

/**
 * Classe Entite (POJO) representant un capteur connecte
 * correspondant a la table SQL "capteurs".
 */
public class Capteur {

    private int id;
    private String codeCapteur;
    private String typeCapteur;
    private double valeurMesuree;
    private int niveauBatterie;
    private LocalDate dateInstallation;

    // Constructeur vide
    public Capteur() {
    }

    // Constructeur sans id (utilise avant insertion en base, id auto-genere)
    public Capteur(String codeCapteur, String typeCapteur, double valeurMesuree,
                   int niveauBatterie, LocalDate dateInstallation) {
        this.codeCapteur = codeCapteur;
        this.typeCapteur = typeCapteur;
        this.valeurMesuree = valeurMesuree;
        this.niveauBatterie = niveauBatterie;
        this.dateInstallation = dateInstallation;
    }

    // Constructeur complet (avec id, utilise notamment lors de la lecture depuis la base)
    public Capteur(int id, String codeCapteur, String typeCapteur, double valeurMesuree,
                   int niveauBatterie, LocalDate dateInstallation) {
        this.id = id;
        this.codeCapteur = codeCapteur;
        this.typeCapteur = typeCapteur;
        this.valeurMesuree = valeurMesuree;
        this.niveauBatterie = niveauBatterie;
        this.dateInstallation = dateInstallation;
    }

    // ----- Getters / Setters -----

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeCapteur() {
        return codeCapteur;
    }

    public void setCodeCapteur(String codeCapteur) {
        this.codeCapteur = codeCapteur;
    }

    public String getTypeCapteur() {
        return typeCapteur;
    }

    public void setTypeCapteur(String typeCapteur) {
        this.typeCapteur = typeCapteur;
    }

    public double getValeurMesuree() {
        return valeurMesuree;
    }

    public void setValeurMesuree(double valeurMesuree) {
        this.valeurMesuree = valeurMesuree;
    }

    public int getNiveauBatterie() {
        return niveauBatterie;
    }

    public void setNiveauBatterie(int niveauBatterie) {
        this.niveauBatterie = niveauBatterie;
    }

    public LocalDate getDateInstallation() {
        return dateInstallation;
    }

    public void setDateInstallation(LocalDate dateInstallation) {
        this.dateInstallation = dateInstallation;
    }

    @Override
    public String toString() {
        return "Capteur{" +
                "id=" + id +
                ", codeCapteur='" + codeCapteur + '\'' +
                ", typeCapteur='" + typeCapteur + '\'' +
                ", valeurMesuree=" + valeurMesuree +
                ", niveauBatterie=" + niveauBatterie +
                ", dateInstallation=" + dateInstallation +
                '}';
    }
}