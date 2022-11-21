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
            Scanner FileReader = new Scanner(new File("temps.csv"));
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
                if(InputStream.hasNextInt() && (Selected_Month = InputStream.nextInt()) <= 12 && Selected_Month > 0)
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
                        Thread.sleep(5000);
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

            //Construct Double Array With Length of File
            double[] MonthData = new double[File_Length];

            //Redefine Scanner
            FileReader = new Scanner(new File("temps.csv"));

            //Discard Line One
            FileReader.nextLine();

            //For Each Data Line
            for(int i = 0; i < File_Length; i++)
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
            //Generate Random Delta Point
            int Delta_Point = random.nextInt(File_Length-2);

            //Construct Double Array With Length of 3
            double[] Delta = new double[3];
            int Delta_Position = 0;

            //Iterate Double Array
            for(int i = 0; i <= (Delta_Point+2);i++)
            {
                if(i >= Delta_Point)
                {
                    Delta[Delta_Position] = MonthData[i];
                    Delta_Position++;
                }
            }
            //Sort Points
            Arrays.sort(Delta);

            //Output Part A
            System.out.print("\033[H\033[2J");
            System.out.println("-----------------------");
            System.out.println("Part A:");
            System.out.println("[Random Delta]");
            System.out.println("(" + Arrays.toString(Delta) + ") \nChange: " + (Delta[2] - Delta[0]));
            System.out.println("-----------------------");
            System.out.print("Enter Anything to Proceed: ");
            InputStream.next();

            //Part B

            //Sort Month Data Array
            Arrays.sort(MonthData);

            //Output to file
            for (double monthDatum : MonthData)
            {
                Output.println(monthDatum);
            }
            //Close File
            Output.close();

            //Retrieve Data
            MonthData = new double[File_Length];

            //Create Output.dat Scanner
            Scanner Output_FileReader = new Scanner(new File("Output.dat"));

            //Iterate through and collect data
            for(int i = 0; i < File_Length; i++)
            {
                MonthData[i] = Double.parseDouble(Output_FileReader.nextLine());
            }

            //Math

            //Calculate Mean
            double Mean = 0.0;
            for (double monthDatum : MonthData) {
                Mean += monthDatum;
            }
            Mean /= MonthData.length-1;

            //Calculate Median
            double Median = MonthData[(MonthData.length-1) /2];

            //Calculate Mode
            double Mode = 0;
            int Occurrences = 0;
            for (double monthDatum : MonthData) {
                //Reset Occurrences
                int Local_Occurrences = 0;
                for (double datum : MonthData) {
                    if (datum == monthDatum) {
                        Local_Occurrences++;
                    }
                }
                //Check against Occurrences
                if ((Local_Occurrences - 1) > Occurrences) {
                    Mode = monthDatum;
                }
            }

            //Output Part B
            System.out.print("\033[H\033[2J");
            System.out.println("-----------------------");
            System.out.println("Part B:");
            System.out.println("Mean: " + Mean);
            System.out.println("Median: " + Median);
            System.out.println("Mode: " + Mode);
            System.out.println("-----------------------");
            System.out.print("Press Anything to Proceed: ");
            InputStream.next();

            //Exit Prompt
            System.out.print("\033[H\033[2J");
            System.out.print("Would you like to Exit?: ");
            char response = InputStream.next().charAt(0);
            if (response == 'Y' || response == 'y')
            {
                Running = false;
            }

        }
        //Exit Message
        System.out.print("\033[H\033[2J");
        System.out.println("Exiting...");
        System.out.println("Credit : Cody Washington");

    }
}