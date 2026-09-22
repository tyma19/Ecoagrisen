package fr.agrisens.service;

import fr.agrisens.dao.CapteurDaoImpl;
import fr.agrisens.dao.ICapteurDao;
import fr.agrisens.model.Capteur;

import java.util.ArrayList;
import java.util.List;

/**
 * Couche metier : validation, filtres et calculs sur les capteurs.
 */
public class CapteurService {

    private final ICapteurDao capteurDao;

    public CapteurService() {
        this.capteurDao = new CapteurDaoImpl();
    }

    public List<Capteur> listerCapteursBatterieFaible(int seuilMin) throws Exception {
        List<Capteur> resultat = new ArrayList<>();
        List<Capteur> tous = capteurDao.listerTous();

        for (Capteur c : tous) {
            if (c.getNiveauBatterie() < seuilMin) {
                resultat.add(c);
            }
        }
        return resultat;
    }

    public double calculerMoyenneValeurParType(String typeCapteur) throws Exception {
        List<Capteur> tous = capteurDao.listerTous();

        double somme = 0;
        int compteur = 0;

        for (Capteur c : tous) {
            if (c.getTypeCapteur().equalsIgnoreCase(typeCapteur)) {
                somme += c.getValeurMesuree();
                compteur++;
            }
        }

        return compteur == 0 ? 0.0 : somme / compteur;
    }

    // Pour que Main puisse acceder aux operations CRUD via le service
    public ICapteurDao getCapteurDao() {
        return capteurDao;
    }
}