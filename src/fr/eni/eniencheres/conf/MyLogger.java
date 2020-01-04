package fr.eni.eniencheres.conf;

import java.util.logging.Logger;

public class MyLogger {

    private static final String logFormatPattern = "%1$tY%1$tm%1$tdT%1$tl:%1$tM:%1$tS.%1$tL %4$-7s %5$s %6$s%n";

    static {
    	System.setProperty("java.util.logging.SimpleFormatter.format", logFormatPattern);
    }
    
    public static Logger getLogger(Class clazz){        
        return Logger.getLogger(clazz.getName());
    }


}
