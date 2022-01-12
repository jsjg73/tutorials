package lifecycleevents.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lifecycleevents.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUserName(String userName);
}
