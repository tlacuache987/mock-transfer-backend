package mx.santandertec.mock.transfer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.santandertec.mock.transfer.entities.User;

public interface IUserService {

	Page<User> findPaginated(Pageable pageable);

	void saveOrUpdate(User user);

	User searchById(long id);

	void delete(User user);
}
