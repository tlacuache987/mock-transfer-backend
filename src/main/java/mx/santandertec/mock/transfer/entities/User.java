package mx.santandertec.mock.transfer.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// getters y setters
@Data
// entidad
@Entity
// constructor sin argumentos
@NoArgsConstructor
// constructor con argumentos
@AllArgsConstructor
public class User {

	// atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank(message = "Name is mandatory")
	private String name;

	@NotBlank(message = "Email is mandatory")
	private String email;

}