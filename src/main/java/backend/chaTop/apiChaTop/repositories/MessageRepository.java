package backend.chaTop.apiChaTop.repositories;

import backend.chaTop.apiChaTop.models.Message;
import backend.chaTop.apiChaTop.models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
