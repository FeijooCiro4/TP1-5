package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Ejercicios {
    private final List<Producto> productList;

    public Ejercicios() {
        this.productList = cargarProductos();
    }


    /*1. Filtrado y Transformación
    Obtener una lista con los nombres y precios de los productos de la categoría
    "Electrónica" cuyo precio sea mayor a 1000, ordenados de mayor a menor
    precio.*/
    public List<Map<String,Double>> ej1(){
        return productList.stream()
                .filter(prod -> prod.getCategoria().equals("Electrónica") && prod.getPrecio() > 1000)
                .sorted((p1,p2) -> Double.compare(p2.getPrecio(), p1.getPrecio()))
                    .map(prod -> {
                        Map<String, Double> mapaValores = new HashMap<>();
                        mapaValores.put(prod.getNombre(), prod.getPrecio());
                        return mapaValores;
                    })
                .collect(Collectors.toList());
    }

    /*2. Promedio de hogares con stock
    * Calcular el precio promedio de los productos de la categoría "Hogar",
    * pero solo considerando aquellos con stock disponible.
    */
    public double ej2(){
        return productList.stream()
                .filter(prod -> Objects.equals(prod.getCategoria(), "Hogar") && prod.getStock() > 0)
                .mapToDouble(Producto::getPrecio)
                .average()
                .orElse(0.0);
    }

    /*3. Producto mas caro
    Obtener el producto más caro de cada categoría y devolver un mapa con la
    categoría como clave y el producto más caro como valor.*/
    public Map<String, Producto> ej3(){
        return productList.stream()
                .collect(
                    Collectors.groupingBy(
                        Producto::getCategoria,
                        Collectors.collectingAndThen(
                            Collectors.maxBy(Comparator.comparingDouble(Producto::getPrecio)),
                            opt -> opt.orElse(null)
                        )
                    )
                );
    }

    /*4. Productos deportivos con stock mayor a 10, devueltos con el nombre en minuscula
    * Encontrar el producto de la categoría "Deportes" con stock mayor a 10 unidades,
    * obtener su nombre en minúsculas y devolverlo dentro de un Optional.
    * Mostrarlo o si no existe, mostrar “Producto Inexistente”
     * */
    public String ej4() {
        List<String> filtro = productList.stream()
                .filter(prod -> Objects.equals(prod.getCategoria(), "Deportes") && prod.getStock() > 10)
                .map(prod -> prod.getNombre().toLowerCase())
                .toList();

        return Optional.of(filtro)
                .filter(f -> !f.isEmpty())
                .map(Object::toString)
                .orElse("Producto Inexistente");
    }

    /*5. Producto Mas Barato
    Encontrar el producto mas barato calculando el valor total de todas las
    unidades en stock (Precio * stock). Devolver un Opcional con el producto. En
    caso de que no exista, lanzar una excepción.*/
    public Optional<Producto> ej5(){
        Optional<Producto>  opt = productList.stream()
                .min(Comparator.comparingDouble((prod) -> prod.getPrecio() * prod.getStock()));

        if(opt.isEmpty()){
            throw new RuntimeException("No se encontro el producto");
        }

        return opt;
    }
    /* Corregí este punto, ya que no se estaba lanzando una excepción
    * y la lógica anterior no era reconocida por el IDE como la más óptima,
    * recomendándome usar Comparator.comparingDouble()
     * */

    /*6. Productos en stock ordenados alfabéticamente
     * Obtener una lista con los nombres de los productos que tienen stock,
     * ordenarlos alfabéticamente y excluir los productos con nombres de menos de 5 caracteres
    * */
    public List<String> ej6(){
        return productList.stream()
                .filter(prod -> prod.getStock() > 0 && prod.getNombre().length() >= 5)
                .map(Producto::getNombre)
                .sorted()
                .toList();
    }

    /*7. Calculo de Stock Total
    Obtener el total de unidades en stock de todos los productos, pero solo
    considerando aquellos con precio superior al promedio.*/
    public int ej7(){
        double avgPrice = productList.stream()
                .map(Producto::getPrecio)
                .reduce(0.0, Double::sum)
                /
                productList.stream()
                        .map(Producto::getStock)
                        .reduce(0, Integer::sum);

        return productList.stream()
                .filter(prod -> prod.getPrecio() > avgPrice)
                .mapToInt(Producto::getStock)
                .sum();
    }

    /*8. Stock por Categoría
     * Calcular cuántas unidades en stock hay por categoría,
     * excluyendo categorías con menos de 3 productos.
    * */
    public Map<String, Integer> ej8(){
        return productList.stream()
                .filter(prod -> prod.getStock()  > 3)
                .collect(Collectors.groupingBy(
                   Producto::getCategoria, Collectors.summingInt(Producto::getStock)
                ));
    }

    /*9. Aplicar descuento
    Crear una nueva lista de productos con un 15% de descuento en su precio,
    pero solo si el producto tiene más de 20 unidades en stock.*/
    public List<Producto> ej9(){
        return productList.stream()
                .filter(prod -> prod.getStock() > 20)
                .map(prod ->
                    new Producto(
                        prod.getNombre(),
                        prod.getPrecio() - (prod.getPrecio()*0.15),     // Esta lógica es más precisa, aunque llamar dos veces al mismo metodo no es para nada practico
                        prod.getCategoria(),
                        prod.getStock()
                    )
                )
                .collect(Collectors.toList());
    }

    /*10. Ganancia total inventario
     * Calcular la ganancia total si se vendiera todo el inventario,
     * considerando que el costo de compra de cada producto es un 45%
     * del valor de venta o de un 65% si pertenece a la categoria Electronica.
     * */
    public double ej10(){
        return productList.stream()
                .filter(prod -> !Objects.equals(prod.getCategoria(), "Electronica"))
                .mapToDouble(prod -> prod.getPrecio() - (prod.getPrecio() * 0.45))
                .sum()
                +
                productList.stream()
                        .filter(prod -> Objects.equals(prod.getCategoria(), "Electronica"))
                        .mapToDouble(prod -> prod.getPrecio() - (prod.getPrecio() * 0.65))
                        .sum();
    }

    /*
    * Transporté el metodo caragarProducto() para mantener la
    * independencia entre Main y la clase de Ejercicios
    * */
    public List<Producto> cargarProductos() {
        return List.of(
                new Producto("Laptop", 1500, "Electrónica", 5),
                new Producto("Smartphone", 800, "Electrónica", 10),
                new Producto("Televisor", 1200, "Electrónica", 3),
                new Producto("Heladera", 2000, "Hogar", 2),
                new Producto("Microondas", 500, "Hogar", 8),
                new Producto("Silla", 150, "Muebles", 12),
                new Producto("Mesa", 300, "Muebles", 7),
                new Producto("Zapatillas", 100, "Deportes", 15),
                new Producto("Pelota", 50, "Deportes", 20),
                new Producto("Bicicleta", 500, "Deportes", 5),
                new Producto("Libro", 30, "Librería", 50),
                new Producto("Cuaderno", 10, "Librería", 100),
                new Producto("Lámpara", 80, "Hogar", 30),
                new Producto("Cafetera", 250, "Hogar", 6),
                new Producto("Auriculares", 120, "Electrónica", 14),
                new Producto("Teclado", 90, "Electrónica", 9),
                new Producto("Mouse", 60, "Electrónica", 18),
                new Producto("Monitor", 700, "Electrónica", 4),
                new Producto("Cama", 800, "Muebles", 2),
                new Producto("Sofá", 1000, "Muebles", 3),
                new Producto("Espejo", 120, "Hogar", 12),
                new Producto("Ventilador", 150, "Hogar", 7),
                new Producto("Patines", 180, "Deportes", 5),
                new Producto("Raqueta", 220, "Deportes", 6),
                new Producto("Taza", 15, "Hogar", 40)
        );
    }
}
