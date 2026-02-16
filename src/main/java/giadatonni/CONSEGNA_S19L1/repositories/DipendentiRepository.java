package giadatonni.CONSEGNA_S19L1.repositories;

import giadatonni.CONSEGNA_S19L1.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DipendentiRepository extends JpaRepository<Dipendente, UUID> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
