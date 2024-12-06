package net.hamdi.virementservice.exceptions;

public class InvalidVirementOperationException extends RuntimeException{
    public InvalidVirementOperationException( String msg){
        super(msg);
    }
}
