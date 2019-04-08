import java.io.*;
import javax.swing.*;
import java.util.*;
public class is17185203
{
	private static boolean full=false;
	private static ArrayList<int[][]> open = new ArrayList<int[][]>();
	private static ArrayList<Integer> openCosts = new ArrayList<Integer>(); //HVALUE
	private static ArrayList<Integer> openLevel = new ArrayList<Integer>(); //GVALUE
	private static ArrayList<int[][]> closed = new ArrayList<int[][]>();
	private static int gValue = 0;
	private static int size=0;
	private final static String message1="Please enter the Start state as a sequence of numbers between [0, 8] e.g 5 2 0 6 1 3 4 8 7";
	private final static String message2="Please enter the Start state as a sequence of numbers between [0, 15] e.g 5 2 0 6 1 3 4 8 7 10 9 13 12 11 15 14";
	
public static void main(String[] args)
{
	setSize();
	int finall[][];
	int goal[][];
	String message;
	if(size==3)
		message=message1;
	else 
		message=message2;
	boolean valid=false;
	finall=new int[3][3];
	finall=fill(message);
	goal=new int[3][3];
	goal=fill(message.replaceAll("Start","End"));
	int posx=getx(finall);
	int posy=gety(finall);
	open.add(finall);
	openCosts.add(getCost(finall, goal));
	openLevel.add(0);
	solveit(finall,posx,posy,goal);
}



public static void solveit(int[][]source,int x,int y,int [][]goal)
{
	try
	{	
		if (compareBoards(source, goal))	
		{
			System.out.println("You win");
			printBoard(source);
		}
		else
		{
		//	System.out.println("OPEN LIST: " + open.size());
		//	System.out.println("CLOSED LIST: " + closed.size());
		//	System.out.println("------------");
		//	System.out.println("BASE:\n");
		//	printBoard(source);
		//	System.out.println("COST = " + (openLevel.get(0) + getCost(source, goal)));
		//	System.out.println();
			gValue = openLevel.get(0) +1;	
			int[][]temp=new int[source.length][source.length];
			temp=copy(source);
			
			int tempx=0;
			if(y+1<source.length)
				{
				//	System.out.println("(B): "+source[x][y+1] + " to the west");
					temp=copy(source);
					tempx =temp[x][y+1];
					temp[x][y+1]=0;
					temp[x][y]=tempx;
					if (!boardSearched(temp))
					{
					//	printBoard(temp);
					//	System.out.println();
					//	System.out.println("Cost =" +(getCost(temp,goal) + gValue));
						open.add(temp);
						openCosts.add(getCost(temp, goal));
						openLevel.add(gValue);
					}
				}
				if(y-1>=0)
				{
				//	System.out.println("(A): "+source[x][y-1] +" to the east");
					temp=copy(source);
					tempx =temp[x][y-1];
					temp[x][y-1]=0;
					temp[x][y]=tempx;
					if (!boardSearched(temp))
					{
					//	printBoard(temp);
					//	System.out.println();
					//	System.out.println("Cost =" +(getCost(temp,goal) + gValue));
						open.add(temp);
						openCosts.add(getCost(temp, goal));
						openLevel.add(gValue);
					}
				}
				
				if(x-1>=0)
				{
				//	System.out.println("(C): "+source[x-1][y] + " to the south");
					temp=copy(source);
					tempx =temp[x-1][y];
					temp[x-1][y]=0;
					temp[x][y]=tempx;
					if (!boardSearched(temp))
					{
					//	printBoard(temp);
					//	System.out.println();
					//	System.out.println("Cost =" +(getCost(temp,goal) + gValue));	
						open.add(temp);
						openCosts.add(getCost(temp, goal));
						openLevel.add(gValue);
					}
				}
				
				if(x+1<source.length)
				{
				//	System.out.println("(D): "+source[x+1][y] + " to the north");
					temp=copy(source);
					tempx =temp[x+1][y];
					temp[x+1][y]=0;
					temp[x][y]=tempx;
					if (!boardSearched(temp))
					{
					//	printBoard(temp);
					//	System.out.println();
					//	System.out.println("Cost =" +(getCost(temp,goal) + gValue));
						open.add(temp);
						openCosts.add(getCost(temp, goal));
						openLevel.add(gValue);
					}
				}
				
				closed.add(open.get(0));
				open.remove(0);
				openCosts.remove(0);
				openLevel.remove(0);
				
				sortOpenList();
			
				//	System.out.println("Best Choice is:\n");
				//printBoard(open.get(0));
				//	System.out.println();
				int[][] newSource = open.get(0);
				int posX = getx(newSource);
				int posY = gety(newSource);
				solveit(newSource, posX, posY, goal);
				
		
		}
	}
	catch (Exception e)
	{System.out.println(e.getMessage());}
}
	
