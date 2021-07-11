package StepDefs;

public enum Order {
    По_умолчанию("По умолчанию", 101),
    Дешевле("Дешевле", 1),
    Дороже("Дороже", 2),
    По_дате("По дате", 104);

    private String order;
    private int id;

    public String getOrder() {
        return order;
    }

    public int getId() {
        return id;
    }

    Order(String order, int id) {
        this.order = order;
        this.id = id;
    }
}
