-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Počítač: 127.0.0.1
-- Vytvořeno: Sob 11. led 2020, 08:24
-- Verze serveru: 10.4.8-MariaDB
-- Verze PHP: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databáze: `test`
--

-- --------------------------------------------------------

--
-- Struktura tabulky `klient`
--

CREATE TABLE `klient` (
  `ico` int(11) NOT NULL,
  `nazevFirmy` varchar(255) COLLATE utf16_czech_ci NOT NULL,
  `email` varchar(255) COLLATE utf16_czech_ci NOT NULL,
  `telefon` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_czech_ci;

--
-- Vypisuji data pro tabulku `klient`
--

INSERT INTO `klient` (`ico`, `nazevFirmy`, `email`, `telefon`) VALUES
(26168685, 'Seznam.cz, a.s.', 'seznam@seznam.cz', 234694430),
(27604977, 'Google Czech Republic, s.r.o.', 'Google@gmail.com', 735846879),
(45273600, 'Pražská teplárenská a.s.', 'ptas@ptas.cz', 266751111);

-- --------------------------------------------------------

--
-- Struktura tabulky `pruvodce`
--

CREATE TABLE `pruvodce` (
  `osobniCislo` int(11) NOT NULL,
  `jmeno` varchar(255) COLLATE utf16_czech_ci NOT NULL,
  `prijmeni` varchar(255) COLLATE utf16_czech_ci NOT NULL,
  `telefon` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_czech_ci;

--
-- Vypisuji data pro tabulku `pruvodce`
--

INSERT INTO `pruvodce` (`osobniCislo`, `jmeno`, `prijmeni`, `telefon`) VALUES
(2, 'Franta', 'Novák', 774885996),
(3, 'Eliška', 'Novotná', 608775442),
(123, 'Daniel', 'Mazaný', 668789412);

-- --------------------------------------------------------

--
-- Struktura tabulky `vylet`
--

CREATE TABLE `vylet` (
  `ID` int(11) NOT NULL,
  `nazevVyletu` varchar(255) COLLATE utf16_czech_ci NOT NULL,
  `popisVyletu` varchar(255) COLLATE utf16_czech_ci NOT NULL,
  `zacatek` date NOT NULL,
  `konec` date NOT NULL,
  `pruvodce` int(11) NOT NULL,
  `klient` int(11) NOT NULL,
  `jmenoPruvodce` varchar(255) COLLATE utf16_czech_ci NOT NULL,
  `nazevFirmy` varchar(255) COLLATE utf16_czech_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_czech_ci;

--
-- Vypisuji data pro tabulku `vylet`
--

INSERT INTO `vylet` (`ID`, `nazevVyletu`, `popisVyletu`, `zacatek`, `konec`, `pruvodce`, `klient`, `jmenoPruvodce`, `nazevFirmy`) VALUES
(2, 'Tůra po Krušných Horách', 'Pěší výlet skrze Krušné hory.\nSebou jídlo / pití na celé dva dny.\nStany budou zajištěny.', '2020-01-09', '2020-01-09', 2, 27604977, 'Franta Novák', 'Google Czech Republic, s.r.o.');

--
-- Klíče pro exportované tabulky
--

--
-- Klíče pro tabulku `klient`
--
ALTER TABLE `klient`
  ADD PRIMARY KEY (`ico`);

--
-- Klíče pro tabulku `pruvodce`
--
ALTER TABLE `pruvodce`
  ADD PRIMARY KEY (`osobniCislo`);

--
-- Klíče pro tabulku `vylet`
--
ALTER TABLE `vylet`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `PruvodceID` (`pruvodce`),
  ADD KEY `KlientID` (`klient`);

--
-- AUTO_INCREMENT pro tabulky
--

--
-- AUTO_INCREMENT pro tabulku `klient`
--
ALTER TABLE `klient`
  MODIFY `ico` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45273601;

--
-- AUTO_INCREMENT pro tabulku `pruvodce`
--
ALTER TABLE `pruvodce`
  MODIFY `osobniCislo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=124;

--
-- AUTO_INCREMENT pro tabulku `vylet`
--
ALTER TABLE `vylet`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Omezení pro exportované tabulky
--

--
-- Omezení pro tabulku `vylet`
--
ALTER TABLE `vylet`
  ADD CONSTRAINT `KlientID` FOREIGN KEY (`klient`) REFERENCES `klient` (`ico`),
  ADD CONSTRAINT `PruvodceID` FOREIGN KEY (`pruvodce`) REFERENCES `pruvodce` (`osobniCislo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
