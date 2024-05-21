package db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"idTest"})
@Entity
@Table(name = "TestBuilder")
public class TestBuilder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idTest", nullable = false, unique = true, length = 20)
	private String idTest;
	
	@Column(name = "nome", length = 20, nullable = true, unique = false)
	private String nome;

	@Column(name = "cognome", length = 20, nullable = true, unique = false)
	private String cognome;

	@Column(name = "mail", length = 40, nullable = true, unique = false)
	private String mail;

	@Column(name = "telefono", length = 20, nullable = true, unique = false)
	private String telefono;
}
