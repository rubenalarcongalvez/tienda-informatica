package org.iesvegademijas.stream.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

import org.hibernate.internal.build.AllowSysOut;
import org.iesvegademijas.hibernate.Fabricante;
import org.iesvegademijas.hibernate.FabricanteHome;
import org.iesvegademijas.hibernate.Producto;
import org.iesvegademijas.hibernate.ProductoHome;
import org.junit.jupiter.api.Test;


class TiendaTest {
	
	@Test
	void testSkeletonFrabricante() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
		
			
			//TODO STREAMS
			
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	

	@Test
	void testSkeletonProducto() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			//TODO STREAMS
		
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	@Test
	void testAllFabricante() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
			assertEquals(9,listFab.size());
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	@Test
	void testAllProducto() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
			assertEquals(11,listProd.size());
		
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		

	
	}
	
	/**
	 * 1. Lista los nombres y los precios de todos los productos de la tabla producto
	 */
	@Test
	void test1() {
	
		ProductoHome prodHome = new ProductoHome();
		
		try {
			prodHome.beginTransaction();
			
			List<Producto> listProd = prodHome.findAll();
			
			List<String> listNomPrec = listProd.stream()
					.map(p -> "Nombre: " + p.getNombre() + ", Precio: " + p.getPrecio())
					.collect(toList());
			
			System.out.println("\n1. Lista los nombres y los precios de todos los productos de la tabla producto");
			listNomPrec.forEach(System.out::println);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	
	}
	
	
	/**
	 * 2. Devuelve una lista de Producto completa con el precio de euros convertido a dólares.
	 */
	@Test
	void test2() {
		
		ProductoHome prodHome = new ProductoHome();
		
		try {
			prodHome.beginTransaction();			
			List<Producto> listProd = prodHome.findAll();
			
			System.out.println("\nListado producto precio euros: ");
			listProd.forEach(System.out::println);
			
			//Con map
			List<Producto> listProdDolar = listProd.stream()
									.map(p -> new Producto(p.getFabricante(), p.getNombre(), p.getPrecio() * 0.99))
									.collect(toList());
			
			//Con ForEach (machaca la lista)
			listProd.forEach(p -> new Producto(p.getFabricante(), p.getNombre(), p.getPrecio() * 0.99));
			
			System.out.println("\n2. Devuelve una lista de Producto completa con el precio de euros convertido a dólares.");
			
			System.out.println("\nListado producto precio dólares (con stream y map): ");
			listProdDolar.forEach(System.out::println);
			
			System.out.print("\nListado producto precio dólares (con forEach): ");
			listProd.forEach(System.out::println);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 3. Lista los nombres y los precios de todos los productos, convirtiendo los nombres a mayúscula.
	 */
	@Test
	void test3() {
		
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			List<String> listNomPrecMay = listProd.stream()
												  .map(p -> "Nombre: " + p.getNombre().toUpperCase() + ", Precio: " + p.getPrecio())
												  .collect(toList());
			
			System.out.println("\n3. Lista los nombres y los precios de todos los productos, convirtiendo los nombres a mayúscula.");
			listNomPrecMay.forEach(System.out::println);
		
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 4. Lista el nombre de todos los fabricantes y a continuación en mayúsculas los dos primeros caracteres del nombre del fabricante.
	 */
	@Test
	void test4() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
					
			List<String> listFabDos = listFab.stream()
											 .map(p -> p.getNombre() + " (" + p.getNombre().substring(0, 2).toUpperCase() + ")")
											 .collect(toList());
					
			System.out.println("\n4. Lista el nombre de todos los fabricantes y a continuación en mayúsculas los dos primeros caracteres del nombre del fabricante.");
			listFabDos.forEach(System.out::println);
			
			System.out.println("\nForma 2:");
			var listfabDos2 = listFab.stream()
								.map(f -> new String[] {f.getNombre(), f.getNombre().substring(0, 2)})
								.collect(toList());
			
			listfabDos2.forEach(s -> System.out.println(s[0] + " (" + s[1].toUpperCase() + ")"));
			
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 5. Lista el código de los fabricantes que tienen productos.
	 */
	@Test
	void test5() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
					
			List<Integer> codFab = listFab.stream()
										  .filter(f -> !(f.getProductos().isEmpty()))
										  .map(Fabricante::getCodigo)
										  .collect(toList());
			
			System.out.println("\n5. Lista el código de los fabricantes que tienen productos.");
			System.out.println(codFab);
			
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 6. Lista los nombres de los fabricantes ordenados de forma descendente.
	 */
	@Test
	void test6() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
					
			List<String> nomFab = listFab.stream()
					  .map(Fabricante::getNombre)
					  .distinct()
					  .sorted(reverseOrder())
					  .collect(toList());
			
			System.out.println("\n6. Lista los nombres de los fabricantes ordenados de forma descendente.");
			nomFab.forEach(System.out::println);
			
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 7. Lista los nombres de los productos ordenados en primer lugar por el nombre de forma ascendente y en segundo lugar por el precio de forma descendente.
	 */
	@Test
	void test7() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
					
			//Forma no optimizada
			List<String> nomProd =listProd.stream()
										  .sorted((o1, o2) -> Double.compare(o2.getPrecio(), o1.getPrecio()))
										  .sorted((o1, o2) -> o1.getNombre().compareToIgnoreCase(o2.getNombre())) //Como antes se ordenó por precio, se mantiene el orden si los nombres son iguales
										  .map(Producto::getNombre)
										  .collect(toList());
			
			//Segunda forma (más óptima)
			nomProd =listProd.stream()
					  .sorted(comparing(Producto::getNombre).thenComparing(Producto::getPrecio, reverseOrder()))
					  .map(Producto::getNombre)
					  .collect(toList());
			
			System.out.println("\n7. Lista los nombres de los productos ordenados en primer lugar por el nombre de forma ascendente y en segundo lugar por el precio de forma descendente.");
			System.out.println("Nota: Están ordenados por primera prioridad que es el nombre, y segunda prioridad que es el precio, pero como"
							+ "no hay productos con el mismo nombre, solo se van a ordenar por nombre");
			nomProd.forEach(System.out::println);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 8. Devuelve una lista con los 5 primeros fabricantes.
	 */
	@Test
	void test8() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
					
			List<Fabricante> fabCinco = listFab.stream()
									  .limit(5)
									  .collect(toList());
			
			
			System.out.println("\n8. Devuelve una lista con los 5 primeros fabricantes.");
			fabCinco.forEach(System.out::println);
			
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 9.Devuelve una lista con 2 fabricantes a partir del cuarto fabricante. El cuarto fabricante también se debe incluir en la respuesta.
	 */
	@Test
	void test9() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
					
			List<Fabricante> dosFab = listFab.stream()
											 .skip(3)
											 .limit(2)
											 .collect(toList());
			
			System.out.println("\n9.Devuelve una lista con 2 fabricantes a partir del cuarto fabricante. El cuarto fabricante también se debe incluir en la respuesta.");
			dosFab.forEach(System.out::println);
			
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 10. Lista el nombre y el precio del producto más barato
	 */
	@Test
	void test10() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			List<String> barato = listProd.stream()
										  .sorted((o1, o2) -> Double.compare(o1.getPrecio(), o2.getPrecio()))
										  .limit(1)
										  .map(f -> "Nombre: " + f.getNombre() + ", Precio: " + f.getPrecio())
										  .collect(toList());
			
			//Forma optimizada
			Optional<Producto> barato2 = listProd.stream()
					  					  .collect(minBy(comparing(Producto::getPrecio)));
			
			System.out.println("\n10. Lista el nombre y el precio del producto más barato");
			System.out.println(barato.get(0)); //No hace falta hacer un forEach. También se puede imprimir la lista entera y ya
			System.out.println("Forma optimizada:");
			barato2.ifPresentOrElse(p -> System.out.println("Nombre: " + p.getNombre() + ", Precio: " + p.getPrecio()), () -> System.out.println("No existe el producto"));
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 11. Lista el nombre y el precio del producto más caro
	 */
	@Test
	void test11() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			List<String> caro = listProd.stream()
					  .sorted((o1, o2) -> Double.compare(o2.getPrecio(), o1.getPrecio()))
					  .limit(1)
					  .map(f -> "Nombre: " + f.getNombre() + ", Precio: " + f.getPrecio())
					  .collect(toList());
			
			//Forma optimizada
			Optional<Producto> caro2 = listProd.stream()
												 .collect(maxBy(comparing(Producto::getPrecio)));
			
			System.out.println("\n11. Lista el nombre y el precio del producto más caro");
			System.out.println(caro.get(0)); //No hace falta hacer un forEach. También se puede imprimir la lista entera y ya
			System.out.println("Forma optimizada:");
			caro2.ifPresentOrElse(p -> System.out.println("Nombre: " + p.getNombre() + ", Precio: " + p.getPrecio()), () -> System.out.println("No existe el producto"));
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 12. Lista el nombre de todos los productos del fabricante cuyo código de fabricante es igual a 2.
	 * 
	 */
	@Test
	void test12() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			List<String> nomDos = listProd.stream()
										  .filter(f -> f.getFabricante().getCodigo() == 2)
										  .map(p -> p.getNombre())
										  .collect(toList());
			
			System.out.println("\n12. Lista el nombre de todos los productos del fabricante cuyo código de fabricante es igual a 2.");
			nomDos.forEach(System.out::println);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 13. Lista el nombre de los productos que tienen un precio menor o igual a 120€.
	 */
	@Test
	void test13() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			List<String> nom120 = listProd.stream()
										  .filter(p -> p.getPrecio() <= 120)
										  .map(p -> p.getNombre())
										  .collect(toList());
			
			System.out.println("\n13. Lista el nombre de los productos que tienen un precio menor o igual a 120€.");
			nom120.forEach(System.out::println);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 14. Lista los productos que tienen un precio mayor o igual a 400€.
	 */
	@Test
	void test14() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			List<Producto> prod400 = listProd.stream()
											 .filter(p -> p.getPrecio() >= 400)
											 .collect(toList());
			
			System.out.println("\n14. Lista los productos que tienen un precio mayor o igual a 400€.");
			prod400.forEach(System.out::println);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 15. Lista todos los productos que tengan un precio entre 80€ y 300€. 
	 */
	@Test
	void test15() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();	
			
			List<Producto> prod80Y300 = listProd.stream()
											 	.filter(p -> p.getPrecio() > 80 && p.getPrecio() < 300)
											 	.collect(toList());
			
			System.out.println("\n15. Lista todos los productos que tengan un precio entre 80€ y 300€.");
			prod80Y300.forEach(System.out::println);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 16. Lista todos los productos que tengan un precio mayor que 200€ y que el código de fabricante sea igual a 6.
	 */
	@Test
	void test16() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			List<Producto> productos = listProd.stream()
				 	.filter(p -> p.getPrecio() > 200 && p.getFabricante().getCodigo() == 6)
				 	.collect(toList());

			System.out.println("\n16. Lista todos los productos que tengan un precio mayor que 200€ y que el código de fabricante sea igual a 6.");
			productos.forEach(System.out::println);	
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 17. Lista todos los productos donde el código de fabricante sea 1, 3 o 5 utilizando un Set de codigos de fabricantes para filtrar.
	 */
	@Test
	void test17() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			Set<Integer> codFabricantes = new HashSet<>(Arrays.asList(1, 3, 5)); //Le paso un array directamente, más rápido
			List<Producto> productos = listProd.stream()
											   .filter(p -> codFabricantes.contains(p.getFabricante().getCodigo()))
											   .collect(toList());
			
			System.out.println("\n17. Lista todos los productos donde el código de fabricante sea 1, 3 o 5 utilizando un Set de codigos de fabricantes para filtrar.");
			productos.forEach(System.out::println);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 18. Lista el nombre y el precio de los productos en céntimos.
	 */
	@Test
	void test18() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
			
			List<String> productos = listProd.stream()
											 .map(p -> "Nombre: " + p.getNombre() + ", Precio: " + (p.getPrecio() * 100) + " cts")
											 .collect(toList());
			
			System.out.println("\n18. Lista el nombre y el precio de los productos en céntimos.");
			productos.forEach(System.out::println);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 19. Lista los nombres de los fabricantes cuyo nombre empiece por la letra S
	 */
	@Test
	void test19() {
	
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
					
			List<String> productos = listFab.stream()
					 						 .map(f -> f.getNombre())
					 						 .filter(f -> f.toUpperCase().startsWith("S")) //toUpperCase para hacerlo case insensitive, aunque no hace falta
					 						 .collect(toList());

			System.out.println("\n19. Lista los nombres de los fabricantes cuyo nombre empiece por la letra S");
			productos.forEach(System.out::println);
			
			
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 20. Devuelve una lista con los productos que contienen la cadena Portátil en el nombre.
	 */
	@Test
	void test20() {
	
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			List<String> productos = listProd.stream()
											 .map(p -> p.getNombre())
											 .filter(p -> p.toLowerCase().contains("portátil")) //toLowerCase para hacerlo case insensitive, aunque no hace falta
											 .collect(toList());

			System.out.println("\n20. Devuelve una lista con los productos que contienen la cadena Portátil en el nombre.");
			productos.forEach(System.out::println);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 21. Devuelve una lista con el nombre de todos los productos que contienen la cadena Monitor en el nombre y tienen un precio inferior a 215 €.
	 */
	@Test
	void test21() {
	
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			List<String> productos = listProd.stream()
									 .filter(p -> p.getNombre().toLowerCase().contains("monitor")
											 && p.getPrecio() < 215) //toLowerCase para hacerlo case insensitive, aunque no hace falta
									 .map(p -> p.getNombre())
									 .collect(toList());

			System.out.println("\n21. Devuelve una lista con el nombre de todos los productos que contienen la cadena Monitor en el nombre y tienen un precio inferior a 215 €.");
			productos.forEach(System.out::println);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 22. Lista el nombre y el precio de todos los productos que tengan un precio mayor o igual a 180€. 
	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre (en orden ascendente).
	 */
	@Test
	void test22() {
		
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			List<String> productos = listProd.stream()
					 						 .filter(p -> p.getPrecio() >= 180)
											 .sorted((o1, o2) -> o1.getNombre().compareToIgnoreCase(o2.getNombre())) //No hago thenComparing por el IgnoreCase
											 .sorted((o1, o2) -> Double.compare(o2.getPrecio(), o1.getPrecio())) //Como antes se ordenó por nombre, se mantiene el orden si los precios son iguales
											 .map(p -> "Nombre: " + p.getNombre() + ", Precio: " + p.getPrecio())
											 .collect(toList());
											 
			System.out.println("\n22. Lista el nombre y el precio de todos los productos que tengan un precio mayor o igual a 180€. \n"
					+ "Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre (en orden ascendente).");
			productos.forEach(System.out::println);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 23. Devuelve una lista con el nombre del producto, precio y nombre de fabricante de todos los productos de la base de datos. 
	 * Ordene el resultado por el nombre del fabricante, por orden alfabético.
	 */
	@Test
	void test23() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			List<String> productos = listProd.stream()
											 .sorted((o1, o2) -> o1.getFabricante().getNombre().compareToIgnoreCase(o2.getFabricante().getNombre()))
											 .map(p -> "Nombre: " + p.getNombre() + ", Precio: " + p.getPrecio()
											 + ", Nombre de fabricante: " + p.getFabricante().getNombre())
											 .collect(toList());

			System.out.println("\n23. Devuelve una lista con el nombre del producto, precio y nombre de fabricante de todos los productos de la base de datos. \n"
					+ "Ordene el resultado por el nombre del fabricante, por orden alfabético.");
			productos.forEach(System.out::println);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 24. Devuelve el nombre del producto, su precio y el nombre de su fabricante, del producto más caro.
	 */
	@Test
	void test24() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			List<String> productos = listProd.stream()
					 						 .sorted((o1, o2) -> Double.compare(o2.getPrecio(), o1.getPrecio()))
					 						 .limit(1)
					 						 .map(p -> "Nombre: " + p.getNombre() + ", Precio: " + p.getPrecio() 
					 						 + ", Nombre de fabricante: " + p.getFabricante().getNombre())
					 						 .collect(toList());

			System.out.println("\n24. Devuelve el nombre del producto, su precio y el nombre de su fabricante, del producto más caro.");
			productos.forEach(System.out::println);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 25. Devuelve una lista de todos los productos del fabricante Crucial que tengan un precio mayor que 200€.
	 */
	@Test
	void test25() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			List<Producto> productos = listProd.stream()
											 .filter(p -> p.getFabricante().getNombre().equalsIgnoreCase("Crucial")
													 && p.getPrecio() > 200)
											 .collect(toList());

			System.out.println("\n25. Devuelve una lista de todos los productos del fabricante Crucial que tengan un precio mayor que 200€.");
			productos.forEach(System.out::println);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 26. Devuelve un listado con todos los productos de los fabricantes Asus, Hewlett-Packard y Seagate
	 */
	@Test
	void test26() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			List<Producto> productos = listProd.stream()
											 .filter(p -> p.getFabricante().getNombre().equalsIgnoreCase("Asus")
													 || p.getFabricante().getNombre().equalsIgnoreCase("Hewlett-Packard")
													 || p.getFabricante().getNombre().equalsIgnoreCase("Seagate"))
											 .collect(toList());


			System.out.println("\n26. Devuelve un listado con todos los productos de los fabricantes Asus, Hewlett-Packard y Seagate");
			productos.forEach(System.out::println);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 27. Devuelve un listado con el nombre de producto, precio y nombre de fabricante, de todos los productos que tengan un precio mayor o igual a 180€. 
	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre.
	 * El listado debe mostrarse en formato tabla. Para ello, procesa las longitudes máximas de los diferentes campos a presentar y compensa mediante la inclusión de espacios en blanco.
	 * La salida debe quedar tabulada como sigue:

Producto                Precio             Fabricante
-----------------------------------------------------
GeForce GTX 1080 Xtreme|611.5500000000001 |Crucial
Portátil Yoga 520      |452.79            |Lenovo
Portátil Ideapd 320    |359.64000000000004|Lenovo
Monitor 27 LED Full HD |199.25190000000003|Asus

		Realmente no habría que hacer tanta parafernalia con cuadrarlo si no estuviera la impresora, pero bueno, solucionado
	 */		
	@Test
	void test27() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			List<String> productos = listProd.stream()
											 .filter(p -> p.getPrecio() >= 180)
											 .sorted((o1, o2) -> o1.getNombre().compareToIgnoreCase(o2.getNombre())) //No hago thenComparing por el IgnoreCase
											 .sorted((o1, o2) -> Double.compare(o2.getPrecio(), o1.getPrecio())) //Como antes se ordenó por nombre, se mantiene el orden si los precios son iguales
											 .map(p -> { 
												 int longitud = 31 - p.getNombre().length(); //31 es la longitud del nombre de la impresora
												 String sobrante = "";
												 
												 for(int i = 0; i < longitud; i++) {
													 sobrante += " ";
												 }
												 
												 return p.getNombre() + sobrante + " |" + p.getPrecio() + "\t\t\t|" + p.getFabricante().getNombre();
												})
											 .collect(toList());

			System.out.println("\n27. Devuelve un listado con el nombre de producto, precio y nombre de fabricante, de todos los productos que tengan un precio mayor o igual a 180€. \n"
					+ "	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre.\n"
					+ "	 * El listado debe mostrarse en formato tabla. Para ello, procesa las longitudes máximas de los diferentes campos a presentar y compensa mediante la inclusión de espacios en blanco.");
			System.out.println("\nProducto\t\t\tPrecio\t\t\tFabricante\n"
					+ "-------------------------------------------------------------------------");
			productos.forEach(System.out::println);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 28. Devuelve un listado de los nombres fabricantes que existen en la base de datos, junto con los nombres productos que tiene cada uno de ellos. 
	 * El listado deberá mostrar también aquellos fabricantes que no tienen productos asociados. 
	 * SÓLO SE PUEDEN UTILIZAR STREAM, NO PUEDE HABER BUCLES
	 * La salida debe queda como sigue:
Fabricante: Asus

            	Productos:
            	Monitor 27 LED Full HD
            	Monitor 24 LED Full HD

Fabricante: Lenovo

            	Productos:
            	Portátil Ideapd 320
            	Portátil Yoga 520

Fabricante: Hewlett-Packard

            	Productos:
            	Impresora HP Deskjet 3720
            	Impresora HP Laserjet Pro M26nw

Fabricante: Samsung

            	Productos:
            	Disco SSD 1 TB

Fabricante: Seagate

            	Productos:
            	Disco duro SATA3 1TB

Fabricante: Crucial

            	Productos:
            	GeForce GTX 1080 Xtreme
            	Memoria RAM DDR4 8GB

Fabricante: Gigabyte

            	Productos:
            	GeForce GTX 1050Ti

Fabricante: Huawei

            	Productos:


Fabricante: Xiaomi

            	Productos:

	 */
	@Test
	void test28() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
					
			List<String> fabricantes = listFab.stream()
											  .map(f -> "Fabricante: " + f.getNombre() + "\n\n\t\tProductos:\n\t\t" //Para hacerlo con .reduce, hay que quitar del final de esta línea \n\t\t
												+ f.getProductos().parallelStream()
																  .map(p -> p.getNombre()).collect(joining("\n\t\t"))  + "\n")
											  					  //Si lo queremos hacer con .reduce, lo descomentamos y comentamos la línea .map
//											  					  .reduce("\n", (a, b) -> a + "\t\t" + b) + "\n")
											  .collect(toList());

			System.out.println("\n28. Devuelve un listado de los nombres fabricantes que existen en la base de datos, junto con los nombres productos que tiene cada uno de ellos. \n"
					+ "	 * El listado deberá mostrar también aquellos fabricantes que no tienen productos asociados. \n"
					+ "	 * SÓLO SE PUEDEN UTILIZAR STREAM, NO PUEDE HABER BUCLES");
			fabricantes.forEach(System.out::println);
								
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 29. Devuelve un listado donde sólo aparezcan aquellos fabricantes que no tienen ningún producto asociado.
	 */
	@Test
	void test29() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
					
			List<Fabricante> productos = listFab.stream()
											.filter(f -> f.getProductos().isEmpty())
											.collect(toList());

			System.out.println("\n29. Devuelve un listado donde sólo aparezcan aquellos fabricantes que no tienen ningún producto asociado.");
			productos.forEach(System.out::println);
								
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 30. Calcula el número total de productos que hay en la tabla productos. Utiliza la api de stream.
	 */
	@Test
	void test30() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			short productos = (short) listProd.stream()
									 .count();

			System.out.println("\n30. Calcula el número total de productos que hay en la tabla productos. Utiliza la api de stream.");
			System.out.println(productos);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}

	
	/**
	 * 31. Calcula el número de fabricantes con productos, utilizando un stream de Productos.
	 */
	@Test
	void test31() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			long productos = listProd.stream()
									 		 .map(p -> p.getFabricante())
									 		 .distinct()
									 		 .count();
			
			System.out.println("\n31. Calcula el número de fabricantes con productos, utilizando un stream de Productos.");
			System.out.println(productos);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 32. Calcula la media del precio de todos los productos
	 */
	@Test
	void test32() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			double productos = listProd.stream()
									 		 .collect(averagingDouble(Producto::getPrecio));
			
			System.out.println("\n32. Calcula la media del precio de todos los productos");
			System.out.println(productos);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 33. Calcula el precio más barato de todos los productos. No se puede utilizar ordenación de stream.
	 */
	@Test
	void test33() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			OptionalDouble productos = listProd.stream()
											 .mapToDouble(Producto::getPrecio)
											 .min();
			
			System.out.println("\n33. Calcula el precio más barato de todos los productos. No se puede utilizar ordenación de stream.");
			System.out.println(productos);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 34. Calcula la suma de los precios de todos los productos.
	 */
	@Test
	void test34() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			Double productos = listProd.stream()
									 		 .collect(summingDouble(Producto::getPrecio));
			
			System.out.println("\n34. Calcula la suma de los precios de todos los productos.");
			System.out.println(productos);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 35. Calcula el número de productos que tiene el fabricante Asus.
	 */
	@Test
	void test35() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			short productos = (short) listProd.stream()
											 .filter(p -> p.getFabricante().getNombre().equalsIgnoreCase("Asus"))
											 .count();
			
			System.out.println("\n35. Calcula el número de productos que tiene el fabricante Asus.");
			System.out.println(productos);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 36. Calcula la media del precio de todos los productos del fabricante Asus.
	 */
	@Test
	void test36() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			Double productos = listProd.stream()
											 .filter(p -> p.getFabricante().getNombre().equalsIgnoreCase("Asus"))
											 .collect(averagingDouble(Producto::getPrecio));
			
			System.out.println("\n36. Calcula la media del precio de todos los productos del fabricante Asus.");
			System.out.println(productos);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 37. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos que tiene el fabricante Crucial. 
	 *  Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 */
	@Test
	void test37() {
		
		//TODO ATENCIÓN, en este ejercicio, tu solución no es correcta profe (aunque solo miré este y poco más de tus soluciones antes de enviar), puesto que
		//el conteo debería estar en el campo final (aunque no es relevante) y pedías la media, no el precio total de todos los productos
		//lo he rehecho desde el principio
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
									
			Double[] arrayDouble = listProd.stream()
											.filter(p -> (p.getFabricante().getNombre()).equals("Crucial"))
											.map(p -> new Double[] {p.getPrecio(), p.getPrecio(), p.getPrecio(), null})
											.reduce(new Double[] {null, null, null, 0.0}, (anterior, actual) -> {
						
												if (anterior[0] != null)
													actual[0] = Math.max(anterior[0], actual[0]); //Va cogiendo los máximos
												if (anterior[1] != null)
													actual[1] = Math.min(anterior[1], actual[1]); //Va cogiendo los mínimos
						
												if (anterior[2] != null) { //Hay que hacer otro if para que no coja el primero (0)
													actual[2] = (anterior[2] + actual[2]) / 2; //Va haciendo la media
												}
												
												actual[3] = anterior[3] + 1; //Acumula
						
												return actual;
											});
			
			System.out.println("\n37. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos que tiene el fabricante Crucial. \n"
					+ "	 *  Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como \"acumulador\".");
			Arrays.stream(arrayDouble).forEach(System.out::println);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 38. Muestra el número total de productos que tiene cada uno de los fabricantes. 
	 * El listado también debe incluir los fabricantes que no tienen ningún producto. 
	 * El resultado mostrará dos columnas, una con el nombre del fabricante y otra con el número de productos que tiene. 
	 * Ordene el resultado descendentemente por el número de productos. Utiliza String.format para la alineación de los nombres y las cantidades.
	 * La salida debe queda como sigue:
	 
     Fabricante     #Productos
-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
           Asus              2
         Lenovo              2
Hewlett-Packard              2
        Samsung              1
        Seagate              1
        Crucial              2
       Gigabyte              1
         Huawei              0
         Xiaomi              0

	 */
	@Test
	void test38() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO ATENCIÓN aquí tuve que mirar porque String.format no lo hemos dado, ni en 1º ni en 2º
			Integer longitudMax = listFab.stream().map( f -> f.getNombre().length()).max(Integer::compare).get();			
			List<String> listaString = listFab.stream().map( f -> String.format("%1$" + longitudMax + "s",f.getNombre())
													+ String.format("%1$" + longitudMax + "s",f.getProductos().size())).collect(toList());
			
			System.out.println("\n38. Muestra el número total de productos que tiene cada uno de los fabricantes. \n"
					+ "	 * El listado también debe incluir los fabricantes que no tienen ningún producto. \n"
					+ "	 * El resultado mostrará dos columnas, una con el nombre del fabricante y otra con el número de productos que tiene. \n"
					+ "	 * Ordene el resultado descendentemente por el número de productos. Utiliza String.format para la alineación de los nombres y las cantidades.");
			
			System.out.println(String.format("%1$" + longitudMax + "s","Fabricante") + String.format("%1$" + longitudMax + "s","#Productos"));
			System.out.println(String.format("%1$" + longitudMax + "s","Fabricante")
					.replaceAll(".", "-*") + String.format("%1$" + longitudMax + "s","#Productos").replaceAll(".", "-*"));
			listaString.forEach(System.out::println);
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 39. Muestra el precio máximo, precio mínimo y precio medio de los productos de cada uno de los fabricantes. 
	 * El resultado mostrará el nombre del fabricante junto con los datos que se solicitan. Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 * Deben aparecer los fabricantes que no tienen productos.
	 */
	@Test
	void test39() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			List<String> productos = listFab.stream().map(f -> {
				 		 Double[] arrayDouble = f.getProductos().stream()
																.map(p -> new Double[] {p.getPrecio(), p.getPrecio(), null, p.getPrecio() })
																.reduce(new Double[] {null, null, 0.0, 0.0}, (anterior, actual) -> {
										
																	if (anterior[0] != null)
																		actual[0] = Math.max(anterior[0], actual[0]);
																	if (anterior[1] != null)
																		actual[1] = Math.min(anterior[1], actual[1]);
										
																	actual[2] = anterior[2] + 1;
																	actual[3] = anterior[3] + actual[3];
										
																	return actual;
																});
				 		 								
												 		String precios = "Precio máximo: " + arrayDouble[0] 
																+ ", Precio mínimo: " + arrayDouble[1]
																+ ", Precio medio: " + (arrayDouble[3] / arrayDouble[2]);
				 		 								String resultado = "Fabricante: " + f.getNombre() + " | ";
				 		 								
				 		 								resultado += (arrayDouble[2] == 0.0) ? "No tiene productos" : precios; //Si está vacío, lo dice, si no, lo pone
				 		 								
				 		 								return resultado;
													})
												   .collect(toList());
			
			
			System.out.println("\n39. Muestra el precio máximo, precio mínimo y precio medio de los productos de cada uno de los fabricantes. \n"
					+ "	 * El resultado mostrará el nombre del fabricante junto con los datos que se solicitan. Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como \"acumulador\".\n"
					+ "	 * Deben aparecer los fabricantes que no tienen productos.");
			productos.forEach(System.out::println);
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 40. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos de los fabricantes que tienen un precio medio superior a 200€. 
	 * No es necesario mostrar el nombre del fabricante, con el código del fabricante es suficiente.
	 */
	@Test
	void test40() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			List<String> productos = listFab.stream()
											.map(f -> {
												DoubleSummaryStatistics arrayDouble = f.getProductos().stream()
														//Se puede hacer con lo que está comentado, o resumiendo a solo 1 línea como en este caso
																									  .collect(summarizingDouble(Producto::getPrecio));
//																					   .reduce(new Double[] {null, null, 0.0, 0.0}, (anterior, actual) -> {
//															
//																							if (anterior[0] != null)
//																								actual[0] = Math.max(anterior[0], actual[0]);
//																							if (anterior[1] != null)
//																								actual[1] = Math.min(anterior[1], actual[1]);
//																
//																							actual[2] = anterior[2] + 1;
//																							actual[3] = anterior[3] + actual[3];
//																
//																							return actual;
//																						});
												return new Object[] {f.getCodigo(), arrayDouble.getMax(), arrayDouble.getMin(), arrayDouble.getAverage()};
//												return new Object[] {f.getNombre(), arrayDouble[0], arrayDouble[1], (arrayDouble[3] / arrayDouble[2])};
											  })
											  .filter(f -> ((Double) f[3]) > 200)
											  .map(f -> "Código de fabricante: " + f[0] + " | Precio máximo: " + f[1]
												+ ", Precio mínimo: " + f[2] + ", Precio medio: " + f[3])
											  .collect(toList());
			
			System.out.println("\n40. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos de los fabricantes que tienen un precio medio superior a 200€. \n"
					+ "	 * No es necesario mostrar el nombre del fabricante, con el código del fabricante es suficiente.");
			productos.forEach(System.out::println);
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 41. Devuelve un listado con los nombres de los fabricantes que tienen 2 o más productos.
	 */
	@Test
	void test41() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			List<String> productos = listFab.stream()
									 		 .filter(f -> f.getProductos().size() >= 2)
									 		 .map(Fabricante::getNombre)
									 		 .collect(toList());
			
			System.out.println("\n41. Devuelve un listado con los nombres de los fabricantes que tienen 2 o más productos.");
			productos.forEach(System.out::println);
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 42. Devuelve un listado con los nombres de los fabricantes y el número de productos que tiene cada uno con un precio superior o igual a 220 €. 
	 * Ordenado de mayor a menor número de productos.
	 */
	@Test
	void test42() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS
			List<Object[]> listaObject = listFab.stream().map(f -> {
													return new Object[] {f.getNombre(), f.getProductos().stream().filter(p -> p.getPrecio() >= 200).count()};
												})
													.sorted(comparing(o -> (long) o[1], reverseOrder()))													
													.collect(toList());
			
			
			
			System.out.println("\n42. Devuelve un listado con los nombres de los fabricantes y el número de productos que tiene cada uno con un precio superior o igual a 220 €. \n"
					+ "	 * Ordenado de mayor a menor número de productos.");
			listaObject.forEach(lista -> System.out.println("Fabricante: " + lista[0] + ", Total de productos: " + lista[1]));
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 43.Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 */
	@Test
	void test43() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			List<String> productos = listFab.stream()
									 		 .filter(f -> f.getProductos().stream().collect(summingDouble(Producto::getPrecio)) > 1000)
									 		 .map(Fabricante::getNombre)
									 		 .collect(toList());
			
			System.out.println("\n43.Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €");
			productos.forEach(System.out::println);
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 44. Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 * Ordenado de menor a mayor por cuantía de precio de los productos.
	 */
	@Test
	void test44() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			List<Object> productos = listFab.stream()
									 		 .filter(f -> f.getProductos().stream().collect(summingDouble(Producto::getPrecio)) > 1000)
									 		 .map(f -> {
									 			double cuantia = f.getProductos().stream().collect(summingDouble(Producto::getPrecio));
									 			
									 			return new Object[] {f.getNombre(), cuantia};
									 		 })
									 		 .sorted(comparing(o -> (double) o[1]))
									 		 .map(f -> f[0]) //Solo quiero que se muestre el fabricante
									 		 .collect(toList());
			
			System.out.println("\n44. Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €\n"
					+ "	 * Ordenado de menor a mayor por cuantía de precio de los productos.");
			productos.forEach(System.out::println);
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 45. Devuelve un listado con el nombre del producto más caro que tiene cada fabricante. 
	 * El resultado debe tener tres columnas: nombre del producto, precio y nombre del fabricante. 
	 * El resultado tiene que estar ordenado alfabéticamente de menor a mayor por el nombre del fabricante.
	 * 
	 * Aquí también miré un poco
	 */ 
	@Test
	void test45() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS
			List<Object[]> productos = listFab.stream().map(f -> {
										return new Object[] {f.getNombre(), f.getProductos().stream().collect(maxBy(comparing(Producto::getPrecio))) };
								})																								
														.sorted(comparing(o -> (String)o[0],naturalOrder()))												
														.collect(toList());
			
			System.out.println("\n45. Devuelve un listado con el nombre del producto más caro que tiene cada fabricante. \n"
					+ "	 * El resultado debe tener tres columnas: nombre del producto, precio y nombre del fabricante. \n"
					+ "	 * El resultado tiene que estar ordenado alfabéticamente de menor a mayor por el nombre del fabricante.");
			
			productos.forEach(ao -> System.out.println("Producto: "
					+ (((Optional<Producto>) ao[1]).isPresent() ? ((Optional<Producto>) ao[1]).get().getNombre()
							: "Sin producto")
					+ ", Precio: "
					+ (((Optional<Producto>) ao[1]).isPresent() ? ((Optional<Producto>) ao[1]).get().getPrecio()
							: "Sin producto")
					+ ", Fabricante: " + ao[0]));
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
			
	}
	
	/**
	 * 46. Devuelve un listado de todos los productos que tienen un precio mayor o igual a la media de todos los productos de su mismo fabricante.
	 * Se ordenará por fabricante en orden alfabético ascendente y los productos de cada fabricante tendrán que estar ordenados por precio descendente.
	 * 
	 * Y aquí también, pero por falta de tiempo
	 * 
	 */
	@Test
	void test46() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			List<Object[]> lista = listFab.stream().map(f -> {
				
				final Double precioMedioFab = f.getProductos().stream().collect(averagingDouble(Producto::getPrecio));
				
				return new Object[] {f.getNombre(), 
						f.getProductos().stream()
										.filter(p -> p.getPrecio() >= precioMedioFab)
										.sorted(comparing(Producto::getPrecio, reverseOrder()))
										.collect(toList())
						 			};
								})																					
												  .sorted(comparing(o -> (String)o[0], naturalOrder()))												
												  .collect(toList());
			
			System.out.println("\n46. Devuelve un listado de todos los productos que tienen un precio mayor o igual a la media de todos los productos de su mismo fabricante.\n"
					+ "	 * Se ordenará por fabricante en orden alfabético ascendente y los productos de cada fabricante tendrán que estar ordenados por precio descendente.");
			
			lista.forEach( ao -> { System.out.println(ao[0]);
				if ( ((List<Producto>)ao[1]).isEmpty() ) System.out.println("**Sin Productos**");
				else ((List<Producto>)ao[1]).forEach(System.out::println);									
			});														
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
			
	}
	
}

