package net.javierjimenez.Pokemons;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.Label;

public class PokemonsController implements Initializable {
	@FXML
	private Button generarEquip;
	@FXML
	private Button iniciarCombat;
	@FXML
	private Label combatBlau;
	@FXML
	private Label reservaBlau;
	@FXML
	private Label reservaVermell;
	@FXML
	private Label combatVermell;

	private List<Pokemon> pokemons = new ArrayList<Pokemon>();

	Connection con = null;

	Statement cerca = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {

			con = DriverManager.getConnection("jdbc:mysql://192.168.4.1/pokemons", "pokemon", "pokemon");
			cerca = con.createStatement();

		} catch (SQLException e) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Problema de conexión con BD");
			alert.setHeaderText("ERROR: Problema de conexión");
			alert.setContentText("Error al conectar con la base de datos.\nRevise si esta operativa.");

			alert.showAndWait();

			System.exit(0);

		}
	}

	public void genTeams(ActionEvent event) throws SQLException {

		pokemons.clear();

		ResultSet poket_monster = null;
		ResultSet pokemon_type = null;
		ResultSet pokemon_pow = null;

		int i = 0;

		while (i < 4) {

			Pokemon p = new Pokemon();

			int contador = 0;

			int count = 0;

			int[] tipus;

			poket_monster = cerca.executeQuery("SELECT POKEMON_ID, NOM, PES FROM POKEMONS ORDER BY RAND() LIMIT 1");

			if (poket_monster.isBeforeFirst()) {
				poket_monster.next();
			}

			p.setId(poket_monster.getInt("POKEMON_ID"));
			p.setNom(poket_monster.getString("NOM"));
			p.setPes(poket_monster.getDouble("PES"));

			int pokemonId = poket_monster.getInt(1);

			System.out.println(pokemonId);

			poket_monster.close();

			pokemon_type = cerca.executeQuery("SELECT TIPUS_ID FROM POKETIPUS WHERE POKEMON_ID = " + pokemonId);

			if (pokemon_type.isBeforeFirst()) {
				pokemon_type.next();
			}

			while (pokemon_type.next()) {
				count++;
			}

			if (count < 1) {

				tipus = new int[2];

				while (pokemon_type.next()) {
					tipus[contador] = pokemon_type.getInt("TIPUS_ID");
					contador++;
				}

			} else {

				tipus = new int[1];
			}

			while (pokemon_type.next()) {
				tipus[contador] = pokemon_type.getInt("TIPUS_ID");
				contador++;
			}

			pokemon_type.close();

			pokemon_pow = cerca
					.executeQuery("SELECT VALOR FROM POKEMON_PODER WHERE PODER_ID = 7 AND POKEMON_ID = " + pokemonId);

			if (pokemon_pow.isBeforeFirst()) {
				pokemon_pow.next();
			}

			p.setTipus(tipus);
			p.setVida(200);
			p.setPoder(pokemon_pow.getDouble("VALOR"));

			pokemon_pow.close();

			pokemons.add(p);

			i++;
		}

		combatBlau.setText(pokemons.get(0).getNom());
		combatVermell.setText(pokemons.get(1).getNom());
		reservaBlau.setText(pokemons.get(2).getNom());
		reservaVermell.setText(pokemons.get(3).getNom());

	}

	public void startCombat(ActionEvent event) {

		if (pokemons.get(0).getPes() < pokemons.get(1).getPes()) {

			while (pokemons.get(0).getVida() <= 0 || pokemons.get(1).getVida() <= 0) {

				pokemons.get(1).setVida(pokemons.get(1).getVida() - pokemons.get(0).getPoder());

				if (pokemons.get(1).getVida() > 0) {

					pokemons.get(0).setVida(pokemons.get(0).getVida() - pokemons.get(1).getPoder());

				}
			}
			
			if(pokemons.get(0).getVida() <= 0){
				
				combatBlau.setText(pokemons.get(2).getNom());
				reservaBlau.setText(pokemons.get(0).getNom());
				reservaBlau.setDisable(true);
			
			} else {
				combatBlau.setText(pokemons.get(3).getNom());
				reservaBlau.setText(pokemons.get(1).getNom());
				reservaBlau.setDisable(true);
			}

		} else {

			while (pokemons.get(0).getVida() == 0 || pokemons.get(1).getVida() == 0) {

				pokemons.get(0).setVida(pokemons.get(0).getVida() - pokemons.get(1).getPoder());

				if (pokemons.get(0).getVida() > 0) {

					pokemons.get(1).setVida(pokemons.get(1).getVida() - pokemons.get(0).getPoder());

				}
				
			}
		}
	}
}
