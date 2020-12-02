package mx.santandertec.mock.transfer.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.santandertec.mock.transfer.entities.User;

// repositorio
@Repository // no es necesaria la anotación @Repository
public interface UserRepository extends JpaRepository<User, Long> {

	// define query method
	List<User> findByName(String name);
}