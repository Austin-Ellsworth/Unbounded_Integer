// File: UnboundedInt.java

/**
 * @note 
 *   The idea of this class is to allow numerical values(integers) that are of any size and not limited to 32 or 64 bits of storage.  
 *   To do this we will use a linked list of IntNode objects.  Each Node will contain an integer value from zero to 999.  
 *   In addition, when concatenated together this will allow us to store values with almost unlimited size.   
 *   
 * @author 
 *   Austin Ellsworth
 *
 **/
public class UnboundedInt implements Cloneable{
	
	//Invariant of the UnboundedInt class: 
	//  1. The number of nodes used for one integer value is the instance variable numberOfNodes.
	//	2. A link to the front of the list is the instance variable frontLink.
	//	3. A link to the back of the list is the instance variable backLink.
	private int numberOfNodes;
	private IntNode head;
	private IntNode tail;
	private IntNode cursor;
	
	
	/**
	 * Constructor to initialize an UnboundedInt of arbitrary length from one String.
	 *  
	 * @param String
	 *   An arbitrary String representing a numerical integer value.
	 * @precondition
	 *  Element must be numerical, positive and must not include commas.
	 * @postcondition
	 *   This UnboundedInt is composed of the integer elements represented by given String.
	 * @exception Os an element is negative or greater than three integers long.utOfMemoryError
	 *   Indicates insufficient memory for new UnboundedInt.. 
	 **/ 
	public UnboundedInt(String number) {
		String data;
		int dataNumber;
		int count = 0;
		for (int i = 0; i < number.length(); i++) {
			
			if((number.length() -i) % 3 == 1) {
				data = number.substring(i, i+=1);
			} else if ((number.length() - i) % 3 == 2) {
				data = number.substring(i, i+=2);
			} else {
				data = number.substring(i, i+=3);
			}

			dataNumber = Integer.parseInt(data);
			IntNode nextNode = new IntNode(dataNumber, null);
			nextNode.setLink(head);
			head = nextNode;
			numberOfNodes++;
			i--;
			if(count == 0) {
				tail = head;
			}
			count++;
		}
	} //end constructor
	
	/**
	 * A method to add one unboundedInt to another UnboundedInt and return their sum.
	 * @param addendInt
	 *   The UnboundedInt to be added to the called on UnboundedInt.
	 * @precondition
	 *   The invoked UnboundedInt and the addendInt may be any size or empty. 
	 *   An UnboundedInt may be added to itself. 
	 * @return
	 *   Returns the sum of the called on UnboundedInt and the addedNumber as a new UnboundedInt. 
	 **/ 
	public UnboundedInt add(UnboundedInt addedNumber) {
		UnboundedInt answer = new UnboundedInt("0");
		int sum;
		boolean remainder = false;
		final int REMAINDER = 1;
		final int THOUSAND = 1000;
		final int OVERFLOW = 999;
		int count  =0;
		start();
		addedNumber.start();
		
		while(cursor != null && addedNumber.cursor != null) {
			sum = getNodeValue() + addedNumber.getNodeValue();
			if(remainder == true) {
				sum += REMAINDER;
			}
			
			if (sum > OVERFLOW) {
				sum -= THOUSAND;
				remainder = true;
			} else {
				remainder = false;
			}
			if(count == 0) {
				IntNode nextNode = new IntNode(sum, null);
				answer.head = nextNode;
				answer.tail = answer.head;
				numberOfNodes++;
			} else {
				answer.addEnd(sum);
			}
			count++;
			advance();
			addedNumber.advance();
		} 
		while(count != -1) {
			if(remainder == true) {
				if(cursor == null && addedNumber.cursor == null) {
					answer.addEnd(REMAINDER);
					remainder = false;
				} else if (cursor != null) {
					sum = getNodeValue() + REMAINDER;
					if(sum > OVERFLOW) {
						sum -= THOUSAND;
						answer.addEnd(sum);
						remainder = true;
					} else {
						answer.addEnd(sum);
						remainder = false;
					}
				} else if(addedNumber.cursor != null) {
					sum = addedNumber.getNodeValue() + REMAINDER;
					if(sum > OVERFLOW) {
						sum -= THOUSAND;
						answer.addEnd(sum);
						remainder = true;
					} else {
						answer.addEnd(sum);
						remainder = false;
					}
				}	
			} else if (remainder == false) {
				if (cursor != null) {
					answer.addEnd(getNodeValue());
				} else if (addedNumber.cursor != null) {
					answer.addEnd(addedNumber.getNodeValue());
				}
			}
		if(count < numberOfNodes - 1) {
			advance();
			count++;
		} else if(count < addedNumber.numberOfNodes) {
			addedNumber.advance();
			count++;
		} else {
			count = -1;
		}
		}
		answer.start();
		return answer;
	}
	
	public void addEnd(int element) {
		tail.addNodeAfter(element);
		tail = tail.getLink();
		numberOfNodes++;
	} //end add
	
