/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ca2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author Maziar
 */
public class Main
{

    public static void main(String[] args) throws FileNotFoundException, IOException
	{
		RandomAccessFile file = null;
		List<String> db;
		db =  new LinkedList<String>();
		Iterator<String> it;
		String line = new String();
		String cch = new String();
		String temp[] = new String[3];
		StringTokenizer tokkon = null;
		
		line = "";

		try
		{
			file = new RandomAccessFile("DataFile.Dat","r");
			while (true)
			{
				line = "";
				line = file.readLine();
				if(line == null)
					break;
				temp = line.split(" ");

				if (temp[0].compareToIgnoreCase("insert") == 0)
				{
					db.add(temp[1]);
				}
				else if (temp[0].compareToIgnoreCase("delete") == 0)
				{
					it = db.iterator();

					while(it.hasNext())
					{
						if(it.next().compareToIgnoreCase(temp[1]) == 0)
						{
							it.remove();
							break;
						}
					}
				}
				else if (temp[0].compareToIgnoreCase("change") == 0)
				{
					it = db.iterator();
					while(it.hasNext())
					{
						if(it.next().compareToIgnoreCase(temp[1]) == 0)
						{
							it.remove();
							db.add(temp[2]);
							break;
						}
					}
				}
			}

			file.close();		//closing file
		}
		catch(FileNotFoundException filenotfound)
		{
			System.err.println( "Error in opening Dictionary File!" );
		}

		// until now we built the main database
		try
		{
			file = new RandomAccessFile("TestFile.Dat","r");
			RandomAccessFile mistakes;
			mistakes  = new RandomAccessFile("Mistakes.txt", "rw");

			int line_number = 0;
			while (true)
			{
				line = "";
				line = file.readLine();
				line_number++;
				if (line == null)
					break;

				tokkon = new StringTokenizer(line);

				mistakes.writeBytes("Line " + line_number + " :\n");

				while (tokkon.hasMoreTokens())
				{
					Boolean correct = false;
					cch = "";
					cch = tokkon.nextToken();
					
					it = db.iterator();
					while(it.hasNext())
					{
						if(it.next().compareToIgnoreCase(cch) == 0)
						{
							correct = true;
							break;
						}
					}
					if(!correct)
						mistakes.writeBytes(cch + " has false dictation!\n");
				}
			}
			mistakes.close();
		}
		catch(FileNotFoundException filenotfound)
		{
			System.err.println( "Error in opening Text File!" );
		}



	}

}

