package util;

public class ComplexNumber {

	double real;
	double imaginary;
	
	
	public ComplexNumber() {
		real = 0;
		imaginary = 0;
	}
	
	public ComplexNumber (double r, double i) {
		real = r;
		imaginary = i;
	}
	
	void addTo (ComplexNumber c) {
		real += c.real;
		imaginary += c.imaginary;
	}

	void subFrom (ComplexNumber c) {
		real -= c.real;
		imaginary -= c.imaginary;
	}

	void multBy (ComplexNumber c) {
		double real1 = real;
		double imaginary1 = imaginary;
		
		real = (real1 * c.real) + (imaginary1 * c.imaginary) * (-1);//-1 is the i^2
		imaginary = (imaginary1 * c.real) + (c.imaginary * real1);
	}

	void squareThisCN() {
		double real1 = real;
		double imaginary1 = imaginary;
		
		real = Math.pow(real1, 2) + Math.pow(imaginary1, 2) * -1;
		imaginary = 2 * (real1 * imaginary1);
	}
		
	ComplexNumber add (ComplexNumber c) {
		ComplexNumber complex = new ComplexNumber();
		complex.real = real + c.real;
		complex.imaginary = imaginary + c.imaginary;
		return complex;
	}
	
	ComplexNumber sub (ComplexNumber c) {
		ComplexNumber complex = new ComplexNumber();
		complex.real = real - c.real;
		complex.imaginary = imaginary - c.imaginary;
		return complex;
	}
		
	ComplexNumber mult (ComplexNumber c) {
		ComplexNumber complex = new ComplexNumber();
		complex.real = (real * c.real) + (imaginary * c.imaginary) * (-1);//-1 is the i^2
		complex.imaginary = (imaginary * c.real) + (c.imaginary * real);	
		return complex;
	}
	
	ComplexNumber square() {
		ComplexNumber complex = new ComplexNumber();
		complex.real = Math.pow(real, 2) + Math.pow(imaginary, 2) * -1;
		complex.imaginary = 2 * (real * imaginary);
		return complex;
	}
	
	
	void clear() {
		real = 0;
		imaginary = 0;
	}
	
	void set(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	ComplexNumber exponent(ComplexNumber c, int e) {
		ComplexNumber complex = c;
		for (int i = 1; i < e; i++) {
			complex = complex.mult(c);
		}
		
		return complex;
	}
	
	
/*	ComplexNumber cube(ComplexNumber c) {
		
		ComplexNumber complex = c;//new ComplexNumber();
		for (int i = 0; i < MandelbrotGUI.getZExponent() ; i++) {
			complex = complex.mult(c);
		}
		
		//complex = (c.square()).mult(c); 
		return complex;
	}
	*/
	
	
	
	
	boolean equals (ComplexNumber c) {
		if(real == c.real && imaginary == c.imaginary) return true;
		else return false;
	}
	
	double modulus() {
		
		double mod = Math.sqrt((Math.pow(real, 2))+(Math.pow(imaginary, 2)));
		return mod;
	}
	
	double modSquare() {
		
		double ms = Math.pow(Math.sqrt((Math.pow(real, 2))+(Math.pow(imaginary, 2))), 2);
		return ms;
	}
	
	void display () {	
		System.out.println(real+" + "+imaginary+"i");
	}
		
}


