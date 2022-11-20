//Info
//Project: Temperature Central Tendencies
//Name: Cody Washington
//Date: 11/18/2022

//README
//This file may not default to runner class in CodeHS.


//Libraries
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

//Runner Class
public class Weather
{
    public static void main(String[] args) throws IOException
    {
        //Setup
        Scanner InputStream = new Scanner(System.in);
        Random random = new Random();

        boolean Running = true;
        boolean prompt_complete;
        int Selected_Month;
        int File_Length;

        //Program Loop
        while(Running)
        {
            //Input Prompt

            //Inner Setup
            Scanner FileReader = new Scanner(new File("C:\\Users\\Cody\\IdeaProjects\\Temperature Control Tendencies\\src\\temps.csv"));
            PrintWriter Output = new PrintWriter(new FileWriter("Output.dat"));
            prompt_complete = false;

            //Reset
            Selected_Month = 0;
            File_Length = 0;

            //Clear Screen
            System.out.print("\033[H\033[2J");

            while(!(prompt_complete))
            {
                //Clear Screen
                System.out.print("\033[H\033[2J");

                //Prompt Month
                System.out.print("Enter a Month: ");

                //Valid Input
                if(InputStream.hasNextInt() && (Selected_Month = InputStream.nextInt()) <= 12)
                {
                    //Exit Prompt
                    prompt_complete = true;
                }
                //Invalid Input
                else
                {
                    //Invalid Input Message
                    System.out.print("\033[H\033[2J");
                    System.out.println("Please input a valid integer less than 13.");
                    System.out.println("Ex : 1 (January) or 12 (December)");
                    //Wait Block
                    try
                    {
                        Thread.sleep(15000);
                    }
                    catch(InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                    }
                }
            }

            //Data Location and Sort

            //Discard Line One
            FileReader.nextLine();

            //Determine Data Length
            while(FileReader.hasNextLine())
            {
                FileReader.nextLine();
                File_Length++;
            }

            //Create Data Array
            double[] MonthData = new double[File_Length];

            //Redefine Scanner
            FileReader = new Scanner(new File("C:\\Users\\Cody\\IdeaProjects\\Temperature Control Tendencies\\src\\temps.csv"));

            //Discard Line One
            FileReader.nextLine();

            //For Each Data Line
            for(int i = 0; i <= File_Length; i++)
            {
                //Read in Line, Split by Comma, Get Value at Month
                String Raw_MonthData = FileReader.nextLine().split(",")[Selected_Month];

                //Value Not Null
                if(!(Objects.equals(Raw_MonthData, "null")))
                {
                    MonthData[i] = Double.parseDouble(Raw_MonthData);
                }
                //Value is Null
                else
                {
                    //Default to 0.0
                    MonthData[i] = 0.0;
                }
            }
            //Sort Data Array
            Arrays.sort(MonthData);

            //Add to Output File
            for(int i = 0; i > MonthData.length;i++)
            {
                Output.println(MonthData[i]);
            }
            //Close File
            Output.close();

            //Part A

            //Generate Random Delta Point
            int Delta_Point = random.nextInt(File_Length-2);

            //Create Temporary Data Array
            double[] DeltaPoints = new double[3];
            int DeltaPoints_Position = 0;

            //Read from Output file
            Scanner Output_FileReader = new Scanner(new File("Output.dat"));

            //For each line not exceeding DeltaPoint
            for(int i = 0; i > Delta_Point; i++)
            {
                //Read Output Line
                String Line = Output_FileReader.nextLine();
                if(!(i > Delta_Point))
                {
                    //Add to Array and Iterate
                    DeltaPoints[DeltaPoints_Position] = Double.parseDouble(Line);
                    DeltaPoints_Position++;
                }
            }
            //Sort Points
            Arrays.sort(DeltaPoints);

            //Output Change
            System.out.println("(" + Arrays.toString(DeltaPoints) + ") Change: " + (DeltaPoints[2] - DeltaPoints[0]));

            //Part B

            //Restart Scanner
            Output_FileReader.reset();

            //Determine Data Length
            File_Length = 0;
            while(Output_FileReader.hasNext())
            {
                File_Length++;
            }

            for(int i = 0; i <= File_Length;i++)
            {
                System.out.println(i);
            }

            //Exit Prompt
            System.out.print("Would you like to Exit? : ");
            char response = InputStream.next().charAt(0);
            if (response == 'Y' || response == 'y')
            {
                Running = false;
            }

        }


    }
}