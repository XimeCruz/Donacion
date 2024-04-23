package com.example.donacion.service;

import com.example.donacion.model.Categoria;
import com.example.donacion.model.ProductoStock;
import com.example.donacion.model.Usuario;
import com.example.donacion.repository.ProductoStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoStockService {

    @Autowired
    private ProductoStockRepository productoStockRepository;


    @Autowired
    private VendedorService vendedorServices;

    @Autowired
    private CategoriaService categoriaServices;

    public ProductoStock getById(Long id) {

        return productoStockRepository
                .findById(id)
                .orElse(null);
    }

    public List<ProductoStock> getProductos() {

        return productoStockRepository.findAll();
    }

    public void guardarProducto(ProductoStock productoStock, Authentication authentication) {

        Usuario donante=vendedorServices.GetbyEmail(authentication.getName());
        productoStock.setFechaDePublicacion(java.sql.Date.valueOf(LocalDate.now()));
        productoStock.setDonante(donante);
        productoStockRepository.save(productoStock);
    }

    public void eliminarProducto(Long id) {

        productoStockRepository.deleteById(id);
    }


    public void actualizarProducto(ProductoStock productoStock) {

        ProductoStock productoStockActualizar=productoStockRepository
                .findById(productoStock.getId())
                .get();

        Categoria categoria= categoriaServices
                .getById(productoStock
                        .getCategoria()
                        .getId());


        productoStockActualizar.setNombre(productoStock.getNombre());
        productoStockActualizar.setUnidadesDisponibles(productoStock.getUnidadesDisponibles());
        productoStockActualizar.setPrecio(productoStock.getPrecio());
        productoStockActualizar.setDescripcion(productoStock.getDescripcion());


        productoStockActualizar.setCategoria(categoria);

        productoStockRepository.save(productoStockActualizar);
    }

    public List<ProductoStock> deMenorAMayorPrecio() {

        return productoStockRepository.findAll(Sort.by(Sort.Direction.ASC,"precio"));
    }

    public List<ProductoStock> deMayorAMenorPrecio() {

        return productoStockRepository.findAll(Sort.by(Sort.Direction.DESC,"precio"));
    }


    public List<ProductoStock> porCategoria(Long id) {

        Categoria categoria= categoriaServices.getById(id);

        return productoStockRepository.findByCategoria(categoria);
    }


    public List<ProductoStock> porRangoDePrecios(Double min, Double Max) {

        return productoStockRepository.findByPrecioBetween(min, Max);
    }


    public Page<ProductoStock> getProductos(Pageable pageable) {

        return productoStockRepository.findAll(pageable);
    }


    public List<ProductoStock> porFechaDepublicacion() {

        return productoStockRepository.findAll(Sort.by(Sort.Direction.DESC, "fechaDePublicacion"));
    }


    public List<ProductoStock> porCantidadDisponible() {

        return productoStockRepository.findAll(Sort.by(Sort.Direction.DESC, "unidadesDisponibles"));
    }

    public List<ProductoStock> porPrecioMenorA(Double precio) {

        return productoStockRepository.findAll()
                .stream()
                .filter(P -> P.getPrecio()<=precio)
                .collect(Collectors.toCollection(ArrayList::new));
    }


    public List<ProductoStock> porPrecioMayorA(Double precio) {

        return productoStockRepository.findAll()
                .stream()
                .filter(P -> P.getPrecio()>=precio)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<ProductoStock> findByFechaVencBetween(Date fechaActual, Date fechaProximaSemana){
        return productoStockRepository.findByFechaDeVencimientoBetween(fechaActual,fechaProximaSemana);
    }

}
