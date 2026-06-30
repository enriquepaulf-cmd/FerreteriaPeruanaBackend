package isw.sigconbackend.service;

import isw.sigconbackend.model.Almacen;
import isw.sigconbackend.repository.AlmacenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlmacenService {
    
    @Autowired
    AlmacenRepository almacenRepository;

    public List<Almacen> getAlmacenes() {
        return almacenRepository.findAll();
    }

    public Almacen insertAlmacen(Almacen almacen) {
        return almacenRepository.save(almacen);
    }
}