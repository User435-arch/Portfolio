#!/bin/bash

if [ $# -ne 1 -o ! -e "$1" ]; then
   echo "4"; exit 1
fi

if [ ! -f "$1" -a ! -d "$1" ]; then
   echo "3"; exit 1
fi

if test -d "$1"; then      #Il est possible d'écrire "test" à la place des crochets
   echo "2"; exit 0
fi

if test -f "$1" -a ! -x "$1"; then
   echo "1" ; exit 0
fi

if test -f "$1" -a -x "$1" ; then
   echo "0" ; exit 0
fi
