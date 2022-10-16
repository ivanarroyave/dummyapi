package io.dummyapi.questions.general;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ReturnIntegerValue implements Question<Integer> {

    private final Integer systemValue;

    public ReturnIntegerValue(Integer systemValue) {
        this.systemValue = systemValue;
    }

    @Override
    public Integer answeredBy(Actor actor) {
        return systemValue;
    }

    public static ReturnIntegerValue returnIntegerValue(Integer systemValue){
        return new ReturnIntegerValue(systemValue);
    }
}
