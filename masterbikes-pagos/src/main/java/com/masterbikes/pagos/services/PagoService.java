package com.masterbikes.pagos.services;

import com.masterbikes.pagos.models.PagoModel;
import com.masterbikes.pagos.repositories.PagoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public List<PagoModel> obtenerTodos() {
        return pagoRepository.findAll();
    }

    public Optional<PagoModel> obtenerPorId(Long id) {
        return pagoRepository.findById(id);
    }

    public PagoModel guardar(PagoModel pago) {
        return pagoRepository.save(pago);
    }

    public void eliminar(Long id) {
        pagoRepository.deleteById(id);
    }
}
