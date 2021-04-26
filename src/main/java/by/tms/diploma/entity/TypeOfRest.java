package by.tms.diploma.entity;

public enum TypeOfRest {
    EXCURSION_TOUR, REST_AT_SEA, EXTREME_TOUR;

    public static TypeOfRest getEnumName(String name){
        switch (name.toLowerCase()){
            case "excursion tour":
                return EXCURSION_TOUR;
            case "rest at sea":
                return REST_AT_SEA;
            case "extreme tour":
                return EXTREME_TOUR;
            default:
                throw new EnumConstantNotPresentException(TypeOfRest.class, name);
        }
    }

    public static String getStringName(TypeOfRest typeOfRest){
        switch (typeOfRest){
            case EXCURSION_TOUR:
                return "Excursion tour";
            case REST_AT_SEA:
                return "Rest at sea";
            case EXTREME_TOUR:
                return "extreme tour";
            default:
                throw new EnumConstantNotPresentException(TypeOfRest.class, typeOfRest.name());
        }
    }
}
