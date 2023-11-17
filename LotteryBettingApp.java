import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;

public class LotteryBettingApp
{
    public static void main(String[]  args)
    {
        Scanner sc = new Scanner(System.in);

        //declare constant values
        final int SIZE = 6;

        //declare variables
        char option;

        //declare arrays
        int[] lottoNumbers = new int[SIZE];

        //display a welcome message
        System.out.println("\n^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^" + 
                             "\n\t\t\t\tWelcome To Lottery Betting App\n" + 
                             "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n");      
        
        do{

            //promt the user for total numbers they want to play
            int total_number;
            do{
                System.out.print("\nPlease enter total number you want to play [1-6]:\t\t");
                total_number = sc.nextInt();

                if(total_number <= 0) {
                    System.out.println("The number must not be less than 1!");
                }
				if(total_number >6){
					System.out.println("The number must not be greater than 6!");
				}
            }while( ! (evaluateInput (total_number)));
            
            System.out.println("Thanks...\n");

            //promt the user for the amount to use to pay
            double playAmount;

            do{
                System.out.print("\nHow much do want to play with: R");
                playAmount = sc.nextDouble();

                if( playAmount < 1 ) {
                    System.out.println("Insufficient amount, Amount must be more than 0!");
                }
            }while( playAmount < 1);

            System.out.println("Thanks...\n");
			
            //generate lotto numbers
            generateLottoNumbers(lottoNumbers);

			//get user bet numbers
            int[] userBetNumbers = getUserBetNumbers(total_number);
			
            //count winning numbers
            int total_Wining_Numbers = countWinningNumbers(lottoNumbers, userBetNumbers);

            //get winning numbers
            int[] winningNumbers = getWinningNumbers(lottoNumbers, userBetNumbers, total_Wining_Numbers);

            //determine winning amount
            double winingAmount = determineWinningAmount(winningNumbers);

            //calculate the total winning amount
            double totalWinningAmount = winingAmount * (playAmount / 2.0);

            //sort the numbers
            bubbleSort(winningNumbers);

            //display the bet information
            displayBetInformation(lottoNumbers, userBetNumbers, winningNumbers, winingAmount, totalWinningAmount);
            
            do{
                System.out.print("\nDo you want to procced? [Y/y]or[N/n]:\t");
                option = Character.toUpperCase(sc.next().charAt(0));

                if(option != 'Y' && option != 'N'){
                    System.out.println("\nInvalid input!!");
                }

            }while(option != 'Y' && option != 'N');
        }while(option == 'Y');
    }

    public static boolean evaluateInput(int input)
    {
        return (input < 1 || input >6) ? false : true;
    }

    public static void generateLottoNumbers(int[] lottoNumbers)
    {
        Random rand = new Random();

        for(int a = 0; a < lottoNumbers.length; a++)
        {
            int number;

            do{
                number = rand.nextInt(50 - 1) + 1;
            }while((examineIfUnique(number, lottoNumbers)) );
            
            lottoNumbers[a] = number;
        }
    }

    public static boolean examineIfUnique(int numToExamine, int[] numbers)
    {
        boolean isUnique = false;

        for(int b = 0; b < numbers.length; b++)
        {
            if(numbers[b] == numToExamine)
            {
                isUnique = true;
            }
        }
        return isUnique;
    }

    public static int[] getUserBetNumbers(int totalNumbers)
    {
        Scanner sc = new Scanner(System.in);
        
        //declare variables
        int user_Bet_Number;
        int[] userBetNumbers = new int[totalNumbers];

        for(int c = 0; c < totalNumbers; c++)
        {
            do{
                System.out.print("\nPlease enter your [" + (c+1) + "] bet number between <1 - 49>:\t\t");
                user_Bet_Number = sc.nextInt();

                if(examineIfUnique(user_Bet_Number, userBetNumbers))
                {
                    System.out.println("\n" + user_Bet_Number + " already exists\n");
				}

            }while(examineIfUnique(user_Bet_Number, userBetNumbers));
          
			userBetNumbers[c] = user_Bet_Number;
			
            
        }
        return userBetNumbers;
    }

    public static int countWinningNumbers(int[] lottoNumbers, int[] userBetNumbers)
    {
        int totalWiningNumbers = 0;

        for(int d = 0; d < userBetNumbers.length; d++)
        {
            for(int e = 0; e < lottoNumbers.length; e++)
            {
                if(userBetNumbers[d] == lottoNumbers[e])
                {
                    totalWiningNumbers++;
                    break;
                }
            }
        }
        return totalWiningNumbers;
    }

    public static int[] getWinningNumbers(int[] lottoNumbers, int[] userBetNumbers, int totalWiningNumbers)
    {
        int[] total_Wining_Numbers = new int[totalWiningNumbers];

        for(int f = 0; f < totalWiningNumbers; f++)
        {
            for(int d = 0; d < userBetNumbers.length; d++)
            {
                for(int e = 0; e < lottoNumbers.length; e++)
                {
                    if(userBetNumbers[d] == lottoNumbers[e])
                    {
                        total_Wining_Numbers[f] = userBetNumbers[d];
                        break;
                    }
                }
            } 
        }
        return total_Wining_Numbers;
    }

    public static double determineWinningAmount(int[] winningNumbers)
    {
        double winingAmount = 0;

        for(int g = 0; g < winningNumbers.length; g++)
        {
            winingAmount += (winningNumbers[g] * 2);
        }

        return winingAmount;
    }

    public static void bubbleSort(int[] numbers)
    {
        for(int i = 0; i < numbers.length - 1; i++)
        {
            for(int j = 0; j < numbers.length - 1; j++)
            {
                if(numbers[j] > numbers[j+1])
                {
                    int temp = numbers[j];
                    numbers[j] = numbers[j+1];
                    numbers[j+1] = temp;
                }
            }
        }
    }

    public static void displayBetInformation(int[] lottoNumbers, int[] userBetNumbers, int[] winningNumbers, double betAmount, double totalWinningAmount)
    {
        DecimalFormat fmt = new DecimalFormat("R###,##0.00");

        System.out.println("\n============================================================================================================\n" + 
                           "\t\t\t Bet Results\n" + 
                           "==============================================================================================================\n" + 
                           "LOTTO NUMBERS");
        
                            for(int a = 0; a < lottoNumbers.length; a++)
                            {
                                System.out.print(lottoNumbers[a] + "\t");
                            }

        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" + 
                             "USER BET NUMBERS");

                            for(int a = 0; a < userBetNumbers.length; a++)
                            {
                                System.out.print(userBetNumbers[a] + "\t");
                            }

        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" + 
                             "WINNING NUMBERS");
                            
                            for(int a = 0; a < winningNumbers.length; a++)
                            {
                                System.out.print(winningNumbers[a] + "\t");
                            }
                            
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" + 
                             "BET AMOUNT:\t\t" + fmt.format(betAmount) + "\n" +
                             "TOTAL WINNING AMOUNT:\t" + fmt.format(totalWinningAmount) + 
                             "\n=================================================================================================================");           

    }
}