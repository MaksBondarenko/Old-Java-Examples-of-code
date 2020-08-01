package Races;

public class StatusChangeException extends Exception{
    int excNumber;
    StatusChangeException(int excNumber){
        this.excNumber = excNumber;
    }
    void showExceptionMessage(){
        switch (excNumber){
            case(1):
                System.out.println("Can't change status from 'Canceled'");
                break;
            case(2):
                System.out.println("Can't change status from 'Ended'");
                break;
            case(3):
                System.out.println("Can't change status from 'In Progress' to  'Canceled'");
                break;
            case(4):
                System.out.println("Can't change status from 'Planned' to  'Ended'");
                break;
        }
    }
}
