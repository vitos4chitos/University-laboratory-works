import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.math.BigDecimal;

@FacesConverter("typeConverter")
public class TypeConverter implements Converter {
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        try {
            BigDecimal y_value = new BigDecimal(s);
            return y_value;
        }
        catch(NumberFormatException e){
            throw new ConverterException(new FacesMessage( "Неверный формат для Х"));
        }
    }
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return o.toString();
    }
}