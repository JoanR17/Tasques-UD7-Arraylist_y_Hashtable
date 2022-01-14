import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ListaSupermercadoApp {

	public static void main(String[] args) {

		ArrayList<ArrayList<String>> productos = new ArrayList<>();
		
		introducirProductos(productos);
		
		mostrarTicket(productos);

	}
	
	/**
	 * Introduccion de los productos que se tienen que pagar
	 * @param productos
	 */
	public static void introducirProductos(ArrayList<ArrayList<String>> productos) 
	{
		int cantidad;
		
		do
		{
			try 
			{
				do 
				{
					cantidad = Integer.parseInt(JOptionPane.showInputDialog("Introduce cantidad de nuevo producto (0 para pagar)."));
					
					if(cantidad < 0)
					{
						JOptionPane.showMessageDialog(null, "Tienes que introducir una cantidad positiva");
					}
				} 
				while (cantidad < 0);
				
				if(cantidad != 0)
				{
					productos.add(introducirProducto(cantidad));
				}
			} 
			catch (Exception e) 
			{
				cantidad = -1;
			}
		}
		while(cantidad != 0);
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
	 * Devuelve los datos sobre un producto en formato arraylist
	 * @param cantidad
	 * @return
	 */
	public static ArrayList<String> introducirProducto(int cantidad) 
	{
		ArrayList<String> producto = new ArrayList<String>();
		
		double precio;
		double iva;
		
		do
		{
			try 
			{
				precio = (double)Math.round(Double.parseDouble(JOptionPane.showInputDialog("Indica el precio del producto.")) * 100) / 100 ;
			} 
			catch (Exception e) 
			{
				precio = 0;
			}
		}
		while(precio <= 0.0);
		
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
		
		return producto;
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
	
	/**
	 * Devuelve el total de un producto con IVA incluido
	 * @param arrayList
	 * @return
	 */
	public static double precioProductoIVA(ArrayList<String> arrayList)
	{
		return (double)Math.round((double)Integer.parseInt(arrayList.get(0)) * (Double.parseDouble(arrayList.get(1)) + Double.parseDouble(arrayList.get(1)) * Double.parseDouble(arrayList.get(2))) * 100) / 100;
	}
}
