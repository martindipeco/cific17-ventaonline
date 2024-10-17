package modelo.dominio;

public enum EnumCategoria {

    TECNOLOGIA(EnumCategoriaTecnologia.class),
    INDUMENTARIA(null),
    CALZADO(null),
    DEPORTE(null),
    HOGAR(null),
    MUEBLES(null),
    ELECTRODOMESTICOS(null);
    //INFORMATICA; //Hacer que informática sea una sub-categoría de tecnología

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
