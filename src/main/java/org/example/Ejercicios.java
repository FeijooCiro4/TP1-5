package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Ejercicios {
    private final List<Producto> productList;

    public Ejercicios() {
        this.productList = Main.cargarProductos();
    }


    /*1. Filtrado y Transformación
    Obtener una lista con los nombres y precios de los productos de la categoría
    "Electrónica" cuyo precio sea mayor a 1000, ordenados de mayor a menor
    precio.*/

    public List<Map<String,Double>> ej1(){
        return productList.stream().filter(prod -> prod.getCategoria().equals("Electrónica") && prod.getPrecio() > 1000)
                .sorted((p1,p2) -> Double.compare(p2.getPrecio(), p1.getPrecio())).map(prod -> {
                    Map<String, Double> mapaValores = new HashMap<>();
                    mapaValores.put(prod.getNombre(), prod.getPrecio());
                    return mapaValores;
                })
                .collect(Collectors.toList());
    }
    //Termine usando un map dentro de una list para poder poner exclusivamente dos valores ne una lista


    /*3. Producto mas caro
    Obtener el producto más caro de cada categoría y devolver un mapa con la
    categoría como clave y el producto más caro como valor.*/

    public Map<String, Producto> ej3(){
        return productList.stream().collect(Collectors.groupingBy(Producto::getCategoria,Collectors.collectingAndThen(Collectors.maxBy(
                Comparator.comparingDouble(Producto::getPrecio)), opt -> opt.orElse(null))));
    }
    //Uso Collectors.collectingAndThen() Para poder cambiar optional<Producto> de maxBy() a un Producto.
    //Use maxBy porque el groupingBy() solo permite usar maxBy dentro suyo.

    /*5. Producto Mas Barato
    Encontrar el producto mas barato calculando el valor total de todas las
    unidades en stock (Precio * stock). Devolver un Opcional con el producto. En
    caso de que no exista, lanzar una excepción.*/

    public Optional<Producto> ej5(){
        return productList.stream().min((prod1,prod2) ->
        Double.compare(prod1.getPrecio() * prod1.getStock(), prod2.getPrecio() * prod2.getStock()));
    }

    /*7. Calculo de Stock Total
    Obtener el total de unidades en stock de todos los productos, pero solo
    considerando aquellos con precio superior al promedio.*/

    public int ej7(){
        double avgPrice = productList.stream().map(Producto::getPrecio).reduce(0.0, Double::sum) /
                productList.stream().map(Producto::getStock).reduce(0, Integer::sum);

        return productList.stream().filter(prod -> prod.getPrecio() > avgPrice)
                .mapToInt(Producto::getStock).sum();
    }

    /*9. Aplicar descuento
    Crear una nueva lista de productos con un 15% de descuento en su precio,
    pero solo si el producto tiene más de 20 unidades en stock.*/

    public List<Producto> ej9(){
        return productList.stream().filter(prod -> prod.getStock() > 20).
                map(prod ->
                        new Producto(prod.getNombre(),
                                prod.getPrecio() * 0.85,
                                prod.getCategoria(),
                                prod.getStock()))
                .collect(Collectors.toList());
    }
}
