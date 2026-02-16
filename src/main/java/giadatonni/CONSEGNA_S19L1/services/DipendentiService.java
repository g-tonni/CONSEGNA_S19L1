package giadatonni.CONSEGNA_S19L1.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import giadatonni.CONSEGNA_S19L1.entities.Dipendente;
import giadatonni.CONSEGNA_S19L1.exceptions.BadRequestException;
import giadatonni.CONSEGNA_S19L1.exceptions.NotFoundException;
import giadatonni.CONSEGNA_S19L1.payload.DipendenteDTO;
import giadatonni.CONSEGNA_S19L1.repositories.DipendentiRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class DipendentiService {
    private final DipendentiRepository dipendentiRepository;
    private final Cloudinary cloudinaryUploader;

    public DipendentiService(DipendentiRepository dipendentiRepository, Cloudinary cloudinaryUploader) {
        this.dipendentiRepository = dipendentiRepository;
        this.cloudinaryUploader = cloudinaryUploader;
    }

    public Page<Dipendente> getDipendenti(int page, int size, String orderBy){
        if(page < 0) page = 0;
        if(page > 30) page = 30;
        if(size < 0) size = 0;
        if(size > 20) size = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.dipendentiRepository.findAll(pageable);
    }

    public Dipendente findById(UUID dipendenteId){
        return this.dipendentiRepository.findById(dipendenteId).orElseThrow(() -> new NotFoundException(dipendenteId));
    }

    public Dipendente postDipendente(DipendenteDTO body){
        if (this.dipendentiRepository.existsByUsername(body.username())) throw new BadRequestException("Username già in uso");
        if (this.dipendentiRepository.existsByEmail(body.email())) throw new BadRequestException("Email già in uso");
        String imageUrl = "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome();
        Dipendente nuovoDipendente = new Dipendente(body.username(), body.nome(), body.cognome(), body.email(), body.password(), imageUrl);
        this.dipendentiRepository.save(nuovoDipendente);
        System.out.println("Dipendente salvato");
        return nuovoDipendente;
    }

    public Dipendente putDipendente(UUID dipendenteId, DipendenteDTO body){
        Dipendente found = this.findById(dipendenteId);
        if(!found.getUsername().equals(body.username())){
           if (this.dipendentiRepository.existsByUsername(body.username())) throw new BadRequestException("Username già in uso");
        }
        if(!found.getEmail().equals(body.email())){
           if (this.dipendentiRepository.existsByEmail(body.email())) throw new BadRequestException("Email già in uso");
        }
        found.setUsername(body.username());
        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setEmail(body.email());
        found.setPassword(body.password());
        this.dipendentiRepository.save(found);
        System.out.println("Dipendente aggiornato");
        return found;
    }

   public Dipendente uploadFotoProfilo(UUID dipendenteId, MultipartFile file) {
        Dipendente found = this.findById(dipendenteId);
        try {
            Map result = cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) result.get("secure_url");
            found.setFotoProfilo(imageUrl);
            this.dipendentiRepository.save(found);
            System.out.println("Foto profilo aggiornata");
            return found;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
