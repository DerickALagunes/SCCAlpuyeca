package com.simor.sistemacontrolcobros.model;

import java.util.List;

public class DataTableCostoResponse {
    private int draw;
    private int recordsTotal;
    private int recordsFiltered;
    private List<Costo> data;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<Costo> getData() {
        return data;
    }

    public void setData(List<Costo> data) {
        this.data = data;
    }
}
