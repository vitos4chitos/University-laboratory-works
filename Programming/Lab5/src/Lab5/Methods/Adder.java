package Lab5.Methods;

import Lab5.DataBases.Color;
import Lab5.DataBases.Country;
import Lab5.DataBases.DataBase;
import Lab5.DataBases.Person;

import java.time.LocalDate;

public class Adder {
    public static Person addPerson(String line){
        String[] property = line.split(";");
        Integer id = Integer.valueOf(property[0]);
        String name = property[1];
        int cx = Integer.parseInt(property[2]);
        Double cy = Double.valueOf(property[3]);
        int year = Integer.parseInt(property[4].split("-")[0]),
                day = Integer.parseInt(property[4].split("-")[2]),
                month = Integer.parseInt(property[4].split("-")[1]);
        LocalDate creationDate = LocalDate.of(year, month, day);
        Float height = Float.valueOf(property[5]);
        String passportID = property[6];
        DataBase.passportId.add(passportID);
        Color hairColor = Color.valueOf(property[7]);
        Country nationality = Country.valueOf(property[8]);
        float lx = Float.parseFloat(property[9]);
        Long ly = Long.valueOf(property[10]);
        Long lz = Long.valueOf(property[11]);
        return new Person(id, name, cx, cy, creationDate, height, passportID, hairColor, nationality, lx, ly, lz);
    }

}
