package net.hamdi.beneficiaryservice.exceptionhandler;

public class BeneficiaireNotFoundException extends RuntimeException{

    public BeneficiaireNotFoundException(String msg){
        super(msg);
    }
}
