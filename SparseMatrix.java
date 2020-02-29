
/* Put your student number here - 
 * 1719368
 * Optionally, if you have any comments regarding your submission, put them here. 
 * For instance, specify here if your program does not generate the proper output or does not do it in the correct manner.
 */

import java.util.*;
import java.io.*;
import java.lang.Math;
// A class that represents a dense vector and allows you to read/write its elements
class DenseVector {
	private int[] elements;

	public DenseVector(int n) {
		elements = new int[n];
	}

	public DenseVector(String filename) {
		File file = new File(filename);
		ArrayList<Integer> values = new ArrayList<Integer>();

		try {
			Scanner sc = new Scanner(file);

			while (sc.hasNextInt()) {
				values.add(sc.nextInt());
			}

			sc.close();

			elements = new int[values.size()];
			for (int i = 0; i < values.size(); ++i) {
				elements[i] = values.get(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Read an element of the vector
	public int getElement(int idx) {
		return elements[idx];
	}

	// Modify an element of the vector
	public void setElement(int idx, int value) {
		elements[idx] = value;
	}

	// Return the number of elements
	public int size() {
		return (elements == null) ? 0 : (elements.length);
	}

	// Print all the elements
	public void print() {
		if (elements == null) {
			return;
		}

		for (int i = 0; i < elements.length; ++i) {
			System.out.println(elements[i]);
		}
	}
}

// A class that represents a sparse matrix
public class SparseMatrix {
	// Auxiliary function that prints out the command syntax
	public static void printCommandError() {
		System.err.println("ERROR: use one of the following commands");
		System.err.println(" - Read a matrix and print information: java SparseMatrix -i <MatrixFile>");
		System.err.println(" - Read a matrix and print elements: java SparseMatrix -r <MatrixFile>");
		System.err.println(" - Transpose a matrix: java SparseMatrix -t <MatrixFile>");
		System.err.println(" - Add two matrices: java SparseMatrix -a <MatrixFile1> <MatrixFile2>");
		System.err.println(" - Matrix-vector multiplication: java SparseMatrix -v <MatrixFile> <VectorFile>");
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			printCommandError();
			System.exit(-1);
		}

		if (args[0].equals("-i")) {
			if (args.length != 2) {
				printCommandError();
				System.exit(-1);
			}

			SparseMatrix mat = new SparseMatrix();
			mat.loadEntries(args[1]);
			System.out.println("Read matrix from " + args[1]);
			System.out.println("The matrix has " + mat.getNumRows() + " rows and " + mat.getNumColumns() + " columns");
			System.out.println("It has " + mat.numNonZeros() + " non-zeros");
		} else if (args[0].equals("-r")) {
			if (args.length != 2) {
				printCommandError();
				System.exit(-1);
			}

			SparseMatrix mat = new SparseMatrix();
			mat.loadEntries(args[1]);
			System.out.println("Read matrix from " + args[1] + ":");
			mat.print();
		} else if (args[0].equals("-t")) {
			if (args.length != 2) {
				printCommandError();
				System.exit(-1);
			}

			SparseMatrix mat = new SparseMatrix();
			mat.loadEntries(args[1]);
			System.out.println("Read matrix from " + args[1]);
			SparseMatrix transpose_mat = mat.transpose();
			System.out.println();
			System.out.println("Matrix elements:");
			mat.print();
			
			System.out.println();
			System.out.println("Transposed matrix elements:");
			transpose_mat.print();
		} else if (args[0].equals("-a")) {
			if (args.length != 3) {
				printCommandError();
				System.exit(-1);
			}

			SparseMatrix mat1 = new SparseMatrix();
			mat1.loadEntries(args[1]);
			System.out.println("Read matrix 1 from " + args[1]);
			System.out.println("Matrix elements:");
			mat1.print();

			System.out.println();
			SparseMatrix mat2 = new SparseMatrix();
			mat2.loadEntries(args[2]);
			System.out.println("Read matrix 2 from " + args[2]);
			System.out.println("Matrix elements:");
			mat2.print();
			SparseMatrix mat_sum1 = mat1.add(mat2);

			System.out.println();
			mat1.multiplyBy(2);
			SparseMatrix mat_sum2 = mat1.add(mat2);

			mat1.multiplyBy(5);
			SparseMatrix mat_sum3 = mat1.add(mat2);

			System.out.println("Matrix1 + Matrix2 =");
			mat_sum1.print();
			System.out.println();

			System.out.println("Matrix1 * 2 + Matrix2 =");
			mat_sum2.print();
			System.out.println();

			System.out.println("Matrix1 * 10 + Matrix2 =");
			mat_sum3.print();
		} else if (args[0].equals("-v")) {
			if (args.length != 3) {
				printCommandError();
				System.exit(-1);
			}

			SparseMatrix mat = new SparseMatrix();
			mat.loadEntries(args[1]);
			DenseVector vec = new DenseVector(args[2]);
			DenseVector mv = mat.multiply(vec);

			System.out.println("Read matrix from " + args[1] + ":");
			mat.print();
			System.out.println();

			System.out.println("Read vector from " + args[2] + ":");
			vec.print();
			System.out.println();

			System.out.println("Matrix-vector multiplication:");
			mv.print();
		}
	}

	// Loading matrix entries from a text file
	// You need to complete this implementation
	public void loadEntries(String filename) {
		File file = new File(filename);

		try {
			Scanner sc = new Scanner(file);
			numRows = sc.nextInt();
			numCols = sc.nextInt();
			entries = new ArrayList<Entry>();

			while (sc.hasNextInt()) {
				// Read the row index, column index, and value of an element
				int row = sc.nextInt();
				int col = sc.nextInt();
				int val = sc.nextInt();

				int position = 0;
				position =  row * (numCols);
				position = position + col ;
				// Add your code here to add the element into data member entries
				Entry newEntry = new Entry( position, val);
				entries.add(newEntry);
				//System.out.println(position);
				//System.out.println(val);
			}
						// Add your code here for sorting non-zero elements
				/*for(int i = 0 ; i <= entries.size() -1 ; i++){
			    	System.out.print(entries.get(i).getPosition());
			    	System.out.print(" - ");
			    	System.out.println(entries.get(i).getValue());
			    }
			    System.out.println("After Sort");
				heapsort(entries);
			    for(int i = 0 ; i <= entries.size() -1 ; i++){
			    	System.out.print(entries.get(i).getPosition());
			    	System.out.print(" - ");
			    	System.out.println(entries.get(i).getValue());
			    }*/
			    heapsort(entries);
			
		} catch (Exception e) {
			e.printStackTrace();
			numRows = 0;
			numCols = 0;
			entries = null;
		}
	}
	public void heapsort(ArrayList<Entry> entries){

		int n = entries.size();

		//build heap
		for(int i = n/ 2-1 ; i >=0;i--){
			buildheap(entries, n, i);
		}
		for (int i=n-1; i>=0; i--) 
        { 
            // Move current root to end 

            Entry temp = entries.get(0); 
            entries.set(0,entries.get(i))  ; 
            entries.set(i, temp); 
  
            // call max buildheap on the reduced heap 
            buildheap(entries, i, 0); 
        }
	}
	public void buildheap(ArrayList<Entry> entries, int n, int i){
		int largest = i; // Initialize largest as root 
        int l = 2*i + 1; // left = 2*i + 1 
        int r = 2*i + 2; // right = 2*i + 2 

        // If left child is larger than root 
        if (l < n && entries.get(l).getPosition() > entries.get(largest).getPosition()) 
            largest = l;
         if (r < n && entries.get(r).getPosition() > entries.get(largest).getPosition()) 
            largest = r; 

  		 if (largest != i) 
        { 
        	Entry swap = entries.get(i); 
            entries.set(i,entries.get(largest))  ; 
            entries.set(largest, swap);
           
  
            // Recursively heapify the affected sub-tree 
            buildheap(entries, n, largest); 
        } 
	}
	// Default constructor
	public SparseMatrix() {
		numRows = 0;
		numCols = 0;
		entries = null;
	}

	// A class representing a pair of column index and elements
	private class Entry {
		private int position; // Position within row-major full array representation
		private int value; // Element value

		// Constructor using the column index and the element value
		public Entry(int pos, int val) {
			this.position = pos;
			this.value = val;
		}

		// Copy constructor
		public Entry(Entry entry) {
			this(entry.position, entry.value);
		}

		// Read column index
		int getPosition() {
			return position;
		}

		// Set column index
		void setPosition(int pos) {
			this.position = pos;
		}

		// Read element value
		int getValue() {
			return value;
		}

		// Set element value
		void setValue(int val) {
			this.value = val;
		}
	}

	// Adding two matricesvoid
	public SparseMatrix add(SparseMatrix M) {
		
		
		int CurrentPointer = 0;
		int MPointer = 0;
		Entry current = entries.get(CurrentPointer);
		Entry Mcurrent = M.entries.get(MPointer);

		SparseMatrix V = new SparseMatrix();
		V.entries = new ArrayList<Entry>();
		V.numRows = numRows;
		V.numCols = numCols;
		
		int Vval;
		//System.out.println(maxPosition);
		int i = 0;
		
		Mcurrent = M.entries.get(MPointer);
		current = entries.get(CurrentPointer);
		
		while(CurrentPointer < entries.size() || MPointer < M.entries.size()){
			
			
		

			if( current.getPosition()>Mcurrent.getPosition() && MPointer < M.entries.size()-1){
				i = Mcurrent.getPosition();
			}else if( current.getPosition()<Mcurrent.getPosition() && CurrentPointer < entries.size()-1  ){
				i = current.getPosition();
			}else if(current.getPosition()==Mcurrent.getPosition()){
				i = current.getPosition();
			}else if(CurrentPointer > entries.size() -1){
				i = Mcurrent.getPosition();
			}else if( MPointer > M.entries.size() -1 ){
				i = current.getPosition();
			}


			
			if(Mcurrent.getPosition() == current.getPosition() && Mcurrent.getPosition() == i && current.getPosition() == i ){
				Vval = Mcurrent.getValue() + current.getValue();	
				
				Entry newEntry = new Entry( i,Vval);
				V.entries.add(newEntry);
				
				MPointer = MPointer + 1;
				
				if(MPointer < M.entries.size() ){
					
					Mcurrent = M.entries.get(MPointer);
				}
				

				CurrentPointer = CurrentPointer + 1;
				if(CurrentPointer < entries.size()){
					
					current = entries.get(CurrentPointer);
				}
				

			}else if(Mcurrent.getPosition() == i){
				Vval = Mcurrent.getValue() ;	
				
				Entry newEntry = new Entry( i,Vval);
				V.entries.add(newEntry);
				
				MPointer = MPointer + 1;

				if(MPointer < M.entries.size()){
					Mcurrent = M.entries.get(MPointer);
				}
			}else if(current.getPosition() == i) {
				Vval = current.getValue() ;	
				
				Entry newEntry = new Entry( i,Vval);
				V.entries.add(newEntry);
				
				CurrentPointer = CurrentPointer + 1;		
				if(CurrentPointer < entries.size()){
					current = entries.get(CurrentPointer);
				}
			}

			


			
		}


		
		heapsort(V.entries);
		return(V);
	}
	
	// Transposing a matrix
	public SparseMatrix transpose() {
		// Add your code here
		SparseMatrix M = new SparseMatrix();
		M.entries = new ArrayList<Entry>();
		M.numRows = numCols;
		M.numCols = numRows;
		int position1;
		int val1;
		double x1;
		int x2;
		
		int y;
		//	
		//	position = this.entries.get(j).getPosition();
		//	val = this.entries.get(j).getValue(); 
		Entry current = entries.get(1);
		
		for (int i = 0; i < entries.size(); ++i) {
			current = entries.get(i);
			position1 = current.getPosition();
			val1 = current.getValue();
			//System.out.println(current.getValue());
			x1 = position1 / numCols;
			//System.out.println(position1);
			
			x1 = Math.floor(x1);
			//System.out.println(x1);
			x2 = (int)x1 ;
			
			y = position1 - (x2 * numCols);
			//System.out.println(y);
			
			position1 = (y*numRows)+x2 ;
			Entry newEntry = new Entry( position1, val1);
			
			
			M.entries.add(newEntry);
		}
		//postion1 =  (row * (numCols)) + col

		
		heapsort(M.entries);
		return(M);
	}

	// Matrix-vector multiplication
	public DenseVector multiply(DenseVector v) {
		// Add your code here
		
		DenseVector t = new DenseVector(numRows);

		int position1;
		int val1;
		double x1;
		int x2;
		int y;
		Entry current = entries.get(1);
		int val2;
		for (int i = 0; i < entries.size(); ++i) {
			current = entries.get(i);
			position1 = current.getPosition();
			val1 = current.getValue();

			x1 = position1 / numCols;
			x1 = Math.floor(x1);
			
			x2 = (int)x1;
			y = position1 - (x2 * numCols);
			

			val1 = val1 * v.getElement(y);
			val2= val1 + t.getElement(x2);
			t.setElement(x2,val2);

		}
		

		return(t);
	}

	// Return the number of non-zeros
	public int numNonZeros() {
		// Add your code here
		return(entries.size());
	}

	// Multiply the matrix by a scalar, and update the matrix elements
	public void multiplyBy(int scalar) {
		// Add your code here
		Entry current = entries.get(1);
		int x = 0;
		for(int i= 0; i<entries.size();i++){
			current = entries.get(i);
			x = current.getValue();
			x = x * scalar;
			current.setValue(x);
			entries.set(i, current);
		}

	}

	// Number of rows of the matrix
	public int getNumRows() {
		return this.numRows;
	}

	// Number of columns of the matrix
	public int getNumColumns() {
		return this.numCols;
	}

	// Output the elements of the matrix, including the zeros
	// Do not modify this method
	public void print() {
		int n_elem = numRows * numCols;
		int pos = 0;

		for (int i = 0; i < entries.size(); ++i) {
			int nonzero_pos = entries.get(i).getPosition();

			while (pos <= nonzero_pos) {
				if (pos < nonzero_pos) {
					System.out.print("0 ");
				} else {
					System.out.print(entries.get(i).getValue());
					System.out.print(" ");
				}

				if ((pos + 1) % this.numCols == 0) {
					System.out.println();
				}

				pos++;
			}
		}

		while (pos < n_elem) {
			System.out.print("0 ");
			if ((pos + 1) % this.numCols == 0) {
				System.out.println();
			}

			pos++;
		}
	}

	private int numRows; // Number of rows
	private int numCols; // Number of columns
	private ArrayList<Entry> entries; // Non-zero elements
}
