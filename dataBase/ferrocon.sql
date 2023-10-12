-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 12-10-2023 a las 17:57:03
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ferrocon`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cargo`
--

CREATE TABLE `cargo` (
  `IDCARGO` int(11) NOT NULL,
  `NOMBRE_CARGO` varchar(30) NOT NULL,
  `ESTADO` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cargo`
--

INSERT INTO `cargo` (`IDCARGO`, `NOMBRE_CARGO`, `ESTADO`) VALUES
(1, 'ADMINISTRADOR', b'1'),
(2, 'VENDEDOR', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria_producto`
--

CREATE TABLE `categoria_producto` (
  `ID_CATEGORIA` int(11) NOT NULL,
  `NOMBRE_CATEGORIA` varchar(50) NOT NULL,
  `DESCRIPCION_PRODUCTO` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `ID_CLIENTE` int(11) NOT NULL,
  `NOMBRE` varchar(30) NOT NULL,
  `APELLIDO` varchar(30) NOT NULL,
  `DIRECCION` varchar(100) NOT NULL,
  `TELEFONO` varchar(13) NOT NULL,
  `NIT` varchar(13) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_ventas`
--

CREATE TABLE `detalle_ventas` (
  `ID_DETALLE` int(11) NOT NULL,
  `CANTIDAD` int(11) NOT NULL,
  `PRECIO_UNITARIO` float(13,2) NOT NULL,
  `SUBTOTAL` float(13,2) NOT NULL,
  `ID_VENTA` int(11) DEFAULT NULL,
  `ID_PRODUCTO` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

CREATE TABLE `empleado` (
  `ID_EMPLEADO` int(11) NOT NULL,
  `NOMBRE` varchar(30) NOT NULL,
  `APELLIDO` varchar(30) NOT NULL,
  `TELEFONO` varchar(13) NOT NULL,
  `ID_USUARIO` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`ID_EMPLEADO`, `NOMBRE`, `APELLIDO`, `TELEFONO`, `ID_USUARIO`) VALUES
(1, 'Edgar', 'Rodas', '---', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `metodo_pago`
--

CREATE TABLE `metodo_pago` (
  `ID_METODO_PAGO` int(11) NOT NULL,
  `NOMBRE_METODO` varchar(30) NOT NULL,
  `DESCRIPCION_METODO` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `ID_PRODUCTO` int(11) NOT NULL,
  `CODIGO` varchar(13) NOT NULL,
  `NOMBRE_PRODUCTO` varchar(50) NOT NULL,
  `DESCRIPCION` varchar(100) NOT NULL,
  `PRECIO_UNITARIO` float(13,2) NOT NULL,
  `PRECIO_VENTA` float(13,2) NOT NULL,
  `STOCK` int(8) NOT NULL,
  `ENTRADAS` int(13) NOT NULL,
  `SALIDAS` int(13) NOT NULL,
  `PROVEEDOR` int(13) NOT NULL,
  `CATEGORIA` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `NIT` int(13) NOT NULL,
  `NOMBRE_CONTACTO` varchar(30) NOT NULL,
  `NOMBRE_EMPRESA` varchar(30) NOT NULL,
  `DIRECCION` varchar(100) NOT NULL,
  `TELEFONO_EMPRESA` varchar(13) NOT NULL,
  `TELEFONO_CONTACTO` varchar(13) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `IDUSUARIO` int(11) NOT NULL,
  `NOMBRE_USUARIO` varchar(30) NOT NULL,
  `PASSWORD` varchar(30) NOT NULL,
  `ESTADO` bit(1) NOT NULL,
  `ID_CARGO` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`IDUSUARIO`, `NOMBRE_USUARIO`, `PASSWORD`, `ESTADO`, `ID_CARGO`) VALUES
(1, 'Admin', '722435', b'1', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `ID_VENTA` int(11) NOT NULL,
  `FECHA` varchar(25) NOT NULL,
  `TOTAL` float(13,2) NOT NULL,
  `ID_METODO_PAGO` int(11) DEFAULT NULL,
  `ID_CLIENTE` int(11) DEFAULT NULL,
  `ID_EMPLEADO` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cargo`
--
ALTER TABLE `cargo`
  ADD PRIMARY KEY (`IDCARGO`);

--
-- Indices de la tabla `categoria_producto`
--
ALTER TABLE `categoria_producto`
  ADD PRIMARY KEY (`ID_CATEGORIA`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`ID_CLIENTE`);

--
-- Indices de la tabla `detalle_ventas`
--
ALTER TABLE `detalle_ventas`
  ADD PRIMARY KEY (`ID_DETALLE`),
  ADD KEY `fk_id_venta` (`ID_VENTA`),
  ADD KEY `fk_id_producto` (`ID_PRODUCTO`);

--
-- Indices de la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD PRIMARY KEY (`ID_EMPLEADO`),
  ADD KEY `fk_empleado_usuario` (`ID_USUARIO`);

--
-- Indices de la tabla `metodo_pago`
--
ALTER TABLE `metodo_pago`
  ADD PRIMARY KEY (`ID_METODO_PAGO`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`ID_PRODUCTO`),
  ADD KEY `fk_proveedor` (`PROVEEDOR`),
  ADD KEY `fk_categoria` (`CATEGORIA`);

--
-- Indices de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD PRIMARY KEY (`NIT`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`IDUSUARIO`),
  ADD KEY `fk_usuario_cargo` (`ID_CARGO`);

--
-- Indices de la tabla `venta`
--
ALTER TABLE `venta`
  ADD PRIMARY KEY (`ID_VENTA`),
  ADD KEY `fk_id_metodo_pago` (`ID_METODO_PAGO`),
  ADD KEY `fk_id_cliente` (`ID_CLIENTE`),
  ADD KEY `fk_id_empleado` (`ID_EMPLEADO`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cargo`
--
ALTER TABLE `cargo`
  MODIFY `IDCARGO` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `categoria_producto`
--
ALTER TABLE `categoria_producto`
  MODIFY `ID_CATEGORIA` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `ID_CLIENTE` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `detalle_ventas`
--
ALTER TABLE `detalle_ventas`
  MODIFY `ID_DETALLE` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `empleado`
--
ALTER TABLE `empleado`
  MODIFY `ID_EMPLEADO` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `metodo_pago`
--
ALTER TABLE `metodo_pago`
  MODIFY `ID_METODO_PAGO` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `ID_PRODUCTO` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `IDUSUARIO` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `venta`
--
ALTER TABLE `venta`
  MODIFY `ID_VENTA` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle_ventas`
--
ALTER TABLE `detalle_ventas`
  ADD CONSTRAINT `fk_id_producto` FOREIGN KEY (`ID_PRODUCTO`) REFERENCES `producto` (`ID_PRODUCTO`),
  ADD CONSTRAINT `fk_id_venta` FOREIGN KEY (`ID_VENTA`) REFERENCES `venta` (`ID_VENTA`);

--
-- Filtros para la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD CONSTRAINT `empleado_ibfk_1` FOREIGN KEY (`ID_USUARIO`) REFERENCES `usuario` (`IDUSUARIO`),
  ADD CONSTRAINT `fk_empleado_usuario` FOREIGN KEY (`ID_USUARIO`) REFERENCES `usuario` (`IDUSUARIO`);

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `fk_categoria` FOREIGN KEY (`CATEGORIA`) REFERENCES `categoria_producto` (`ID_CATEGORIA`),
  ADD CONSTRAINT `fk_proveedor` FOREIGN KEY (`PROVEEDOR`) REFERENCES `proveedor` (`NIT`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `fk_usuario_cargo` FOREIGN KEY (`ID_CARGO`) REFERENCES `cargo` (`IDCARGO`);

--
-- Filtros para la tabla `venta`
--
ALTER TABLE `venta`
  ADD CONSTRAINT `fk_id_cliente` FOREIGN KEY (`ID_CLIENTE`) REFERENCES `cliente` (`ID_CLIENTE`),
  ADD CONSTRAINT `fk_id_empleado` FOREIGN KEY (`ID_EMPLEADO`) REFERENCES `empleado` (`ID_EMPLEADO`),
  ADD CONSTRAINT `fk_id_metodo_pago` FOREIGN KEY (`ID_METODO_PAGO`) REFERENCES `metodo_pago` (`ID_METODO_PAGO`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
