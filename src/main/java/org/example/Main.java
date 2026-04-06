package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main() {
        Ejercicios ejercicios = new Ejercicios();


        System.out.println("Lista de productos Electronicos con precio mayor a 1000 de mayor a menor: \n" +
                ejercicios.ej1() + "\n");

        System.out.println("Promedio de Hogares con Stock: \n" +
                ejercicios.ej2() + "\n");

        System.out.println("Producto más caro por categoria: \n" +
                ejercicios.ej3()  + "\n");

        System.out.println("Productos deportivos con stock mayor a 10, devueltos con el nombre en minuscula: \n" +
                ejercicios.ej4() + "\n");

        System.out.println("Producto mas barato: \n" +
                ejercicios.ej5() + "\n");

        System.out.println("Productos en stock ordenados alfabeticamente: \n" +
                ejercicios.ej6() + "\n");

        System.out.println("Calculo de stock total de productos con precio mayor al promedio: \n" +
                ejercicios.ej7() + "\n");

        System.out.println("Stock por categoria: \n" +
                ejercicios.ej8() + "\n");

        System.out.println("Lista de productos con un 15% de descuento: \n" +
                ejercicios.ej9() + "\n");

        System.out.println("Ganancia total del inventario: \n" +
                ejercicios.ej10() + "\n");
    }
}
