package net.hamdi.beneficiaryservice.exceptionhandler;

public class InvalidBeneficiaryDataException extends RuntimeException{
    public InvalidBeneficiaryDataException(String msg){
        super(msg);
    }
}
