package com.masterbikes.servicio_tecnico.services;

import com.masterbikes.servicio_tecnico.client.ClienteClient;
import com.masterbikes.servicio_tecnico.dtos.request.OrdenServicioRequest;
import com.masterbikes.servicio_tecnico.dtos.response.ClienteResponse;
import com.masterbikes.servicio_tecnico.dtos.response.OrdenServicioResponse;
import com.masterbikes.servicio_tecnico.exceptions.NotFoundException;
import com.masterbikes.servicio_tecnico.exceptions.RemoteServiceException;
import com.masterbikes.servicio_tecnico.models.OrdenServicioModel;
import com.masterbikes.servicio_tecnico.repositories.OrdenServicioRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrdenServicioService {

    private static final Logger log = LoggerFactory.getLogger(OrdenServicioService.class);

    private final OrdenServicioRepository ordenServicioRepository;
    private final ClienteClient clienteClient;

    public OrdenServicioService(OrdenServicioRepository ordenServicioRepository, ClienteClient clienteClient) {
        this.ordenServicioRepository = ordenServicioRepository;
        this.clienteClient = clienteClient;
    }

    public List<OrdenServicioResponse> obtenerTodas() {
        return ordenServicioRepository.findAll()
                .stream()
                .map(this::mapToResponseConCliente)
                .toList();
    }

    public OrdenServicioResponse obtenerPorId(Long id) {
        OrdenServicioModel ordenServicio = ordenServicioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe la orden de servicio con id: " + id));

        return mapToResponseConCliente(ordenServicio);
    }

    public List<OrdenServicioResponse> obtenerPorCliente(Long idCliente) {
        return ordenServicioRepository.findByIdCliente(idCliente)
                .stream()
                .map(this::mapToResponseConCliente)
                .toList();
    }

    public OrdenServicioResponse guardar(OrdenServicioRequest request) {
        // Antes de guardar, se consulta clientes para no crear ordenes con clientes inexistentes.
        ClienteResponse cliente = obtenerClienteDesdeServicio(request.getIdCliente());

        OrdenServicioModel ordenServicio = new OrdenServicioModel();
        ordenServicio.setFechaIngreso(request.getFechaIngreso());
        ordenServicio.setHoraIngreso(request.getHoraIngreso());
        ordenServicio.setCosto(request.getCosto());
        ordenServicio.setIdCliente(request.getIdCliente());
        ordenServicio.setProblema(request.getProblema());

        OrdenServicioModel guardada = ordenServicioRepository.save(ordenServicio);

        log.info("Orden de servicio creada con id {} para cliente {}", guardada.getId(), request.getIdCliente());
        return mapToResponse(guardada, cliente);
    }

    public OrdenServicioResponse actualizar(Long id, OrdenServicioRequest request) {
        OrdenServicioModel ordenServicio = ordenServicioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe la orden de servicio con id: " + id));

        ClienteResponse cliente = obtenerClienteDesdeServicio(request.getIdCliente());

        ordenServicio.setFechaIngreso(request.getFechaIngreso());
        ordenServicio.setHoraIngreso(request.getHoraIngreso());
        ordenServicio.setCosto(request.getCosto());
        ordenServicio.setIdCliente(request.getIdCliente());
        ordenServicio.setProblema(request.getProblema());

        OrdenServicioModel actualizada = ordenServicioRepository.save(ordenServicio);

        log.info("Orden de servicio actualizada con id {}", id);
        return mapToResponse(actualizada, cliente);
    }

    public void eliminar(Long id) {
        OrdenServicioModel ordenServicio = ordenServicioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe la orden de servicio con id: " + id));

        log.info("Eliminando orden de servicio con id {}", id);
        ordenServicioRepository.delete(ordenServicio);
    }

    private ClienteResponse obtenerClienteDesdeServicio(Long idCliente) {
        try {
            log.info("Consultando microservicio de clientes para validar cliente {}", idCliente);
            return clienteClient.obtenerClientePorId(idCliente);
        } catch (FeignException.NotFound e) {
            log.warn("Cliente {} no existe segun microservicio de clientes", idCliente);
            throw new NotFoundException("No existe el cliente con id: " + idCliente);
        } catch (FeignException e) {
            log.error("Error al consultar microservicio de clientes para cliente {}", idCliente, e);
            throw new RemoteServiceException("Error al comunicarse con el microservicio de clientes");
        }
    }

    private OrdenServicioResponse mapToResponseConCliente(OrdenServicioModel ordenServicio) {
        ClienteResponse cliente = obtenerClienteDesdeServicio(ordenServicio.getIdCliente());
        return mapToResponse(ordenServicio, cliente);
    }

    private OrdenServicioResponse mapToResponse(OrdenServicioModel ordenServicio, ClienteResponse cliente) {
        return OrdenServicioResponse.builder()
                .id(ordenServicio.getId())
                .fechaIngreso(ordenServicio.getFechaIngreso())
                .horaIngreso(ordenServicio.getHoraIngreso())
                .costo(ordenServicio.getCosto())
                .idCliente(ordenServicio.getIdCliente())
                .problema(ordenServicio.getProblema())
                .cliente(cliente)
                .build();
    }
}
