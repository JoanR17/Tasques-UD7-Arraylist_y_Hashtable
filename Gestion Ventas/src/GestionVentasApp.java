import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JOptionPane;

public class GestionVentasApp {

	public static void main(String[] args) {

		Hashtable<String, Double> productoPrecio = new Hashtable<String, Double>();
		Hashtable<String, Integer> productoCantidad = new Hashtable<String, Integer>();
		ArrayList<ArrayList<String>> productos = new ArrayList<>();

		generar10Articulos(productoPrecio, productoCantidad);
		
		int op;
		
		do
		{
			try
			{
				op = Integer.parseInt(JOptionPane.showInputDialog("Escoge una opción:\n\n1. Añadir nuevo articulo\n2. Consultar Datos Articulo\n3. Mostrar Todos Los Datos\n4. Comprar Productos\n5. Salir\n\n"));
				
				switch (op) 
				{
					case 1:
							String articulo = JOptionPane.showInputDialog("Introduzca el codigo del articulo.");
							
							if(existeProducto(productoPrecio, articulo))
							{
								editarProducto(productoCantidad, articulo);
							}
							else
							{
								crearProducto(productoPrecio, productoCantidad, articulo);
							}
							
						break;
						
					case 2:
							mostrarUnArticulo(productoPrecio, productoCantidad);
					break;
					
					case 3:
							mostrarProductos(productoPrecio, productoCantidad);
					break;
					
					case 4:
							introducirProductos(productos, productoPrecio, productoCantidad);

							mostrarTicket(productos);
					break;
					
					case 5:
							JOptionPane.showMessageDialog(null, "Fin de programa");
					break;
			
					default:
							JOptionPane.showMessageDialog(null, "Opcion no valida");
						break;
				}
			}
			catch(Exception e)
			{
				op = 0;
			}
		}
		while(op != 5);

	}

	/******************************************************************* FUNCIONES COMPRA **************************************************************/
	
