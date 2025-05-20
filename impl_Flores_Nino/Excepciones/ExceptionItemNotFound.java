package impl_Flores_Nino.Excepciones;

/**
 * Excepcion que se usa en caso de que se quiera realizar alguna operacion de 
 * busqueda, elimincaion u otra, en alguna estructura de datos.
 */
public class ExceptionItemNotFound extends RuntimeException {
    
    /**
     * @param mnsj Mensaje que acompaña la excepcion
     */
    public ExceptionItemNotFound(String mnsj) {
        super(mnsj);
    }

    // Excepcion sin mensaje
    public ExceptionItemNotFound(){}
}