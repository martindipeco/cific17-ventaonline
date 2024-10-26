package modelo.dominio;

public enum EnumCategoria {

    TECNOLOGIA(EnumSubcategoria.class),
    INDUMENTARIA(null),
    CALZADO(null),
    DEPORTE(null),
    HOGAR(null),
    MUEBLES(null),
    ELECTRODOMESTICOS(null);

    private Class <? extends Enum<?>> subcategoria;

    EnumCategoria(Class<? extends Enum<?>> subcategoria)
    {
        this.subcategoria = subcategoria;
    }

    public Class <? extends Enum<?>> getSubcategoria()
    {
        return subcategoria;
    }
}
