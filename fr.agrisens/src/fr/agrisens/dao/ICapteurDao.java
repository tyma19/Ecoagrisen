package fr.agrisens.dao;

import fr.agrisens.model.Capteur;
import java.util.List;

public interface ICapteurDao {

    void ajouterCapteur(Capteur c) throws Exception;

    List<Capteur> listerTous() throws Exception;
    Capteur trouverParId(int id) throws Exception;
    void mettreAJourBatterie(int id, int nouveauNiveau) throws Exception;
    void supprimerCapteur(int id) throws Exception;
}