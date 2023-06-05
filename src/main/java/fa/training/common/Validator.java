package fa.training.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class Validator {
    public boolean isNumberId(String strNumber){
        if(strNumber == null){
            return false;
        }
        try{
            int checknumber = Integer.parseInt(strNumber);
            if(checknumber < 0){
                return false;
            }
        }catch (NumberFormatException nfe){
            return false;
        }
        return true;
    }
    public boolean isNumberic(String strNumber){
        if(strNumber == null){
            return false;
        }
        try{
            double checknumber = Double.parseDouble(strNumber);
        }catch (NumberFormatException nfe){
            return false;
        }
        return true;
    }
    public boolean isDate(String strDate){
        if(strDate == null){
            return false;
        }
        try {
            java.util.Date utilDate = new SimpleDateFormat("dd/MM/yyyy").parse(strDate);
            Date date = new Date(utilDate.getTime());
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
