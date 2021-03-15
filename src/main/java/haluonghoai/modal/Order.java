package haluonghoai.modal;

import java.util.Date;

public class Order {
    private int id;
    private Date timecreate;
    private String note;
    private int idUser;
    private int idCustomer;
    private int idPayments;
    private int idOrderstatus;
    private Boolean statusPaments;

    public Order() {
    }

    public Order(int id, Date timecreate, String note, int idUser, int idCustomer, int idPayments, int idOrderstatus, Boolean statusPaments) {
        this.id = id;
        this.timecreate = timecreate;
        this.note = note;
        this.idUser = idUser;
        this.idCustomer = idCustomer;
        this.idPayments = idPayments;
        this.idOrderstatus = idOrderstatus;
        this.statusPaments = statusPaments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimecreate() {
        return timecreate;
    }

    public void setTimecreate(Date timecreate) {
        this.timecreate = timecreate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdPayments() {
        return idPayments;
    }

    public void setIdPayments(int idPayments) {
        this.idPayments = idPayments;
    }

    public int getIdOrderstatus() {
        return idOrderstatus;
    }

    public void setIdOrderstatus(int idOrderstatus) {
        this.idOrderstatus = idOrderstatus;
    }

    public Boolean getStatusPaments() {
        return statusPaments;
    }

    public void setStatusPaments(Boolean statusPaments) {
        this.statusPaments = statusPaments;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", timecreate=" + timecreate +
                ", note='" + note + '\'' +
                ", idUser=" + idUser +
                ", idCustomer=" + idCustomer +
                ", idPayments=" + idPayments +
                ", idOrderstatus=" + idOrderstatus +
                ", statusPaments=" + statusPaments +
                '}';
    }
}