	/**
	 * A method to multiply one unboundedInt by another UnboundedInt and return their product.  
	 * @param multNum
	 *   The UnboundedInt to multiply the called on UnboundedInt by.
	 * @precondition
	 *   Both UnboundedInts must not be null.
	 * @return
	 *   Returns the product of the called on UnboundedInt and the multNum as a new UnboundedInt. 
	 **/ 
	public UnboundedInt multiply(UnboundedInt multNum) {
		UnboundedInt answer = new UnboundedInt("0");
		UnboundedInt nothing = new UnboundedInt("0");
		int multiplication = 0;
		int zeros = 0;
		if(this.equals(nothing) || multNum.equals(nothing)) {
			return nothing;
		}
		
		for(cursor = head; cursor != null; cursor = cursor.getLink()) {
			int carry = 0;
			UnboundedInt total = new UnboundedInt("0");
			for(int i = 0; i < zeros; i++) {
				total.addEnd(0);
			}
			for(IntNode curs = multNum.head; curs != null; curs = curs.getLink()) {
				multiplication = ((curs.getData() * cursor.getData()) + carry);
				carry = multiplication / 1000;
				multiplication = multiplication % 1000;
				
				total.addEnd(multiplication);
			}
			if(carry > 0) {
				total.addEnd(carry);
			}
			
			answer = answer.add(total);
			total = null;
			zeros++;
		}
		answer.start();
		String actual = answer.toString();
		String actual2 = "";
		for(int i = 0; i < actual.length() - 4; i++) {
			actual2 += actual.charAt(i);
			
		}
		actual2 = actual2.replace(",", "");
		UnboundedInt actualAnswer = new UnboundedInt(actual2);
		
		return actualAnswer;
	}
	
	/**
	 * A method to set the currently selected element to the element at the head of the UnboundedInt.  
	 * 
	 * @param none
	 * @postcondition
	 *   The current element is now at the head.
	 **/
	public void start()
	{
		cursor =  head;
		
	}//End start
	
	/**
	 * A method to make the next element the currently selected element.
	 * @param none
	 * @precondition
	 *   There must currently be a non-null element selected.
	 * @postcondition
	 *   The next element after the currently selected element is the new currently selected element 
	 **/
	public void advance() {
		cursor = cursor.getLink();
	}//end advance
	
	/**
	 * Accessor method to get the currently selected integer element.
	 * @param none
	 * @precondition
	 *   There must currently be a non-null element selected.
	 * @return
	 *   Returns the integer held in the currently selected element.
	 **/
	public int getNodeValue() {
		return cursor.getData();
	}//end getNodeValue
	
	/**
	 * Method to return string of UnboundedInt with no commas.
	 * @param none
	 * @precondition
	 *   UnboundedInt is not empty.
	 * @return
	 *   Returns a String that represents UnboundedInt.
	 **/
	public String toString() {
		String unboundedNumber = "";
		String unboundedWithCommas ="";
		IntNode current = head;
		
		for (int i = numberOfNodes; i > 0; i--) {
			
				if(IntNode.listPosition(current, i).getData() < 10) {
					if(numberOfNodes - i > 0) {
						unboundedNumber += "00";
					}
				}else if(IntNode.listPosition(current, i).getData() < 100) {
					if(numberOfNodes - i > 0) {
						unboundedNumber += "0";
					}
				}
				unboundedNumber += IntNode.listPosition(current, i).getData();

		}
		for(int i = 1; i <= unboundedNumber.length(); i++) {
			char ch = unboundedNumber.charAt(unboundedNumber.length() - i);
				if(i % 3 == 1 && i > 1) {
					unboundedWithCommas = "," + unboundedWithCommas; 
				}
				unboundedWithCommas = ch + unboundedWithCommas;
		}
		return unboundedWithCommas;
	}//end toString
	
	/**
	 * Method to return string of UnboundedInt with commas.
	 * @param none
	 * @precondition
	 *   UnboundedInt is not empty.
	 * @return
	 *   Returns a String that represents UnboundedInt.
	 **/
	public String toStringNoCommas() {
		String unboundedNumber = "";
		IntNode current = head;
		
		for (int i = numberOfNodes; i > 0; i--) {
			unboundedNumber += IntNode.listPosition(current, i).getData();
		}
		return unboundedNumber;
	}//end toStringNoCommas
	
	/**
	 * A method to compare two UnboundedInt objects and determine if they are equal. 
	 * @param obj
	 *   The UnboundedInt that is being compared to the current UnboundedInt.
	 * @postcondition
	 *   If the UnboundedInt's being compared are equivalent, then equals will return true. 
	 *   Otherwise equals will return false.
	 **/ 
	public boolean equals(Object obj) {
		boolean equal = false;
		
		if (obj instanceof UnboundedInt) {
			UnboundedInt candidate = (UnboundedInt) obj;
			
			if (numberOfNodes == candidate.numberOfNodes) {
				equal = true;
				start();
				candidate.start();
				int count = 0;
				while (count < numberOfNodes) {
					if(candidate.getNodeValue() != getNodeValue()) {
						equal = false;
					}
					count++;
					advance();
					candidate.advance();
				}
			}
		}
		return equal;
	}
	
	/**
	 * A method to generate a clone of UnboundedInt. 
	 * @param none
	 * @return
	 *   The return value is a clone of UnboundedInt.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for creating the clone.
	 * @exception CloneNotSupportedException
	 *   Indicates this class does not implement cloneable.
	 **/
	public UnboundedInt clone() {
		UnboundedInt cloneInt;
		IntNode cloneArr [];
		try {
			cloneInt = (UnboundedInt) super.clone();
		} catch(CloneNotSupportedException e) {
			throw new IllegalArgumentException 
			("This class does not implement cloneable.");
		}
		cloneArr = IntNode.listCopyWithTail(head);
		cloneInt.head = cloneArr[0];
		cloneInt.tail = cloneArr[1];
		
		return cloneInt;
	}
	
	
}



