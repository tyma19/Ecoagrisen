package fr.agrisens.dao;

import fr.agrisens.config.DBConnection;
import fr.agrisens.model.Capteur;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CapteurDaoImpl implements ICapteurDao {

    @Override
    public void ajouterCapteur(Capteur c) throws Exception {
        String sql = "INSERT INTO capteurs (code_capteur, type_capteur, valeur_mesuree, "
                + "niveau_batterie, date_installation) VALUES (?, ?, ?, ?, ?)";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, c.getCodeCapteur());
            ps.setString(2, c.getTypeCapteur());
            ps.setDouble(3, c.getValeurMesuree());
            ps.setInt(4, c.getNiveauBatterie());
            ps.setDate(5, Date.valueOf(c.getDateInstallation()));

            ps.executeUpdate();
        }
    }

    @Override
    public List<Capteur> listerTous() throws Exception {
        List<Capteur> capteurs = new ArrayList<>();
        String sql = "SELECT * FROM capteurs";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                capteurs.add(mapResultSetToCapteur(rs));
            }
        }
        return capteurs;
    }

    @Override
    public Capteur trouverParId(int id) throws Exception {
        String sql = "SELECT * FROM capteurs WHERE id = ?";
        Capteur capteur = null;

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    capteur = mapResultSetToCapteur(rs);
                }
            }
        }
        return capteur;
    }

    @Override
    public void mettreAJourBatterie(int id, int nouveauNiveau) throws Exception {
        String sql = "UPDATE capteurs SET niveau_batterie = ? WHERE id = ?";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, nouveauNiveau);
            ps.setInt(2, id);

            ps.executeUpdate();
        }
    }

    @Override
    public void supprimerCapteur(int id) throws Exception {
        String sql = "DELETE FROM capteurs WHERE id = ?";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Capteur mapResultSetToCapteur(ResultSet rs) throws Exception {
        return new Capteur(
                rs.getInt("id"),
                rs.getString("code_capteur"),
                rs.getString("type_capteur"),
                rs.getDouble("valeur_mesuree"),
                rs.getInt("niveau_batterie"),
                rs.getDate("date_installation").toLocalDate()
        );
    }
}