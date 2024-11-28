package net.hamdi.beneficiaryservice.exceptionhandler;

public class DuplicateBeneficiaryException extends RuntimeException{
    public DuplicateBeneficiaryException(String msg){
        super(msg);
    }
}
