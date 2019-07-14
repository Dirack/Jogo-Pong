#!/bin/bash
#
# Makefile
# 
# Objetivo: Compilação do jogo Pong. Projeto do curso de desenvolvimento de games da
# Danki code.
# 
# Site: http://www.dirackslounge.online
# 
# Versão 1.0
# 
# Programador: Rodolfo Dirack 14/07/2019
# 
# Email: rodolfo_profissional@hotmail.com
# 
# Licença: GPL-3.0 <https://www.gnu.org/licenses/gpl-3.0.txt>.

CC = javac
PAC = $(wildcard ./GamePong/*.java)
DEP = $(PAC:.java=.class)

all:	libbed run

libbed:	$(DEP)

run:	main.class
	java $<

%.class:	%.java
	$(CC) $<
