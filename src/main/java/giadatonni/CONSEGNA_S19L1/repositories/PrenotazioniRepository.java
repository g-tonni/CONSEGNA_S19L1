package giadatonni.CONSEGNA_S19L1.repositories;

import giadatonni.CONSEGNA_S19L1.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazione, UUID> {

    @Query("SELECT p FROM Prenotazione p WHERE p.dipendente.dipendenteId = :dipendenteId")
    List<Prenotazione> findByDipendente(UUID dipendenteId);
}
