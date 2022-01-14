import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.JOptionPane;

public class NotaMediaApp {

	public static void main(String[] args) {

		Hashtable<String,String> notasAlumnos = new Hashtable<String,String>();
		
		int op = pedirOpcion();
		
		while(op != 2)
		{
			//Si hubieran mas opciones usariamos un switch
			
			introducirNuevoAlumno(notasAlumnos);
			
			op = pedirOpcion();
		}
		
		mostrarDatos(notasAlumnos);
	}

	/**
	 * Funcion para mostrar los datos de un Hashtable clave - valor
	 * @param notasAlumnos
	 */
	public static void mostrarDatos(Hashtable<String, String> notasAlumnos) 
	{
		Enumeration<String> nombres = notasAlumnos.keys();
		Enumeration<String> notas = notasAlumnos.elements();
		String str = "";
		
		while (nombres.hasMoreElements()) 
		{
			str += "Alumno / Nota Media: " + nombres.nextElement() + " / " + notas.nextElement() + "\n";
		}
		
		if(!str.equals(""))
		{
			JOptionPane.showMessageDialog(null, str);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "No se han introducido datos");
		}
	}

	/**
	 * Menu que retorna opcion del usuario
	 * @return
	 */
	public static int pedirOpcion()
	{
		int x;

		do 
		{
			try 
			{
				x = Integer.parseInt(JOptionPane.showInputDialog("Elige una opción(1-2):\n1. Insertar notas Alumno nuevo. 2. Salir"));
			} 
			catch (Exception e) 
			{
				x = 0;
			}
			
			if(x != 1 && x != 2)
			{
				JOptionPane.showMessageDialog(null, "Opcion no valida, introduzca 1 o 2");
			}
		} 
		while (x != 1 && x != 2);
		
		return x;
	}
	
	/**
	 * Intoduccion de datos al Hashtable de notas
	 * @param notasAlumnos
	 */
	public static void introducirNuevoAlumno(Hashtable<String, String> notasAlumnos) 
	{
		String nombreAlumno = JOptionPane.showInputDialog("Introduzca el nombre del alumno.");
		String notaMedia = introducirNotas();

		notasAlumnos.put(nombreAlumno, notaMedia);
	}

	/**
	 * Funcion que devuelve la media calculada de las notas a pedir
	 * @return
	 */
	public static String introducirNotas() 
	{
		ArrayList<Double> notas = new ArrayList<>();
		
		añadirNotas(notas);
		
		return "" + calcularMedia(notas);
	}

	/**
	 * Funcion que pide al usuario notas y que contola la introduccion de estas
	 * @param notas
	 */
	public static void añadirNotas(ArrayList<Double> notas) 
	{
		double x;
		int i = 0;
		
		do 
		{
			try 
			{
				x = Double.parseDouble(JOptionPane.showInputDialog("Añade notas, para salir introduce -1."));
				if(x >= 0 && x <= 10)
				{
					notas.add(x);
					i++;
				}
				else if(x > 10 || x < -1)
				{
					JOptionPane.showMessageDialog(null, "Debes introducir una nota valida 0-10.");
				}
				else if(x == -1 && i == 0) 
				{
					JOptionPane.showMessageDialog(null, "Debes introducir minimo una nota");
				}
			} 
			catch (Exception e) 
			{
				x = 0;
			}
		} 
		while (x != -1 || i < 1);
	}

	/**
	 * Funcion que calcula la media de las notas introducidas por el usuario
	 * @param notas
	 * @return
	 */
	public static double calcularMedia(ArrayList<Double> notas) 
	{
		Double suma = 0.0;
		Iterator<Double> it = notas.iterator();
		
		while (it.hasNext()) 
		{
			suma += it.next();
		}
		
		return (double)Math.round((suma / notas.size())* 100)/100;
	}

}