	/**
	 * Introduccion de los productos que se tienen que pagar
	 * @param productos
	 * @param productoCantidad 
	 * @param productoPrecio 
	 */
	public static void introducirProductos(ArrayList<ArrayList<String>> productos, Hashtable<String, Double> productoPrecio, Hashtable<String, Integer> productoCantidad) 
	{
		String articulo;
		do
		{
			articulo = JOptionPane.showInputDialog("Introduzca el codigo del articulo.(Codigo \"Pagar\" para finalizar)");
			if(!articulo.equalsIgnoreCase("pagar"))
			{
				if(existeProducto(productoPrecio, articulo))
				{
					int cantidad;
					
						try 
						{
							do 
							{
								cantidad = Integer.parseInt(JOptionPane.showInputDialog("Introduce cantidad del producto."));
								
								if(cantidad < 0)
								{
									JOptionPane.showMessageDialog(null, "Tienes que introducir una cantidad positiva");
								}
							} 
							while (cantidad < 0);
							
							if(cantidad != 0)
							{
								if(hayStock(cantidad, productoCantidad, articulo))
								{
									productos.add(introducirProducto(cantidad, productoPrecio, productoCantidad, articulo));
								}
								else
								{
									JOptionPane.showMessageDialog(null, "No tenemos tanto stock de este producto.");
								}
							}
						} 
						catch (Exception e) 
						{
							cantidad = -1;
						}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "No tenemos stock de este producto.");
				}
			}
		}
		while(!articulo.equalsIgnoreCase("Pagar"));
	}
	
	/**
	 * Funcion para saber si hay suficiente stock de un producto
	 * @param cantidad
	 * @param productoCantidad
	 * @param articulo
	 * @return
	 */
	public static boolean hayStock(int cantidad, Hashtable<String, Integer> productoCantidad, String articulo) 
	{		
		if(productoCantidad.get(articulo) < cantidad)
		{
			return false;
		}
		return true;
	}

	/**
	 * Devuelve los datos sobre un producto en formato arraylist
	 * @param cantidad
	 * @param articulo 
	 * @return
	 */
	public static ArrayList<String> introducirProducto(int cantidad, Hashtable<String, Double> productoPrecio, Hashtable<String, Integer> productoCantidad, String articulo) 
	{
		ArrayList<String> producto = new ArrayList<String>();
		
		double precio = productoPrecio.get(articulo);
		double iva;
		
		do
		{
			try 
			{
				iva = Double.parseDouble(JOptionPane.showInputDialog("Indica el iva (0,04 o 0,21)."));
			} 
			catch (Exception e) 
			{
				iva = 0;
			}
		}
		while(iva != 0.21 && iva != 0.04);
		
		producto.add(""+cantidad);
		producto.add(""+precio);
		producto.add(""+iva);
		
		if(productoCantidad.get(articulo) > cantidad)
		{
			productoCantidad.replace(articulo, (productoCantidad.get(articulo)-cantidad)); //Quitamos de stock los productos vendidos
		}
		else //Si no hay cantidad lo quitamos de stock
		{
			productoCantidad.remove(articulo);
			productoPrecio.remove(articulo);
		}
		
		return producto;
	}

	/**
	 * Pide el dinero al cliente y comprueba que este sea correcto
	 * @param total
	 * @return
	 */
	public static double dineroCliente(double total) 
	{
		double dinero;
		
		do
		{
			try 
			{
				dinero = (double)Math.round(Double.parseDouble(JOptionPane.showInputDialog("Introduce el dinero de pago.")) * 100) / 100 ;
				if(dinero < total)
				{
					JOptionPane.showMessageDialog(null, "Esta cantidad es inferior a la pedida.");
				}
			} 
			catch (Exception e) 
			{
				dinero = 0;
			}
		}
		while(dinero < total);
		
		return dinero;
	}

	/**
	 * Devuelve la suma del precio de los productos con todas sus cantidades incluyendo IVA
	 * @param productos
	 * @return
	 */
	public static double totalPagar(ArrayList<ArrayList<String>> productos) 
	{
		double suma = 0;
		
		for (ArrayList<String> arrayList : productos) 
		{
			suma += precioProductoIVA(arrayList);
		}
		
		return (double)Math.round(suma * 100) / 100;
	}

	/**
	 * Devuelve la suma del precio de los productos con todas sus cantidades sin incluir el IVA
	 * @param productos
	 * @return
	 */
	public static double totalPagarSenseIVA(ArrayList<ArrayList<String>> productos) 
	{
		double suma = 0;
		
		for (ArrayList<String> arrayList : productos) 
		{
			suma += ((double)Integer.parseInt(arrayList.get(0)) * Double.parseDouble(arrayList.get(1)));
		}
		
		return (double)Math.round(suma * 100) / 100;
	}
	
	/**
	 * Devuelve el total de un producto con IVA incluido
	 * @param arrayList
	 * @return
	 */
	public static double precioProductoIVA(ArrayList<String> arrayList)
	{
		return (double)Math.round((double)Integer.parseInt(arrayList.get(0)) * (Double.parseDouble(arrayList.get(1)) + Double.parseDouble(arrayList.get(1)) * Double.parseDouble(arrayList.get(2))) * 100) / 100;
	}

	/**
	 * Muestra el ticket final
	 * @param productos
	 */
	public static void mostrarTicket(ArrayList<ArrayList<String>> productos) 
	{
		double total = totalPagar(productos);
		
		if(total != 0)
		{
			JOptionPane.showMessageDialog(null, "Tienes que pagar un total de " + total + "€");
			
			double pagado = dineroCliente(total);
			
			String str = "";
			int i = 1;
			int suma = 0;
			
			for (ArrayList<String> arrayList : productos) 
			{
				str += i + "- " + arrayList.get(0) + " * " + arrayList.get(1) + "€ " + (int)(Double.parseDouble(arrayList.get(2)) * 100) + "% = " + precioProductoIVA(arrayList) + "€\n";
				suma += Integer.parseInt(arrayList.get(0));
				i++;
			}
			
			str += "\n\n Nº Total de articulos " + suma + "\n\nPrecio sin IVA = " + totalPagarSenseIVA(productos) + "€\n\nPrecio Final = " + total + "€\n\nCantidad pagada = " + pagado + "€\n\nCambio = " + (double)Math.round((pagado - total) * 100) / 100 + "€";
			
			JOptionPane.showMessageDialog(null, str);
		}
		
		JOptionPane.showMessageDialog(null, "Gracias por su visita.");
	}
	
	
	
	/******************************************************************* FUNCIONES ADMINISTRACION ******************************************************/

	/**
	 * Funcion para tener unos articulos base
	 * @param productoPrecio
	 * @param productoCantidad
	 */
	public static void generar10Articulos(Hashtable<String, Double> productoPrecio, Hashtable<String, Integer> productoCantidad) 
	{

		productoPrecio.put("COD001", 1.65);
		productoPrecio.put("COD002", 10.95);
		productoPrecio.put("COD003", 0.99);
		productoPrecio.put("COD004", 4.50);
		productoPrecio.put("COD005", 6.25);
		productoPrecio.put("COD006", 12.39);
		productoPrecio.put("COD007", 9.99);
		productoPrecio.put("COD008", 7.0);
		productoPrecio.put("COD009", 2.25);
		productoPrecio.put("COD010", 5.35);
		productoCantidad.put("COD001", 1);
		productoCantidad.put("COD002", 1);
		productoCantidad.put("COD003", 1);
		productoCantidad.put("COD004", 1);
		productoCantidad.put("COD005", 1);
		productoCantidad.put("COD006", 1);
		productoCantidad.put("COD007", 1);
		productoCantidad.put("COD008", 1);
		productoCantidad.put("COD009", 1);
		productoCantidad.put("COD010", 1);

	}

	/**
	 * Funcion para añadir un nuevo producto a stock
	 * @param productoPrecio
	 * @param productoCantidad
	 * @param nombre
	 */
	public static void crearProducto(Hashtable<String, Double> productoPrecio, Hashtable<String, Integer> productoCantidad, String nombre)
	{
		productoPrecio.put(nombre, pedirPrecio());
		productoCantidad.put(nombre, pedirCantidad());
	}

	/**
	 * Funcion para añadir stock de un producto
	 * @param productoCantidad
	 * @param nombre
	 */
	public static void editarProducto(Hashtable<String, Integer> productoCantidad, String nombre)
	{
		productoCantidad.replace(nombre, (productoCantidad.get(nombre) + pedirCantidad()));
	}

	/**
	 * Funcion para saber si un producto intoducido ya existe
	 * @param productoPrecio
	 * @param nombre
	 * @return
	 */
	public static boolean existeProducto(Hashtable<String, Double> productoPrecio, String nombre)
	{
		Enumeration<String> productos = productoPrecio.keys();
		boolean existe = false;
		
		while (productos.hasMoreElements() && !existe) 
		{
			if(productos.nextElement().equals(nombre))
			{
				existe = true;
			}
		}
		
		return existe;
	}

	/**
	 * Funcion para pedir cantidad al usuario
	 * @return
	 */
	public static int pedirCantidad()
	{
		int cantidad;

		do 
		{
			try 
			{
				cantidad = Integer.parseInt(JOptionPane.showInputDialog("Introduce la cantidad del producto."));

				if(cantidad <= 0)
				{
					JOptionPane.showMessageDialog(null, "Tienes que introducir una cantidad positiva");
				}
			} 
			catch (Exception e) 
			{
				cantidad = -1;
			}
		} 
		while (cantidad <= 0);

		return cantidad;
	}

	/**
	 * Funcion para pedir precio al usuario
	 * @return
	 */
	public static double pedirPrecio()
	{
		double precio;

		do
		{
			try 
			{
				precio = (double)Math.round(Double.parseDouble(JOptionPane.showInputDialog("Indica el precio del producto.")) * 100) / 100 ;

				if(precio <= 0.0)
				{
					JOptionPane.showMessageDialog(null, "Tienes que introducir una precio positivo");
				}
			} 
			catch (Exception e) 
			{
				precio = 0;
			}
		}
		while(precio <= 0.0);

		return precio;
	}
	
	/**
	 * Funcion que lista todos los productos y sus datos
	 * @param productoPrecio
	 * @param productoCantidad
	 */
	public static void mostrarProductos(Hashtable<String, Double> productoPrecio, Hashtable<String, Integer> productoCantidad)
	{
		Enumeration<String> productos = productoPrecio.keys();
		String str = "";
		
		while (productos.hasMoreElements()) 
		{
			String producto = productos.nextElement();
			str += "Codigo/Precio/Cantidad :: " + producto + "/" + productoPrecio.get(producto) + "€/" + productoCantidad.get(producto) + "\n\n";
		}
		
		JOptionPane.showMessageDialog(null, str);
	}

	/**
	 * Funcion que pide un articulo comprueba que exita y devuelve sus datos
	 * @param productoPrecio
	 * @param productoCantidad
	 */
	public static void mostrarUnArticulo(Hashtable<String, Double> productoPrecio, Hashtable<String, Integer> productoCantidad) 
	{
		Enumeration<String> productos = productoPrecio.keys();
		String str, codPro;
		
		str = "Que producto quieres: ";
		
		while (productos.hasMoreElements()) 
		{
			String producto = productos.nextElement();
			str += "Codigo: " + producto + "\n\n";
		}
		
		do 
		{	
			try 
			{
				codPro = JOptionPane.showInputDialog(str);
				if(!existeProducto(productoPrecio, codPro))
				{
					JOptionPane.showMessageDialog(null, "Este codigo no esta en stock.");
				}
			} 
			catch (Exception e) 
			{
				codPro = "";
			}
		}
		while(!existeProducto(productoPrecio, codPro));
			
		JOptionPane.showMessageDialog(null, "El producto " + codPro + " tiene un precio de " + productoPrecio.get(codPro) + "€ y hay una cantidad en stock de " + productoCantidad.get(codPro));
		
	}

}