	public static void printBoard(int[][] board)
	{
		for (int i=0;i < board.length;i++)
		{
			for (int j=0;j < board.length;j++)
			{
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	
	
	public static void sortOpenList() //BUBBLE SORT FOR NOW
	{
		int[][] temp = new int[3][3];
		int tempVar = 0;
		int tempVar2 = 0;
		for (int i = 0; i < openCosts.size()-1; i++)
		{
			for(int j = 0; j < openCosts.size()-i-1; j++)
			{
				if(openCosts.get(j) > openCosts.get(j + 1))
				{
                   tempVar = openCosts.get(j+1);
                   openCosts.set(j+1, openCosts.get(j));
                   openCosts.set(j, tempVar);
				   
				   temp = open.get(j+1); 
				   open.set(j+1, open.get(j));
				   open.set(j, temp);
				   
				   tempVar2 = openLevel.get(j+1);
				   openLevel.set(j+1, openLevel.get(j));
				   openLevel.set(j, tempVar2);
				}
			}
		}
	}
	
	
	public static boolean compareBoards(int[][] board1, int[][]board2)
	{
		for (int i=0;i < board1.length;i++)
		{
			for (int j=0;j < board1.length;j++)
			{
				if (board1[i][j] != board2[i][j])
					return false;
			}
		}
		return true;
	}
	
	
	public static boolean boardSearched(int[][] board)
	{
		if (open.size() > 0)
		{
			for (int i=0;i < open.size();i++)
			{
				if (compareBoards(board, open.get(i)))
					return true;
			}
		}
		if (closed.size() > 0)
		{
			for (int i=0;i < closed.size();i++)
			{
				if (compareBoards(board, closed.get(i)))
					return true;
			}
		}
		return false;
	}
	
	
	
	public static int[][] getinput()
	{
		int answer[][];
		answer=new int[3][3];
		for(int i=0;i<3;i++)
		{
			for(int z=0;z<3;z++)
			{
				String code = JOptionPane.showInputDialog(null,"Enter number at position ["+i+"]"+",["+z+"]:");
				int num=Integer.parseInt(code);
				answer[i][z]=num;
			}
		}
		return answer;
	}
	
	public static int getx(int[][]a)
	{
		int answ=0;
		for(int i=0;i<3;i++)
		{
			for(int z=0;z<3;z++)
			{	
				if(a[i][z]==0)
				{
				answ=i;
				
				}
			}
		}
		return answ;
	}
	
	public static int gety(int[][]a)
	{
		int ans=0;
		for(int i=0;i<3;i++)
		{
			for(int z=0;z<3;z++)
			{	
				if(a[i][z]==0)
				{
				ans=z;
				
				}
				
			}
		}
		return ans;
	}
	
	public static boolean inputCheck(int[][] values)
	{ 
		boolean[] check=new boolean[size*size];
		for(int g=0;g<((size*size));g++)
		{
			check[g]=false;
		}
		for (int i = 0; i < values.length; i++)
		{
		    for (int j = 0; j < values[0].length; j++)
			{
			         // check to see if values are in range.
                                 if (values[i][j] > ((size*size)-1) || values[i][j] < 0) return false;
				 
				 // we are accessing each value once here. Have to check to make sure that that is the only time that number appeared.
				 else if (check[values[i][j]] == false)
				 {
					 check[values[i][j]] = true;
				 }
                                 else if(check[values[i][j]] == true)
				 {
					 return false; // this means that the value has occurred more than once
				 }					 
			}
		}
        return true; // the values have passed the range check and the duplication check so set must be valid.
	}
	
public static int [][] fill(String text)
    {
	int [][] answer=new int[size][size];
	String [] sArr=new String[size];
	String input="";
	int counter=0;
	boolean alldigit=true;
	while(!full)
		{
		input=JOptionPane.showInputDialog(null,text);
		sArr=input.split(" ");
		if(sArr.length==(size*size))
			{
			for(int h=0;h<sArr.length;h++)
			{
				if(!sArr[h].matches("\\d+"))
					alldigit=false;
			}
			if(alldigit)
			{
			for(int i=0;i<size;i++)
				{
				for(int j=0;j<size;j++)
					{
					answer[i][j]=Integer.parseInt(sArr[counter]);
					counter++;
					}
				}
					if(inputCheck(answer))
					{
						full=true;
					}
					else
					{
						JOptionPane.showMessageDialog(null,"State entered did not contain numbers 0-"+((size*size)-1));
					}
			}
			else
			{
				JOptionPane.showMessageDialog(null,"State entered did not contain all numbers");
			}
			}
		else
			{
			JOptionPane.showMessageDialog(null,"State entered did not contain exactly"+(size*size)+ " states");
			}
			alldigit=true;
			counter=0;
		}
		full=false;
		return answer;
    }
	
	
	public static int getCost(int[][] values,int[][] goal)
	{
		int val=0;
		for(int i=0;i<values.length;i++)
		{
			for(int j=0;j<values.length;j++)
			{
				if(values[i][j]!=0)
				{
					if(values[i][j]!=goal[i][j])
						val++;
					
				}
			}
		}
		
		return val;
	}
	
	public static int [][] copy(int [][] source)
	{
		int [][]temp=new int[source.length][source.length];
		
		for (int i=0;i<source.length;i++)
		{
			for(int j=0;j<source.length;j++)
			{
			temp[i][j]=source[i][j];
			}
		
		}
		return temp;
	}
	
	public static void setSize()
	{
		String options[]={"8","15"};
        int result = JOptionPane.showOptionDialog(null, "Please select the size of the puzzle.", "A*", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		if(result==0)
			size=3;
		else
			size=4;
	}
	
	public static void printarr(int [][] ass)
	{
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				System.out.println(ass[i][j]);
			}
		}
	}
	
}
