package carrocompra;

import java.util.ArrayList;

public class CarroCompra {

	private ArrayList<ProductoCarro> elementos;

	public CarroCompra() {
		elementos = new ArrayList<ProductoCarro>();
	}

	public void setElementos(ArrayList<ProductoCarro> elementos) {
		this.elementos = elementos;
	}

	public ArrayList<ProductoCarro> getElementos() {
		return elementos;
	}

	/**
	 *	AÑADIR PRODUCTOS AL CARRO 
	 */
	public void addElemento(ProductoCarro p) {
		int posicion = elementos.indexOf(p);
		if (posicion == -1) {
			elementos.add(p);
		} else {
			ProductoCarro actual = elementos.get(posicion);
			actual.setCantidad(actual.getCantidad() + 1);
		}
	}

	
	public void restarElemento(ProductoCarro p) {
		int posicion = elementos.indexOf(p);
		if (posicion != -1) {
			ProductoCarro actual = elementos.get(posicion);
			if(actual.getCantidad() > 0){
				actual.setCantidad(actual.getCantidad() - 1);
			}
		} 
	}
	
	public void deleteElemento(ProductoCarro p) {
		int posicion = elementos.indexOf(p);
		if (posicion != -1) {
			elementos.remove(p);
		}
	}
	
	public double getPrecioTotal(){
		double total = 0;
		for(ProductoCarro pc : elementos){
			total = total + (pc.getPrecioNormal()*pc.getCantidad());
		}
		return total;
	}
	
	
	
}
