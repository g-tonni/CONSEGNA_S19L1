package giadatonni.CONSEGNA_S19L1.repositories;

import giadatonni.CONSEGNA_S19L1.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ViaggiRepository extends JpaRepository<Viaggio, UUID> {
}
