package com.simor.sistemacontrolcobros.model;

import java.sql.Date;

public class Transaccion {
    private int idTransaccion;
    private Cliente cliente;
    private Verificacion verificacion;
    private TipoPago tipoPago;
    private String numeroNota;
    private String cotizacion;
    private String cuentaDeposito;
    private String numeroFactura;
    private Pagado pagado;
    private String atiendeYCobra;
    private Date fechaPedido;

    public Transaccion(Cliente cliente, Verificacion verificacion, TipoPago tipoPago, String numeroNota, String numeroCotizacion, String cuentaDeposito, String numeroFactura, Pagado pagado, String atiendeYCobra, Date sqlDate2) {
        this.cliente = cliente;
        this.verificacion = verificacion;
        this.tipoPago = tipoPago;
        this.numeroNota = numeroNota;
        this.cotizacion = numeroCotizacion;
        this.cuentaDeposito = cuentaDeposito;
        this.numeroFactura = numeroFactura;
        this.pagado = pagado;
        this.atiendeYCobra = atiendeYCobra;
        this.fechaPedido = sqlDate2;
    }

    public enum TipoPago {
        FACTURA, DEPOSITO, EFECTIVO, NULL
    }

    public enum Pagado {
        SI, NO, NULL
    }

    // Getters and Setters
    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Verificacion getVerificacion() {
        return verificacion;
    }

    public void setVerificacion(Verificacion verificacion) {
        this.verificacion = verificacion;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(String numeroNota) {
        this.numeroNota = numeroNota;
    }

    public String getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(String cotizacion) {
        this.cotizacion = cotizacion;
    }

    public String getCuentaDeposito() {
        return cuentaDeposito;
    }

    public void setCuentaDeposito(String cuentaDeposito) {
        this.cuentaDeposito = cuentaDeposito;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Pagado getPagado() {
        return pagado;
    }

    public void setPagado(Pagado pagado) {
        this.pagado = pagado;
    }

    public String getAtiendeYCobra() {
        return atiendeYCobra;
    }

    public void setAtiendeYCobra(String atiendeYCobra) {
        this.atiendeYCobra = atiendeYCobra;
    }

    public Transaccion(Cliente cliente, Verificacion verificacion, TipoPago tipoPago, String numeroNota, String cotizacion, String cuentaDeposito, String numeroFactura, Pagado pagado, String atiendeYCobra) {
        this.cliente = cliente;
        this.verificacion = verificacion;
        this.tipoPago = tipoPago;
        this.numeroNota = numeroNota;
        this.cotizacion = cotizacion;
        this.cuentaDeposito = cuentaDeposito;
        this.numeroFactura = numeroFactura;
        this.pagado = pagado;
        this.atiendeYCobra = atiendeYCobra;
    }

    public Transaccion() {
    }

    public Transaccion(int idTransaccion, Cliente cliente, Verificacion verificacion, TipoPago tipoPago, String numeroNota, String cotizacion, String cuentaDeposito, String numeroFactura, Pagado pagado, String atiendeYCobra, Date fechaPedido) {
        this.idTransaccion = idTransaccion;
        this.cliente = cliente;
        this.verificacion = verificacion;
        this.tipoPago = tipoPago;
        this.numeroNota = numeroNota;
        this.cotizacion = cotizacion;
        this.cuentaDeposito = cuentaDeposito;
        this.numeroFactura = numeroFactura;
        this.pagado = pagado;
        this.atiendeYCobra = atiendeYCobra;
        this.fechaPedido = fechaPedido;
    }

    public void setTipoPago(String tipoPago) {
        switch (tipoPago) {
            case "EFECTIVO":
                this.tipoPago = TipoPago.EFECTIVO;
                break;
            case "FACTURA":
                this.tipoPago = TipoPago.FACTURA;
                break;
            case "DEPOSITO":
                this.tipoPago = TipoPago.DEPOSITO;
                break;
            default:
                this.tipoPago = TipoPago.NULL;
                break;
        }
    }

    public void setPagado(String pagado) {
        switch (pagado) {
            case "SI":
                this.pagado = Pagado.SI;
                break;
            default:
                this.pagado = Pagado.NO;
                break;
        }
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "idTransaccion=" + idTransaccion +
                ", cliente=" + cliente.toString() +
                ", verificacion=" + verificacion.toString() +
                ", tipoPago=" + tipoPago +
                ", numeroNota='" + numeroNota + '\'' +
                ", cotizacion='" + cotizacion + '\'' +
                ", cuentaDeposito='" + cuentaDeposito + '\'' +
                ", numeroFactura='" + numeroFactura + '\'' +
                ", pagado=" + pagado +
                ", atiendeYCobra='" + atiendeYCobra + '\'' +
                ", fechaPedido=" + fechaPedido +
                '}';
    }
}
