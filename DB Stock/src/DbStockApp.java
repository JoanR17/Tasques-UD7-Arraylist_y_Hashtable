import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JOptionPane;

public class DbStockApp {

	public static void main(String[] args) {

		Hashtable<String, Double> productoPrecio = new Hashtable<String, Double>();
		Hashtable<String, Integer> productoCantidad = new Hashtable<String, Integer>();

		generar10Articulos(productoPrecio, productoCantidad);
		
		int op;
		
		do
		{
			try
			{
				op = Integer.parseInt(JOptionPane.showInputDialog("Escoge una opción:\n\n1. Añadir nuevo articulo\n2. Consultar Datos Articulo\n3. Mostrar Todos Los Datos\n4. Salir"));
				
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
		while(op != 4);
	}

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
