-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-12-2023 a las 00:36:35
-- Versión del servidor: 10.4.25-MariaDB
-- Versión de PHP: 8.0.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gidissoft`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `coautoreslibro`
--

CREATE TABLE `coautoreslibro` (
  `idcoautoreslibro` bigint(20) NOT NULL,
  `idautor` bigint(20) NOT NULL,
  `idcoautor` bigint(20) NOT NULL,
  `idlibro` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `libro`
--

CREATE TABLE `libro` (
  `idlibro` bigint(20) NOT NULL,
  `titulo` varchar(100) COLLATE utf8_bin NOT NULL,
  `isbn` varchar(13) COLLATE utf8_bin NOT NULL,
  `anio` varchar(20) COLLATE utf8_bin NOT NULL,
  `mes` varchar(20) COLLATE utf8_bin NOT NULL,
  `disciplina` varchar(100) COLLATE utf8_bin NOT NULL,
  `editorial` varchar(50) COLLATE utf8_bin NOT NULL,
  `tipoeditorial` varchar(50) COLLATE utf8_bin NOT NULL,
  `mediodivulgacion` varchar(50) COLLATE utf8_bin NOT NULL,
  `lugarpublicacion` varchar(50) COLLATE utf8_bin NOT NULL,
  `documentoevidencia` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`documentoevidencia`)),
  `certificadocreditos` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `certificadoinstitucionavala` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `idrol` bigint(20) NOT NULL,
  `nombre` varchar(20) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `idusuario` bigint(20) NOT NULL,
  `primernombre` varchar(100) COLLATE utf8_bin NOT NULL,
  `segundonombre` varchar(100) COLLATE utf8_bin NOT NULL,
  `primerapellido` varchar(100) COLLATE utf8_bin NOT NULL,
  `segundoapellido` varchar(100) COLLATE utf8_bin NOT NULL,
  `documento` varchar(100) COLLATE utf8_bin NOT NULL,
  `telefono` varchar(100) COLLATE utf8_bin NOT NULL,
  `direccion` varchar(100) COLLATE utf8_bin NOT NULL,
  `username` varchar(100) COLLATE utf8_bin NOT NULL,
  `email` varchar(100) COLLATE utf8_bin NOT NULL,
  `clave` varchar(100) COLLATE utf8_bin NOT NULL,
  `enable` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuariolibro`
--

CREATE TABLE `usuariolibro` (
  `idusuariolibro` bigint(20) NOT NULL,
  `idusuario` bigint(20) NOT NULL,
  `idlibro` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuariorol`
--

CREATE TABLE `usuariorol` (
  `id` bigint(20) NOT NULL,
  `idusuario` bigint(20) NOT NULL,
  `idrol` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `coautoreslibro`
--
ALTER TABLE `coautoreslibro`
  ADD PRIMARY KEY (`idcoautoreslibro`),
  ADD KEY `idautor` (`idautor`),
  ADD KEY `idcoautor` (`idcoautor`),
  ADD KEY `idlibro` (`idlibro`);

--
-- Indices de la tabla `libro`
--
ALTER TABLE `libro`
  ADD PRIMARY KEY (`idlibro`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`idrol`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idusuario`) USING BTREE;

--
-- Indices de la tabla `usuariolibro`
--
ALTER TABLE `usuariolibro`
  ADD PRIMARY KEY (`idusuariolibro`),
  ADD KEY `idusuario` (`idusuario`),
  ADD KEY `idlibro` (`idlibro`);

--
-- Indices de la tabla `usuariorol`
--
ALTER TABLE `usuariorol`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idusuario` (`idusuario`),
  ADD KEY `idrol` (`idrol`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `coautoreslibro`
--
ALTER TABLE `coautoreslibro`
  MODIFY `idcoautoreslibro` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `libro`
--
ALTER TABLE `libro`
  MODIFY `idlibro` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `idrol` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idusuario` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuariolibro`
--
ALTER TABLE `usuariolibro`
  MODIFY `idusuariolibro` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuariorol`
--
ALTER TABLE `usuariorol`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `coautoreslibro`
--
ALTER TABLE `coautoreslibro`
  ADD CONSTRAINT `coautoreslibro_ibfk_1` FOREIGN KEY (`idautor`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `coautoreslibro_ibfk_2` FOREIGN KEY (`idcoautor`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `coautoreslibro_ibfk_3` FOREIGN KEY (`idlibro`) REFERENCES `libro` (`idlibro`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuariolibro`
--
ALTER TABLE `usuariolibro`
  ADD CONSTRAINT `usuariolibro_ibfk_1` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `usuariolibro_ibfk_2` FOREIGN KEY (`idlibro`) REFERENCES `libro` (`idlibro`) ON DELETE NO ACTION ON UPDATE NO ACTION;


CREATE INDEX usuariolibro_ibfk_2 ON rol (idrol);

--
-- Filtros para la tabla `usuariorol`
--
ALTER TABLE `usuariorol`
  ADD CONSTRAINT `usuariorol_ibfk_1` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `usuariorol_ibfk_2` FOREIGN KEY (`idrol`) REFERENCES `rol` (`idrol`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

SHOW CREATE TABLE usuariorol;