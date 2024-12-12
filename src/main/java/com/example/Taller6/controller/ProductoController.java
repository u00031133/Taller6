/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Taller6.controller;



import com.example.Taller6.model.Producto;
import com.example.Taller6.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")

public class ProductoController {
     @Autowired
    private ProductoRepository productoRepository;

    // Mostrar la lista de productos
    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "index"; // Retorna el template "index.html"
    }

    // Mostrar el formulario de creación de un nuevo producto
    @GetMapping("/create")
    public String crearProductoForm(Model model) {
        model.addAttribute("producto", new Producto());
        return "create"; // Retorna el template "create.html"
    }

    // Crear un nuevo producto
    @PostMapping
    public String crearProducto(@ModelAttribute Producto producto) {
        productoRepository.save(producto); // Guarda el producto en la base de datos
        return "redirect:/productos"; // Redirige al listado de productos
    }

    // Mostrar el formulario de edición de un producto
    @GetMapping("/{id}/edit")
    public String editarProductoForm(@PathVariable Long id, Model model) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        model.addAttribute("producto", producto);
        return "edit"; // Retorna el template "edit.html"
    }

    // Editar un producto existente
    @PostMapping("/{id}")
    public String editarProducto(@PathVariable Long id, @ModelAttribute Producto producto) {
        Producto productoExistente = productoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        productoExistente.setNombre(producto.getNombre());
        productoExistente.setCategoria(producto.getCategoria());
        productoExistente.setFechaVencimiento(producto.getFechaVencimiento());
        productoExistente.setStockMinimo(producto.getStockMinimo());
        productoExistente.setStockMaximo(producto.getStockMaximo());
        productoExistente.setMarca(producto.getMarca());
        productoExistente.setPeso(producto.getPeso());
        
        productoRepository.save(productoExistente); // Guarda las modificaciones en la base de datos
        return "redirect:/productos"; // Redirige al listado de productos
    }

    // Eliminar un producto
    @GetMapping("/{id}/delete")
    public String eliminarProducto(@PathVariable Long id) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        productoRepository.delete(producto); // Elimina el producto de la base de datos
        return "redirect:/productos"; // Redirige al listado de productos
    }
}
