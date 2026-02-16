package giadatonni.CONSEGNA_S19L1.services;

import giadatonni.CONSEGNA_S19L1.entities.Dipendente;
import giadatonni.CONSEGNA_S19L1.entities.Prenotazione;
import giadatonni.CONSEGNA_S19L1.entities.Viaggio;
import giadatonni.CONSEGNA_S19L1.exceptions.BadRequestException;
import giadatonni.CONSEGNA_S19L1.exceptions.NotFoundException;
import giadatonni.CONSEGNA_S19L1.payload.PrenotazioneDTO;
import giadatonni.CONSEGNA_S19L1.payload.PutPrenotazioneDTO;
import giadatonni.CONSEGNA_S19L1.repositories.PrenotazioniRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PrenotazioniService {

    private final PrenotazioniRepository prenotazioniRepository;
    private final DipendentiService dipendentiService;
    private final ViaggiService viaggiService;

    public PrenotazioniService(PrenotazioniRepository prenotazioniRepository, DipendentiService dipendentiService, ViaggiService viaggiService) {
        this.prenotazioniRepository = prenotazioniRepository;
        this.dipendentiService = dipendentiService;
        this.viaggiService = viaggiService;
    }

    public Page<Prenotazione> getPrenotazioni(int page, int size, String orderBy){
        if(page < 0) page = 0;
        if(page > 30) page = 30;
        if(size < 0) size = 0;
        if(size > 20) size = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.prenotazioniRepository.findAll(pageable);
    }

    public Prenotazione findById(UUID prenotazioneId){
        return this.prenotazioniRepository.findById(prenotazioneId).orElseThrow(() -> new NotFoundException(prenotazioneId));
    }

    public Prenotazione postPrenotazione(PrenotazioneDTO body){
        Dipendente dipendenteFound = this.dipendentiService.findById(body.dipendente());
        Viaggio viaggioFound = this.viaggiService.findById(body.viaggio());
        List<Prenotazione> listaPrenotazioniDipendente = this.trovaByUtente(body.dipendente()).stream()
                .filter(prenotazione -> prenotazione.getViaggio().getDataPartenza().isBefore(viaggioFound.getDataPartenza()) && prenotazione.getViaggio().getDataRitorno().isAfter(viaggioFound.getDataPartenza())).toList();
        if(listaPrenotazioniDipendente.isEmpty()) {
            Prenotazione nuovaPrenotazione = new Prenotazione(LocalDate.now(), body.richiesteSpeciali(), viaggioFound, dipendenteFound);
            this.prenotazioniRepository.save(nuovaPrenotazione);
            System.out.println("Prenotazione salvata");
            return nuovaPrenotazione;
        } else throw new BadRequestException("Non possono esserci più prenotazioni per lo stesso dipendente nelle stesse date");
    }

    public Prenotazione putPrenotazione(UUID prenotazioneId, PutPrenotazioneDTO body){
        Prenotazione found = this.findById(prenotazioneId);
        found.setRichiesteSpeciali(body.richiesteSpeciali());
        this.prenotazioniRepository.save(found);
        System.out.println("Prenotazione aggiornata");
        return found;
    }

    public List<Prenotazione> trovaByUtente(UUID dipendenteId){
        return this.prenotazioniRepository.findByDipendente(dipendenteId);
    }
}
