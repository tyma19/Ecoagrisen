package fr.agrisens.main;

import fr.agrisens.dao.ICapteurDao;
import fr.agrisens.model.Capteur;
import fr.agrisens.service.CapteurService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;


public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CapteurService service = new CapteurService();
    private static final ICapteurDao capteurDao = service.getCapteurDao();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        boolean continuer = true;

        while (continuer) {
            afficherMenu();
            int choix = lireEntier("Votre choix : ");

            try {
                switch (choix) {
                    case 1:
                        afficherListeCapteurs();
                        break;
                    case 2:
                        ajouterCapteur();
                        break;
                    case 3:
                        mettreAJourBatterie();
                        break;
                    case 4:
                        supprimerCapteur();
                        break;
                    case 5:
                        afficherCapteursBatterieFaible();
                        break;
                    case 6:
                        calculerMoyenneParType();
                        break;
                    case 7:
                        continuer = false;
                        System.out.println("Fermeture de l'application. A bientot !");
                        break;
                    default:
                        System.out.println("Choix invalide. Veuillez reessayer.");
                }
            } catch (Exception e) {
                System.out.println("Erreur : " + e.getMessage());
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void afficherMenu() {

        System.out.println("         ECOAGRISENS - GESTION CAPTEURS ");
        System.out.println("=========================================");
        System.out.println("1. Afficher la liste complete des capteurs");
        System.out.println("2. Ajouter un nouveau capteur connecte");
        System.out.println("3. Mettre a jour le niveau de batterie d'un capteur");
        System.out.println("4. Supprimer un capteur par son identifiant");
        System.out.println("5. Afficher la liste des capteurs a batterie faible (< 20%)");
        System.out.println("6. Calculer la moyenne des mesures pour un type de capteur");
        System.out.println("7. Quitter l'application");

    }

    private static void afficherListeCapteurs() throws Exception {
        List<Capteur> capteurs = capteurDao.listerTous();
        if (capteurs.isEmpty()) {
            System.out.println("Aucun capteur enregistre.");
            return;
        }
        System.out.println("--- Liste des capteurs ---");
        for (Capteur c : capteurs) {
            System.out.println(formatCapteur(c));
        }
    }

    private static void ajouterCapteur() throws Exception {
        System.out.print("Code capteur (ex: CAP-8091) : ");
        String code = scanner.nextLine();

        System.out.print("Type de capteur (HUMIDITE / TEMPERATURE / LUMINOSITE) : ");
        String type = scanner.nextLine();

        double valeur = lireDouble("Valeur mesuree : ");
        int batterie = lireEntier("Niveau de batterie (0-100) : ");

        LocalDate dateInstallation = lireDate("Date d'installation (jj/MM/aaaa) : ");

        Capteur c = new Capteur(code, type, valeur, batterie, dateInstallation);
        capteurDao.ajouterCapteur(c );
        System.out.println("Capteur ajoute avec succes.");
    }

    private static void mettreAJourBatterie() throws Exception {
        int id = lireEntier("ID du capteur : ");
        Capteur existant = capteurDao.trouverParId(id);
        if (existant == null) {
            System.out.println("Aucun capteur trouve avec l'ID " + id);
            return;
        }
        int nouveauNiveau = lireEntier("Nouveau niveau de batterie (0-100) : ");
        capteurDao.mettreAJourBatterie(id, nouveauNiveau);
        System.out.println("Niveau de batterie mis a jour avec succes.");
    }

    private static void supprimerCapteur() throws Exception {
        int id = lireEntier("ID du capteur a supprimer : ");
        Capteur existant = capteurDao.trouverParId(id);
        if (existant == null) {
            System.out.println("Aucun capteur trouve avec l'ID " + id);
            return;
        }
        capteurDao.supprimerCapteur(id);
        System.out.println("Capteur supprime avec succes.");
    }

    private static void afficherCapteursBatterieFaible() throws Exception {
        int seuil = lireEntier("Seuil de batterie (ex: 20) : ");
        List<Capteur> capteurs = service.listerCapteursBatterieFaible(seuil);
        if (capteurs.isEmpty()) {
            System.out.println("Aucun capteur en dessous du seuil de " + seuil + "%.");
            return;
        }
        System.out.println("--- Capteurs a batterie faible (< " + seuil + "%) ---");
        for (Capteur c : capteurs) {
            System.out.println(formatCapteur(c));
        }
    }

    private static void calculerMoyenneParType() throws Exception {
        System.out.print("Type de capteur (HUMIDITE / TEMPERATURE / LUMINOSITE) : ");
        String type = scanner.nextLine();
        double moyenne = service.calculerMoyenneValeurParType(type);
        System.out.printf("Moyenne des valeurs mesurees pour '%s' : %.2f%n", type, moyenne);
    }

    private static String formatCapteur(Capteur c) {
        return String.format("ID: %d | Code: %s | Type: %s | Valeur: %.2f | Batterie: %d%% | Installation: %s",
                c.getId(), c.getCodeCapteur(), c.getTypeCapteur(), c.getValeurMesuree(),
                c.getNiveauBatterie(), c.getDateInstallation().format(DATE_FORMAT));
    }



    private static int lireEntier(String message) {
        while (true) {
            System.out.print(message);
            String saisie = scanner.nextLine();
            try {
                return Integer.parseInt(saisie.trim());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre entier valide.");
            }
        }
    }

    private static double lireDouble(String message) {
        while (true) {
            System.out.print(message);
            String saisie = scanner.nextLine();
            try {
                return Double.parseDouble(saisie.trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
        }
    }

    private static LocalDate lireDate(String message) {
        while (true) {
            System.out.print(message);
            String saisie = scanner.nextLine();
            try {
                return LocalDate.parse(saisie.trim(), DATE_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Format de date invalide. Utilisez jj/MM/aaaa.");
            }
        }
    }
}