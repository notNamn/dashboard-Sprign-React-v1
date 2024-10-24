import React from 'react';
import { jsPDF } from 'jspdf';

export const Factura = () => {
  const generarPDF = () => {
    const doc = new jsPDF();
    
    // Datos de la factura simulada
    const factura = {
      codigo:"9935454",
      cliente: "John Doe",
      direccion: "123 Calle Falsa, Ciudad",
      fecha: "2024-10-21",
      productos: [
        { descripcion: "Producto 1", cantidad: 2, precio: 50 },
        { descripcion: "Producto 2", cantidad: 1, precio: 30 },
        { descripcion: "Producto 3", cantidad: 3, precio: 20 },
      ],
      impuestos: 0.18
    };

    const calcularSubtotal = () => {
      return factura.productos.reduce((sum, producto) => sum + producto.cantidad * producto.precio, 0);
    };

    const subtotal = calcularSubtotal();
    const impuestos = subtotal * factura.impuestos;
    const total = subtotal + impuestos;

    // Estilo básico del PDF
    doc.setFontSize(18);
    doc.text("Factura de Compra", 20, 20);

    doc.setFontSize(12);
    doc.text(`Cliente: ${factura.cliente}`, 20, 30);
    doc.text(`Dirección: ${factura.direccion}`, 20, 40);
    doc.text(`Fecha: ${factura.fecha}`, 20, 50);

    // Detalles de productos
    doc.text("Detalles de los productos:", 20, 60);
    factura.productos.forEach((producto, index) => {
      const posicionY = 70 + index * 10;
      doc.text(`${producto.descripcion} - Cantidad: ${producto.cantidad} - Precio: $${producto.precio}`, 20, posicionY);
    });

    // Subtotal, impuestos y total
    doc.text(`Subtotal: $${subtotal.toFixed(2)}`, 20, 100);
    doc.text(`Impuestos (18%): $${impuestos.toFixed(2)}`, 20, 110);
    doc.text(`Total: $${total.toFixed(2)}`, 20, 120);

    // Descargar PDF
    const nombrePdf = `${factura.codigo}_factura.pdf`
    doc.save(nombrePdf);
  };

  return (
    <div>
      <h1>Generar Factura</h1>
      <button onClick={generarPDF}>Descargar Factura en PDF</button>
    </div>
  );
};

