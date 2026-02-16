package giadatonni.CONSEGNA_S19L1.services;

import giadatonni.CONSEGNA_S19L1.entities.Viaggio;
import giadatonni.CONSEGNA_S19L1.exceptions.BadRequestException;
import giadatonni.CONSEGNA_S19L1.exceptions.NotFoundException;
import giadatonni.CONSEGNA_S19L1.payload.ViaggioDTO;
import giadatonni.CONSEGNA_S19L1.repositories.ViaggiRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class ViaggiService {

    private final ViaggiRepository viaggiRepository;

    public ViaggiService(ViaggiRepository viaggiRepository) {
        this.viaggiRepository = viaggiRepository;
    }

    public Page<Viaggio> getViaggi(int page, int size, String orderBy){
        if(page < 0) page = 0;
        if(page > 30) page = 30;
        if(size < 0) size = 0;
        if(size > 20) size = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.viaggiRepository.findAll(pageable);
    }

    public Viaggio findById(UUID dipendenteId){
        return this.viaggiRepository.findById(dipendenteId).orElseThrow(() -> new NotFoundException(dipendenteId));
    }

    public Viaggio postViaggio(ViaggioDTO body){
        if (body.dataPartenza().isBefore(LocalDate.now())) throw new BadRequestException("La data di partenza deve essere successiva al giorno corrente");
        if (body.dataPartenza().isAfter(body.dataRitorno())) throw new BadRequestException("La data di partenza deve essere precedente alla data di ritorno");
        if (body.dataRitorno().isBefore(LocalDate.now())) throw new BadRequestException("La data di ritorno deve essere successiva al giorno corrente");
        Viaggio nuovoViaggio = new Viaggio(body.destinazione(), body.dataPartenza(), body.dataRitorno(), "in_programma");
        this.viaggiRepository.save(nuovoViaggio);
        System.out.println("Viaggio salvato");
        return nuovoViaggio;
    }

    public Viaggio putViaggio(UUID viaggioId, ViaggioDTO body){
        Viaggio found = this.findById(viaggioId);
        if (body.dataPartenza().isBefore(LocalDate.now())) throw new BadRequestException("La data di partenza deve essere successiva al giorno corrente");
        if (body.dataPartenza().isAfter(body.dataRitorno())) throw new BadRequestException("La data di partenza deve essere precedente alla data di ritorno");
        if (body.dataRitorno().isBefore(LocalDate.now())) throw new BadRequestException("La data di ritorno deve essere successiva al giorno corrente");
        found.setDestinazione(body.destinazione());
        found.setDataPartenza(body.dataPartenza());
        found.setDataRitorno(body.dataRitorno());
        this.viaggiRepository.save(found);
        System.out.println("Viaggio aggiornato");
        return found;
    }

    public Viaggio patchStatoViaggio(UUID viaggioId, String stato){
        Viaggio found = this.findById(viaggioId);
        if (stato.equals("concluso") && found.getDataRitorno().isAfter(LocalDate.now())) throw new BadRequestException("Lo stato del viaggio non può essere 'concluso' se la data di ritorno è successiva alla data di oggi");
        if(!stato.equals("concluso") && !stato.equals("annullato")) throw new BadRequestException("Lo stato può essere: in_programma, annullato o concluso");
        found.setStato(stato);
        this.viaggiRepository.save(found);
        return found;
    }
}
